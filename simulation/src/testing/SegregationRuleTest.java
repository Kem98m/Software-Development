package testing;

import backend.Cell;
import backend.Grid;
import backend.SegregationRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SegregationRuleTest {

    private static final ResourceBundle myResources = ResourceBundle.getBundle("properties/Segregation");
    private Cell test;
    private Grid myGrid;

    private Cell a;
    private Cell b;
    private Cell c;
    private Cell d;
    private Cell e;

    @BeforeEach
    public void setUp() {
        test = new Cell(1, 1, 2, 3,3);
        a = new Cell(0, 1, 2, 3, 3);
        b = new Cell(1, 0, 2, 3, 3);
        c = new Cell(1, 2, 2, 3, 3);
        d = new Cell(2, 1, 2, 3, 3);
        e = new Cell(0, 0, 0, 3, 3);
        List<Cell> list = new ArrayList<>();
        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);
        list.add(e);
        list.add(test);

        for (Cell cell : list) {
            cell.giveCellResources(myResources);
        }

        myGrid = new Grid(list, "Segregation");
        myGrid.neighbors();
    }

    @Test
    public void testSurroundedByLike() {
        myGrid.updateGrid();
        Cell actual = new Cell(0,0,-1);
        for (Cell c : myGrid.getCells()) {
            if (c.getRow() == 1 && c.getCol() == 1) {
                actual = c;
                break;
            }
        }
        assertEquals(2, actual.getState());
    }

    @Test
    public void testMoveToEmpty() {
        test.setState(1);

        List<Cell> list = new ArrayList<>();
        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);
        list.add(e);
        list.add(test);

        myGrid = new Grid(list, "Segregation");

        myGrid.updateGrid();
        Cell nowEmpty = new Cell(0,0,-1);
        Cell other = new Cell(0,0,-1);

        for (Cell c : myGrid.getCells()) {
            if (c.getCol() == 1 && c.getRow() == 1) {
                nowEmpty = c;
            } else if (c.getCol() == 0 && c.getRow() == 0) {
                other = c;
            }
        }

        assertEquals(0, nowEmpty.getState());
        assertEquals(1, other.getState());
    }
}
