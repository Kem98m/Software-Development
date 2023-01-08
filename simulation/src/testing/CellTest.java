package testing;

import backend.Cell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;


public class CellTest {

    private static final ResourceBundle myResources = ResourceBundle.getBundle("properties/GameOfLife");

    private Cell a;
    private Cell b;
    private Cell c;

    @BeforeEach
    private void initialize() {

        int testWidth = 10;
        int testHeight = 10;

        a = new Cell(3,2,2, testWidth, testHeight);
        a.giveCellResources(myResources);
        b = new Cell(3,3,1, testWidth, testHeight);
        b.giveCellResources(myResources);
        c = new Cell(3,5,3, testWidth, testHeight);
        c.giveCellResources(myResources);

    }

    @Test
    void testIsNeighbor() {
        assertTrue(a.isNeighbor(b));
        assertFalse(a.isNeighbor(c));
        assertFalse(b.isNeighbor(c));
    }

    @Test
    void testAddNeighbor() {
        a.addNeighbors(b);
        b.addNeighbors(a);

        assertEquals(1, a.getNeighbors().size());
        assertTrue(a.getNeighbors().contains(b));
        assertEquals(1, b.getNeighbors().size());
        assertTrue(b.getNeighbors().contains(a));
    }

    @Test
    void testEquals() {
        Cell test = new Cell(3, 2, 2);
        Cell other = new Cell(0, 0, 0);

        assertTrue(test.equals(a));
        assertFalse(other.equals(a));
    }
}
