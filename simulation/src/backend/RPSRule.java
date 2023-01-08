package backend;

/**
 * This class implements the rules for RPS Simulation
 */
public class RPSRule extends Rule {

    private static final int ROCK_CELL = 0;
    private static final int SCISSOR_CELL = 1;
    private static final int PAPER_CELL = 2;

    private static int THRESHOLD = 3;

    private int rockNeighbors = 0;
    private int scissorNeighbors = 0;
    private int paperNeighbors = 0;

    public Cell update(Cell cell){

        getNeighborDistribution(cell);

        if(cell.getState()==ROCK_CELL && paperNeighbors > THRESHOLD){
            return makeCell(PAPER_CELL,cell);
        } else if(cell.getState()==PAPER_CELL && scissorNeighbors > THRESHOLD){
            return makeCell(SCISSOR_CELL,cell);
        } else if(cell.getState()==SCISSOR_CELL && rockNeighbors > THRESHOLD){
            return makeCell(ROCK_CELL,cell);
        }
        return makeCell(cell.getState(),cell);
    }

    private void getNeighborDistribution(Cell cell) {
        rockNeighbors = 0;
        scissorNeighbors = 0;
        paperNeighbors = 0;

        for(Cell c: cell.getMyNeighborhood().getNeighbors()){
            switch (c.getState()){
                case ROCK_CELL:
                    rockNeighbors++;
                    break;
                case SCISSOR_CELL:
                    scissorNeighbors++;
                    break;
                case PAPER_CELL:
                    paperNeighbors++;
                    break;
            }
        }
    }

}