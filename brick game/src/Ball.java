import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.awt.*;

/**
 * This class represents the ball object that will be used in the game.
 */
public class Ball {

    private ImageView myBall;
    private double myXVelocity = 1;
    private double myYVelocity = 1;
    private double mySpeed;

    /**
     * This constructor initializes all the instance variables of the Ball class.
     * @param imageFile specifies the image file to be used to represent the ball
     * @param windowWidth specifies the width of the game window
     * @param initialSpeed specifies the starting speed of the ball
     * @param gamePaddle specifies the paddle object in the game
     */
    public Ball(String imageFile , double windowWidth, double initialSpeed, Paddle gamePaddle) {
        var image = new Image(this.getClass().getClassLoader().getResourceAsStream(imageFile));
        myBall = new ImageView(image);
        mySpeed = initialSpeed;
        resetPosition(windowWidth, gamePaddle);
    }

    /**
     * This method helps set the ball's speed to a new speed.
     * @param newSpeed specifies the new speed of the ball.
     */
    public void setSpeed(double newSpeed) {
        mySpeed = newSpeed;
    }

    /**
     * This method helps get the ball's current speed
     * @return the current speed of the ball.
     */
    public double getSpeed() {
        return mySpeed;
    }

    /**
     * This method helps get the imageview of the ball that represents the ball in the game.
     * @return the imageview of the ball.
     */
    public ImageView getShape() {
        return myBall;
    }

    /**
     * This method gelps get the center x co-ordinate of the ball.
     * @return the center x co-ordinate of the ball.
     */
    public double getXCenter() {
        return (myBall.getX() + myBall.getBoundsInLocal().getWidth()/2);
    }

    /**
     * This method gelps get the center y co-ordinate of the ball.
     * @return the center y co-ordinate of the ball.
     */
    public double getYCenter() {
        return (myBall.getY() + myBall.getBoundsInLocal().getHeight()/2);
    }

    /**
     * This method helps take necessary actions on the event of collision of ball and window walls (left, top and right)
     * @param windowWidth specifies the width of the window
     */
    public void windowCollisions(double windowWidth) {

        if (myBall.getX() <= 0 || myBall.getX() + myBall.getBoundsInLocal().getWidth() >= windowWidth) {
            invertXVelocity();
        }
        if (myBall.getY() <= 0) {
            invertYVelocity();
        }
    }

    /**
     * This method helps take necessary actions on the event of collision of ball and paddle.
     * @param gamePaddle specifies the paddle object in the game
     */
    public void paddleCollision(Paddle gamePaddle) {
        if (myBall.getBoundsInParent().intersects(gamePaddle.getShape().getBoundsInParent())) {
            if (getXCenter() <= gamePaddle.getX() || getXCenter() >= gamePaddle.getX() + gamePaddle.getWidth()) {
                invertXVelocity();
            }
            if (getYCenter() <= gamePaddle.getY()) {
                invertYVelocity();
            }
        }
    }

    /**
     * This method helps take necessary actions on the event of collision of ball and any brick.
     * @param gameBricks specifies the current grid of bricks used in the game.
     * @return the brick's row number and column number as a Point object if there is a collision, else return null.
     */
    public Point brickCollision(BrickGrid gameBricks) {
        Brick[][] grid = gameBricks.getBricks();
        Point brickCoordinates;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] != null && myBall.getBoundsInParent().intersects(grid[i][j].getShape().getBoundsInParent())) {
                    if (getXCenter() <= grid[i][j].getShape().getX() || getXCenter() >= grid[i][j].getShape().getX() + grid[i][j].getShape().getBoundsInLocal().getWidth()) {
                        invertXVelocity();
                    }
                    if (getYCenter() <= grid[i][j].getShape().getY() || getYCenter() >= grid[i][j].getShape().getY() + grid[i][j].getShape().getBoundsInLocal().getHeight()) {
                        invertYVelocity();
                    }
                    brickCoordinates = new Point(i, j);
                    return brickCoordinates;

                }
            }
        }
        return null;
    }

    /**
     * This method helps check if the paddle missed the ball
     * @param windowHeight specifies the width of the window
     * @return true if the paddle fails to hit the ball back.
     */
    public boolean missedPaddle(double windowHeight) {
        if (getYCenter() >= windowHeight) {
            return true;
        }
        return false;
    }

    /**
     * This method helps reset the ball to its initial position.
     * @param windowWidth specifies the width of the window
     * @param gamePaddle specifies the paddle object in the game
     */
    public void resetPosition(double windowWidth, Paddle gamePaddle) {
        myBall.setX(windowWidth/2 - myBall.getBoundsInLocal().getWidth()/2);
        myBall.setY(gamePaddle.getY() - myBall.getBoundsInLocal().getHeight() - 1);
    }

    /**
     * This method helps update the ball position after every frame
     * @param elapsedTime specifies the time elasped between each frame
     * @param level specifies the current level as the ball speed of any level is dependent on the level number.
     */
    public void updatePosition(double elapsedTime, int level) {
        myBall.setX(myBall.getX() + ((level + 1) * getMyXVelocity() * mySpeed * elapsedTime));
        myBall.setY(myBall.getY() - ((level + 1) * getMyYVelocity() * mySpeed * elapsedTime));
    }

    /**
     * This method helps get the current ball velocity in x direction which helps identify direction of travel.
     * @return the velocity in the x direction.
     */
    public double getMyXVelocity() {
        return myXVelocity;
    }

    /**
     * This method helps get the current ball velocity in y direction which helps identify direction of travel.
     * @return the velocity in the y direction.
     */
    public double getMyYVelocity() {
        return myYVelocity;
    }

    /**
     * This method helps change velocity of the ball in the x axis.
     * @param newXVelocity specifies the new velocity in the x axis.
     */
    public void setMyXVelocity(double newXVelocity) {
        myXVelocity = newXVelocity;
    }

    /**
     * This method helps change velocity of the ball in the y axis.
     * @param newYVelocity specifies the new velocity in the y axis.
     */
    public void setMyYVelocity(double newYVelocity) {
        myYVelocity = newYVelocity;
    }

    /**
     * This method helps invert the direction of the ball in the x axis.
     */
    public void invertXVelocity() {
        setMyXVelocity(myXVelocity * -1);
    }

    /**
     * This method helps invert the direction of the ball in the y axis.
     */
    public void invertYVelocity() {
        setMyYVelocity(myYVelocity * -1);
    }
}

