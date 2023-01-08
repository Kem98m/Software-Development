package backend;

import java.util.HashSet;

/**
 * This method implements the neighborhood for Triangle Grid Type
 */
public class TriangleNeighborhood extends Neighborhood{

    public TriangleNeighborhood(Cell c, int neighbor, int edge) {
        super(neighbor,edge);
        center = c;
        myNeighbors = new HashSet<Cell>();
    }

    @Override
    public boolean isCardinalNeighbor(Cell c) {
        return (((center.getCol() + 1 == c.getCol()) && (center.getRow() + 1 == c.getRow())) ||
                ((center.getCol() == c.getCol() + 1) && (center.getRow() == c.getRow() + 1)))
                || (center.getRow() == c.getRow() && ((center.getCol() + 1 == c.getCol()) || (center.getCol() == c.getCol() + 1)));
    }

    @Override
    public boolean isDiagonalNeighbor(Cell c) {
        int myCol = center.getCol();
        int myRow = center.getRow();

        if (myCol % 2 == 0) {
            if (myRow + 1 == c.getRow()) {
               return (c.getCol() == myCol) || (c.getCol() == myCol - 1) || (c.getCol() == myCol + 2) || (c.getCol() == myCol + 3);
            } else if (myRow == c.getRow()) {
                return (Math.abs(myCol - c.getCol()) == 2);
            } else if (myRow == c.getRow() + 1) {
                return (c.getCol() == myCol - 2) || (c.getCol() == myCol) || (c.getCol() == myCol - 1);
            }
        } else {
            if (myRow + 1 == c.getRow()) {
                return (c.getCol() == myCol) || (c.getCol() == myCol + 1) || (c.getCol() == myCol + 2);
            } else if (myRow == c.getRow()) {
                return (Math.abs(myCol - c.getCol()) == 2);
            } else if (myRow == c.getRow() + 1) {
                return (c.getCol() == myCol - 3) || (c.getCol() == myCol - 2) || (c.getCol() == myCol) || (c.getCol() == myCol + 1);
            }
        }
        return false;
    }

    @Override
    public boolean toroidalCardinal(Cell c) {
        return false;
    }

    @Override
    public boolean toroidalDiagonal(Cell c) {
        return false;
    }

    @Override
    public boolean customPolicy(Cell c) {
        return false;
    }
}
