import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameHandler extends Application {

    public static final String TITLE = "Amik and Rohan's BREAKOUT in JavaFX";
    public static final int WINDOW_HEIGHT = 500;
    public static final int WINDOW_WIDTH = 700;
    public static final int FRAMES_PER_SECOND = 60;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    public static final int INITIAL_LIVES = 3;
    public static final String BALL_IMAGE = "ball.gif";

    public static final int INITIAL_BALL_SPEED = 75;

    public static final String PADDLE_IMAGE = "paddle.gif";
    public static final int INITIAL_PADDLE_SPEED = 10;

    public static final int LEVEL_WIDTH = 10;
    public static final int LEVEL_HEIGHT = 12;
    public static final Paint BACKGROUND = Color.HONEYDEW;

    private int myLives = INITIAL_LIVES;
    private int myLevel;
    private boolean inPlay = true;
    private String testCase = "";

    // objects in game
    private Scene splashScreen;
    private Ball myGameBall;
    private Paddle myGamePaddle;
    private BrickGrid myGameBricks;
    private Group myGroup;
    private Status myStatus;
    private Animation myAnimation;
    public PowerUp activePowerUp;
    private Text myText;

    // TESTING STATE
    private double initialXpos;
    private double initialYpos;
    private double initialXvel;
    private double initialYvel;


    /**
     * This method helps start the game.
     * @param stage The current stage object for the game.
     */
    public void start(Stage stage) {
        splashScreen = startScreen(WINDOW_WIDTH, WINDOW_HEIGHT, BACKGROUND);
        stage.setScene(splashScreen);
        stage.setTitle(TITLE);
        stage.show();
    }

    /**
     * This method helps create the starting splash screen
     * @param width specifies the width of the game window
     * @param height specifies the height of the game window
     * @param background specifies the background color of the splash screen
     * @return the scene created
     */
    public Scene startScreen(int width, int height, Paint background) {
        myGroup = new Group();
        var scene = new Scene(myGroup, width, height, background);
        Text rules = new Text();
        rules.setText("This is the famous game BREAKOUT. Use the arrow keys to control the paddle at the bottom of the screen. Use the paddle " +
                "to bounce the ball into the bricks. If you destroy all the bricks, you win! Every time you miss the ball with the paddle, you lose a life." +
                "If you lose all your lives, the game is over! Press ENTER to begin the game. Press COMMA, PERIOD, or BACKSLASH to run test scenarios.");
        rules.setWrappingWidth((double) WINDOW_WIDTH - 30);
        rules.setX(WINDOW_WIDTH/2 - rules.getBoundsInLocal().getWidth()/2);
        rules.setY(WINDOW_HEIGHT/2 - rules.getBoundsInLocal().getHeight()/2);
        rules.setFont(Font.font ("Verdana", 20));
        myGroup.getChildren().add(rules);
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        return scene;
    }

    /**
     * This method helps set up the game before each level
     */
    public void setupGame() {
        myLevel = 0;
        myGroup.getChildren().clear();
        initializeLevel();
        myText = new Text();
        myText.setText("You Lose! Boooo!");
        myText.setFont(Font.font ("Arial", 50));
        myText.setX(WINDOW_WIDTH/2-myText.getBoundsInLocal().getWidth()/2);
        myText.setY(WINDOW_HEIGHT/2-myText.getBoundsInLocal().getHeight()/2);
        myText.setFill(Color.RED);
        myText.setStroke(Color.BLACK);
        playGame();
    }

    /**
     * This method helps add all the needed objects to the scene based on the current level.
     */
    public void initializeLevel(){
        myGameBricks = new BrickGrid(myLevel);
        for (int i = 0; i < LEVEL_HEIGHT; i++) {
            for (int j = 0; j < LEVEL_WIDTH; j++) {
                if (myGameBricks.getBricks()[i][j] != null) {
                    myGameBricks.getBricks()[i][j].getShape().setX(j * myGameBricks.getBricks()[i][j].getShape().getBoundsInLocal().getWidth());
                    myGameBricks.getBricks()[i][j].getShape().setY(i * myGameBricks.getBricks()[i][j].getShape().getBoundsInLocal().getHeight() + 40);
                    myGroup.getChildren().add(myGameBricks.getBricks()[i][j].getShape());
                }
            }
        }
        myStatus = new Status(myLives, myLevel);
        myGroup.getChildren().addAll(myStatus.getDisplay());
        setBallAndPaddle();
    }

    /**
     * This method helps set the ball and paddle to their preset position.
     */
    public void setBallAndPaddle(){
        myGamePaddle = new Paddle(PADDLE_IMAGE, WINDOW_WIDTH, WINDOW_HEIGHT, INITIAL_PADDLE_SPEED);
        myGameBall = new Ball(BALL_IMAGE, WINDOW_WIDTH, INITIAL_BALL_SPEED, myGamePaddle);
        myGroup.getChildren().add(myGamePaddle.getShape());
        myGroup.getChildren().add(myGameBall.getShape());
    }

    /**
     * This method helps create the animation required to play any given level.
     */
    public void playGame() {
        var frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step(SECOND_DELAY));
        var animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        myAnimation = animation;
        animation.play();
    }

    /**
     * This method helps check the test cases.
     * @return true if the test case evaluate to be true.
     */
    public boolean checkTestCase() {
        if (testCase.equals("bounced_off_corner")) {
            return (myGameBall.getShape().getY() <= 0 && (myGameBall.getShape().getX() <= 0 || myGameBall.getShape().getX() + myGameBall.getShape().getBoundsInLocal().getWidth() >= WINDOW_WIDTH));
        }
        else if (testCase.equals("brick_destroyed")) {
            return (myGameBricks.getDestroyedBricks() > 0);
        }
        else if (testCase.equals("lost_life")) {
            return (myLives == INITIAL_LIVES - 1);
        }
        return false;
    }

    /**
     * This method helps set up our test.
     * @param testFile specifies the test file to be used
     */
    private void setupTest(String testFile) {
        myGroup.getChildren().clear();
        TestConfiguration gameTest = new TestConfiguration(testFile);
        initializeLevel();
        initialXpos = (double) Integer.parseInt(gameTest.getConditions().get("ballX"));
        initialYpos = (double) Integer.parseInt(gameTest.getConditions().get("ballY"));
        initialXvel = (double) Integer.parseInt(gameTest.getConditions().get("ballXVel"));
        initialYvel = (double) Integer.parseInt(gameTest.getConditions().get("ballYVel"));
        myGameBall.getShape().setX(initialXpos);
        myGameBall.getShape().setY(initialYpos);
        myGameBall.setMyXVelocity(initialXvel);
        myGameBall.setMyYVelocity(initialYvel);
        testCase = gameTest.getConditions().get("event");
        playGame();
    }

    /**
     * This method handles the actions upon the event of pressing of certain keyboard key.
     * @param code specifies the key pressed.
     */
    private void handleKeyInput(KeyCode code) {
        if (code == KeyCode.ENTER) {
            Data d = new Data();
            if (myLevel + 1 >= d.getNumberOfLevels())
                Platform.exit();
            else
                setupGame();
        }
        else if(code == KeyCode.S) {
            myGameBall.invertXVelocity();
            myGameBall.invertYVelocity();
        }
        else if(code==KeyCode.ESCAPE) {
            Platform.exit();
        }
        else if (code == KeyCode.COMMA) {
            //have the ball traveling towards a corner and subsequently bouncing back in the exact direction it came from
            setupTest("bounced_off_corner.txt");
        }
        else if (code == KeyCode.PERIOD) {
            //have the ball traveling towards a block and subsequently destroying it
            setupTest("brick_destroyed.txt");
        }
        else if (code == KeyCode.SLASH) {
            //have the ball fall towards the bottom of the screen and the player subsequently losing a life
            setupTest("lost_life.txt");
        }
        else if (inPlay && myGamePaddle.checkRightBounds(WINDOW_WIDTH) && code == KeyCode.RIGHT) {
            myGamePaddle.setX(myGamePaddle.getX() + myGamePaddle.getSpeed());
        }
        else if (inPlay && myGamePaddle.checkLeftBounds() && code == KeyCode.LEFT) {
            myGamePaddle.setX(myGamePaddle.getX() - myGamePaddle.getSpeed());
        }
        else if (code == KeyCode.SPACE) {
            if (inPlay) {
                myAnimation.pause();
                inPlay = false;
            }
            else {
                myAnimation.play();
                inPlay = true;
            }
        }
        else if (code == KeyCode.L) {
            myLives++;
            myStatus.setLives(myLives);
        }
        else if (code == KeyCode.R) {
            myGamePaddle.resetPosition(WINDOW_WIDTH, WINDOW_HEIGHT);
            myGameBall.resetPosition(WINDOW_WIDTH, myGamePaddle);
        }
    }

    /**
     * This method changes the necessary for certain objects in the scene between every frame.
     * @param elapsedTime specifies the time elapsed between two frames.
     */
    private void step(double elapsedTime) {
        myGameBall.windowCollisions(WINDOW_WIDTH);
        myGameBall.paddleCollision(myGamePaddle);
        if (activePowerUp != null) {
            if (activePowerUp.powerUpStep(elapsedTime, WINDOW_HEIGHT, myGameBall, myGameBricks, myGamePaddle, myStatus)) {
                myGroup.getChildren().remove(activePowerUp.getShape());
            }
        }

        Point destroyedBrick = myGameBall.brickCollision(myGameBricks);
        if (destroyedBrick != null) {
            ArrayList<PowerUp> newPU = new ArrayList<>();
            if (myGameBricks.getBricks()[destroyedBrick.x][destroyedBrick.y].ballCollision(myGameBall, myGamePaddle, newPU)) {
                if (myGameBricks.getBricks()[destroyedBrick.x][destroyedBrick.y].getType() == Type.POWER) {
                    activePowerUp = newPU.get(0);
                    myGroup.getChildren().add(activePowerUp.getShape());
                }
                myGroup.getChildren().remove(myGameBricks.getBricks()[destroyedBrick.x][destroyedBrick.y].getShape());
                myGameBricks.getBricks()[destroyedBrick.x][destroyedBrick.y] = null;
            }
            myStatus.setScore(myStatus.getIntScore() + 1);
        }

        myGameBall.updatePosition(elapsedTime, myLevel);
        if (myGameBall.missedPaddle(WINDOW_HEIGHT)) {

            myLives--;
            myStatus.setLives(myLives);
            myGroup.getChildren().remove(myGameBall.getShape());
            myGroup.getChildren().remove(myGamePaddle.getShape());
            setBallAndPaddle();
            if(myLives <= 0) {
                endGame();
            }
            inPlay = false;
            myAnimation.pause();
        }

        if (checkTestCase()) {
            Platform.exit();
        }

        if (myGameBricks.checkWin()) {
            Data d = new Data();
            if (myLevel + 1 >= d.getNumberOfLevels()){
                myText.setText("You Win!");
                myText.setX(WINDOW_WIDTH/2-myText.getBoundsInLocal().getWidth()/2);
                myText.setY(WINDOW_HEIGHT/2-myText.getBoundsInLocal().getHeight()/2);
                endGame();
            }
            else {
                myLevel++;
                removeFromElementsFromScene();
                myGroup.getChildren().removeAll(myStatus.getDisplay());
                initializeLevel();
            }
        }
    }

    /**
     * This method helps remove all the gameplay objects from the scene.
     */
    public void removeFromElementsFromScene() {
        myGroup.getChildren().remove(myGamePaddle.getShape());
        myGroup.getChildren().remove(myGameBall.getShape());
        if (activePowerUp != null) {
            myGroup.getChildren().remove(activePowerUp.getShape());
        }
        for (int i = 0; i < LEVEL_HEIGHT; i++) {
            for (int j = 0; j < LEVEL_WIDTH; j++) {
                if (myGameBricks.getBricks()[i][j] != null) {
                    myGroup.getChildren().remove(myGameBricks.getBricks()[i][j].getShape());
                }
            }
        }
    }

    /**
     * This method helps end the game and do the necessary at the end of the game.
     */
    public void endGame(){
        removeFromElementsFromScene();
        myGroup.getChildren().add(myText);
        myAnimation.stop();
    }

    /**
     * This method is the main method.
     * @param args specifies the arguments for main.
     */
    public static void main(String[] args){
        launch(args);
    }
}
