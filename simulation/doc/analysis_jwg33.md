CompSci 307: Simulation Project Analysis
===================

> This is the link to the [assignment](http://www.cs.duke.edu/courses/compsci307/current/assign/03_simulation/):

Design Review
=======

### Status

- What makes the code well-written and readable (i.e., does it do what you expect, does it require comments to understand)?

I think the naming conventions of the code make is very easy to read. For example, the class names are all related to their function,
and classes that extend an abstract class all have that abstract class's name in their class names (i.e., HexagonNeighborhood extends
the abstract class Neighborhood). We also tried to make each varible unambiguous in their naming conventions, and also turned
"magic numbers" into an understandable instance variable where applicable (i.e., in Neighborhood:)
```
private static final int ALL_NEIGHBOR_TYPE = 0;
private static final int CARDINAL_NEIGHBOR_TYPE = 1;
private static final int DIAGONAL_NEIGHBOR_TYPE = 2;
``` 
This all lends to the readability of the code and I think it helped me a lot personally when I was going through code that 
my teammates had worked on. 


- What makes this project's code flexible or not (i.e., what new features do you feel are easy or hard to add)?

One of the things we prided ourselves on this project was the fact that we generalized the backend so well. For example, the Grid
class is pretty much generalized to any simulation, and there were no abstractions needed. You simply have to add the new type of simulation
to a few lines of code within Grid and add the Rule abstraction for the new simulation. In this regard, adding new simulations is a 
relatively easy process with the way we have set up the backend. Regarding the front end, it is also pretty easy to add new types of 
grid visualizations, since the GridMaker is abstracted out. Thus, each new implementation of the grid just has to specify the shapes
and how those shapes are arranged, and everything else should be done for you in terms of filling the shapes with the appropriate colors/
images and relating those shapes to their respective cells.


- What dependencies between the code are clear (e.g., public methods and parameters) and what are through "back channels" 
(e.g., static calls, order of method call, requirements for specific subclass types)? 

After running the dependency analysis on IntelliJ, it seems as if most of the dependencies are clear, and there are no backward/cyclic 
dependencies in the code. In the code, most of the dependencies are apparent, such as the various Neighborhood extentions' dependencies
on Cell, and GUI's dependency on SimulationViewer (to be able to play and pause the Timeline). 


### Overall Design

* As briefly as possible, describe everything that needs to be done to add a new kind of simulation to the project.

Starting from the very beginning, each simulation needs a configuration .csv file and a .properties file. The .csv file will be the
initial configuration and should include the name of the simulation, the size of the grid (width by height) and the actual grid state
values, separated by commas. The properties file should include the number of potential states in the simulation, and any default
color codes/image file paths for each state. The default simulation configurations for the neighborhood, edge type, and grid shape should
be included in the properties file as well.

Next, the simulation should have its own Rule class, that extends the abstract Rule class. This class will dictate how each cell changes
in each step of the animation. Then, the animation should be included in the if statement in RuleMaker in the Grid class. Afterwards,
this should be all you need to do get a new simulation up and running. The frontend should not require any changing as it is not dependent
on the backend configuration.


* Describe the overall design of the program, without referring to specific data structures or actual code (focus on how the 
classes relate to each other through behavior (methods) rather than their state (instance variables)).

The overall design of the program is broken up into three parts - utility, backend, and frontend. The utility classes take in raw configuration
files and process them in a way that is readable for the backend. The backend then takes the initial configuration and decides how each
element interacts with each other, and how each cell changes from one time point to another in relation to its neighbors. The frontend
then takes this collection of cells and organizes them in a way that visually represents the "Grid" and animates them in time, using
the rules and processes that the backend has specified.

* Justify why your overall code is designed the way it is and any issues the team wrestled with when coming up with the final design.

Our code is ultimately focused on flexibility, and I think we have achieved that with the way that the backend and frontend are generalized.
We also tried to ensure that each part of the code function independently and are not completely reliant on each other for working. For example,
the backend does not import any front end JavaFX libraries and does not depend on the frontend package to develop the backend grid and rules,
which shows that the back and front end are both completely separated. Naturally, I think the three part design we put together for the
program makes sense, as it is along the lines of Reading, Processing, and Displaying, where the reading process is exemplfied by the utility
package with the CSVReader class, and processing and displaying are represented by the backend and frontend, respectively. 


### Flexibility

