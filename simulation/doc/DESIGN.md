### Provides the high-level design goals of your project

Most of our coding was designed in a way such that it is easy to add as many new features as possible and to extend
the software to do multiple similar things without changing a lot of code. The way we achieved this was by having classes
for every different things and abstracting them as much as possible. This helped us write methods using the interfaces 
which helped us generalize the methods to work for every object that extends the interface. For example, to add a new simulation
most of the changes we need to do is to just add a rule file describing the rule of the simulation. Everything from grid, neighborhood, frontend, etc.
don't need to be changed as the rule method can use the interface for neighborhood, grid, etc. to apply its rules.

### Explain, in detail, how to add new features to your project.

Features are easily added to our project. Because we split our project into three distinct packages, it is relatively simple to pinpoint
where you want to add your feature. For example, if you wanted to change how the raw data/configuration files are processed, you would
go into the util package and modify CSVReader. In there, you can determine how the configuration file is structured and make the corresponding
changes to the way the Scanner reads the .csv files. In the backend, our project is flexible enough so that you can add new types of
neighbor and edge rules, as well as different simulation rules quite simply. For example, to add a new type of simulation, 
using Jeffrey's words from his individual analysis:

"Starting from the very beginning, each simulation needs a configuration .csv file and a .properties file. The .csv file will be the
initial configuration and should include the name of the simulation, the size of the grid (width by height) and the actual grid state
values, separated by commas. The properties file should include the number of potential states in the simulation, and any default
color codes/image file paths for each state. The default simulation configurations for the neighborhood, edge type, and grid shape should
be included in the properties file as well.
Next, the simulation should have its own Rule class, that extends the abstract Rule class. This class will dictate how each cell changes
in each step of the animation. Then, the animation should be included in the if statement in RuleMaker in the Grid class. Afterwards,
this should be all you need to do get a new simulation up and running. The frontend should not require any changing as it is not dependent
on the backend configuration."

Adding a new type of neighborhood rule is easy as well, since the class is abstracted. The corresponding abstract methods simply need to 
be overrode. 

Adding frontend features is also relatively simple. We tried to divide up the interface designer and the actual interface that the
user interacts with with the GUI and SimulationViewer class. For the most part, the GUI class synthesizes the interface components
and gives them to the SimulationViewer class with getter methods. The SimulationViewer is the class that actually extends the JavaFX
Application class and runs the Timeline animation. To add new frontend features, you can modify existing menus within GUI and
SimulationViewer, such as the methods simSettingsPage and makeFullCustomizerMenu. You can add more buttons or Labels for instructions
by simply adding to the root node within each menu and positioning it to your liking. SimulationViewer, for the most part, initializes
a GUI object and creates the necessary menus/buttons with the methods within GUI. You can modify the order in which they appear by simply 
rearranging the lines of code, as well as add your own menus for when a certain button is pressed, etc. 


### Justify major design choices, including trade-offs (i.e., pros and cons), made in your project

The major framework of the project is as follows: We have various front end classes to visualize the simulations, a flexible grid class and cell
class to handle the relationship between cells, an abstract class and extensions for the various rules for each simulation, an abstract class and 
extensions for different types of neighborhoods (for example a hexagonal shaped cell has a different neighborhood from square), and lastly we had
several utility classes.

One of the major design choice we made early on was to have a central, highly generalized grid and cell. We wanted the heart of the simulation to
stay almost constant. The major pro of this is that it made adding new simulations later on way easier. Instead of heavily modifying or abstracting
our grid and cells so that they had to be specifically written for each type of simulation, we chose to abstract only the simulation rules and 
neighborhoods. By abstracting the rules, whenever we wanted to add a new simulation, all that was required was making a new rule class, and adding
it to a switch statement in grid. This allowed us to save time and reduce duplicate code. The advantages are similar with the abstract neighborhood
class.

There are also cons to all this. Making the rules and grid separate was a difficult engineering challenge because often times the rules required
full knowledge of the grid. Thus, it was difficult to maintain the dependency inversion principle. Ultimately, I think we made the correct call,
when we were adding simulations we were really thankful for our initial set up.

### State any assumptions or decisions made to simplify or resolve ambiguities in your the project's functionality

There aren't many assumptions made. However, few come to mind:
- In Predator Prey, the maximum number of steps between reproduction is assumed, as is the range of possible starting values for a fish/shark's reproduction and death.
- In segregation, the number of races is assumed to be two, and the number of like race neighbor cells for a cell to be satisfied with its position is 3.
- Some formatting and structure is assumed in the csv and configuration files.

### How to run our individual analysis simulations

* Choose a netID to run.
* In SimulationViewer, comment out line 145 and uncomment out line 146: 
```
<!--reader = new CSVReader("/configurations/" + configName);-->
reader = new CSVReader("/configurations/individualSimulations/" + configName);
```
* In SimulationViewer, comment out line 152 and uncomment out line 153, and replace the netID after analysis_ with the appropriate netID:
```             
<!--myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + simType);-->
myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "individualProperties/analysis_NETID");
```
* In Grid, comment out line 30 and uncomment out line 31, and replace the netID with the netID of the chosen simulation.
```
    <!--ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PATH + mySim);-->
ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PATH + "individualProperties/analysis_jwg33");
```

* When prompted to select a configuration file, navigate to /data/configurations/individualSimulations. Choose your selected simulation file.
* Run the simulation!

