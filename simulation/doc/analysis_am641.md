CompSci 307: Simulation Project Analysis
===================

> This is the link to the [assignment](http://www.cs.duke.edu/courses/compsci307/current/assign/03_simulation/):

Design Review
=======

### Status

- What makes the code well-written and readable (i.e., does it do what you expect, does it require comments to understand)?

We tried to make sure that our code is very readable by naming every variable and method based on the value it is storing, 
or the value or the work it is doing respectively. We also made to sure to not have any magic values instead we used static final
constants. We added javadoc comments when and where required to better explain certain abstraction or link between two classes. 

- What makes this project's code flexible or not (i.e., what new features do you feel are easy or hard to add)?

It is actually very easy to add a new feature to this project as that was our goal throughout the decisions
we took while deciding the design of the project. So like anything from adding a simulation to adding a new grid type should take at most
one new class and very little to none modifications elsewhere.


- What dependencies between the code are clear (e.g., public methods and parameters) and what are through "back channels" 
(e.g., static calls, order of method call, requirements for specific subclass types)? 

We ran the check for dependency for IntelliJ and the dependencies are really coherent and there are no cyclic dependencies.
We also never used static calls so the order of method calls should not affect the overall output at all.


### Overall Design

* As briefly as possible, describe everything that needs to be done to add a new kind of simulation to the project.

We need to make a class extending the rule class that defines the rules of the new simulation. Then we need
to add the new simulation in Grid which basically would let grid know what simulation to run based on the csv file for this simulation.
For the csv file we need to make sure that the first string has the name of the simulation. Additionally, we need 
a properties file to specify other specifications of the simulation like cardinal neighbors, toroidal edges, etc.


* Describe the overall design of the program, without referring to specific data structures or actual code (focus on how the 
classes relate to each other through behavior (methods) rather than their state (instance variables)).

We have three packages frontend, backend, util. The classes in backend sustain the grid and its state at any given moment.
The frontend classes helps display that grid and its state. The util package has other helped method that help run the simulation
in general.

* Justify why your overall code is designed the way it is and any issues the team wrestled with when coming up with the final design.

We tried to break down every object in the simulation to each class and this really helped us later on as that both gave
us the flexibility to add new stuff while having very less to debug. Some abstractions we implemented later on for example
neighborhood which we could have done in the start but keeping a class for every object ideology helped us as we 
had to change very few lines of code to implement abstraction. 

### Flexibility

* Describe what you think makes this project's overall design flexible or not (i.e., what new features do you feel it is able 
to support adding easily).

As mentioned before, the primary goal from the beginning of the project was to make the project as flexible as possible.
Adding a simulation, adding new neighborhood, etc. can be made easily because of the generalization of the backend
and the abstraction. Even in frontend there are abstractions as and where required to support new features like new grid type.

* Describe one feature from the assignment specification that was implemented by a team mate in detail:

The GridPaneMaker is a feature that my teammate implemented. He used abstraction for it and had an abstractgridmaker and have different
gridmakers like squaregridmaker for example to make the frontend for the square grid. 

* What is interesting about this code? (why did you choose it?)

I think the abstraction that this code implements is very flexible and is based on as little abstraction as possible.
This helps customize the grid to a great extent.


* Describe the design of this feature in detail? (what parts are closed? what implementation details are encapsulated? what assumptions
 are made? do they limit its flexibility?)
 
The feature is based on AbstractGridMaker. All its instance variables are encapsulated and it just have abstract methods
to only manipulate these variables in certain way so that the grid pane behaves as expected while giving the user the
customizing property.
 
* How flexible is the design of this feature? (is it clear how to extend the code as designed? what kind of change might be hard given
 this design?)
 
I think the design is very flexible, as it is very easy to add a new kind of grid pane as you would have to just write
one extra class and implement the abstract methods to run that grid.

### Your Design

* Describe an abstraction (either a class or class hierarchy) you created and how it helped (or hurt) the design.

In the package backend, I created the Rule class which was an abstract class which helps in adding a simulation very easily. It 
tries to refactor out the code that every simulation will have in common for example ,updating the state of a cell, and leaves the rest for the 
new rules of the new simulation to fill out. This way adding a new simulation is really easy.

* Discuss any Design Checklist issues within your code (justify why they do not need to be fixed or describe how they could be fixed 
if you had more time).

- Communication

I think we used a static final variables which could have been converted to enumerators. If I had more time, I would definitely use 
enumerators to provide more structure to the code.

- Modularity

I think we did a good job in the modularity aspect of the code as we tried to hide instance variables and data as much as possible
and in most of the cases, most of the data has package-private access.

- Flexibility

The code itself is very flexible and adding any new features should be very easy.


* Describe one feature that you implemented in detail:
Provide links to one or more GIT commits that contributed to implementing this feature, identifying whether the commit represented 
primarily new development, debugging, testing, refactoring, or something else.

One of the features that I implemented was the abstraction of the rule class. We started with this as we knew there were multiple simulations
we need to do and having an abstract class will be really useful. Here is the [commit where I first added the abstraction](https://coursework.cs.duke.edu/compsci307_2019spring/simulation_team04/commit/bda83c88a8704856067054db718adfdc2d28fa4c).
In this commit (which is one of our initial commits) we can see that we added the Rule class very early and the core concept of the Rule class remained the same even till the end of
the project. This shows that this abstraction really helped in adding new features. The commit also has two implemented rules.

* Justify why the code is designed the way it is or what issues you wrestled with that made the design challenging.

The code is a basic abstraction designed keeping in mind to write concrete methods for code that will be common for
every simulation and giving flexibity to the class extending for the rest of the parts. This makes adding any kind of new simulation easy.


* Are there any assumptions or dependencies from this code that impact the overall design of the program? If not, how did you hide or 
remove them?

It has a dependency on the Cell class, which is important since it needs to know how the state of cell to update it.

### Alternate Designs

* Describe two design decisions made during the project in detail:

1. Abstracting out Neighborhood Class

Abstracting the neighborhood class was one of the decisions we took during the project. Initially we had an arraylist of cells to
represent the neighbors of a cell but then during complete when we had to implement different kinds of grid, making a neighborhood abstract
class became important as that gave us a lot of flexibility to generalize our code and still have different kinds of grid.

2. Keeping Grid generalized

We also made Grid generalized which was really interesting as we didn't need any abstraction for grid. The way we accomplished this was keeping
the working of common for all. Just the visual element was different which was taken care by grid pane maker.

* What alternate designs were considered?

We considered using the neighborhood as an arraylist of cells but that found out to be difficult while implementing different grid types
as the cardinal neighbors and diagonal neighbors are different for different grid type. So we had to implement a neighborhood class to account
for all the different elements.

* What are the trade-offs of the design choice (describe the pros and cons of the different designs)?

The trade-off was more lines of code and more classes for every grid type. But generalizing that class was impossible and 
as mentioned before hard to account for all the differences of different grid types.

* Which would you prefer and why (it does not have to be the one that is currently implemented)?

I think I would definitely go with the neighborhood abstraction as this really helps adding a new kind of grid easy while
adding/modifying little changes to other classes.