* Describe what you think makes this project's overall design flexible or not (i.e., what new features do you feel it is able 
to support adding easily).

The generalizability of the backend, for example the Grid class and the Rule abstraction, really aids the program's flexibility.
With these abstractions, you only have to add a few lines of code and a new Rule extension in order to add a new type of simulation.
And since the frontend does not depend on any of these backend processes, it will be able to visualize the new simulation without an 
issue, as long as the Grid and Rules are implemented correctly. It is also easy to add new types of frontend Grid Shapes due to the abstraction
of the GridMaker class. The abstractions of the Neighborhood class also allow it to add new types of Neighbor rules easily. 

* Describe one feature from the assignment specification that was implemented by a team mate in detail:

The Rule abstraction is an excellent example of our program's flexibility that was implemented by my teammates. In this abstraction,
the constructor makes a new, updated cell according to that cell's rules in the previous grid. It also gives the new cell the 
resource bundle of the previous cell. This method is the same across all of the simulation types; where the abstraction comes into play
is the update method, which is abstract and will require a different implementation with each simulation. Each cell contains an image
of their own neighbors, so the update method only requires a Cell type parameter, and not the entire grid, making it more efficient. 

* What is interesting about this code? (why did you choose it?)

I think the variability of the complexity of this abstraction was interesting, because for some simulations, the update method is simple
and only a few lines of code. In others, the update method needs multiple helper methods, such as in PredatorPrey. This just further
cements the idea that this abstraction was necessary, because I can't imagine how complex and long the alternative class would have been
had we lumped all of the simulations into one class. 


* Describe the design of this feature in detail? (what parts are closed? what implementation details are encapsulated? what assumptions
 are made? do they limit its flexibility?)
 
The feature is based off of an abstraction in the Rule class. It consists of a constructor that is universal between all of the extensions,
while the only abstract method is the update method, which is implemented differently in each simulation type. This class is relatively
closed because there are no instance variables (at least in the abstract class) to be given away. This makes the class even simpler and
more flexible. In some of the implementations, like PredatorPrey, there are some private instance variables that are only available to that
class, so it is very closed off.
 
* How flexible is the design of this feature? (is it clear how to extend the code as designed? what kind of change might be hard given
 this design?)
 
I think the design is very flexible, as it is relatively simple to add new Rules for new simulations, as I have described above.

### Your Design

* Describe an abstraction (either a class or class hierarchy) you created and how it helped (or hurt) the design.

In the frontend package, I abstracted out the GridMaker class, which essentially creates the visual grid and sets it on an AnchorPane
in the stage. It is abstracted because there are multiple different types of shapes that could be required to visualize the grid,
such as hexagons, squares, and triangles. Thus, instead of lumping it all in one class, I chose to abstract out the class, and kept
the methods that were in common with every implementation within the abstract class. These methods included the ones that
gave each "cell" its color or image. Since all of the "cells" were some type of JavaFX shape, the methods to give them a color or image
were the same, so I kept those in the abstract class. Where each shape differed was how it was arranged on the screen, and the calculations
involved. Some were harder than others, such as hexagon requiring much more difficult calculations than square or triangle. Overall,
I believe this abstraction helped the design as it really made it easy to switch between shape types and to add new shape types.

* Discuss any Design Checklist issues within your code (justify why they do not need to be fixed or describe how they could be fixed 
if you had more time).

- Communication

The only issue with communication is that there are some magic variables used when calculating the position of each shape in the grid.
We had the option of extracting these to instance variables, but since there were so many and they were all relatively arbitrary,
we just decided to keep them. 

- Modularity

I think the abstraction does pretty well with modularity, as we have avoided public static variables (only final variables used),
and the getter methods that we do use are necessary for the SimulationViewer to get the required images and shapes/grids. All of the
getter methods are functional.

- Flexibility

A big part of my refactoring was to reduce duplicated code (not necessarily in AbstractGridMaker since it was already pretty good) within
the frontend code, which I had mostly wrote. I implemented multiple helper methods that helped generalize some similar/duplicate code within
the frontend.


* Describe one feature that you implemented in detail:
Provide links to one or more GIT commits that contributed to implementing this feature, identifying whether the commit represented 
primarily new development, debugging, testing, refactoring, or something else.

