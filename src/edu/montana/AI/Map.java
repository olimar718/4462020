package edu.montana.AI;

import java.util.ArrayList;
import java.util.List;

public class Map {
    int mapSize;
    ArrayList<Connection> connections;
    Region[] regions;

    public Map(int numberOfRegions) {

        this.mapSize = numberOfRegions;
        this.regions = new Region[this.mapSize];
        this.connections = new ArrayList<>();
    }
    public Map(int mapSize, Region[] regions, ArrayList<Connection> connections){//clone constructor
        this.mapSize=mapSize;
        this.regions=regions;
        this.connections=connections;
    }

    public int goal() {
        int number_of_incorrect=0;
        for (Connection connection : this.connections) {
            if(!(connection.connectionCorrect())){
                number_of_incorrect = number_of_incorrect + 1;
            }
        }
        return number_of_incorrect;
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
    @Override
    public Object clone(){
        Map map = null;
        try {
            map = (Map) super.clone();
        } catch (Exception e) {
            ArrayList<Connection> new_map_connection = new ArrayList<>();
            Region[] new_map_regions = new Region[this.mapSize];
            for (Connection connection : this.connections) {
                new_map_connection.add((Connection) connection.clone());
            }
            for(int i = 0; i < mapSize; i = i + 1){
                new_map_regions[i]=(Region) this.regions[i].clone();
                //System.out.println(this.regions[i].clone().getClass());
            }
            // workaround to reattach regions to connection after the clone
            map = new Map(this.mapSize, new_map_regions, new_map_connection );
            for (Connection connection : map.connections) {
                for (Region region : map.regions) {
                    if (region.regionId == connection.connectedRegion1.regionId) {
                        connection.connectedRegion1 = region;
                    }
                    if (region.regionId == connection.connectedRegion2.regionId) {
                        connection.connectedRegion2 = region;
                    }
                }

            }
        }
        return map;
    }
}
