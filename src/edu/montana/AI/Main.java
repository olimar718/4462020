package edu.montana.AI;

import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int regionIndex = 0;
        int numberOfRegions = 10;// Shoud be defined by argument (String[] args)
        int size = 500;// Shoud be defined by argument (String[] args)
        Map map = new Map(numberOfRegions);

        // Scatter the regions, empty color
        Random rand = new Random();
        for (int i = 0; i < numberOfRegions; i = i + 1) {
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

        new DrawingPanel((Map) map.clone(), "problem");
        Algorithms algo = new Algorithms();

        //simplebacktracking
        // Map solution = algo.simpleBacktracking(map);
        
        
        
        
        

        // simulated_annealing
        //Map solution = algo.simulated_annealing(map, 100, 0.5, 0);// simulated_annealing(Map map, int
                                                                  // initial_temperature,
                                                                  // short annealing_factor, double four_color_penality)
        
        
        // genetic

        int population_size = 20;
        long tournament_size = Math.round(population_size * 0.25);
        long number_of_parents = Math.round(tournament_size * 0.25);
        int inverse_mutation_probability = numberOfRegions;
        int number_of_generation_limit=1000;
        double four_color_penality = 0;

        Map solution = algo.genetic(map, population_size,(int)tournament_size,(int)number_of_parents,inverse_mutation_probability,number_of_generation_limit,four_color_penality);
        //four color penalty should be set to 0 for larges map if you want a correct

        new DrawingPanel(solution, "solution");

    }
}
