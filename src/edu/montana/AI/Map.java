package edu.montana.AI;

import java.util.ArrayList;
import java.util.List;

public class Map {
    int mapSize;
    List<Connection> connections;
    List<Region> regions;

    public Map(int numberOfRegions) {
        this.mapSize = numberOfRegions;
        this.regions = new ArrayList<>();
        this.connections = new ArrayList<>();
    }

    public int goal(Map map) {
        return 0;
    }

    public Connection connectRegion(Region region1, Region region2) {
        return new Connection(Boolean.FALSE, region1, region2);
    }

    public void colorRegion(Region region, String[] possibleColors) {

        /** change how list works */

        //first we find the color of the neighbor to our region
        List<String> neighborcolors = new ArrayList<>();
        for (Connection mapConnections : this.connections) {//going trought the list of connection to find the one connected to the current region
            if (mapConnections.connectedRegion1.regionId == region.regionId) {
                neighborcolors.add(mapConnections.connectedRegion2.color);
            }
            //change to make sure color is not added more than once
            if (mapConnections.connectedRegion2.regionId == region.regionId) {
                neighborcolors.add(mapConnections.connectedRegion1.color);
            }
        }
        Boolean[] colorpresent = {Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE};//4 possible color so a boolean array of 4 elements
        for (String neighColor : neighborcolors) {//all the color of our neighbour
            int index = 0;//to keep track of the color
            for (String possibleColor : possibleColors) {//all the possible color

                if (neighColor.equals(possibleColor)) {//if they match then the color is present
                    colorpresent[index] = Boolean.TRUE;
                }
                index = index + 1;
            }
        }
        System.out.println(colorpresent[0].toString() + colorpresent[1].toString() + colorpresent[2].toString() + colorpresent[3].toString());//debug
        int index = 0;//to keep track of the color
        for (Boolean ispresent : colorpresent) {
            if (ispresent.equals(Boolean.FALSE)) {
                System.out.println(region.color);//debug
                region.color = possibleColors[index];//this color is available so we use it.
                System.out.println(region.color);//debug
                break;
            }
            index = index + 1;
        }

    }
}
