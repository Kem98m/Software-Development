# Duplicate Code
* Fixed button positioning in gridpane in Simulation Viewer by extracting method (positionButton)
* Removed comments from some Rule classes and refactored return statement in Rule
* Extracted getRandCell method in Predator Prey Rule in order to prevent duplicate code.
* Extracted method from duplicate FileChooser lines in GUI and SimulationViewer
* Attempted to extract method from SimulationViewer.setUpSim in lines 245-248 but the method would take in too many
parameters for it to be reasonable.
* Instead of extracting method, we changed the .add to .addAll and were able to add all of the buttons in one line.

#Reviewing top 3 longest methods:

Long method #3 RPSRule.update
* The method does two things: computes the distribution of neighbors (lines 14-31), and makes a new cell (34-41)
* There is a switch statement (20-30) and a long if else statement (34-40)
* We really think that the switch statements and if statements are the whole point of this class, and the update method. There isn't much we can do to get rid of those, however, we did extract the first half of the update method in order to make it truly have one job and to make it shorter.

GUI.makeColorChooser                                                                                                         
* There is a lot of duplicate code that can be extracted out. The common themes are that multiple nodes are made and for each  VBox, it interacts the same way with the rectangles and color choosers so we will refactor that.
                                                                                                                               
SimulationViewer.setUpSim
* Reduced duplicate code as described above in Duplicate Code
* Extracted method that initializes the buttons for simOptions in order to reduce the code in setUpSim   


# Checklist Refactoring
* Made maps and sets concrete throughout your code.
* Magic variables are keys for properties file so we left that as it is.
* We are working on Exception handling and we will fix them while we include error handling.

# General Refactoring 
* Make a new abstract class neighborhood --> the hope is this will allow for increased flexibility between simulations, especially as we need to change how neighbors are found.
* Abstract some of the front end, such as the shape displayed in the graphical grid. 
