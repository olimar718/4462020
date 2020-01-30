package edu.montana.AI;

import java.util.ArrayList;
import java.util.Random;
import java.lang.Thread;

public class Algorithms {
    public Algorithms() {

    }

    public Map minConflict(Map map, String[] colors) {

        // initial assignement
        // randomAssignement(map);
        Boolean consistent = Boolean.FALSE;
        ArrayList<Connection> conflictingConnections = new ArrayList<>();
        while (!(consistent)) {
            for (Connection connection : map.connections) {
                if (!(connection.connectionCorrect())) {
                    conflictingConnections.add(connection);
                }

            }
            if (conflictingConnections.size() == 0) {
                consistent = Boolean.TRUE;
                break;
            }
            Connection conflictingConnection = conflictingConnections
                    .get((int) (Math.random() * conflictingConnections.size()));
            ArrayList<String> neighbourcolor = new ArrayList<>();
            double selectedregion = Math.random();
            if (selectedregion > 0.5) {
                neighbourcolor.add(conflictingConnection.connectedRegion1.color);// we know it to be our neighbour so we
                                                                                 // add it to the list

                for (Connection mapConnection : map.connections) {// going trought the list of connection to find the
                                                                  // one connected to the current region
                    if (mapConnection.connectedRegion1.regionId == conflictingConnection.connectedRegion2.regionId) {
                        neighbourcolor.add(mapConnection.connectedRegion2.color);
                    }
                    // change to make sure color is not added more than once
                    if (mapConnection.connectedRegion2.regionId == conflictingConnection.connectedRegion2.regionId) {
                        neighbourcolor.add(mapConnection.connectedRegion1.color);
                    }
                }

            } else {
                neighbourcolor.add(conflictingConnection.connectedRegion2.color);// we know it to be our neighbour so we
                                                                                 // add it to the list

                for (Connection mapConnection : map.connections) {// going trought the list of connection to find the
                                                                  // one connected to the current region
                    if (mapConnection.connectedRegion1.regionId == conflictingConnection.connectedRegion1.regionId) {
                        neighbourcolor.add(mapConnection.connectedRegion2.color);
                    }
                    // change to make sure color is not added more than once
                    if (mapConnection.connectedRegion2.regionId == conflictingConnection.connectedRegion1.regionId) {
                        neighbourcolor.add(mapConnection.connectedRegion1.color);
                    }
                }

            }
            // count the number of time a color is present in neighbourcolour
            int[] colorTally = new int[4];
            for (String color : neighbourcolor) {
                switch (color) {
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
            int index = 0;// index of the color the least present in the neightbour
            for (int i = 0; i < colorTally.length - 1; i++) {
                if (colorTally[i] < colorTally[index]) {
                    index = i;
                }
            }

            if (selectedregion > 0.5) {

            } else {

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

    public Map genetic(Map map, int population_size, int tournament_size, int number_of_parents) {
        Map[] population = new Map[population_size];
        Boolean reached_goal = Boolean.FALSE;
        Random rand = new Random();
        for (int i = 0; i < population_size; i = i + 1) {// generates the base population of population_size randomly
            population[i] = (Map)map.clone();
            randomAssignement(population[i]);
        }
        for(int i = 0; i < population_size; i = i + 1){
            System.out.println(population[i]);
            new DrawingPanel(population[i]);
        }
        while (!(reached_goal)) {
            //tournament selection
            Map[] parents = new Map[number_of_parents];
            for (int i = 0; i < number_of_parents; i = i + 1) {//selecting the parent via tournament_selection
                Map[] tournament_contestant = new Map[tournament_size];
                for (int j = 0; j < tournament_size; j = j + 1) {//selecting the contestant
                    tournament_contestant[j]=population[rand.nextInt(population_size - 1)];
                }
                for (Map map2 : tournament_contestant) {
                    
                    System.out.println("GOALLLLLLLLL "+map2.goal());
                    //new DrawingPanel(map2);
                }
                try{
                Thread.sleep(900000000);
                }catch(Exception e){

                }
            }
        }

        return map;
    }

    public void randomAssignement(Map map) {
        Random rand2 = new Random();
        // while(Boolean.TRUE){
        //     try {
        //         Thread.sleep(1000);
        //     } catch (Exception e) {
        //         // TODO: handle exception
        //     }
        //     System.out.println(rand2.ints());
        // }
        String[] colors = { "red", "green", "blue", "yellow" };
        Random rand = new Random();
        // TODO random seed avoid sleep.
        // try {
        //     Thread.sleep(1000);
        // } catch (Exception e) {
        //     // TODO: handle exception
        // }
        rand.setSeed(System.nanoTime());
        for (Region region : map.regions) {
            region.color = colors[rand.nextInt(4)];
            // System.out.println(region.color);
        }
    }
}
