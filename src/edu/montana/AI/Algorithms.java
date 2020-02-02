package edu.montana.AI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.lang.Thread;

public class Algorithms {
    String[] colors = { "red", "green", "blue", "yellow" };
    int colors1[];
    int btMetric = 0;
    int fcMetric = 0;
    int arcMetric = 0;

    public Algorithms() {

    }


    protected Map simpleBacktracking(Map map) {
        btMetric = 0;
        int graph[][] = new int[map.mapSize][map.mapSize];
        graph = makeAdjacent(map, graph);
        colors1 = new int[map.mapSize];
        int numColors = 3;
        if (!(graphColoring(graph, colors1, numColors, 0))) {
            System.out.println("No 3 coloring");
            numColors = 4;
            graphColoring(graph, colors1, numColors, 0);
        }
        map = colorCorrection(graph, map);
        System.out.println("Backtracking Metric: " + btMetric);
        return map;
    }

    protected Map backtrackingForwardChecking(Map map) {
        fcMetric = 0;
        int graph[][] = new int[map.mapSize][map.mapSize];
        int colorOptions3[][] = new int[map.mapSize][3];
        colorOptions3 = assignOptions(colorOptions3);
        int colorOptions4[][] = new int[map.mapSize][4];
        colorOptions4 = assignOptions(colorOptions4);
        graph = makeAdjacent(map, graph);
        colors1 = new int[map.mapSize];
        int numColors = 3;
        if (!(graphColoringForwardCheck(graph, colors1, numColors, 0, colorOptions3))) {
            System.out.println("No 3 coloring");
            numColors = 4;
            graphColoringForwardCheck(graph, colors1, numColors, 0, colorOptions4);
        }
        map = colorCorrection(graph, map);
        System.out.println("Forward Checking Metric: " + fcMetric);
        return map;
    }

    protected Map backtrackingArc(Map map) {
        arcMetric = 0;
        int graph[][] = new int[map.mapSize][map.mapSize];
        int colorOptions3[][] = new int[map.mapSize][3];
        colorOptions3 = assignOptions(colorOptions3);
        int colorOptions4[][] = new int[map.mapSize][4];
        colorOptions4 = assignOptions(colorOptions4);
        graph = makeAdjacent(map, graph);
        colors1 = new int[map.mapSize];
        int numColors = 3;
        if (!(graphColoringArc(graph, colors1, numColors, 0, colorOptions3))) {
            System.out.println("No 3 coloring");
            numColors = 4;
            graphColoringArc(graph, colors1, numColors, 0, colorOptions4);
        }
        map = colorCorrection(graph, map);
        System.out.println("Arc Metric: " + arcMetric);
        return map;
    }

    protected Map simulated_annealing(Map map, int initial_temperature, double annealing_factor,
            double four_color_penality) {
        Random rand = new Random();
        Boolean reached_goal = Boolean.FALSE;
        randomAssignement(map);

        int step_count = 0;
        double temperature = initial_temperature;
        map.performance = map.goal(four_color_penality);
        if (map.performance == 0) {
            reached_goal = Boolean.TRUE;
        }
        while (!(reached_goal) && temperature > 0.1) {
            temperature = simulated_annealing_schedule(step_count, annealing_factor, initial_temperature);
            System.out.println("Current temperature : " + temperature);
            step_count = step_count + 1;
            rand.setSeed(System.nanoTime());
            // System.out.println("performance " + map.performance);
            ArrayList<Connection> incorrect_connection = new ArrayList<>();
            for (Connection connection : map.connections) {
                if (!(connection.connectionCorrect())) {
                    incorrect_connection.add(connection);
                }
            }
            Connection selected_connection;
            if (!(incorrect_connection.size() == 0)) {
                selected_connection = incorrect_connection.get(rand.nextInt(incorrect_connection.size()));
            } else {// if the assignement is consistent we select a valid connection to minimize the
                    // number of color
                selected_connection = map.connections.get(rand.nextInt(map.connections.size()));
            }

            Map old_state = (Map) map.clone();
            String possible_colors[] = new String[3];// only 3 other possible color
            Map neighbour_states[] = new Map[3];
            int neighbour_states_index = 0;
            if (rand.nextBoolean()) {// every other time we take etheir the first region or the second

                possible_colors = absent_color(selected_connection.connectedRegion1);
                for (String possible_color : possible_colors) {
                    selected_connection.connectedRegion1.color = possible_color;
                    map.performance = map.goal(four_color_penality);
                    neighbour_states[neighbour_states_index] = (Map) map.clone();
                    neighbour_states_index = neighbour_states_index + 1;
                }

            } else {
                possible_colors = absent_color(selected_connection.connectedRegion2);
                for (String possible_color : possible_colors) {
                    selected_connection.connectedRegion2.color = possible_color;
                    map.performance = map.goal(four_color_penality);
                    neighbour_states[neighbour_states_index] = (Map) map.clone();
                    neighbour_states_index = neighbour_states_index + 1;
                }

            }
            Map new_state = (Map) neighbour_states[0].clone();
            for (Map neighbour : neighbour_states) {
                if (new_state.performance > neighbour.performance) {
                    new_state = neighbour;
                }
            }
            if (new_state.performance > old_state.performance) {
                // System.out.println("previous state better");
                double probability = Math.pow(((Double) Math.E), (-(new_state.performance / temperature)));// Boltzman
                                                                                                           // distribution
                                                                                                           // using the
                                                                                                           // temperature
                System.out.println(probability);
                if (Math.random() > probability) {
                    System.out.println("accepted worst state");
                    map = (Map) new_state.clone();
                } else {
                    map = (Map) old_state.clone();
                }
            } else {
                map = (Map) new_state.clone();
            }

            System.out.println("performance " + map.performance);
            if (map.performance == 0) {
                reached_goal = Boolean.TRUE;
                break;
            }

        }
        System.out.println("Metric, number of step :  " + step_count);
        System.err.println(map.mapSize+","+step_count+","+temperature+","+map.performance);
        return map;
    }

