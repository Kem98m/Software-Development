
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class Status {
    private Text myLives;
    private Text myScore;
    private Text myLevel;
    private Text myHighScore;
    private ArrayList<Text> myDisplay = new ArrayList<>();
    private int gameScore = 0;

    Status(int lives, int level) {
        myLives = new Text();
        myLives.setText("Lives: " + lives);
        myLives.setX(30);
        myLives.setY(30);
        myLives.setFont(Font.font ("Verdana", 20));

        myScore = new Text();
        myScore.setText("Score: 0");
        myScore.setX(GameHandler.WINDOW_WIDTH/4-myScore.getBoundsInLocal().getWidth()/4+40);
        myScore.setFont(Font.font ("Verdana", 20));
        myScore.setY(30);

        myHighScore = new Text();
        myHighScore.setText("HighScore: 0");
        myHighScore.setX(3*GameHandler.WINDOW_WIDTH/4-myScore.getBoundsInLocal().getWidth()-50);
        myHighScore.setFont(Font.font ("Verdana", 20));
        myHighScore.setY(30);

        myLevel = new Text();
        myLevel.setFont(Font.font ("Verdana", 20));
        myLevel.setText("Level: " + level);
        myLevel.setX(GameHandler.WINDOW_WIDTH-myLevel.getBoundsInLocal().getWidth()-50);
        myLevel.setY(30);

        myDisplay.add(myLives);
        myDisplay.add(myScore);
        myDisplay.add(myHighScore);
        myDisplay.add(myLevel);
    }

    /**
     * This method returns all of the Text objects contained in the Status, so that a new display may be made in the game
     * @return list of all Text objects being shown in the game
     */
    public ArrayList<Text> getDisplay() {
        return myDisplay;
    }

    public Text getLives() {
        return myLives;
    }

    public Text getScore() {
        return myScore;
    }

    public Text getLevel() {
        return myLevel;
    }

    public Text getHighScore(){
        return myHighScore;
    }

    /**
     * Sets the high score display in the game
     * @param highScore the value to be displayed as the high score
     */
    public void setHighScore(int highScore){
        myHighScore.setText("HighScore: " + highScore);
    }

    public void setLives(int lives) {
        myLives.setText("Lives: " + lives);
    }

    /**
     * Sets the score display in the game
     * @param score the value to be displayed as the score
     */
    public void setScore(int score) {
        myScore.setText("Score: " + score);
        gameScore = score;
    }
    /**
     * Sets the level  display in the game
     * @param level the value to be displayed as the level
     */
    public void setLevel(int level) {
        myLevel.setText("Level: " + level);
    }

    /**
     * Returns access to the integer value of the score, differentiated from the Text object that displays the score
     * @return the integer value of the current score
     */
    public int getIntScore() {
        return gameScore;
    }
}
