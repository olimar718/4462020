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

    protected int goal() {// This a performance and goal function. It returns 0 if the variable
                          // assignement is a complete and consistent solution. Otherwise, it returns the
                          // number of constraint violation.
        int number_of_incorrect = 0;
        for (Connection connection : this.connections) {
            if (!(connection.connectionCorrect())) {
                number_of_incorrect = number_of_incorrect + 1;
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
