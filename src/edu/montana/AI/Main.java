package edu.montana.AI;

import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        
        int regionIndex = 0;
        int numberOfRegions = 100;//Shoud be defined by argument (String[] args)
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
                
                Region closest = region.findClosest(map);//find the closest region
                if(closest==region){
                    fully_Connected_Region.add(region);
                    continue;
                }
                map.connections.add(map.connectRegion(region, closest));
            }
        }
        Algorithms algo=new Algorithms();
        algo.randomAssignement(map);
        // for (Connection connection : map.connections) {
        //     System.out.println(connection);     
        // }
        DrawingPanel draw = new DrawingPanel(map);
        //Start to color the regions properly

    }
}
