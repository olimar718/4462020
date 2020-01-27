package edu.montana.AI;


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

    public Region findClosest(Region[] regions) {
        Region currentClosest = null;
        int distance = 1000;
        for (Region region : regions) {
            if (!(region.regionId == this.regionId)) { //if this is not the same region. If calculate the distance to the same region, then this will alway be the closest one.
                //change formula to calculate hypotenuse insead of just lenth of x+y
                //int currentDistance = Math.addExact(Math.abs(this.x - region.x), Math.abs(this.y - region.y));//calculate the distance by summing the absolute value of the difference between coordinate x and coordinate y
                int currentDistance = (int) Math.sqrt(Math.pow(Math.abs(this.x - region.x), 2) + Math.pow(Math.abs(this.y - region.y), 2));
                if (distance > currentDistance) {
                    currentClosest = region;
                    distance = currentDistance;
                }
            }
        }
        //System.err.println("Region " + this.regionId + " coordinate " + this.x + " " + this.y + " is closest to Region " + currentClosest.regionId + " coordinate " + currentClosest.x + " " + currentClosest.y);//debug
        System.out.flush();
        return currentClosest;
    }
}