One of the features that I implemented was the abstraction of the visual grid maker. Originally, we had a single GridMaker class that
would only make a grid with square cells. However, when we realized we needed to have diverse shapes for our grid, I decided that abstracting
out the class would be the best course of action. Here is the [commit where I first added the abstraction](https://coursework.cs.duke.edu/compsci307_2019spring/simulation_team04/commit/db19bbe0c272e393fa1dd482fc6c99df46f0c099).
The idea behind the abstraction was that the abstract class would have all the methods necessary to update the grid in terms of 
filling the cell with the appropriate color/image, while the classes that extended the abstraction would simply initialize the grid's
cells with the its respective shape. For example, here is an implementation for a [triangular shaped grid](https://coursework.cs.duke.edu/compsci307_2019spring/simulation_team04/commit/8c508cf2d8dc028721848253597176ea39184838).


* Justify why the code is designed the way it is or what issues you wrestled with that made the design challenging.

The code is designed this way to reduce the amount of duplicate code. Instead of having separate classes for each type of grid shape,
and having them contain their own methods that update the cell colors/images, I compiled all of those common methods into the abstraction
so that all of the extensions could access it and thus eliminate the possibility of duplicate code. It also makes the code very flexible
in that it is very simple to add new types of grid shapes; you simply have to make a new class that extends the abstract class and write
the makeGrid method. 


* Are there any assumptions or dependencies from this code that impact the overall design of the program? If not, how did you hide or 
remove them?

It has a dependency on the Cell class, which is necessary since it has a direct relationship to each Cell, as it creates the grid from
the Cell's current state and location. 

### Alternate Designs

* Describe two design decisions made during the project in detail:

1. Abstracting out GridMaker

Like I described above, abstracting out the GridMaker for the frontend package was a major design decision that I think ultimately benefited
the project. We originally had a class called GridPaneMaker which esentially created a Grid Pane that consisted of square shapes that
represented the cells of the simulation. However, this was not particularly flexible when considering other types of cell shapes, since
Grid Panes are locked in their configuration, and a hexagonal or triagonal grid requires fitting shapes with each other and not necessarily 
right next to each other as is the case with squares/rectangles. Thus, abstracting out the GridMaker class and having the Square, Hexagon,
and TriangleGridMaker classes extend it solved this issue. More details are available in the section above, but essentially this increased
the flexibility of the code and made it easier to alter different types of grids without affecting the others. 

2. Keeping Grid generalized but not abstract

One aspect of our program that we were especially proud of was how generalized the Grid class was. While the neighborhood and Rule classes
all utilized inheritance hierarchies, the Grid class was a universal class that was used by all types of grids and also did not require
an abstraction. It contains all of the methods required to initialize and update the grid and cell states, while also being general
enough to apply to all of the simulations.

* What alternate designs were considered?

For the GridMaker, we first had an all-inclusive GridPaneMaker that was not flexible enough to consider other types of shapes, such as
hexagons and triangles. For Grid, we were thinking of abstracting it so it would be specific to each simulation type, but realized it 
was not necessary. Additionally, if we were to abstract it, it would actually hurt the encapsulation so we thought keeping it a general
class was the best choice. We were also thinking about adding enumerations to the Grid, since there is a quite lengthy if statement
to determine the type of Rule class that it should initialize:
```
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
An enumeration would have definitely helped but we did not have time to implement it. 

* What are the trade-offs of the design choice (describe the pros and cons of the different designs)?

Cons for the general GridPaneMaker that would have included all of the shapes is that it is not flexible enough to visualize all 
shape types. Pros for the abstract GridMaker are that it is much more flexible and can accommodate many more grid shape types. However,
the abstraction comes at the cost of some encapsulation, since any changes in the super class can ripple through the class hierarchy.

The biggest con of the Grid design choice was not using enumerations in place of the large "if" statement. Having enumerations would
be preferable since the rule type associated with each simulation type is constant and will never be changed. Thus, they should be treated
as such. 

* Which would you prefer and why (it does not have to be the one that is currently implemented)?

I think the design choices we made for both instances are the best we could have done, with the exception of not using enumerated types.
It is interesting because the reasoning behind selecting these two designs are essentially opposite. We went with the AbstractGridMaker
because it makes the code more flexible and pretty much because we needed to have different types of shape, which our previous
design did not allow us to have. On the other hand, we chose not to abstract Grid because 1) it was not necessary, and 2) abstracting
would have cost encapsulation to some level. There are pros and cons to both designs but in the end, I think the choices complemented
our program well.