    private double simulated_annealing_schedule(int step_count, double annealing_factor, int initial_temperature) {
        return ((initial_temperature * annealing_factor) / (annealing_factor + step_count));
    }

    protected Map genetic(Map map, int population_size, int tournament_size, int number_of_parents,
            int mutation_probability, int number_of_generation_limit, double four_color_penality) {// Implementation of
                                                                                                   // the genetic
                                                                                                   // algorithms, with
                                                                                                   // generational
                                                                                                   // replacement, fixed
                                                                                                   // population size
                                                                                                   // and tournament
                                                                                                   // selection.
                                                                                                   // Implements a
                                                                                                   // tentative three
                                                                                                   // coloring, if the
                                                                                                   // map uses four
                                                                                                   // colors, it gets a
                                                                                                   // penality

        Map[] population = new Map[population_size];
        Boolean reached_goal = Boolean.FALSE;
        Random rand = new Random();
        // generates the base population of population_size randomly
        for (int i = 0; i < population_size; i = i + 1) {
            population[i] = (Map) map.clone();// clone to have a separate instance of the map, or else every member of
                                              // the population will be overwriting the same map
            randomAssignement(population[i]);
        }

        // start the genetic algorithm
        int generation_count = 0;// count the number of generation, is used to stop the algorithm after the
                                 // maximum has been reached
        population[0].performance = population[0].goal(four_color_penality);
        Map current_best = (Map) population[0].clone();// taking the first member at the start of the algorithm. This
                                                       // "variable" alway holds the best individual of all generation
                                                       // seen so far, so that it can be returned if the maximum number
                                                       // of generation is reached
        while (!(reached_goal)) {// main loop of the method.
            generation_count = generation_count + 1;
            System.out.println("Currently computing generation " + generation_count);
            Map best_of_generation = (Map) population[0].clone();// this variable holds the best individual of the
                                                                 // current generation.
            for (Map individual : population) {// finds the best of the generation and if applicable the best of all
                                               // generation.
                individual.performance = individual.goal(four_color_penality);
                if (individual.performance < current_best.performance) {
                    current_best = (Map) individual.clone();
                }
                if (individual.performance < best_of_generation.performance) {
                    best_of_generation = (Map) individual.clone();
                }
            }
            System.out.println("Current_best individual across all generation " + " score " + current_best.performance);
            System.out.println(
                    "Best individual of generation " + generation_count + " score " + best_of_generation.performance);
            if (current_best.performance == 0) {
                reached_goal = Boolean.TRUE;
                System.out.println("Found consistent solution, returning");
                System.out.println("Metric, number of genereation :  " + generation_count);
                System.err.println(map.mapSize+","+generation_count+","+"1,"+current_best.performance);//print to standart error for plot
                return current_best;
            }
            if (generation_count >= number_of_generation_limit) {
                System.out.println("Number of generation limit was reached, returning current best solution");
                System.out.println("Metric, number of genereation :  " + generation_count);
                System.err.println(map.mapSize+","+generation_count+","+"0,"+current_best.performance);//print to standart error for plot
                return current_best;
            }
            Map[] parents = new Map[number_of_parents];
            for (int i = 0; i < number_of_parents; i = i + 1) {// selecting the parent via tournament_selection
                Map[] tournament_contestants = new Map[tournament_size];
                for (int j = 0; j < tournament_size; j = j + 1) {// selecting the contestant randomly within the
                                                                 // population
                    tournament_contestants[j] = population[rand.nextInt(population_size - 1)];
                }
                // runs the tournament
                Map winner = tournament_contestants[0];
                for (Map tournament_contestant : tournament_contestants) {
                    if (tournament_contestant.performance < winner.performance) {
                        winner = tournament_contestant;
                    }
                }
                // save the winner as a parent
                parents[i] = (Map) winner.clone();
            }
            // recombine (crossover)
            for (int i = 0; i < population_size; i = i + 1) {
                population[i] = (Map) genetic_recombine(parents).clone();
            }
            // mutate
            for (Map child : population) {
                genetic_mutate(child, mutation_probability);
            }
        }
        System.out.println("Metric, number of genereation :  " + generation_count);
        
        return current_best;
    }

