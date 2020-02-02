package edu.montana.AI;

import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int numberOfRegions = 10;// Shoud be defined by argument (String[] args)
        int size = 500;// Shoud be defined by argument (String[] args)
        Map map = new Map(numberOfRegions);
        map = map_generate(map, size); // Scatter the regions, connect them, no color

        //new DrawingPanel((Map) map.clone(), "problem");
        Algorithms algo = new Algorithms();

        // Algorithm call

        // simplebacktracking
        // Map test = algo.simpleBacktracking(map);

        // new DrawingPanel((Map)test.clone(), "backtrack");

        // Map solution = algo.backtrackingForwardChecking(map);

        // simulated_annealing
        // Map solution = algo.simulated_annealing(map, 100, 0.5, 0);//
        // simulated_annealing(Map map, int
        // initial_temperature,
        // short annealing_factor, double four_color_penality)

        // genetic

        int population_size = 20;
        long tournament_size = Math.round(population_size * 0.25);
        long number_of_parents = Math.round(tournament_size * 0.25);
        int inverse_mutation_probability = numberOfRegions;
        int number_of_generation_limit = 200;
        double four_color_penality = 0;
        Map solution = map;
        while (numberOfRegions < 100) {

            map = new Map(numberOfRegions);
            map = map_generate(map, size);
            solution = algo.genetic(map, population_size, (int) tournament_size, (int) number_of_parents, inverse_mutation_probability, number_of_generation_limit, four_color_penality);

            numberOfRegions = numberOfRegions + 10;
        }
        //new DrawingPanel(map, "solution");


    }

    private static Map map_generate(Map map, int size) {
        int regionIndex = 0;
        Random rand = new Random();
        for (int i = 0; i < map.mapSize; i = i + 1) {
            map.regions[i] = new Region("", rand.nextInt(size), rand.nextInt(size), regionIndex);
            regionIndex = regionIndex + 1;

        }
        System.out.println("Begining program, generated Regions...");
        System.out.println("Mapsize " + map.mapSize);
        ArrayList<Region> fully_Connected_Region = new ArrayList<>();
        while (fully_Connected_Region.size() < map.mapSize) {
            for (Region region : map.regions) {
                if (fully_Connected_Region.contains(region)) {
                    continue;
                }
                // System.out.println(region.x + " " + region.y+ " "+ region.regionId);//debug

                Region closest = region.findClosest_connectable(map);// find the closest region
                if (closest == region) {
                    fully_Connected_Region.add(region);// if no more connection are possible, add it to the list.
                    continue;
                }
                map.connections.add(map.connectRegion(region, closest));
            }
        }
        return map;
    }

}
