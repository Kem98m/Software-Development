package backend;

import java.util.HashSet;

/**
 * This class implements the neighborhood for hexagonal grids
 */
public class HexagonNeighborhood extends Neighborhood{

    public HexagonNeighborhood(Cell c, int neighbor, int edge) {
        super(neighbor,edge);
        center = c;
        myNeighbors = new HashSet<>();
    }

    @Override
    public boolean isCardinalNeighbor(Cell c) {
        int otherRow = c.getRow();
        int myRow = center.getRow();
        int otherCol = c.getCol();
        int myCol = center.getCol();

        if (myRow == otherRow) {
            return (Math.abs(myCol - otherCol) == 1);
        } else if (myRow % 2 == 0) { // even rows (note: row 0 is even)
            if ((myCol - otherCol == 1) || myCol == otherCol) {
                return (Math.abs(myRow - otherRow) == 1);
            }
        } else { // odd rows
            if ((myCol - otherCol == -1) || (myCol == otherCol)) {
                return (Math.abs(myRow - otherRow) == 1);
            }
        }

        return false;
    }

    @Override
    public boolean isDiagonalNeighbor(Cell c) {
        return false;
    }

    @Override
    public boolean toroidalCardinal(Cell c) {
        int otherRow = c.getRow();
        int myRow = center.getRow();
        int otherCol = c.getCol();
        int myCol = center.getCol();
        int width = center.getWidth();
        int height = center.getHeight();

        //toroidal horizontal
        if (myRow == otherRow) {
            return (myCol == 0 && otherCol == width - 1) || (myCol == width - 1 && otherCol == 0);
        }

        // toroidal vertical
        if ((myRow == 0 && otherRow == height - 1) || (myRow == height - 1 && otherRow == 0)) {
            // odd heights
            if (height % 2 == 0) {
                if (myRow % 2 == 0) {
                   return ((otherCol == myCol) || (otherCol + 1 == myCol));
                } else {
                    return ((otherCol == myCol) || (otherCol == myCol + 1));
                }
            } else {
                return (myCol == otherCol);
            }
        }

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
