package edu.montana.AI;

import java.util.ArrayList;
import java.util.List;

public class Map {
    int mapSize;
    ArrayList<Connection> connections;
    Region[] regions;
    int performance;

    protected Map(int numberOfRegions) {// initial constructor

        this.mapSize = numberOfRegions;
        this.regions = new Region[this.mapSize];
        this.connections = new ArrayList<>();
    }

    protected Map(int mapSize, Region[] regions, ArrayList<Connection> connections, int performance) {// this
                                                                                                      // constructor is
                                                                                                      // used by the
                                                                                                      // clone() method
        this.mapSize = mapSize;
        this.regions = regions;
        this.connections = connections;
        this.performance = performance;
    }

    protected int goal(double four_color_penality) {// This a performance and goal function. It returns 0 if the variable
        // assignement is a complete and consistent solution. Otherwise, it returns the
        // number of constraint violation. If a map use four colors it gets an aditional
        // penality.
        int number_of_incorrect = 0;
        for (Connection connection : this.connections) {
            if (!(connection.connectionCorrect())) {
                number_of_incorrect = number_of_incorrect + 1;
            }
        }

        Boolean blue_present = Boolean.FALSE;
        Boolean red_present = Boolean.FALSE;
        Boolean green_present = Boolean.FALSE;
        Boolean yellow_present = Boolean.FALSE;
        if (!(four_color_penality == 0)) {//skip the calculation if we don't want 3 coloring
            
        
        for (Region region : this.regions) {
            switch (region.color) {
            case "blue":
                blue_present = Boolean.TRUE;
                break;
            case "red":
                red_present = Boolean.TRUE;
                break;
            case "green":
                green_present = Boolean.TRUE;
                break;
            case "yellow":
                yellow_present = Boolean.TRUE;
                break;
            default:
                break;
            }
        }
        if (blue_present && red_present && green_present && yellow_present) {//if all the color are present, apply a penality to the goal
            number_of_incorrect= number_of_incorrect+  Math.round((float)(this.mapSize*four_color_penality));
            System.out.println("The map is using four colors");
        }
    }
        return number_of_incorrect;
    }

    protected Connection connectRegion(Region region1, Region region2) {// very simple method to connect between 2
                                                                        // Regions
        return new Connection(Boolean.FALSE, region1, region2);
    }

    @Override
    public Object clone() {// is needed for the genetic algorithms.It allows to
        // do a deep copy instead of a copy by reference (which would overwrite the same
        // memory location).
        Map map = null;
        try {
            map = (Map) super.clone();
        } catch (Exception e) {
            ArrayList<Connection> new_map_connection = new ArrayList<>();
            Region[] new_map_regions = new Region[this.mapSize];
            for (Connection connection : this.connections) {
                new_map_connection.add((Connection) connection.clone());
            }
            for (int i = 0; i < mapSize; i = i + 1) {
                new_map_regions[i] = (Region) this.regions[i].clone();
                // System.out.println(this.regions[i].clone().getClass());
            }
            // workaround to reattach regions to connection after the clone
            map = new Map(this.mapSize, new_map_regions, new_map_connection, this.performance);
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
