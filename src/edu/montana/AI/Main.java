package edu.montana.AI;

import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        
        int regionIndex = 0;
        int numberOfRegions = 50;//Shoud be defined by argument (String[] args)
        int size = 500;//Shoud be defined by argument (String[] args)
        Map map = new Map(numberOfRegions);

        //Scatter the regions and color them randomly
        Random rand = new Random();
        for (int i = 0; i < numberOfRegions; i = i + 1) {
            map.regions[i]=new Region("", rand.nextInt(size), rand.nextInt(size), regionIndex);
            regionIndex = regionIndex + 1;

        }
        //Connect the closest region, checking if the connections cross another (not implemented yet)
        System.out.println("Mapsize "+map.mapSize);
        ArrayList<Region> fully_Connected_Region= new ArrayList<>();
        while(fully_Connected_Region.size()< map.mapSize){
            for (Region region : map.regions) {
                if (fully_Connected_Region.contains(region)){
                    continue;
                }
                //System.out.println(region.x + " " + region.y+ " "+ region.regionId);//debug
                
                Region closest = region.findClosest_connectable(map);//find the closest region
                if(closest==region){
                    fully_Connected_Region.add(region);
                    continue;
                }
                map.connections.add(map.connectRegion(region, closest));
            }
        }

        new DrawingPanel((Map)map.clone(), "problem");
        Algorithms algo=new Algorithms();
        Map backtrackingSolution = algo.simpleBacktracking(map);
        new DrawingPanel(backtrackingSolution, "Backtracking");
        //algo.randomAssignement(map);
        //new DrawingPanel(map);
        // Map solution = algo.genetic(map, 10, 5,2,20);//algo.genetic(map, population_size, tournament_size, number_of_parents, mutation_probability);
        // new DrawingPanel(solution, "solution");

        //simple backtracking
        // algo.simpleBacktracking(map);
        
        //simulated_annealing
        // Map solution=algo.simulated_annealing(map, 100, 0.9);//simulated_annealing(Map map, int initial_temperature, short annealing_factor)
        // new DrawingPanel(solution, "solution");
        //genetic
        // Map solution = algo.genetic(map, 100, 50,2,50,50);//algo.genetic(map, population_size, tournament_size, number_of_parents, 1/mutation_probability, number_of_generation_limit);
        // new DrawingPanel(solution, "solution");

    }
}
