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
        int numColors = 3;
        if (!graphColoring(map, numColors, 0, map.regions[0])) {
            System.out.println("Solution doesn't exist");
        }
        new DrawingPanel(map, "simpleBacktracking");
        System.out.println("Drew simpleBacktracking");
        return map;
    }

    public Map backtrackingForwardChecking(Map map) {
        return map;
    }

    public Map mac(Map map) {
        return map;
    }

    public Map genetic(Map map, int population_size, int tournament_size, int number_of_parents,
            int mutation_probability) {
        Map[] population = new Map[population_size];
        Boolean reached_goal = Boolean.FALSE;
        Random rand = new Random();
        // generates the base population of population_size randomly
        for (int i = 0; i < population_size; i = i + 1) {
            population[i] = (Map) map.clone();
            randomAssignement(population[i]);
        }
        while (!(reached_goal)) {// or limit exceeded
            // tournament selection
            Map[] parents = new Map[number_of_parents];
            for (int i = 0; i < number_of_parents; i = i + 1) {// selecting the parent via tournament_selection
                Map[] tournament_contestants = new Map[tournament_size];
                for (int j = 0; j < tournament_size; j = j + 1) {// selecting the contestant
                    tournament_contestants[j] = population[rand.nextInt(population_size - 1)];
                }
                // run the tournament
                Map winner = tournament_contestants[0];
                for (Map tournament_contestant : tournament_contestants) {
                    int current_contest_goal = tournament_contestant.goal();
                    System.out.println(current_contest_goal);
                    // new DrawingPanel(tournament_contestant, "Contestant");

                    if (current_contest_goal == 0) {// the goal has been reached so we return
                        reached_goal = Boolean.TRUE;
                        return (Map) tournament_contestant.clone();
                    }
                    if (current_contest_goal < winner.goal()) {
                        winner = tournament_contestant;
                    }
                }
                // save the winner as a parent
                parents[i] = (Map) winner.clone();
            }
            // recombine (crossover)
            // for(Map parent : parents){
            // new DrawingPanel(parent, "parent");
            // }
            // try {
            //     Thread.sleep(3000);
            // } catch (Exception e) {
            // }
            //new DrawingPanel((Map) genetic_recombine(parents).clone(), "new generation base"); //debug
            for (int i = 0; i < population_size; i = i + 1) {
                population[i] = (Map) genetic_recombine(parents).clone();
            }
            // for(Map child : population){
            // new DrawingPanel(child, "child");
            // }

            // mutate
            for (Map child : population) {
                genetic_mutate(child, mutation_probability);
                // new DrawingPanel(child, "mutated");
            }

            // try {
            // Thread.sleep(900000000);
            // } catch (Exception e) {

            // }
        }
        return map;
    }

    public Map genetic_recombine(Map[] maps) {
        Random rand = new Random();
        Map recombined = (Map) maps[0].clone();
        for (Region recombined_region : recombined.regions) {
            String[] ordered_colors = new String[maps.length];
            int i = 0;
            for (Map map : maps) {
                for (Region region : map.regions) {
                    if (region.regionId == recombined_region.regionId) {
                        ordered_colors[i] = region.color;
                        i = i + 1;
                    }
                }
            }
            rand.setSeed(System.nanoTime());
            recombined_region.color = ordered_colors[rand.nextInt(ordered_colors.length)];
        }
        return recombined;
    }

    public Map genetic_mutate(Map map, int mutation_probability) {
        String[] colors = { "red", "green", "blue", "yellow" };
        Random rand = new Random();
        for (Region region : map.regions) {
            rand.setSeed(System.nanoTime());
            if (rand.nextInt(mutation_probability) == 0) {
                region.color = colors[rand.nextInt(4)];// TODO gaussian / normal distribution ?
            }
        }
        return map;
    }

    public void randomAssignement(Map map) {
        String[] colors = { "red", "green", "blue", "yellow" };
        Random rand = new Random();
        rand.setSeed(System.nanoTime());
        for (Region region : map.regions) {
            region.color = colors[rand.nextInt(4)];
        }
    }

    public boolean graphColoring(Map map, int numColors, int colored, Region region) {
        if (map.mapSize == colored) {
            return true;
        }

        for (int i = 0; i < numColors; i++) {
            if (colorCheck(colored, map, region)) {
                region.color = "red"; // need to change to be dynamic
                if (graphColoring(map, numColors, colored + 1, region)) {
                    return true;
                }
                region.color = "";
            }
        }

        return false;
    }

    public boolean colorCheck(int colored, Map map, Region region) {
        // return true if surrounding colors are a different color or not yet assigned a
        // color
        for (int i = 0; i < map.mapSize; i++) {
            if (map.regions[i].color.equals(region.color)) {
                return false;
            }
        }
        return true;
    }
}