    private Map genetic_recombine(Map[] maps) {// this method implement uniform crossover
        Random rand = new Random();
        Map recombined = (Map) maps[0].clone();
        for (Region recombined_region : recombined.regions) {// goes trough all the region of the recombined child to
                                                             // color them randomly
            String[] parent_colors = new String[maps.length];
            int i = 0;
            for (Map map : maps) {// finds the color of the parents region corresponding to the currently being
                                  // recombined region
                for (Region region : map.regions) {
                    if (region.regionId == recombined_region.regionId) {
                        parent_colors[i] = region.color;
                        i = i + 1;
                        break;
                    }
                }
            }
            rand.setSeed(System.nanoTime());
            recombined_region.color = parent_colors[rand.nextInt(parent_colors.length)];// select a color randomly
                                                                                        // within the parents.
        }
        return recombined;
    }

    private Map genetic_mutate(Map map, int mutation_probability) {// this method mutate the child.
        Random rand = new Random();
        for (Region region : map.regions) {// goes trough all the regions of the child
            rand.setSeed(System.nanoTime());
            if (rand.nextInt(mutation_probability) == 0) {// mutate with the probability. Only color that were not the
                                                          // region's color can be selected.
                String[] possible_colors = absent_color(region);
                region.color = possible_colors[rand.nextInt(3)];
            }
        }
        return map;
    }

    private String[] absent_color(Region region) {// return all possible color that are not the one of the region
        String[] absent_color = new String[3];
        int absent_color_index = 0;
        for (String color : colors) {
            if (!(region.color.equals(color))) {// matching colors that are not present
                absent_color[absent_color_index] = color;
                absent_color_index = absent_color_index + 1;
            }

        }
        return absent_color;
    }

    private void randomAssignement(Map map) {// color every region randomly.
        Random rand = new Random();
        rand.setSeed(System.nanoTime());
        for (Region region : map.regions) {
            region.color = colors[rand.nextInt(4)];
        }
    }

    // create adjacency matrix for map based on connected regions
    // 1 = regions are connected
    public int[][] makeAdjacent(Map map, int[][] graph) {
        for (Connection connection : map.connections) {
            int i = connection.connectedRegion1.regionId;
            int j = connection.connectedRegion2.regionId;
            graph[i][j] = 1;
            graph[j][i] = 1;
        }
        return graph;
    }

    public int[][] assignOptions(int[][] colorOptions) {
        int i;
        for (i = 0; i < colorOptions.length; i++) {
            for (int j = 0; j < colorOptions[0].length; j++) {
                colorOptions[i][j] = j + 1;
            }
        }
        return colorOptions;
    }

    public boolean graphColoring(int[][] graph, int[] colors1, int numColors, int current) {

        if (current == colors1.length) {
            return true;
        }

        for (int currentColor = 1; currentColor <= numColors; currentColor++) {
            if (assignColor(current, colors1, graph, currentColor, numColors)) {
                colors1[current] = currentColor;
                // System.out.println("Current Color: " + currentColor);
                if (graphColoring(graph, colors1, numColors, current + 1)) {
                    btMetric++;
                    return true;
                }
                colors1[current] = 0;
            }
        }
        btMetric++;
        return false;
    }

    public int[][] clone2dArray(int[][] input){
        int[][] temp = new int[input.length][input[0].length];
        for(int i =0; i < input.length; i++){
            for(int j=0; j < input[0].length;j++){
                temp[i][j] = input[i][j];
            }
        }

        return temp;
    }

