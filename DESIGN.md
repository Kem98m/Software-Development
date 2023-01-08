# High-level design goals
We tried to design the breakout game so that developing the game further, by adding new features or modifying
current ones, would be straightforward for the developer, without having to change already written code. 
We thought the types of bricks, as well as powerups, contained the most room for additional features and better development,
so those classes/objects were abstracted, allowing additional powerups and brick types to be added easily, or customized easily.


# How to add features
For new powerups, you would make a new class that extends PowerUp.java, which is an abstract class. The abstract class
already implements a constructor to spawn the powerup at the brick specified, and a method that moves the powerup downwards
at a user-defined speed. The abstract method powerUpEffect() must be implemented in each specific implementation, as this method
determines what occurs after the player catches the powerup with their paddle. 

For new bricks, you would make a new class for that brick type and extend Brick.java, an abstract class. The abstract class
contains an enumeration for all the brick types so that a brick's type can be determined by other classes. Thus, the new brick type
would also have to be enumerated in this abstract class - to add this brick type to level configurations, you would have to edit
Data.getDataFromFile(). This method contains a switch case that reads in the level configuration file. We set the level configuration
files to be a series of integers separated by commas, where each integer corresponds to a brick type, and 0 corresponds to no brick.
So, when making a new brick, you would add a switch case for the integer-value of the new brick type in the level configuration files, and
construct the new brick type object. Each brick implementation must also implement its own brickCollision method - this is what
differentiates each brick, and determines what happens after the collision occurs. 

For new levels, you must create a new .txt file in resources/Level, with the specific filename of "levelX.txt", where X is an integer
higher than the previous highest level by one. Currently the game has three levels - level3.txt is the third file in resources/Level. 
level4.txt must contain level 4's configuration. Then, in Data.java, modify the public static final int NUMBER_OF_LEVELS to X, the highest level
number in the folder. Data.java reads all the level configuration files in order from level0 to the highest ones, and stores all the configurations.

# Design choices and trade-offs
Overall, we tried to make as many classes as possible to divide the program's working pieces as intuitively as possible. This worked
really well for the basic parts of the game - the paddle, the ball, and the bricks. As further development continued, we realized 
there was an opportunity for abstraction, specifically in Bricks and PowerUps since multiple types of each object should appear in the game.
For the scope of this project, little modification happened to the ball or the paddle, so we left each of them having their own standard implementation.
I think to make the game as customizable as possible, however, nearly every native object in the game should be abstracted. If Ball was abstract,
a developer could make their own ball easily - differences like how the ball bounces off the paddle could be accounted for in the specific implementation,
while standard methods like inverting the ball's X or Y velocity could be implemented in the abstract Ball class. Paddle is open for less customization, but still
could be open to easier modification if abstracted. One of the trade-offs we faced in the abstraction of Bricks and PowerUps. Each abstract class
has a method that determines what happens for each type of powerup/brick (see powerUpEffect and brickCollision as aforementioned). These methods will/must 
be contained in every kind of Brick or PowerUp object, so we abstracted the method in the top abstract class. The trade-off was that depending
on the brick or powerup's specific effect, the method's parameters would vastly differ. For example, a PowerUp that causes a change in the score
only needs access to the Status object from GameHandler (TwoTimesScore.java). But a PowerUp that causes the ball to slow down needs access to the game's
Ball object. Thus, the abstract powerUpEffect() method in PowerUp.java would have to contain the union of all the parameters needed across all the powerUps, 
since an abstract method cannot be implemented with different parameters (as far as my knowledge goes). This also happened in Brick.brickCollision(), albeit to a lesser degree.
This somewhat contradicts the purpose of abstraction - to make a new PowerUp object, you would extend PowerUp, and implement the specific
powerUpEffect() method. However, if this method/specific power up effect needed access to an object not currently passed into powerUpEffect(), it would have to be added
in PowerUp.java. We could have simply not abstracted the powerUpEffect() method, and let each implementation of PowerUp 
declare/define and implement its own powerUpEffect(), but this also defeats the purpose of abstraction in a different sense. When the PowerUp
is caught by the paddle in the game, the GameHandler class simply needs to call PowerUp.powerUpEffect() to give the player the powerUp, without having to
check what kind of powerUp it is. That was the design goal in abstracted PowerUps. If powerUpEffect() was not abstracted, the GameHandler class
would have had to determine the PowerUp's specific implementation, in order to determine which parameters to pass. This would be somewhat equivalent to 
not having all PowerUps extended from one parent abstract class, and determining what PowerUp was received and then implementing its effect, all in
GameHandler's step(). So, we decided abstraction of the method was the better choice, otherwise the amount of code reduction/refactoring would not be nearly as substantial.
In addition, I think we could have used the enumeration of the Bricks more intelligently, and maybe making Data.getDataFromFile() shorter, but this was
mostly insignificant. 
Another issue was having multiple PowerUps on the game's display. We had an ArrayList of PowerUps which contained all of the "active" or currently falling
powerups on the screen. If a powerUp brick was hit and a powerup was spawned, we added the powerup object to this array. If a powerup was caught by the paddle
or fell out of the game's screen, we removed it from the ArrayList. We ran into concurrent modification errors with this ArrayList when removing and adding in for-loops,
because at every step of the game, we must update the position of all powerups, and check to remove them if caught/fell out of game display. Debugging this proved to be troublesome,
and so we decided to let there only be one active powerUp falling at a time. This is still implemented with an ArrayList (its size will always be either 0 or 1), for the sake of
implementing multiple powerUps simultaneously, in the future.  

# Assumptions/decisions
As aforementioned, we decided to have only one active powerUp at a time. A few decisions were made in regards to what the developer
needs to specify - mostly global variables. For example, the paddle's image, ball's image, how many levels there are in resources/Level, Game window width/height, etc.
For the most part, assigning all of these values can be done easily without much effort or thought. The exception would be LEVEL_WIDTH and LEVEL_HEIGHT in GameHandler().
These variables were used to specify the width of a row of bricks in the level configuration file, but once the code there was refactored, they are not needed. (We did not make the change
to substitute them out, however). We also assumed that the ball would always bounce 45 degrees off the walls and the paddle - we wanted to make the direction correlate to
where on the paddle the ball collided as an extra feature, but could not. 
   
