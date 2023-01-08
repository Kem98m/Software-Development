import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class represents the paddle object that will be used in the game.
 */
public class Paddle {

    private ImageView myPaddle;
    private double mySpeed;

    /**
     * This constructor initializes all the instance variables of the Paddle class.
     * @param imageFile specifies the image file to be used to represent the paddle
     * @param windowWidth specifies the width of the game window
     * @param windowHeight specifies the height of the game window
     * @param initialSpeed specifies the starting speed of the paddle
     */
    public Paddle(String imageFile, double windowWidth, double windowHeight, double initialSpeed) {
        var image = new Image(this.getClass().getClassLoader().getResourceAsStream(imageFile));
        myPaddle = new ImageView(image);
        mySpeed = initialSpeed;
        resetPosition(windowWidth, windowHeight);
    }

    /**
     * This method helps get the imageview of the paddle that represents the paddle in the game.
     * @return the paddle of the ball.
     */
    public ImageView getShape() {
        return myPaddle;
    }

    /**
     * This method helps get the width of the paddle
     * @return the width of the paddle.
     */
    public double getWidth() {
        return myPaddle.getBoundsInLocal().getWidth();
    }

    /**
     * This method helps get the current speed of the paddle
     * @return the current speed of the paddle.
     */
    public double getSpeed() {
        return mySpeed;
    }

    /**
     * This method helps change the speed of the paddle
     * @param newSpeed specifies the new speed of the paddle
     */
    public void setSpeed(double newSpeed) {
        mySpeed = newSpeed;
    }

    /**
     * This method helps get the starting x co-ordinate of the paddle
     * @return the starting x co-ordinate of the paddle
     */
    public double getX() {
        return myPaddle.getX();
    }

    /**
     * This method helps change the starting x co-ordinate of the paddle
     * @param newX the new starting x co-ordinate of the paddle
     */
    public void setX(double newX) {
        myPaddle.setX(newX);
    }

    /**
     * This method helps get the starting y co-ordinate of the paddle
     * @return the starting y co-ordinate of the paddle
     */
    public double getY() {
        return myPaddle.getY();
    }

    /**
     * This method helps reset the position of the paddle to its starting position.
     * @param windowWidth specifies the width of the window.
     * @param windowHeight specifies the height of the window.
     */
    public void resetPosition(double windowWidth, double windowHeight) {
        myPaddle.setX(windowWidth/2 - myPaddle.getBoundsInLocal().getWidth()/2);
        myPaddle.setY(windowHeight - myPaddle.getBoundsInLocal().getHeight());
    }

    /**
     * This method helps bound the paddle to the left wall
     * @return true if it has crossed the left wall of the window.
     */
    public boolean checkLeftBounds() {
        return (!(myPaddle.getX() - mySpeed < 0));
    }

    /**
     * This method helps bound the paddle to the right wall
     * @return true if it has crossed the right wall of the window.
     */
    public boolean checkRightBounds(int windowWidth) {
        return (!(myPaddle.getX() + myPaddle.getBoundsInLocal().getWidth() + mySpeed > windowWidth));
    }
}