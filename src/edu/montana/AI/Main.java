package edu.montana.AI;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int regionIndex = 0;
        int numberOfRegions = 10;//Shoud be defined by argument (String[] args)
        String[] colors = {"red", "green", "blue", "yellow"};
        Map map = new Map(numberOfRegions);

        //Scatter the regions and color them randomly
        Random rand = new Random();
        for (int i = 0; i < numberOfRegions; i = i + 1) {
            map.regions.add(new Region(colors[rand.nextInt(4)], rand.nextInt(100), rand.nextInt(100), regionIndex));
            regionIndex = regionIndex + 1;
        }
        //Connect the closest region, checking if the connections cross another (not implemented yet)
        for (Region region : map.regions) {
            System.out.println(region.x + " " + region.y);
            System.out.flush();
            Region closest = region.findClosest(map.regions);//find the closest region
            map.connections.add(map.connectRegion(region, closest));
        }

        //Start to color the regions properly
        while (Boolean.TRUE) {
            break;

        }
    }
}
