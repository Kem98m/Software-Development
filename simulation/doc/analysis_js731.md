CompSci 307: Simulation Project Analysis
===================

> This is the link to the [assignment](http://www.cs.duke.edu/courses/compsci307/current/assign/03_simulation/):

Design Review
=======

### Status

##### Readability
In my opinion, I think the Grid class shows very clean and readable code. For example:
```java
public void updateGrid() {
        Grid updated = new Grid(mySim);
        myRule.giveStates(myStates);
        for (Cell cell : myTotal) {
            updated.add(myRule.update(cell));
        }
        updated.neighbors();
        myTotal = (ArrayList<Cell>) updated.getCells();
        initializeStates();
        updateStateMap();
    }
```
The above code updates the grid visualization with new cell states. it is short, and readable in my opinion. The method names and simple design makes for a relatively easy experience. I think this is indicative of a lot of our code.

Another example of readable code can be found in the front end:
```java
    /**
     * Initializes the grid with hexagons representing each cell and its state
     */
    @Override
    public void makeGrid() {
        myPane = new AnchorPane();
        myShapeList = new ArrayList<>();

        for (Cell c: myCells) {

            Shape cellHex = makeCellHexagon(c);

            cellHex.setOnMouseClicked(m -> changeState(myShapeList, cellHex));

            setCellColor(c.getState(), cellHex);
            setCellImage(c.getState(), cellHex);

            cellHex.setLayoutX(0);
            cellHex.setLayoutY(0);

            myShapeList.add(cellHex);

            myPane.getChildren().add(cellHex);
        }
    }
```

That being said, some areas are not particularly readable. This is especially the case in the Neighborhood classes as they contain a lot of boolean logic.

```java
@Override
    public boolean toroidalCardinal(Cell c) {
        // Check toroidal vertical edges
        if ((center.getCol() == 0 && c.getCol() == center.getWidth() - 1) || (center.getCol() == center.getWidth() - 1 && c.getCol() == 0)) {
            return (center.getRow() == c.getRow());
        }

        // Check toroidal horizontal edges
        if ((center.getRow() == 0 && c.getRow() == center.getHeight() - 1) || (center.getRow() == center.getHeight() - 1 && c.getRow() == 0)) {
            return (center.getCol() == c.getCol());
        }
        return false;
    }

    @Override
    public boolean toroidalDiagonal(Cell c) {
        // Check toroidal vertical edges and corners
        if ((center.getCol() == 0 && c.getCol() == center.getWidth() - 1) || (center.getCol() == center.getWidth() - 1 && c.getCol() == 0)) {
            return (center.getRow() == c.getRow() + 1) || (center.getRow() == c.getRow() - 1) || (center.getRow() == center.getWidth() - 1 && c.getRow() == 0) || (center.getRow() == 0 && c.getRow() == center.getHeight() - 1);
        }

        // Check toroidal horizontal edges
        if ((center.getRow() == 0 && c.getRow() == center.getHeight() - 1) || (center.getRow() == center.getHeight() - 1 && c.getRow() == 0)) {
            return ((center.getCol() == c.getCol() - 1) || (center.getCol() == c.getCol() + 1));
        }
        return false;
    }
```
The above code is from the class SquareNeighborhood. Clearly, we did our best with the method names and inline commenting. However, the actual logic in the code is very difficult to understand after one reading. We determined that there was simply no other way execute the problem.

##### Flexibility

I think our code is pretty flexible in two ways. Firstly, we go upstream to more abstract objects in return statements and parameters. An example of that can be found in the Cell class (amongst many other locations).
```java
public Neighborhood getMyNeighborhood() {
        ...
        
public Set<Cell> getNeighbors()
        ...
```
In the first method's return type, Neighborhood is an abstract object. In order to be called for any of the three Neighborhood types, we use the more flexible Neighborhood. Similarly, although our current code uses a HashMap to store neighbors, we return a Set. This makes it easier for us to change how we store neighbors if that comes up later on in refactoring.

The other way our code is flexible is in design. It is incredibly easy to add simulation types. All that is necessary is to add a new class that extends the abstract class Rule, and add the simulation type to the following switch statement in Grid:

```java
private void ruleMaker(String type) {
        if (type.equals("GameOfLife")) {
            myRule = new GameOfLifeRule();
        } else if (type.equals("Percolation")) {
            myRule = new PercolationRule();
        } else if (type.equals("RPS")) {
            myRule = new RPSRule();
        } else if (type.equals("Fire")) {
            myRule = new FireRule();
        } else if (type.equals("Segregation")) {
            myRule = new SegregationRule();
        } else if (type.equals("PredatorPrey")) {
            myRule = new PredatorPreyRule();
        }
    }
```

Similarly, our code on the front end is designed flexibly. When we had to refactor our code to allow different shapes to for each cell of our simulation, we abstracted our visual grid (AbstractGridMaker). Similarly to above, in order to make a new shape type, one must only make a new class that extends Abstract grid maker and add the type to:
```java
private void initializeGridCreator() {
        gridShape = myResources.getString("GRID_SHAPE");
        if (myUI.getGridType() != null) {
            gridShape = myUI.getGridType();
        }

        switch (gridShape) {
            case "Square":
               testGridCreator = new SquareGridMaker(currentCells.getCells()); break;
            case "Hexagonal":
                testGridCreator = new HexagonGridMaker(currentCells.getCells()); break;
            case "Triangular":
               testGridCreator = new TriangleGridMaker(currentCells.getCells()); break;
        }


        testGridCreator.giveGridDefaultColors(default1, default2, default3);
        testGridCreator.giveGridCustomColor(customColor1, customColor2, customColor3);

        testGridCreator.giveGridDefaultImages(defaultImg1, defaultImg2, defaultImg3);
        testGridCreator.giveGridCustomImages(customImg1, customImg2, customImg3);
    }
```

##### Dependencies
The following images do a really nice job of showing dependencies in the backend, frontend, and util packages. 

![Image of Backend](https://ibb.co/VHZcnFr)

Within the backend, grid is the central class. Every other class is dependent on grid, however the grid is only dependent on Cell. Each class is dependent on Cell, as it is the primary object that we use to hold information. One can also see the abstraction hierarchies that exist.

![Image of FrontEnd](https://ibb.co/GvX1Ysv)

One can see that the Simulation Viewer is the central component. One can also see additional abstraction.

![Image of Util](https://ibb.co/QQhH6Qs)

Across packages, the simulation viewer holds a Grid object and the GUI utilizes the utility classes.

### Overall Design

##### Adding a new simulation

This process has already largely been described, so I will simply summarize again.
1. Create a new class extending Rule. This should outline the rules of the simulation.
2. Add the type to the switch statement in Grid.
3. Make a new configuration file and csv file for the simulation type.

##### Broad Design

The project is designed around a modified MVC approach. There is a front end package, that holds everything responsible for visualizing the grid of the simulation. There is a backend package centered around a Grid class. This is somewhat of a mix of the controller and the model. The Grid holds the arrangement of Cells. The shape of cells affects how the neighborhoods operate, and therefore we have different Neighborhood classes. Furthermore, each simulation type extends an abstract Rule class for making the Grid update appropriately. Lastly, we have a utility package for file loading and writing, and a testing package.

##### Justification

Our code was designed with the complete intention to make adding new simulations easy. As seen by the very short list of steps to make a simulation, I think we were successful in that goal. This design payed off for us well in the second part of the assignment when the simulations became more plentiful and complex. 

Early on in the process we grappled with how to make our grid class. We didn't know if we were going to abstract it and make a new type for each simulation. In the end we decided on simply abstracting the rule class, as that was a significant enough part of the model to justify its own class.

### Flexibilty

##### Flexible or not?

As stated previously, I think our code is very flexible. It is very easy to add simulation types on the back end.

On the front end, it is somewhat easy to add shape types for our simulations, however, it requires additional modification in the back end. You must go and make a new class extending Neighborhood in order for the simulation to work correctly.

##### One Feature that Jeffrey made

The feature at hand is the GUI class. This supplies so much of the front end, and is, in my opinion, well designed. I don't feel it is necessary to explain the class in depth, as it essentially implements a variety of buttons and functionality for the user. 

The reason why I chose this is because the class knows exactly what it needs to know. It is largely closed, only communicating with our Simulation Viewer and utility classes. Furthermore, there are very few assumptions made outside of file placement.

The code is somewhat flexible. Adding a new button or file system would take time and modification, but this is largely to be expected in front end design refactoring.

### My Design

##### My abstraction

My two abstractions were the Neighborhoods and the Rules. Both have already been described above, but I will restate their design here.

When creating simulations with different shapes, it was important to change how neighborhoods were calculated. However, for each neighborhood, certain functionality had to be present. This included different edge and neighbor policies. This was a prime example where abstraction could help flexibility and to reduce duplicate code. Therefore, we have the abstract class Neighborhood that outlines what exactly each type of neighborhood must do, and we write out the specific knowledge for hexagonal, square, and triangular neighborhoods in corresponding classes.

When adding simulations, what one is really doing is changing the rules for how a cell changes states given the same surrounding environment. Therefore we made an abstract class Rule that had a method to make new cells in the same position as old cells, and we required extensions of Rule to include an update method that reutrned an updated cell. Within specialized extensions such as GameOfLifeRule and other similarly named Rules, we specify the logic of updating cells. Once again, this allowed us to be flexible and reduce duplicate code.

##### Design Checklist Issues

The biggest issue in the design checklist is in magic values.

One large area of use for magic values was in the simulation type. The type of simulation is essential knowledge for many aspects of our project, however it is usually represented by a string. One possible fix that we attempted was to replace these strings with Enums, however we found this difficult and time consuming, and ultimately chose to focus on other more timely aspects.

Another area where magic values are used was within Neighborhood logic. There simply exist very specific rules for when a hexagon is neighboring another hexagon. Because of our coordinate system across all shaped grids, we were required to use seemingly magic values for differences in cell's x and y coordinates. We found it impossible to avoid this without reworking how our cells were organized.

Furthermore, the testing has a lot of duplicate code and other flaws in terms of design. We didn't think it would be responsible for us to take significant time to build abstractions and other design features into the testing.

##### Specific Feature Implemented

I am going to walk through the implementation of the Predator and Prey Rule.

I started with the following commit, beginning testing: 
https://coursework.cs.duke.edu/compsci307_2019spring/simulation_team04/commit/ef555d305379a7fee9a708afddc1956088301162

Next I began to implement functionality slowly in the following commits:
https://coursework.cs.duke.edu/compsci307_2019spring/simulation_team04/commit/03be0aea90f69fe3181255c5db7f102b6625b75e
https://coursework.cs.duke.edu/compsci307_2019spring/simulation_team04/commit/48f506666a40750b06fd6525a2870861387abfe8

As I worked, I added additional tests:
https://coursework.cs.duke.edu/compsci307_2019spring/simulation_team04/commit/2d5a68b08cdea5c555b2436694022ab9e682c203

And then continued until I had a working version:
https://coursework.cs.duke.edu/compsci307_2019spring/simulation_team04/commit/e66ec1d08dac45488f65ec1c58ee3022e9f43a9a

There was major debugging and refactoring that of course included updates to testing.
https://coursework.cs.duke.edu/compsci307_2019spring/simulation_team04/commit/e5312c80f90e9d1c3598f2f024953a35f042de99
https://coursework.cs.duke.edu/compsci307_2019spring/simulation_team04/commit/4be6b0199c31437f82639381f935b31c2748aba5
https://coursework.cs.duke.edu/compsci307_2019spring/simulation_team04/commit/7ee322b6c3124beb9938336eb3409b3eb68514f2
https://coursework.cs.duke.edu/compsci307_2019spring/simulation_team04/commit/6e23ce9aef9741349a215b8199454b879cb34b9a

The code is designed similarly to other Rules, however the engineering problem was harder than usual.
Like other Rules, this one's main functionality is within the updateCell method. However, this update is quite complex. First, because Predator and Prey involves cells switching locations, I was required to first go through all the cells in the grid to fine which cells would switch with eachother. This was based on a combination of factors, some of which incorporated random chance. All the replacement pairs are determined in a call from the Grid that is flexible enough to work with every simulation type. Next, the grid iterates through every cell, and the Rule updates said cell with its appropriate replacement.

The challenge of replacement pairs was certainly the main difficulty. A smaller additional complexity was that fish and sharks reproduce and die. This required modifying the Cell class subtly, and checking different instance variables within the cell.

The only important dependency that exists in this feature is on the Grid. The rule requires that the Grid feeds it all the cells before updating each one.

### Alternate Designs

##### The Grid

Early on in the design process, we discussed different ways of holding our Cells. We considered a 2D array of Cells as our primary structure, however we wisely listened to Robert Duvall tell us that this was a bad idea. Instead, we used a coordinate system such that each cell knew where in the grid it was, but the Grid only contained one list of cells. The benefit of this is that it makes it easier for different cell shapes to be actualized. Furthermore, although we have not implemented this functionality, it makes it far easier to import a grid that is not square (it could be a circular arrangement of cells for example).

Additionally we discussed abstracting Grid for each simulation type, but as previously mentioned we chose to abstract the Rule instead.

##### The shapes

The second major design choice we discussed was how to implement the different shapes of cells. This is of course tangentially related to the Grid. We chose two different abstractions, one for back end and one for front end. However, initially we had different designs. In the first implementation, our front end was not based on shapes but based on a GirdPane object in javafx. We experimented and discussed for a long time how to use the GridPane to result in other shapes such as triangles so we would have to change less code. Ultimately, we decided the refactoring and re-writing was worth it, as it was going to be almost impossible to implement hexagons with the GridPane. This was a case where the alternate design was simply flawed, and was therefore not usable.
