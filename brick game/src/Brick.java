import javafx.scene.image.ImageView;
import java.util.ArrayList;

enum Type
{
    POWER, MAGIC, STONE, BLANK;
}
public abstract class Brick {
    private ImageView myBrick;

    public Brick(String brickImage) {
        myBrick = new ImageView(brickImage);
    }

    /**
     * This method gives access to the ImageView contained
     * @return image of the brick
     */
    public ImageView getShape() {
        return myBrick;
    }

    /**
     * This method gives access to the brick's type
     * @return the correct Type enum
     */
    public abstract Type getType();

    /**
     * This method determines the effect of the ball's collision with the brick, depending on the brick's type.
     * @param gameBall the Ball object being shown in the game
     * @param gamePaddle the Paddle object being shown in the game
     * @param gamePowerUps the list of active PowerUps being shown in the game
     * @return the boolean return value is used for bricks who take multiple hits to be destroyed, the method will return true
     * if those bricks just received their last collision, and false if they have more hits left. For all other kinds of bricks, it returns true always.
     */
    public abstract boolean ballCollision(Ball gameBall, Paddle gamePaddle, ArrayList<PowerUp> gamePowerUps);

}