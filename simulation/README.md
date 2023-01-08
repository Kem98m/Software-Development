simulation
====

This project implements a cellular automata simulator.

Names: Jeffrey Gu, Amik Mandal, Jordan Shapiro

### Timeline

Start Date: 2/13/2019

Finish Date: 3/8/2019

Hours Spent: 75

### Primary Roles

Jeffrey - worked majority of the time on front-end components, working on the animation and GUI components. Also headed the abstraction of the front end components, and tried to separate the front end from the back end as much as possible.

Amik - worked as a mediator between front-end and back-end. Implemented rules for several simulations, error handling, graphs, etc. Helped Jeffrey and Jordan in connecting their codes together , debugging, abstractions, etc.

Jordan - Worked almost entirely on backend. The following classes I heavily interacted with: Cell, GameOfLifeRule, Grid, HexagonNeighborhood, Neighborhood, PercolationRule, PredatorPreyRule, Rule, SegregationRule, SquareNeighborhood, TriangleNeighborhood, CSVReader (the randomization), almost all testing.


### Resources Used
stackoverflow.com, Java API


### Running the Program

Main class: SimulationViewer

Data files needed: 
- The .csv file of the simulation you want to run (located in the configurations folder under the data folder)
- The GUI.properties file and the properties file of the simulation you want to run
- Relevant images found in the images folder
- opencsv.jar library
- JavaFX and JUnit libraries

Interesting data files:
- Some of the configurations test out error checking in our implementation, so check that out!
- There are some neat images to be utilized!
- Some of the .csv files are huge grids that get a memory overflow error when we try to run it on our machines. Maybe a better computer is needed?

Features implemented:
- Property files fully customize the GUI and types of grid, neighbor, and edge policies for each simulation type
- Implementations for Fire, Game of Life, Percolation, Predator and Prey, Rock Paper Scissors, and Segregation
- Basic CSS styling for the GUI
- Fully customizable grid, from shape of the grid to colors/image fill of each cell type, to the outline of each cell.
- Extensive testing classes to test the back-end of our program
- Dynamic user interaction - users can click on any cell and have it instantly change states and have an immediate effect on its surrounding neighbors
- Real-time graph shows changing number of cells in each state of the simulation.
- Play/pause/step buttons allow you to stop and go as you please.
- Speed buttons allow you to customize the speed at which the simulation runs.
- Ability to save the simulation's configuration at any point (saves it to the data/saves/ folder). Simply restart the simulation to run it from your save file!
- Ability to load any configuration file at any time to start a new simulation.
- Flexibility to add more/modify existing configurations or simulations due to multiple abstractions and generalizations in the back and front-end of our implementation.
- If fed in a configuration file with "RANDOM" written instead of an actual cell state list, a random grid will be generated. If you want specific ratios of states, "RANDOM" followed on the next lines by the probability of a given cell being that state. For example. RANDOM/n30/n70 will result in each cell having a 30 percent chance to be state 0, and 70 for state 1.
- Grid can be made up of squares, triangles, or hexagons, with the neighbors of the cells adjusting appropriately.
- Neighbors (for squares) can be finite, toroidal, or a custom edge policy. Further more, for each shape of cell, neighbors can be complete, cardinal, or diagonal.
- Error handling to display error on selecting invalid files/data. 

Assumptions or Simplifications:

Known Bugs:
- Sometimes when you select a custom image and press the back button on the main menu, the background image goes away and the background color turns gray.
- With particular grid configurations on a Hexagon grid, the hexagon grid will cover the buttons on the bottom of the screen. This may be due to a miscalculation when forming the hexagons.
- Toroidal edges is either incomplete or incorrect for hexagonal and triangular cell shapes.


Extra credit:


### Notes

##### Resources Used
https://www.redblobgames.com/grids/hexagons/


### Impressions

##### Jeffrey's Impressions:
I thought this project was a great way to learn more about abstraction and generalization, and I really think I got a lot out of the project in that way. I also realized the importance of test driven development and how useful it can be when developing a project. 
I am especially proud of how our team implemented various abstractions that made it relatively simple to add on to the project as time went on. However, as we progressed through the Complete version of Simulation, I felt that the requirements started to deviate from the main purpose of the project, which I am assuming is to help us implement flexibility and abstraction into our code. The requirements seemed to be simply requirements for the sake of having more difficult objectives, but I did not feel like my team and I gained much from implementing features such as custom edge and neighbor policies. Overall, I still think it was a positive experience and I learned a lot from working with my team as well.

##### Jordan's Impressions:
I thought this project was fun and gave us for more of a backend challenge than the previous project, game. I think the abstractions were intellectually stimulating, and I am proud of how we abstracted the Rule and Neighbor class. We also abstracted some things in front end, but I did'nt work intimately with that portion of the project. Furthermore, out initial set up for the backend in Cell and Grid were, in my opinion well designed. They were designed to be very generalizable and flexible, and I think it paid off in the end. In terms of the tasks we were actually told to implement, some of the neighbor policies for other shapes turned out to require a lot of busy work as opposed to being a computational challenge. Ultimately, I thought the project was a good learning experience. 

##### Amik's Impressions:
This project really proved to me the power of good design and made me appreciate good deisgn. Our hierarchy system for rules was super helpful to implement new simulation as all we had to do was add a class that extends the Rule class and just overwrite the update method. On the other hand we did not make any more abstract class and this turned out to be a problem for complete. Lesson learned, we implemented some more abstract classes for both front-end and back-end on different grid types and this helped us in writing the rest of the code rather easily. I am really impressed with the work that our team did and according to me we did more than what we expected.