package backend;

import java.util.*;

/**
 * This abstract class helps implement the different rules.
 */
public abstract class Rule {

    private Map<Integer, Set<Cell>> myMap;

    /**
     * This method makes a new Cell from the old Cell.
     * @param updatedState the new state for the cell
     * @param oldCell the old cell that needs to be changed
     * @return the new cell made
     */
    protected Cell makeCell(int updatedState, Cell oldCell){
        Cell newCell= new Cell(oldCell.getRow(),oldCell.getCol(),updatedState,oldCell.getWidth(),oldCell.getHeight());
        newCell.giveCellResources(oldCell.getSimResources());
        return newCell;
    }

    /**
     * This method updates the cell based on the rules of different game.
     * @param cell the cell that needs to be updated
     * @return the updated cell.
     */
    abstract Cell update(Cell cell);

    public void giveStates(Map<Integer, Set<Cell>> states) {
        myMap = states;
    }
}
