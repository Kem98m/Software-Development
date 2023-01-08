package frontend;

import backend.Cell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

/*
HexagonGridMaker extends AbstractGridMaker; it specifically implements the hexagon grid visualization
 */
public class HexagonGridMaker extends AbstractGridMaker {

    private AnchorPane myPane;

    private List<Shape> myShapeList;

    /**
     * Constructor for the Hexagon grid maker
     * @param cells Collection of cells in the current grid
     */
    public HexagonGridMaker(Collection<Cell> cells) {
        super(cells);
    }

    /**
     * Initializes the grid with hexagons representing each cell and its state
     */
    @Override
    public void makeGrid() {
        myPane = new AnchorPane();
        myShapeList = new ArrayList<>();

        for (Cell c: myCells) {

            Shape cellHex = makeCellHexagon(c);

            cellHex.setOnMouseClicked(m -> changeState(myShapeList, cellHex));

            setCellColor(c.getState(), cellHex);
            setCellImage(c.getState(), cellHex);

            cellHex.setLayoutX(0);
            cellHex.setLayoutY(0);

            myShapeList.add(cellHex);

            myPane.getChildren().add(cellHex);
        }
    }

    private Shape makeCellHexagon(Cell c) {
        int myWidth = c.getWidth();
        int myHeight = c.getHeight();

        int shapeWidth = 2 * SimulationViewer.VIEWER_WIDTH / (2 * myWidth + 1);
        int shapeHeight = SimulationViewer.VIEWER_HEIGHT / (2 * myHeight / 3) - 120 / myHeight;
        shapeHeight = shapeHeight - shapeHeight /myHeight;

        Polygon myShape = new Polygon();
        ResourceBundle myResources = c.getSimResources();
        if (myResources.getString("OUTLINE").equals("Yes")) {
            setOutline(myShape);
        }

        double startX;
        double startY;

        int myCol = c.getCol();
        int myRow = c.getRow();

        if(myRow%2==1){
            startX = myCol * shapeWidth + shapeWidth /2;
        }
        else {
            startX = myCol * shapeWidth;
        }
        startY = myRow * 2 * shapeHeight /3;

        setHexagonSides(myShape, startX, startY, shapeWidth, shapeHeight);

        return myShape;
    }

    private static void setHexagonSides(Polygon polygon, double startX, double startY, double width, double height) {
        polygon.getPoints().clear();
        polygon.getPoints().addAll(startX + width/2, startY);
        polygon.getPoints().addAll(startX + width, startY+height/3);
        polygon.getPoints().addAll(startX + width, startY+2*height/3);
        polygon.getPoints().addAll(startX + width/2, startY + height);
        polygon.getPoints().addAll(startX , startY+2*height/3);
        polygon.getPoints().addAll(startX , startY+height/3);
    }

    /**
     * Returns the root node containing the grid visualization
     * @return AnchorPane
     */
    public AnchorPane getMyPane() {
        return myPane;
    }

}
