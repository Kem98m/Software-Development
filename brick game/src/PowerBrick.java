import java.util.ArrayList;
import java.util.Random;
public class PowerBrick extends Brick {
    private PowerUp myPowerUp;
    private int TOTAL_POWER_UPS = 3;
    public PowerBrick() {
        super("brick3.gif");
    }
    public Type getType() {
        return Type.POWER;
    }
    public boolean ballCollision(Ball gameBall, Paddle gamePaddle, ArrayList<PowerUp> gamePowerUps) {
        Random random = new Random();
        int whichPower = random.nextInt(TOTAL_POWER_UPS) + 1;
        switch (whichPower) {
            case 1:
                myPowerUp = new DoublePaddleSpeed(getShape().getX() + getShape().getBoundsInLocal().getWidth()/2, getShape().getY() + getShape().getBoundsInLocal().getHeight()/2);
                break;
            case 2:
                myPowerUp = new LowerBallSpeed(getShape().getX() + getShape().getBoundsInLocal().getWidth()/2, getShape().getY() + getShape().getBoundsInLocal().getHeight()/2);
                break;
            case 3:
                myPowerUp = new TwoTimesScore(getShape().getX() + getShape().getBoundsInLocal().getWidth()/2, getShape().getY() + getShape().getBoundsInLocal().getHeight()/2);
                break;
            default:
                break;
        }
        gamePowerUps.add(myPowerUp);
        return true;
    }
}
