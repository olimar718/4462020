package edu.montana.AI;

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
        int  impossible_to_connect = 0;
        System.out.println("Mapsize"+map.mapSize);
        while(impossible_to_connect < map.mapSize){
            for (Region region : map.regions) {
                System.out.println(region.x + " " + region.y);//debug
                System.out.flush();
                
                Region closest = region.findClosest(map.regions, map);//find the closest region
                if(closest==region){
                    impossible_to_connect++;
                    System.out.println("impossible "+impossible_to_connect);
                    continue;
                }
                map.connections.add(map.connectRegion(region, closest));
                impossible_to_connect++;
            }
        }
        Algorithms algo=new Algorithms();
        algo.randomAssignement(map);
        for (Connection connection : map.connections) {
            System.out.println(connection);
            
        }
        DrawingPanel draw = new DrawingPanel(map);
        //Start to color the regions properly

    }
}
