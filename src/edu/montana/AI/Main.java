package edu.montana.AI;

import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int regionIndex = 0;
<<<<<<< HEAD
        int numberOfRegions = 6;// Shoud be defined by argument (String[] args)
=======
        int numberOfRegions = 10;// Shoud be defined by argument (String[] args)
>>>>>>> 2ed74ade8a4aadbc14c5e576355e7c63821b5bcf
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
<<<<<<< HEAD
        Map test = algo.simpleBacktracking(map);
        Map solution = algo.backtrackingForwardChecking(map);
        // algo.randomAssignement(map);
        // new DrawingPanel(map);
        // Map solution = algo.genetic(map, 10, 5,2,20);//algo.genetic(map,
        // population_size, tournament_size, number_of_parents, mutation_probability);
        // simple backtracking
        // algo.simpleBacktracking(map);
=======

        //simplebacktracking
        // Map solution = algo.simpleBacktracking(map);
        
        
        
        
        
>>>>>>> 2ed74ade8a4aadbc14c5e576355e7c63821b5bcf

        // simulated_annealing
        //Map solution = algo.simulated_annealing(map, 100, 0.5, 0);// simulated_annealing(Map map, int
                                                                  // initial_temperature,
                                                                  // short annealing_factor, double four_color_penality)
        
        
        // genetic
<<<<<<< HEAD
        // Map solution = algo.genetic(map, 20, 10,2,50,50);//algo.genetic(map,
        // population_size, tournament_size, number_of_parents, 1/mutation_probability,
        // number_of_generation_limit);
        
        new DrawingPanel((Map) solution.clone(), "solution");
=======

        int population_size = 20;
        long tournament_size = Math.round(population_size * 0.25);
        long number_of_parents = Math.round(tournament_size * 0.25);
        int inverse_mutation_probability = numberOfRegions;
        int number_of_generation_limit=1000;
        double four_color_penality = 0;

        Map solution = algo.genetic(map, population_size,(int)tournament_size,(int)number_of_parents,inverse_mutation_probability,number_of_generation_limit,four_color_penality);
        //four color penalty should be set to 0 for larges map if you want a correct

        new DrawingPanel(solution, "solution");
>>>>>>> 2ed74ade8a4aadbc14c5e576355e7c63821b5bcf

    }
}
