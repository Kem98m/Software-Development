package backend;

/**
 * This class implements the rules for Fire Simulation
 */
public class FireRule extends Rule {

    private static final int EMPTY = 0;
    private static final int TREE = 1;
    private static final int BURNING = 2;

    private static final int PROBABILTY = 50;

    @Override
    public Cell update(Cell cell) {
        if(cell.getState()==BURNING){
            return makeCell(EMPTY,cell);
        } else if (cell.getState()==TREE) {
            boolean shouldBurn = false;
            for (Cell c : cell.getMyNeighborhood().getNeighbors()) {
                if(cell.getMyNeighborhood().isCardinalNeighbor(c) && c.getState()==BURNING){
                    shouldBurn = true;
                }
            }
            if(shouldBurn && ((int)(Math.random()*100)>PROBABILTY)){
                return makeCell(BURNING,cell);
            }
        }
        return makeCell(cell.getState(),cell);
    }
}
