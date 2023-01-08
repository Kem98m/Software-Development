import javafx.scene.image.ImageView;

public abstract class PowerUp {
    private ImageView myPowerUp;
    public final double DROP_SPEED = 20;


    PowerUp(String imageFile, double x, double y) {
        myPowerUp = new ImageView(imageFile);
        myPowerUp.setX(x);
        myPowerUp.setY(y);
    }

    /**
     * This method gives access to the PowerUp's contained ImageView
     * @return the contained ImageView object
     */
    public ImageView getShape(){
        return myPowerUp;
    }

    /**
     * This method updates the position of the Power Up, and determines if the PowerUp should continue to fall in the game, or disappear.
     * @param elapsedTime passed from the game's step() method, used with the PowerUp's speed to update its position
     * @param windowHeight the Game's window height, to determine if the PowerUp has fallen out of bounds
     * @param gameBall the Ball object being used in the game
     * @param gameBricks the BrickGrid being used in the game
     * @param gamePaddle the Paddle being used in the game
     * @param gameStatus the Status being displayed in the game
     * @return true if the PowerUp should disappear, false if the PowerUp keeps falling
     */
    public boolean powerUpStep(double elapsedTime, int windowHeight, Ball gameBall, BrickGrid gameBricks, Paddle gamePaddle, Status gameStatus) {
        if (myPowerUp.getY() > windowHeight) {
            return true;
        }
        else if (gamePaddle.getShape().getBoundsInParent().intersects(myPowerUp.getBoundsInParent())) {
            powerUpEffect(gameBall, gameBricks, gamePaddle, gameStatus);
            return true;
        }
        else {
            myPowerUp.setY(myPowerUp.getY() + (elapsedTime * DROP_SPEED));
            return false;
        }
    }

    /**
     * This method enacts the PowerUp's effect, must be implemented for each specific PowerUp
     * @param gameBall  the Ball object being used in the game
     * @param gameBricks the BrickGrid being used in the game
     * @param gamePaddle the Paddle being used in the game
     * @param gameStatus the Status being displayed in the game
     */
    public void powerUpEffect(Ball gameBall, BrickGrid gameBricks, Paddle gamePaddle, Status gameStatus) {}
}
