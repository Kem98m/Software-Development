package frontend;

import backend.Cell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

/*
SquareGridMaker extends AbstractGridMaker; it specifically implements the square grid visualization
 */
public class SquareGridMaker extends AbstractGridMaker {

    private double shapeWidth;
    private double shapeHeight;

    private AnchorPane myPane;

    private List<Shape> myShapeList;

    /**
     * Constructor for the square grid maker
     * @param cells Collection of cells in the current grid
     */
    SquareGridMaker(Collection<Cell> cells) {
        super(cells);
    }

    /**
     * Creates grid of square shapes that represent the cells in the grid
     */
    @Override
    public void makeGrid() {
        myShapeList = new ArrayList<>();

        myPane = new AnchorPane();

        for (Cell c: myCells) {

            Shape cellSquare = makeCellSquare(c);

            cellSquare.setOnMouseClicked(m -> changeState(myShapeList, cellSquare));

            int x = c.getCol()*(int) shapeWidth;
            int y = c.getRow()*(int) shapeHeight;

            cellSquare.setLayoutX(x);
            cellSquare.setLayoutY(y);

            myShapeList.add(cellSquare);

            myPane.getChildren().add(cellSquare);
        }
    }


    private Shape makeCellSquare(Cell c) {
        int myWidth = c.getWidth();
        int myHeight = c.getHeight();

        shapeWidth = SimulationViewer.VIEWER_WIDTH / (1.0*myWidth);
        shapeHeight = (SimulationViewer.VIEWER_HEIGHT / (1.0*myHeight)) - (60 / (1.0*myHeight)); // Subtracting 60/myHeight gives space at the bottom for User Interface buttons

        Rectangle myShape = new Rectangle(shapeWidth, shapeHeight);
        ResourceBundle myResources = c.getSimResources();

        if (myResources.getString("OUTLINE").equals("Yes")) {
            setOutline(myShape);
        }

        setCellColor(c.getState(), myShape);
        setCellImage(c.getState(), myShape);

        return myShape;
    }

    /**
     * Returns the root node containing the grid visualization
     * @return AnchorPane
     */
    public AnchorPane getMyPane() {
        return myPane;
    }

}
