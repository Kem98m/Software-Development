package testing;

import backend.Cell;
import backend.Grid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.CSVFileException;
import util.CSVReader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class GridTest {
    private Grid myGrid;

    @BeforeEach
    private void setUp() throws CSVFileException {
        CSVReader myReader = new CSVReader("/configurations/GridTest.csv");
        myGrid = new Grid(myReader.getCells(), myReader.getType());
    }

    @Test
    void testTotal() {
        ArrayList<Cell> list = (ArrayList<Cell>) myGrid.getCells();
        Cell c = list.get(4);
        assertEquals(1, c.getState());
        assertEquals(0, c.getRow());
        assertEquals(4, c.getCol());
    }

    @Test
    void testStateSize() { //needs to updated after every change to GridTest.csv
        Collection<Cell> zeroState = myGrid.getStateCells(0);
        assertEquals(16, zeroState.size());

        Collection<Cell> oneState = myGrid.getStateCells(1);
        assertEquals(9, oneState.size());
    }

    @Test
    void testFilledContent() { //needs to updated after every change to GridTest.csv
        Collection<Cell> filled = myGrid.getStateCells(1);
        HashSet<Cell> test = new HashSet<>();
        test.add((myGrid.getCells()).get(0));
        test.add((myGrid.getCells()).get(1));
        test.add((myGrid.getCells()).get(4));
        test.add((myGrid.getCells()).get(15));
        test.add((myGrid.getCells()).get(16));
        test.add((myGrid.getCells()).get(19));
        test.add((myGrid.getCells()).get(20));
        test.add((myGrid.getCells()).get(21));
        test.add((myGrid.getCells()).get(24));

        assertTrue(filled.containsAll(test));
        assertTrue(test.containsAll(filled));
    }

    @Test
    void testIsFilled() {
        Collection<Cell> filled = myGrid.getStateCells(1);
        for (Cell c : filled) {
            assertTrue(myGrid.isState(c,1));
        }
    }

    @Test
    void testNeighbors() { //needs to updated after every change to GridTest.csv
        Cell c = (myGrid.getCells()).get(20);
        Collection<Cell> nearby = c.getMyNeighborhood().getNeighbors();

        Collection<Cell> test = new HashSet<Cell>();
        test.add((myGrid.getCells()).get(0));
        test.add((myGrid.getCells()).get(1));
        test.add((myGrid.getCells()).get(4));
        test.add((myGrid.getCells()).get(15));
        test.add((myGrid.getCells()).get(16));
        test.add((myGrid.getCells()).get(19));
        test.add((myGrid.getCells()).get(21));
        test.add((myGrid.getCells()).get(24));

        assertTrue(nearby.containsAll(test));
        assertTrue(test.containsAll(nearby));

        c = (myGrid.getCells()).get(2);
        nearby = c.getMyNeighborhood().getNeighbors();
        test = new HashSet<>();
        test.add((myGrid.getCells()).get(1));
        test.add((myGrid.getCells()).get(3));
        test.add((myGrid.getCells()).get(21));
        test.add((myGrid.getCells()).get(22));
        test.add((myGrid.getCells()).get(23));
        test.add((myGrid.getCells()).get(6));
        test.add((myGrid.getCells()).get(7));
        test.add((myGrid.getCells()).get(8));


        assertTrue(nearby.containsAll(test));
        assertTrue(test.containsAll(nearby));
    }
}
