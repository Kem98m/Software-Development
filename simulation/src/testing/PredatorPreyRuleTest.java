package testing;

import backend.Cell;
import backend.Grid;
import backend.PredatorPreyRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

public class PredatorPreyRuleTest {

    private static final ResourceBundle myResources = ResourceBundle.getBundle("properties/PredatorPrey");

    private Grid myGrid;
    private Cell test;
    private Cell a;
    private Cell b;
    private Cell c;


    @BeforeEach
    public void setUp() {
        test = new Cell(1, 1, 2, 3,3);
        test.giveCellResources(myResources);

        a = new Cell(0, 1, 0, 3, 3);
        a.giveCellResources(myResources);
        b = new Cell(1, 2, 0, 3, 3);
        b.giveCellResources(myResources);
        c = new Cell(0, 0, 0, 3, 3);
        c.giveCellResources(myResources);
    }



    @Test
    public void testEat() {
        test.setReproNum(6);
        a.setState(1);
        //c.setState(1);
        ArrayList<Cell> list = new ArrayList<>();
        list.add(a);
        //list.add(b);
        list.add(b);
        //list.add(d);
        list.add(c);
        list.add(test);
        myGrid = new Grid(list, "PredatorPrey");
        myGrid.updateGrid();

        Cell nowEmpty = new Cell(0,0,-1);
        Cell nowShark = new Cell(0,0,-1);

        for (Cell c : myGrid.getCells()) {
            if (c.getCol() == 1 && c.getRow() == 1) {
                nowEmpty = c;
            } else if (c.getCol() == 1 && c.getRow() == 0) {
                nowShark = c;
            }
        }

        assertEquals(0, nowEmpty.getState());
        assertEquals(2, nowShark.getState());
    }

    @Test
    public void testDeath() {
        test.setState(2);
        test.setEnergy(1);
        a.setState(2);
        b.setState(2);
        c.setState(2);
        c.setEnergy(5);

        ArrayList<Cell> list = new ArrayList<>();
        list.add(a);
        list.add(b);
        list.add(c);
        list.add(test);

        for (Cell c : list) {
            c.setReproNum(5);
        }

        myGrid = new Grid(list, "PredatorPrey");
        myGrid.updateGrid();

        Cell nowEmpty = new Cell(0,0,-1);
        Cell stillFish = new Cell(0,0,-1);

        for (Cell c : myGrid.getCells()) {
            if (c.getCol() == 1 && c.getRow() == 1) {
                nowEmpty = c;
            } else if (c.getRow() == 0 && c.getCol() == 0) {
                stillFish = c;
            }
        }

        assertEquals(0, nowEmpty.getState());
        assertEquals(2, stillFish.getState());

    }

    @Test
    public void testReproduce() {
        test.setReproNum(1);
        ArrayList<Cell> list = new ArrayList<>();
        list.add(a);
        list.add(c);
        list.add(test);
        myGrid = new Grid(list, "PredatorPrey");
        myGrid.updateGrid();

        Cell nowEmpty = new Cell(0,0,-1);
        Cell nowShark = new Cell(0,0,-1);
        Cell stillFish = new Cell(0,0,-1);

        for (Cell c : myGrid.getCells()) {
            if (c.getCol() == 1 && c.getRow() == 1) {
                nowEmpty = c;
            }
            if (c.getCol() == 1 && c.getRow() == 0) {
                nowShark = c;
            }
            if (c.getRow() == 0 && c.getCol() == 0) {
                stillFish = c;
            }
        }

        assertEquals(2, nowEmpty.getState());
        assertEquals(2, nowShark.getState());
        assertEquals(0, stillFish.getState());
    }

}
