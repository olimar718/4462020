# CSCI 446 Artificial intelligence assignement 1
## Assignement goal

The goal of this assignment was to generate a map randomly: a set of points on a plane connected to each other. Then, to color each point so the connected points do not have the same color. The four-color theorem states that for a planar a graph, such a coloring is alway possible with at most 4 colors.

## Abstract 
In order to solve the planar graph coloring problem, a CSP, we implemented five different algorithms on a randomly generated graph. The first 3 algorithms build off of each other. All three of the backtracking algorithms reliably give a correct graph coloring. But, the difference comes in the resources required for each. Simple backtracking is horribly expensive because it has no checks and balances. This is where forward checking improves the algorithm, but in this application, it does very little good after about 50 regions. Implementing arc consistency provides a huge leap in performance and resources needed. Performing much better than initially anticipated. Even up to 100 regions, the algorithm swiftly delivers the correct coloring. Genetic is a very interesting approach but seems unadapted for this problem. The algorithm will not provide consistent assignment of colors for a graph with size 30, this is due to the unguided nature of the search, which is driven by fitness, but occurs with random mutations. Simulated annealing performs much better as the mutations are not random but based on a min conflict heuristic. The space is also explored more thoroughly because of the possibility of accepting a worse state. This minimizes the local optima problem.

## Screenshots

