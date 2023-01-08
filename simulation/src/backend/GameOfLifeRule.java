package backend;

/**
 * This class implements the rules for Fire Simulation
 */

public class GameOfLifeRule extends Rule {

    public Cell update(Cell cell){
        int aliveCells=0;
        for(Cell c: cell.getMyNeighborhood().getNeighbors()){
            if(c.getState()==1)
                aliveCells++;
        }

        if(aliveCells<2||aliveCells>3||((aliveCells==2)&&(cell.getState()==0))){
            return makeCell(0,cell);
        } else {
            return makeCell(1,cell);
        }
    }

}
