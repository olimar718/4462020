package edu.montana.AI;

import java.util.ArrayList;
import java.util.List;

public class Map {
    int mapSize;
    List<Connection> connections;
    List<Region> regions;

    public Map(int numberOfPoint) {
        this.mapSize = numberOfPoint;
        this.regions = new ArrayList<Region>();
        this.connections = new ArrayList<Connection>();
    }


    public int goal(Map this) {
        return 0;
    }

    public Connection connectRegion(Region region1, Region region2) {
        Boolean isCorrect = Boolean.FALSE;
        Connection connection = new Connection(isCorrect, region1, region2);
        return connection;
    }

    public void colorRegion(Region region) {

    }
}
