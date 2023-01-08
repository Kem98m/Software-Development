public class DoublePaddleSpeed extends PowerUp {
    public DoublePaddleSpeed(double x, double y) {
        super("sizepower.gif", x, y);
    }

    @Override
    public void powerUpEffect(Ball gameBall, BrickGrid gameBricks, Paddle gamePaddle, Status gameStatus) {
        gamePaddle.setSpeed(gamePaddle.getSpeed() * 2);
    }
}
