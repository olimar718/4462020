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

    public Region findClosest(Map map) {
        Region currentClosest = this;
        int distance = 250000;
        boolean no_connection_possible = false;
        
        for (Region region : map.regions) {
            if (!(region.regionId == this.regionId)) { //if this is not the same region. If calculate the distance to the same region, then this will alway be the closest one.
                //change formula to calculate hypotenuse insead of just lenth of x+y
                //int currentDistance = Math.addExact(Math.abs(this.x - region.x), Math.abs(this.y - region.y));//calculate the distance by summing the absolute value of the difference between coordinate x and coordinate y
                int currentDistance = (int) Math.sqrt(Math.pow(Math.abs(this.x - region.x), 2) + Math.pow(Math.abs(this.y - region.y), 2));
                if (currentDistance<distance) {//
                    for (Connection connection : map.connections) { 
                        no_connection_possible=false;   
                        if (((connection.connectedRegion1.regionId == region.regionId) && (connection.connectedRegion2.regionId == this.regionId)) || ((connection.connectedRegion1.regionId == this.regionId) && (connection.connectedRegion2.regionId == region.regionId))){
                            no_connection_possible = true;
                            break;   //if there is already a connection
                        }//need to check cross

                    }
                    if(no_connection_possible == true){
                        continue;
                    }else{
                        currentClosest = region;
                        distance = currentDistance;
                    }
                }
            }
        }
        //System.err.println("Region " + this.regionId + " coordinate " + this.x + " " + this.y + " is closest to Region " + currentClosest.regionId + " coordinate " + currentClosest.x + " " + currentClosest.y);//debug
        System.out.flush();
        return currentClosest;
    }

    static boolean onSegment(Region p, Region q, Region r) 
{ 
    if (q.x <= Math.max(p.x, r.x) && q.x >= Math.min(p.x, r.x) && 
        q.y <= Math.max(p.y, r.y) && q.y >= Math.min(p.y, r.y)) 
    return true; 
  
    return false; 
} 

    public int orientation(Region p, Region q, Region r) 
{ 
    // See https://www.geeksforgeeks.org/orientation-3-ordered-points/ 
    // for details of below formula. 
    int val = (q.y - p.y) * (r.x - q.x) - 
            (q.x - p.x) * (r.y - q.y); 
  
    if (val == 0) return 0; // colinear 
  
    return (val > 0)? 1: 2; // clock or counterclock wise 
} 

    public boolean doIntersect(Region p1, Region q1, Region p2, Region q2) 
{ 
    // Find the four orientations needed for general and 
    // special cases 
    int o1 = orientation(p1, q1, p2); 
    int o2 = orientation(p1, q1, q2); 
    int o3 = orientation(p2, q2, p1); 
    int o4 = orientation(p2, q2, q1); 
  
    // General case 
    if (o1 != o2 && o3 != o4) 
        return true; 
  
    // Special Cases 
    // p1, q1 and p2 are colinear and p2 lies on segment p1q1 
    if (o1 == 0 && onSegment(p1, p2, q1)) return true; 
  
    // p1, q1 and q2 are colinear and q2 lies on segment p1q1 
    if (o2 == 0 && onSegment(p1, q2, q1)) return true; 
  
    // p2, q2 and p1 are colinear and p1 lies on segment p2q2 
    if (o3 == 0 && onSegment(p2, p1, q2)) return true; 
  
    // p2, q2 and q1 are colinear and q1 lies on segment p2q2 
    if (o4 == 0 && onSegment(p2, q1, q2)) return true; 
  
    return false; // Doesn't fall in any of the above cases 
}
}
