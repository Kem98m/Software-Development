package backend;

import java.util.Set;

/**
 * This abstract class implements the neighboring cells for different grid type
 */
public abstract class Neighborhood {

    private int myNeighborType;
    private int myEdgeType;

    private static final int ALL_NEIGHBOR_TYPE = 0;
    private static final int CARDINAL_NEIGHBOR_TYPE = 1;
    private static final int DIAGONAL_NEIGHBOR_TYPE = 2;

    private static final int FINITE_EDGE_TYPE = 0;
    private static final int TOROIDAL_EDGE_TYPE = 1;
    private static final int CUSTOM_EDGE_TYPE = 2;

    protected Set<Cell> myNeighbors;
    protected Cell center;

    /**
     * This method initializes neighborType and edgeType
     * @param neighborType specifies the type of neighbors
     * @param edgeType specifies the type of edges for the grid
     */
    public Neighborhood(int neighborType, int edgeType){
        myNeighborType = neighborType;
        myEdgeType = edgeType;
    }

    /**
     * This method helps get the number of cells in neighborhood that is similar to the cell itself
     * @return the total number of same state neighbors.
     */
    int getLikeNeighborNum() {
        Set<Cell> Neighbors = myNeighbors;
        int total = 0;
        for (Cell c : Neighbors) {
            if (center.getState() == c.getState()) {
                total++;
            }
        }
        return total;
    }

    public void addNeighbors(Cell c) {
        myNeighbors.add(c);
    }

    public Set<Cell> getNeighbors() {
        return myNeighbors;
    }

    /**
     * This method returns isNeighbor method based on edge type and neighbor type
     * @param c the neighboring cell to be compared with
     * @return true if it is a neighbor, else return false
     */
    boolean isNeighbor(Cell c) {
        if(myNeighborType==ALL_NEIGHBOR_TYPE && myEdgeType==TOROIDAL_EDGE_TYPE) {
            return isCardinalNeighbor(c) || isDiagonalNeighbor(c) || toroidalCardinal(c) || toroidalDiagonal(c);
        } else if(myNeighborType==CARDINAL_NEIGHBOR_TYPE && myEdgeType==TOROIDAL_EDGE_TYPE) {
            return isCardinalNeighbor(c) || toroidalCardinal(c);
        } else if(myNeighborType==DIAGONAL_NEIGHBOR_TYPE && myEdgeType==TOROIDAL_EDGE_TYPE) {
            return isDiagonalNeighbor(c) || toroidalDiagonal(c);
        } else if(myNeighborType==ALL_NEIGHBOR_TYPE && myEdgeType==FINITE_EDGE_TYPE) {
            return isCardinalNeighbor(c) || isDiagonalNeighbor(c);
        } else if(myNeighborType==CARDINAL_NEIGHBOR_TYPE && myEdgeType==FINITE_EDGE_TYPE) {
            return isCardinalNeighbor(c);
        }else if(myNeighborType==DIAGONAL_NEIGHBOR_TYPE && myEdgeType==FINITE_EDGE_TYPE) {
                        return isDiagonalNeighbor(c);
                    }
        return false;
    }

    /**
     * This method checks for cardinal neighbors
     * @param c the neighboring cell to be compared with
     * @return true if it is a cardinal neighbor, else return false
     */
    protected abstract boolean isCardinalNeighbor(Cell c);

    /**
     * This method checks for diagonal neighbors
     * @param c the neighboring cell to be compared with
     * @return true if it is a diagonal neighbor, else return false
     */
    protected abstract boolean isDiagonalNeighbor(Cell c);

    /**
     * This method checks for toroidal cardinal neighbors
     * @param c the neighboring cell to be compared with
     * @return true if it is a cardinal neighbor for toroidal edges, else return false
     */
    protected abstract boolean toroidalCardinal(Cell c);

    /**
     * This method checks for toroidal diagonal neighbors
     * @param c the neighboring cell to be compared with
     * @return true if it is a diagonal neighbor for toroidal edges, else return false
     */
    protected abstract boolean toroidalDiagonal(Cell c);

    public boolean customPolicy(Cell c) {
        return (center.equals(c)) || (isCardinalNeighbor(c) && ((c.getRow() == 0 || c.getRow() == c.getHeight() - 1) || (c.getCol() == 0 || c.getCol() == c.getWidth() - 1)));
    }
}
