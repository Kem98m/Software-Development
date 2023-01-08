package frontend;

import backend.Cell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import java.util.Collection;
import java.util.ResourceBundle;

/*
TriangleGridMaker extends AbstractGridMaker; it specifically implements the triangle grid visualization
 */
public class TriangleGridMaker extends AbstractGridMaker {

    private AnchorPane myPane;

    /**
     * Constructor for the Triangle grid maker
     * @param cells Collection of cells that make up the current grid
     */
    public TriangleGridMaker(Collection<Cell> cells) {
        super(cells);
    }

    /**
     * Creates grid of triangle shapes that represent the cells in the grid
     */
    @Override
    public void makeGrid() {
        myPane = new AnchorPane();

        for (Cell c: myCells) {
            Shape cellTriangle = makeCellTriangle(c);

            setCellColor(c.getState(), cellTriangle);
            setCellImage(c.getState(), cellTriangle);

            myPane.getChildren().add(cellTriangle);
        }
    }

    private Shape makeCellTriangle(Cell c) {
        double shapeWidth = SimulationViewer.VIEWER_WIDTH/(Math.ceil(c.getWidth()/2.0));
        double shapeHeight = (SimulationViewer.VIEWER_HEIGHT / (1.0*c.getHeight())) - (60 / (1.0*c.getHeight()));

        Polygon myShape = new Polygon();

        ResourceBundle myResources = c.getSimResources();
        if (myResources.getString("OUTLINE").equals("Yes")) {
            setOutline(myShape);
        }

        int myCol = c.getCol();
        int myRow = c.getRow();

        double[] point1 = new double[]{(myCol/2)*shapeWidth, c.getRow()*shapeHeight};
        double[] point2;
        if ((myCol % 2) == 0) {
            point2 = new double[]{(myCol/2)*shapeWidth,(myRow+1)*shapeHeight};
        }
        else {
            point2 = new double[]{((myCol/2)+1)*shapeWidth, (myRow)*shapeHeight};
        }
        double[] point3 = new double[]{((myCol/2)+1)*shapeWidth, (myRow+1)*shapeHeight};

        myShape.getPoints().addAll(
                point1[0],point1[1],
                point2[0],point2[1],
                point3[0],point3[1]);

        return myShape;
    }

    /**
     * Returns the root node containing the grid visualization
     * @return AnchorPane
     */
    @Override
    public AnchorPane getMyPane() {
        return myPane;
    }
}
