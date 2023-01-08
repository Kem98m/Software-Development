package frontend;

import backend.Cell;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Shape;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
This is the abstract class used by the front-end to make the visualization for the simulation.
 */
abstract public class AbstractGridMaker {

    private static final String DEFAULT_STRING = "";
    private static final double OUTLINE_WIDTH = 0.5;

    List<Cell> myCells;

    private int numStates;

    private Color default1;
    private Color default2;
    private Color default3;

    private Color customColor1;
    private Color customColor2;
    private Color customColor3;

    private String defaultImg1 = DEFAULT_STRING;
    private String defaultImg2 = DEFAULT_STRING;
    private String defaultImg3 = DEFAULT_STRING;

    private String customImg1 = DEFAULT_STRING;
    private String customImg2 = DEFAULT_STRING;
    private String customImg3 = DEFAULT_STRING;

    /**
     * Constructor for the abstract grid maker
     * @param cells Takes in a collection of the current cells in the grid
     */
    public AbstractGridMaker(Collection<Cell> cells) {
        myCells = new ArrayList<>(cells);
    }

    /**
     * This method allows us to update the myCells instance variable with the new grid of cells
     * @param cells The new collection of cells in the grid
     * @param numberOfStates Number of possible states in the simulation
     */
    void updateGridMaker(Collection<Cell> cells, int numberOfStates) {
        myCells = new ArrayList<>(cells);
        numStates = numberOfStates;
    }

    /**
     * Gives the grid the default cell colors from the properties file for the simulation
     * @param c1 Color for state 1
     * @param c2 Color for state 2
     * @param c3 Color for state 3
     */
    void giveGridDefaultColors(Color c1, Color c2, Color c3) {
        default1 = c1;
        default2 = c2;
        default3 = c3;
    }

    /**
     * Gives the grid the default images (if any) from the properties file for the simulation
     * @param i1 Image path for state 1
     * @param i2 Image path for state 2
     * @param i3 Image path for state 3
     */
    void giveGridDefaultImages(String i1, String i2, String i3) {
        defaultImg1 = i1;
        defaultImg2 = i2;
        defaultImg3 = i3;
    }

    /**
     * Gives the grid the custom cell color selected from the user interface
     * @param c1 Color for state 1
     * @param c2 Color for state 2
     * @param c3 Color for state 3
     */
    void giveGridCustomColor(Color c1, Color c2, Color c3) {
        customColor1 = c1;
        customColor2 = c2;
        customColor3 = c3;
    }

    /**
     * Gives grid the paths for custom images selected from the user interface
     * @param i1 Image path for state 1
     * @param i2 Image path for state 2
     * @param i3 Image path for state 3
     */
    void giveGridCustomImages(String i1, String i2, String i3) {
        customImg1 = i1;
        customImg2 = i2;
        customImg3 = i3;
    }

    /**
     * Fills the given shape with either the default or custom color.
     * @param myState The state of the cell
     * @param myCellShape The shape that represents the cell
     */
    void setCellColor(int myState, Shape myCellShape) {
        switch (myState) {
            case 0:
                if (customColor1 != null) {
                    myCellShape.setFill(customColor1);
                }
                else {
                    myCellShape.setFill(default1);
                }
                break;
            case 1:
                if (customColor2 != null) {
                    myCellShape.setFill(customColor2);
                }
                else {
                    myCellShape.setFill(default2);
                }
                break;
            case 2:
                if (customColor3 != null) {
                    myCellShape.setFill(customColor3);
                }
                else {
                    myCellShape.setFill(default3);
                }
                break;
        }
    }

    /**
     * Fills the given shape with either the default or a custom image.
     * @param myState The state of the cell
     * @param myCellShape The shape that represents the cell
     */
    void setCellImage(int myState, Shape myCellShape) {
        switch (myState) {
            case 0:
                if (!customImg1.equals(DEFAULT_STRING)) {
                    fillCustomImg(myCellShape, customImg1);
                }
                else if (customColor1 == null && !defaultImg1.equals(DEFAULT_STRING)){
                    fillShapeImg(myCellShape, defaultImg1);
                }
                break;
            case 1:
                if (!customImg2.equals(DEFAULT_STRING)) {
                    fillCustomImg(myCellShape, customImg2);
                }
                else if (customColor2 == null && !defaultImg2.equals(DEFAULT_STRING)){
                    fillShapeImg(myCellShape, defaultImg2);
                }
                break;
            case 2:
                if (!customImg3.equals(DEFAULT_STRING)) {
                    fillCustomImg(myCellShape, customImg3);
                }
                else if (customColor3 == null && !defaultImg3.equals(DEFAULT_STRING)) {
                    fillShapeImg(myCellShape, defaultImg3);
                }
                break;
        }
    }

    /**
     * Gives the shape a thin black outline
     * @param shape The shape to be outlined
     */
    public void setOutline(Shape shape) {
        shape.setStroke(Color.BLACK);
        shape.setStrokeWidth(OUTLINE_WIDTH);
    }

    /**
     * Changes the state and color/image of a cell when its corresponding shape is clicked on by the mouse
     * @param myShapeList The list of shapes in the grid that corresponds to the list of cells in the grid
     * @param shape The shape that was clicked on
     */
    void changeState(List<Shape> myShapeList, Shape shape) {
        int idx = myShapeList.indexOf(shape);
        Cell cellToChange = myCells.get(idx);

        int state = cellToChange.getState();
        int numStates = getNumStates();

        switch (state) {
            case 0:
                cellToChange.setState(1);
                updateCellFill(cellToChange.getState(), shape); break;
            case 1:
                if (numStates == 3) {
                    cellToChange.setState(2);
                    updateCellFill(cellToChange.getState(), shape); break;
                }
                else {
                    cellToChange.setState(0);
                    updateCellFill(cellToChange.getState(), shape); break;
                }
            case 2:
                cellToChange.setState(0);
                updateCellFill(cellToChange.getState(), shape); break;
        }

        List<Cell> newCellList = new ArrayList<>(myCells);
        newCellList.set(idx, cellToChange);

        this.updateGridMaker(newCellList, numStates);

    }

    private void updateCellFill(int state, Shape shape) {
        setCellColor(state, shape);
        setCellImage(state, shape);
    }

    private void fillShapeImg(Shape shape, String imgPath) {
        ImagePattern img = new ImagePattern(new Image(this.getClass().getResourceAsStream(imgPath)));
        shape.setFill(img);
    }

    private void fillCustomImg(Shape shape, String imgPath) {
        ImagePattern img = new ImagePattern(new Image(imgPath));
        shape.setFill(img);
    }

    /*
     * Abstract method that creates a root node upon which to set all the shapes on
     */
    abstract public void makeGrid();

    /*
     * Abstract method to get the root node, an anchor pane in this case
     */
    abstract public AnchorPane getMyPane();

    /**
     * Returns the possible number of states for the simulation
     * @return the number of possible states
     */
    private int getNumStates() {
        return numStates;
    }

}
