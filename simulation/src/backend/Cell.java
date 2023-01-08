package backend;

import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class represents each cell in the simulation.
 */
public class Cell {

    private ResourceBundle simResources;

    private int myRow;
    private int myCol;
    private int myState;

    //Used for neighbor checking --> change to SHAPE...?
    private int myWidth;
    private int myHeight;

    private Neighborhood myNeighborhood;
    private int neighbor;
    private int edge;

    private int myReproductionNumber;
    private int myEnergy;

    private static final int REPRODUCTION_ORIGIN = 1;
    private static final int REPRODUCTION_BOUND = 4;
    private static final int ENERGY_ORIGIN = 2;
    private static final int ENERGY_BOUND = 5;

    /**
     * Constructor to initialize instance variables
     * @param r specifies the mathematical row the cell is in.
     * @param c specifies the mathematical col the cell is in.
     * @param s specifies the state of the cell.
     */
    public Cell(int r, int c, int s) {
        myRow = r;
        myCol = c;
        myState = s;
        myReproductionNumber = ThreadLocalRandom.current().nextInt(REPRODUCTION_ORIGIN, REPRODUCTION_BOUND);
        myEnergy = ThreadLocalRandom.current().nextInt(ENERGY_ORIGIN, ENERGY_BOUND);

    }

    /**
     * Constructor to initialize instance variables
     * @param r specifies the mathematical row the cell is in.
     * @param c specifies the mathematical col the cell is in.
     * @param s specifies the state of the cell.
     * @param w specifies the cell width
     * @param h specifies the cell height
     */
    public Cell(int r, int c, int s, int w, int h) {
        this(r, c, s);
        myWidth = w;
        myHeight = h;
    }

    /**
     * This method gets the resource bundle of the properties file curently used.
     * @param resources
     */
    public void giveCellResources(ResourceBundle resources) {
        simResources = resources;

        setSimOptions();
        setMyNeighborhood();
    }

    private void setSimOptions() {
        String neighborType = simResources.getString("NEIGHBOR");
        String edgePolicy = simResources.getString("EDGE");

        switch (neighborType) {
            case "All":
                neighbor = 0;
                break;
            case "Cardinal":
                neighbor = 1;
                break;
            case "Diagonal":
                neighbor = 2;
                break;
        }

        switch (edgePolicy) {
            case "Finite":
                edge = 0;
                break;
            case "Toroidal":
                edge = 1;
                break;
            case "Custom":
                edge = 2;
                break;
        }
    }

    private void setMyNeighborhood() {
        String gridShape = simResources.getString("GRID_SHAPE");

        switch (gridShape) {
            case "Square":
                myNeighborhood = new SquareNeighborhood(this, neighbor, edge);
                break;
            case "Hexagonal":
                myNeighborhood = new HexagonNeighborhood(this, neighbor, edge);
                break;
            case "Triangular":
                myNeighborhood = new TriangleNeighborhood(this, neighbor, edge);
                break;
        }
    }

    public Neighborhood getMyNeighborhood() {
        return myNeighborhood;
    }

    //think about whether to have this one in addition to in neighborhood
    public Set<Cell> getNeighbors() {
        return myNeighborhood.getNeighbors();
    }

    //think about whether to have this one in addition to in neighborhood
    public void addNeighbors(Cell c) {
        myNeighborhood.addNeighbors(c);
    }

    //think about whether to have this one in addition to in neighborhood
    public boolean isNeighbor(Cell c) {
        return myNeighborhood.isNeighbor(c);
    }

    public void setState(int state) {
        this.myState = state;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Cell))
            return false;
        Cell other = (Cell) object;
        return ((myRow == other.getRow()) && (myCol == other.getCol()) && (myState == other.getState()));
    }

    /**
     * This method gets the mathematical row in the simulation.
     * @return the mathematical row in the simulation.
     */
    public int getRow() {
        return myRow;
    }

    /**
     * This method gets the mathematical col in the simulation.
     * @return the mathematical col in the simuation.
     */
    public int getCol() {
        return myCol;
    }

    /**
     * This method gets the width of the cell.
     * @return the width of the cell.
     */
    public int getWidth() {
        return myWidth;
    }

    /**
     * This method gets the height of the cell.
     * @return the height of the cell.
     */
    public int getHeight() {
        return myHeight;
    }

    /**
     * This method gets the state of the cell
     * @return the state of the cell.
     */
    public int getState() {
        return myState;
    }

    public void setReproNum(int num) {
        myReproductionNumber = num;
    }

    public void setEnergy(int num) {
        myEnergy = num;
    }

    public void eatFish() {
        myEnergy = myEnergy + 2;
    }

    public void age() {
        myEnergy--;
        myReproductionNumber--;
    }

    public int getMyReproNum() {
        return myReproductionNumber;
    }

    public int getEnergy() {
        return myEnergy;
    }

    public ResourceBundle getSimResources() {
        return simResources;
    }
}
