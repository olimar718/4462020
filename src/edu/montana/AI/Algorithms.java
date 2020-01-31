package edu.montana.AI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.lang.Thread;

public class Algorithms {
    public Algorithms() {

    }

    protected Map minConflict(Map map, String[] colors) {

        // initial assignement
        // randomAssignement(map);
        Boolean consistent = Boolean.FALSE;
        ArrayList<Connection> conflictingConnections = new ArrayList<>();
        while (!(consistent)) {
            for (Connection connection : map.connections) {
                if (!(connection.connectionCorrect())) {
                    conflictingConnections.add(connection);
                }

            }
            if (conflictingConnections.size() == 0) {
                consistent = Boolean.TRUE;
                break;
            }
            Connection conflictingConnection = conflictingConnections
                    .get((int) (Math.random() * conflictingConnections.size()));
            ArrayList<String> neighbourcolor = new ArrayList<>();
            double selectedregion = Math.random();
            if (selectedregion > 0.5) {
                neighbourcolor.add(conflictingConnection.connectedRegion1.color);// we know it to be our neighbour so we
                                                                                 // add it to the list

                for (Connection mapConnection : map.connections) {// going trought the list of connection to find the
                                                                  // one connected to the current region
                    if (mapConnection.connectedRegion1.regionId == conflictingConnection.connectedRegion2.regionId) {
                        neighbourcolor.add(mapConnection.connectedRegion2.color);
                    }
                    // change to make sure color is not added more than once
                    if (mapConnection.connectedRegion2.regionId == conflictingConnection.connectedRegion2.regionId) {
                        neighbourcolor.add(mapConnection.connectedRegion1.color);
                    }
                }

            } else {
                neighbourcolor.add(conflictingConnection.connectedRegion2.color);// we know it to be our neighbour so we
                                                                                 // add it to the list

                for (Connection mapConnection : map.connections) {// going trought the list of connection to find the
                                                                  // one connected to the current region
                    if (mapConnection.connectedRegion1.regionId == conflictingConnection.connectedRegion1.regionId) {
                        neighbourcolor.add(mapConnection.connectedRegion2.color);
                    }
                    // change to make sure color is not added more than once
                    if (mapConnection.connectedRegion2.regionId == conflictingConnection.connectedRegion1.regionId) {
                        neighbourcolor.add(mapConnection.connectedRegion1.color);
                    }
                }

            }
            // count the number of time a color is present in neighbourcolour
            int[] colorTally = new int[4];
            for (String color : neighbourcolor) {
                switch (color) {
                case "red":
                    colorTally[0]++;
                    break;
                case "green":
                    colorTally[1]++;
                    break;
                case "blue":
                    colorTally[2]++;
                    break;
                case "yellow":
                    colorTally[3]++;
                    break;
                default:
                    break;
                }
            }
            int index = 0;// index of the color the least present in the neightbour
            for (int i = 0; i < colorTally.length - 1; i++) {
                if (colorTally[i] < colorTally[index]) {
                    index = i;
                }
            }

            if (selectedregion > 0.5) {

            } else {

            }

        }

        return map;
    }

<<<<<<< HEAD
    protected Map simpleBacktracking(Map map) {
=======
    public Map simpleBacktracking(Map map) {
        int graph[][]= new int[map.mapSize][map.mapSize];
        graph = makeAdjacent(map, graph);
        int colors[] = new int[map.mapSize];
>>>>>>> 8fb746d2e57ddc0882eb909ec2e5e91d1af1cae4
        int numColors = 3;
        colors = graphColoring(graph,colors,numColors,0);
        System.out.println("Colors " + Arrays.toString(colors));
        new DrawingPanel(map, "simpleBacktracking");
        return map;
    }

    protected Map backtrackingForwardChecking(Map map) {

        return map;
    }

    protected Map simulated_annealing(Map map) {
        Random rand= new Random();
        Boolean reached_goal=Boolean.FALSE;
        randomAssignement(map);
        int step_count=0;
        int temperature=100;
        while(!(reached_goal)){
            step_count = step_count + 1 ;
            rand.setSeed(System.nanoTime());
            map.performance=map.goal();
            System.out.println(map.performance);
            new DrawingPanel(map, "simulated annealing");     
            ArrayList<Connection> incorrect_connection = new ArrayList<>();
            for (Connection connection : map.connections) {
                if(!(connection.connectionCorrect())){
                    incorrect_connection.add(connection);
                }
            }
            Connection c=incorrect_connection.get(rand.nextInt(incorrect_connection.size()));

            try {
                Thread.sleep(1000000000);
            } catch (Exception e) {
                //TODO: handle exception
            }
        }
        return map;
    }
    private int simulated_annealing_schedule(int step_count, double annealing_factor, int initial_temperature){
        

        return step_count;
    }

    protected Map genetic(Map map, int population_size, int tournament_size, int number_of_parents,
            int mutation_probability, int number_of_generation_limit) {

        Map[] population = new Map[population_size];
        Boolean reached_goal = Boolean.FALSE;
        Random rand = new Random();
        // generates the base population of population_size randomly
        for (int i = 0; i < population_size; i = i + 1) {
            population[i] = (Map) map.clone();// clone to have a separate instance of the map
            randomAssignement(population[i]);
        }
        // start the genetic algorithm
        int generation_count = 0;
        population[0].performance = population[0].goal();
        Map current_best = (Map) population[0].clone();
        while (!(reached_goal)) {
            generation_count = generation_count + 1;
            System.out.println("Currently computing generation " + generation_count);
            // evaluate all the population, keep the best individual, return if a solution
            // has been found
            Map best_of_generation = (Map) population[0].clone();
            for (Map individual : population) {
                individual.performance = individual.goal();
                if (individual.performance < current_best.performance) {
                    current_best = (Map) individual.clone();
                }
                if (individual.performance < best_of_generation.performance) {
                    best_of_generation = (Map) individual.clone();
                }
            }
            System.out.println("Current_best individual across all generation " + generation_count + " score "
                    + current_best.performance);
            System.out.println(
                    "Best individual of generation " + generation_count + " score " + best_of_generation.performance);
            if (current_best.performance == 0) {
                reached_goal = Boolean.TRUE;
                return current_best;
            }
            if (generation_count >= number_of_generation_limit) {
                System.err.println("Number of generation limit was reached, returning current best solution");
                return current_best;
            }
            Map[] parents = new Map[number_of_parents];
            for (int i = 0; i < number_of_parents; i = i + 1) {// selecting the parent via tournament_selection
                Map[] tournament_contestants = new Map[tournament_size];
                for (int j = 0; j < tournament_size; j = j + 1) {// selecting the contestant
                    tournament_contestants[j] = population[rand.nextInt(population_size - 1)];
                }
                // run the tournament
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
        return current_best;
    }

    private Map genetic_recombine(Map[] maps) {
        Random rand = new Random();
        Map recombined = (Map) maps[0].clone();
        for (Region recombined_region : recombined.regions) {
            String[] ordered_colors = new String[maps.length];
            int i = 0;
            for (Map map : maps) {
                for (Region region : map.regions) {
                    if (region.regionId == recombined_region.regionId) {
                        ordered_colors[i] = region.color;
                        i = i + 1;
                    }
                }
            }
            rand.setSeed(System.nanoTime());
            recombined_region.color = ordered_colors[rand.nextInt(ordered_colors.length)];
        }
        return recombined;
    }

    private Map genetic_mutate(Map map, int mutation_probability) {
        String[] colors = { "red", "green", "blue", "yellow" };
        Random rand = new Random();
        for (Region region : map.regions) {
            rand.setSeed(System.nanoTime());
            if (rand.nextInt(mutation_probability) == 0) {
                region.color = colors[rand.nextInt(4)];// TODO gaussian / normal distribution ?
            }
        }
        return map;
    }

    private void randomAssignement(Map map) {
        String[] colors = { "red", "green", "blue", "yellow" };
        Random rand = new Random();
        rand.setSeed(System.nanoTime());
        for (Region region : map.regions) {
            region.color = colors[rand.nextInt(4)];
        }
    }

    //create adjacency matrix for map based on connected regions
    //1 = regions are connected
    public int[][] makeAdjacent(Map map, int[][] graph) {
        for (Connection connection : map.connections) {
            int i = connection.connectedRegion1.regionId;
            int j = connection.connectedRegion2.regionId;
            graph[i][j] = 1;
            graph[j][i] = 1;
        }
        System.out.println("\n");
        return graph;
    }

    public int[] graphColoring(int[][] graph, int[] colors, int numColors, int i) {
        while(true){
            colors[i] = assignColor(i, colors, graph);
            if(colors[i] == 0){
                return colors;
            }
            if(i== graph.length){
                System.out.println(Arrays.toString(colors));
                return colors;
            }
            else{
                graphColoring(graph, colors, numColors, i+1);
            }
        }
    }

    public int assignColor(int i, int[] colors, int[][] graph) {
        while(true){
            int j;

            colors[i] = colors[i]+1;
            if(colors[i] == colors.length){
                return 0;
            }

            for(j = 0; j < colors.length-1; j++){
                if(graph[i][j] == 1 && colors[i] == colors[j] && i!=j){
                    break;
                }
            }
            if(j == colors.length){
                return colors[i];
            }
        }
    }

}
