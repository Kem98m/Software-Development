public class BrickGrid {

    private Brick[][] myBricks;
    private int destroyedBricks = 0;

    public static final int BRICK_COLUMNS = 10;
    public static final int BRICK_ROWS = 12;

    BrickGrid(int level) {
        Data d = new Data();
        d.getDataFromFile();
        myBricks = d.getData(level);
    }

    /**
     * This method returns the 2D array of Brick objects contained.
     * @return 2D array of Brick objects for this level
     */
    public Brick[][] getBricks(){
        return myBricks;
    }

    public int destroyBrick() {
        destroyedBricks++;
        return destroyedBricks;
    }

    /**
     * Returns the number of bricks that have been destroyed
     * @return the number of bricks that have been destroyed
     */
    public int getDestroyedBricks() {
        return destroyedBricks;
    }

    /**
     * Checks to see if all bricks have been destroyed, and if the player has won the game.
     * @return true if player has won, false if there are bricks remaining to be destroyed
     */
    public boolean checkWin() {
        for(int i = 0; i < BRICK_ROWS; i++){
            for(int j=0; j < BRICK_COLUMNS; j++){
                if (myBricks[i][j] != null){
                    return false;
                }
            }
        }
        return true;
    }
}
