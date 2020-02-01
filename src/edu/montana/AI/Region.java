package edu.montana.AI;

public class Region {
    public String color;
    public int x;
    public int y;
    public int regionId;

    protected Region(String color, int x, int y, int regionId) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.regionId = regionId;
    }

    protected Region findClosest_connectable(Map map) {// returns the closest connectable (not crossing an already
                                                       // established connection) region to the current region. If no
                                                       // connection are possible anymore, it return the connection
                                                       // itself.
        Region currentClosest = this;
        int distance = 250000;
        boolean no_connection_possible = false;

        for (Region region : map.regions) {// goes trought all the region to find the closest one
            if (!(region.regionId == this.regionId)) { // if this is not the same region. If calculate the distance to
                                                       // the same region, then this will alway be the closest one.
                int currentDistance = (int) Math
                        .sqrt(Math.pow(Math.abs(this.x - region.x), 2) + Math.pow(Math.abs(this.y - region.y), 2));// using
                                                                                                                   // the
                                                                                                                   // pythagorean
                                                                                                                   // theorem
                                                                                                                   // to
                                                                                                                   // find
                                                                                                                   // the
                                                                                                                   // distance
                if (currentDistance < distance) {// the region is the closest one, now we check if it crosses.
                    for (Connection connection : map.connections) {
                        no_connection_possible = false;
                        if (((connection.connectedRegion1.regionId == region.regionId)
                                && (connection.connectedRegion2.regionId == this.regionId))
                                || ((connection.connectedRegion1.regionId == this.regionId)
                                        && (connection.connectedRegion2.regionId == region.regionId))) {// if there is
                                                                                                        // already a
                                                                                                        // connection
                            no_connection_possible = true;
                            break;
                        }
                        if (!(connection.connectedRegion1.regionId == this.regionId
                                || connection.connectedRegion2.regionId == this.regionId
                                || connection.connectedRegion1.regionId == region.regionId
                                || connection.connectedRegion2.regionId == region.regionId)) {// if this not a
                                                                                              // connection connected to
                                                                                              // our region. If it is,
                                                                                              // it will cross every
                                                                                              // time.
                            if (doIntersect(this, region, connection.connectedRegion1, connection.connectedRegion2)) {
                                // System.out.println("A connection between "+this.regionId+" and
                                // "+region.regionId+" would cross the connection between
                                // "+connection.connectedRegion1.regionId+" and
                                // "+connection.connectedRegion2.regionId);
                                no_connection_possible = true;
                                break; // if the connections crosses another
                            }
                        }

                    }
                    if (no_connection_possible == true) {
                        continue;// we go to the next reigion
                    } else {
                        currentClosest = region;
                        distance = currentDistance;
                    }
                }
            }
        }
        return currentClosest;
    }

    protected static boolean onSegment(Region p, Region q, Region r) {// taken from
                                                                      // https://www.geeksforgeeks.org/check-if-two-given-line-segments-intersect/
        if (q.x <= Math.max(p.x, r.x) && q.x >= Math.min(p.x, r.x) && q.y <= Math.max(p.y, r.y)
                && q.y >= Math.min(p.y, r.y))
            return true;

        return false;
    }

    protected int orientation(Region p, Region q, Region r) {// taken from
                                                             // https://www.geeksforgeeks.org/check-if-two-given-line-segments-intersect/
        int val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);

        if (val == 0)
            return 0; // colinear

        return (val > 0) ? 1 : 2; // clock or counterclock wise
    }

    protected boolean doIntersect(Region p1, Region q1, Region p2, Region q2) {// taken from
                                                                               // https://www.geeksforgeeks.org/check-if-two-given-line-segments-intersect/

        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        // General case
        if (o1 != o2 && o3 != o4)
            return true;

        // Special Cases
        // p1, q1 and p2 are colinear and p2 lies on segment p1q1
        if (o1 == 0 && onSegment(p1, p2, q1))
            return true;

        // p1, q1 and q2 are colinear and q2 lies on segment p1q1
        if (o2 == 0 && onSegment(p1, q2, q1))
            return true;

        // p2, q2 and p1 are colinear and p1 lies on segment p2q2
        if (o3 == 0 && onSegment(p2, p1, q2))
            return true;

        // p2, q2 and q1 are colinear and q1 lies on segment p2q2
        if (o4 == 0 && onSegment(p2, q1, q2))
            return true;

        return false; // Doesn't fall in any of the above cases
    }

    @Override
    public Object clone() {// is needed for the genetic algorithms. Is used by the map class. It allows to
                           // do a deep copy instead of a copy by reference (which would overwrite the same memory location)
        Region region = null;
        try {
            region = (Region) super.clone();
        } catch (CloneNotSupportedException e) {//it always goes here and we have to clone manually
            region = new Region(this.color, this.x, this.y, this.regionId);//we create a new object with the same atributes.
        }
        return region;
    }
}
