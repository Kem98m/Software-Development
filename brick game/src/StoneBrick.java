import java.util.ArrayList;

public class StoneBrick extends Brick {
    private boolean hitOnce;
    public StoneBrick() {
        super("brick1.gif");
        hitOnce = false;
    }
    public Type getType() {
        return Type.STONE;
    }

    public boolean ballCollision(Ball gameBall, Paddle gamePaddle, ArrayList<PowerUp> gamePowerUps) {
        if (!(hitOnce)) {
            hitOnce = true;
            return false;
        }
        return true;
    }
}
