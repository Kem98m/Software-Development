game
====

This project implements the game of Breakout.

Name: 

### Timeline

Start Date: 28 January 2019

Finish Date: 11 February 2019

Hours Spent: 40 hours

### Resources Used


### Running the Program

Main class: GameHandler

Data files needed: Folders Level and Tests are mandatory. Many of the GIFs given in the resources folder are also used - bricks 1-3, ball, paddle, and three powerups.

Key/Mouse inputs: 
* Left Arrow to move paddle to the left. 
* Right Arrow to move paddle to the right.
* Esc to exit. 
* R to reset the ball and paddle to their initial position. 
* Enter to start game during startup.
* Space to pause/play the game.

Cheat keys:
* Comma - have the ball traveling towards a corner and subsequently bouncing back in the exact direction it came from.
* Period - have the ball traveling towards a block and subsequently destroying it
* Slash - have the ball fall towards the bottom of the screen and the player subsequently losing a life
* S - reverse the ball's direction
Known Bugs:
* restarting during gameplay doubles the ball speed somehow. although this doesn't happen during any 
display menu after win/loss of previous game.
* could not maintain more than one active powerup on the screen at a time, ran into concurrent modification errors?

Extra credit:

N/A

### Notes

Our three brick types are stone brick which takes two hits to destroy, the power brick which produces falling powerups
and magic brick which teleports the paddle directly underneath the location of the brick.

### Impressions

Starting with separate classes for different objects/purposes did really help us switch from basic 
to complete easily while writing less lines of code. On the other hand, our code was hard to go back and abstract. 
Even though we split the game into many functional classes, for things like Brick/PowerUp, we mostly used instance variables
and conditional logic to determine what types of brick/power ups were being interacted with, and their effects. Abstracting
these classes made the project seem much cleaner, but was a hassle to code. As a result, we missed out on writing thorough test cases.
In general, it was just difficult to tell at what point we felt satisfied with the design. This project was obviously a big step
up from BabyNames, and without any prior data or rigorous feedback, I always felt like the design could be better / wasn't good enough.
Despite having fourteen or so classes, the main GameHandler class still seemed rather chaotic, and the step() method was around 50 lines.
