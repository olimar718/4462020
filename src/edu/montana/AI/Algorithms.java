package edu.montana.AI;

import java.util.ArrayList;


public class Algorithms {
    public Algorithms() {

    }

    public Map minConflict(Map map, String[] colors) {

        //initial assignement
        map = randomAssignement(map);
        Boolean consistent = Boolean.FALSE;
        ArrayList<Connection> conflictingConnections = new ArrayList<>();
        while (!(consistent)) {
            for (Connection connection : map.connections) {
                if (!(connection.connectionCorrect())) {
                    conflictingConnections.add(connection);
                }

            }
            if(conflictingConnections.size()==0){
                consistent=Boolean.TRUE;
                break;
            }
            Connection conflictingConnection = conflictingConnections.get((int) (Math.random() * conflictingConnections.size()));
            ArrayList<String> neighbourcolor=new ArrayList<>();
            double selectedregion=Math.random();
            if (selectedregion > 0.5) {
                neighbourcolor.add(conflictingConnection.connectedRegion1.color);//we know it to be our neighbour so we add it to the list

                for (Connection mapConnection : map.connections) {//going trought the list of connection to find the one connected to the current region
                    if (mapConnection.connectedRegion1.regionId == conflictingConnection.connectedRegion2.regionId) {
                        neighbourcolor.add(mapConnection.connectedRegion2.color);
                    }
                    //change to make sure color is not added more than once
                    if (mapConnection.connectedRegion2.regionId == conflictingConnection.connectedRegion2.regionId) {
                        neighbourcolor.add(mapConnection.connectedRegion1.color);
                    }
                }

            }
            else{
                neighbourcolor.add(conflictingConnection.connectedRegion2.color);//we know it to be our neighbour so we add it to the list

                for (Connection mapConnection : map.connections) {//going trought the list of connection to find the one connected to the current region
                    if (mapConnection.connectedRegion1.regionId == conflictingConnection.connectedRegion1.regionId) {
                        neighbourcolor.add(mapConnection.connectedRegion2.color);
                    }
                    //change to make sure color is not added more than once
                    if (mapConnection.connectedRegion2.regionId == conflictingConnection.connectedRegion1.regionId) {
                        neighbourcolor.add(mapConnection.connectedRegion1.color);
                    }
                }

            }
            //count the number of time a color is present in neighbourcolour
            int[] colorTally=new int[4];
            for(String color: neighbourcolor){
                switch (color){
                    case "red":
                        colorTally[0]++;
                        break;
                    case "green":
                        colorTally[1]++;
                        break;
                    case "blue":
                        colorTally[2]++;
                        break;
                    case "yellow":
                        colorTally[3]++;
                        break;
                    default:
                        break;
                }
            }
            int index=0;//index of the color the least present in the neightbour
            for(int i =0;i<colorTally.length-1;i++){
                if (colorTally[i]<colorTally[index]){
                    index=i;
                }
            }

            if (selectedregion>0.5){

            }else{

            }


        }


        return map;
    }

    public Map simpleBacktracking(Map map) {
        return map;
    }

    public Map backtrackingForwardChecking(Map map) {
        return map;
    }

    public Map mac(Map map) {
        return map;
    }

    public Map genetic(Map map) {

        //ArrayList<Map> population = new ArrayList<>();//vs List<Map> population= new ArrayList<>();//vs Map[] pop; ???
        return map;
    }

    public Map randomAssignement(Map map) {
        String[] colors = {"red", "green", "blue", "yellow"};
        for (Region region : map.regions) {
            region.color = colors[(int) (Math.random() * 4)];
            System.out.println(region.color);
        }
        return map;
    }
}
