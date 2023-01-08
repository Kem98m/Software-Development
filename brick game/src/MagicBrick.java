import java.util.ArrayList;

public class MagicBrick extends Brick {
    public MagicBrick() {
        super("brick2.gif");
    }
    public Type getType() {
        return Type.MAGIC;
    }
    public boolean ballCollision(Ball gameBall, Paddle gamePaddle, ArrayList<PowerUp> gamePowerUps) {
        gamePaddle.setX(getShape().getX());
        return true;
    }
}