    public boolean graphColoringForwardCheck(int[][] graph, int[] colors1, int numColors, int current, int[][] colorOptions) {
        if (current == colors1.length) {
            return true;
        }

        int[][] temp = new int[colorOptions.length][colorOptions[0].length];
        temp = clone2dArray(colorOptions);
        for (int currentColor = 1; currentColor <= numColors; currentColor++) {
            if (assignColor(current, colors1, graph, currentColor, numColors)) {
                colorOptions = forwardCheck(colorOptions, currentColor, current, graph);
                colors1[current] = currentColor;                
                //System.out.println("Current Region: " + current + " Current Color: " + currentColor + " Options: " + Arrays.deepToString(colorOptions));
                if (colorCheck(colorOptions) && graphColoringForwardCheck(graph, colors1, numColors, current + 1, colorOptions)) {
                    fcMetric++;
                    return true;
                }
                colorOptions = clone2dArray(temp);
                colors1[current] = 0;
            }
        }
        fcMetric++;
        return false;
    }

    public boolean graphColoringArc(int[][] graph, int[] colors1, int numColors, int current, int[][] colorOptions) {
        if (current == colors1.length) {
            return true;
        }

        int[][] temp = new int[colorOptions.length][colorOptions[0].length];
        temp = clone2dArray(colorOptions);
        for (int currentColor = 1; currentColor <= numColors; currentColor++) {
            if (assignColor(current, colors1, graph, currentColor, numColors)) {
                colorOptions = forwardCheck(colorOptions, currentColor, current, graph);
                colorOptions = arcCheck(colorOptions, currentColor, current, graph);
                colors1[current] = currentColor;                
                //System.out.println("Current Region: " + current + " Current Color: " + currentColor + " Options: " + Arrays.deepToString(colorOptions));
                if (colorCheck(colorOptions) && graphColoringArc(graph, colors1, numColors, current + 1, colorOptions)) {
                    arcMetric++;
                    return true;
                }
                colorOptions = clone2dArray(temp);
                colors1[current] = 0;
            }
        }
        arcMetric++;
        return false;
    }

    public int[][] forwardCheck(int[][] colorOptions, int currentColor, int current, int[][] graph) {
        // what isn't currentColor in colorentries = 0
        for(int i = 0; i < colorOptions[0].length; i++){
            if(currentColor != colorOptions[current][i]){
                colorOptions[current][i] = 0;
            }
            else{
                colorOptions[current][i] = currentColor;
            }
        }
        for(int j =0; j < graph.length; j++){
            //if on graph the value is 1 on the current row, set the corresponding value to 0 on the colorOptions
            if (graph[current][j] == 1) {
                colorOptions[j][currentColor-1] = 0;
            }
        }

        return colorOptions;
    }

    public int[][] arcCheck(int[][] colorOptions, int currentColor, int current, int[][] graph) {
        //colorOptions[i] = graph[i];
        int tally = 0;
        for(int i = 0; i < colorOptions.length;i++){
            tally = 0;
            for(int j = 0; j < colorOptions[0].length;j++){
                if(colorOptions[i][j] == 0){
                    tally++;
                }
                else{
                    int temp = colorOptions[i][j];
                }
            }
            if(tally == colorOptions[0].length-1){
                //check consistency with surrounding regions
                for(int k =0; k < graph.length;k++){
                    for(int j = 0; j < graph[0].length;j++){
                        if(graph[i][j] == 1){

                        }
                    }
                }
            }
        }        
        return colorOptions;
    }

    public boolean colorCheck(int[][] colorOptions){
        int tally = 0;
        for(int i = 0; i < colorOptions.length;i++){
            tally = 0;
            for(int j = 0; j < colorOptions[0].length;j++){
                if(colorOptions[i][j] == 0){
                    tally++;
                }
            }
            if(tally == colorOptions[0].length){
                // System.out.println("tally reached" + tally);
                return false;
            }
        }        
        return true;
    }

    public boolean assignColor(int current, int[] colors1, int[][] graph, int currentColor, int numColors) {
        for (int j = 0; j < graph.length; j++) {
            if (graph[current][j] == 1 && currentColor == colors1[j]) {
                return false;
            }
        }
        return true;
    }

    public Map colorCorrection(int[][] graph, Map map) {
        for (int i = 0; i < colors1.length; i++) {
            switch (colors1[i]) {
            case 1:
                map.regions[i].color = "red";
                break;
            case 2:
                map.regions[i].color = "green";
                break;
            case 3:
                map.regions[i].color = "blue";
                break;
            case 4:
                map.regions[i].color = "yellow";
                break;
            default:
                break;
            }
        }
        return map;
    }
}
