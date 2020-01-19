package edu.montana.AI;

import java.util.List;

public class Region {
    public String color;
    public int x;
    public int y;
    public int regionId;

    public Region(String color, int x, int y, int regionId) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.regionId = regionId;
    }

    public Region findClosest(List<Region> regions) {
        Region currentClosest = null;
        int distance = 1000;
        for (Region region : regions) {
            if (!(region.regionId == this.regionId)) { //if this is not the same region. If calculate the distance to the same region, then this will alway be the closest one.
                int currentDistance = Math.addExact(Math.abs(this.x - region.x), Math.abs(this.y - region.y));//calculate the distance by summing the absolute value of the difference between coordinate x and coordinate y
                if (distance > currentDistance) {
                    currentClosest = region;
                    distance = currentDistance;
                }
            } else {//if this is the same region then continue the loop
                continue;
            }

        }
        System.err.println("Region " + this.regionId + " coordinate " + this.x + " " + this.y + " is closest to Region " + currentClosest.regionId + " coordinate " + currentClosest.x + " " + currentClosest.y);
        System.out.flush();
        return currentClosest;
    }
}
