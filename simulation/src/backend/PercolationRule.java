package backend;

/**
 * This class implements the rules for Percolation Simulation
 */
public class PercolationRule extends Rule {

    public Cell update(Cell cell) {
        if (cell.getState() == 2) { // Blocked cell
            return makeCell(cell.getState(), cell);
        }
        for(Cell c: cell.getMyNeighborhood().getNeighbors()){
            if((c.getCol()==cell.getCol())||(c.getRow()==cell.getRow())) {
                if (c.getState() == 1) {
                    return makeCell(1, cell);
                }
            }
        }
        return makeCell(cell.getState(), cell);
    }
}
