package backend;

//using for basic early implementation and testing

import java.util.HashSet;

/**
 * This method implements the neighborhood for square grid
 */
public class SquareNeighborhood extends Neighborhood {

    public SquareNeighborhood(Cell c, int neighbor, int edge) {
        super(neighbor,edge);
        center = c;
        myNeighbors = new HashSet<Cell>();
    }

    @Override
    public boolean isCardinalNeighbor(Cell c) {
        return (c.getRow() == center.getRow() && (c.getCol() == center.getCol() - 1 || c.getCol() == center.getCol() + 1)) || (c.getCol() == center.getCol() && (c.getRow() == center.getRow() - 1 || c.getRow() == center.getRow() + 1));

    }

    @Override
    public boolean isDiagonalNeighbor(Cell c) {
        return ((center.getRow() == c.getRow() + 1 && center.getCol() == c.getCol() + 1) || (center.getRow() == c.getRow() - 1 && center.getCol() == c.getCol() - 1) || (center.getRow() == c.getRow() + 1 && center.getCol() == c.getCol() - 1) || (center.getRow() == c.getRow() - 1 && center.getCol() == c.getCol() + 1));
    }

    @Override
    public boolean toroidalCardinal(Cell c) {
        // Check toroidal vertical edges
        if ((center.getCol() == 0 && c.getCol() == center.getWidth() - 1) || (center.getCol() == center.getWidth() - 1 && c.getCol() == 0)) {
            return (center.getRow() == c.getRow());
        }

        // Check toroidal horizontal edges
        if ((center.getRow() == 0 && c.getRow() == center.getHeight() - 1) || (center.getRow() == center.getHeight() - 1 && c.getRow() == 0)) {
            return (center.getCol() == c.getCol());
        }
        return false;
    }

    @Override
    public boolean toroidalDiagonal(Cell c) {
        // Check toroidal vertical edges and corners
        if ((center.getCol() == 0 && c.getCol() == center.getWidth() - 1) || (center.getCol() == center.getWidth() - 1 && c.getCol() == 0)) {
            return (center.getRow() == c.getRow() + 1) || (center.getRow() == c.getRow() - 1) || (center.getRow() == center.getWidth() - 1 && c.getRow() == 0) || (center.getRow() == 0 && c.getRow() == center.getHeight() - 1);
        }

        // Check toroidal horizontal edges
        if ((center.getRow() == 0 && c.getRow() == center.getHeight() - 1) || (center.getRow() == center.getHeight() - 1 && c.getRow() == 0)) {
            return ((center.getCol() == c.getCol() - 1) || (center.getCol() == c.getCol() + 1));
        }
        return false;
    }

}
