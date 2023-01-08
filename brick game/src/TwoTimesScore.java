public class TwoTimesScore extends PowerUp {
    public TwoTimesScore(double x, double y) {
        super("pointspower.gif", x, y);
    }

    @Override
    public void powerUpEffect(Ball gameBall, BrickGrid gameBricks, Paddle gamePaddle, Status gameStatus) {
        gameStatus.setScore(gameStatus.getIntScore() * 2);
    }
}
