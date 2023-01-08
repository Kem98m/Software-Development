public class LowerBallSpeed extends PowerUp {

    public LowerBallSpeed(double x, double y) {
        super("extraballpower.gif", x, y);
    }

    @Override
    public void powerUpEffect(Ball gameBall, BrickGrid gameBricks, Paddle gamePaddl, Status gameStatus) {
        if (gameBall.getSpeed() > 30) {
            gameBall.setSpeed(gameBall.getSpeed() * 0.75);
        }
    }
}
