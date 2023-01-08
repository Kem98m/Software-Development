package testing;

import backend.Cell;
import backend.TriangleNeighborhood;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TriangleNeighborhoodTest {
    private TriangleNeighborhood myNeighborhood;
    Cell a;
    Cell b;
    Cell c;
    Cell d;
    Cell e;
    Cell test;
    ArrayList<Cell> list;

    @BeforeEach
    public void setUp() {
        a = new Cell(0,0,0,3,3);
        b = new Cell(0,1,0,3,3);
        c = new Cell(0, 2, 0, 3, 3);
        d = new Cell(1,0,0,3,3);
        test = new Cell(1,1,0,3,3);
        e = new Cell(1, 2,0,3,3);

        list = new ArrayList<>();
        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);
        list.add(e);
        list.add(test);

        myNeighborhood = new TriangleNeighborhood(test,0,1);
    }

    @Test
    public void testCardinalNeighbors() {
        for (Cell cell : list) {
            if (myNeighborhood.isCardinalNeighbor(cell)) {
                myNeighborhood.addNeighbors(cell);
            }
        }
        Set<Cell> n = myNeighborhood.getNeighbors();

        HashSet<Cell> expected = new HashSet<>();
        expected.add(a);
        expected.add(d);
        expected.add(e);

        assertTrue(n.containsAll(expected));
        assertTrue(expected.containsAll(n));

        myNeighborhood = new TriangleNeighborhood(a,0,1);

        for (Cell cell : list) {
            if (myNeighborhood.isCardinalNeighbor(cell)) {
                myNeighborhood.addNeighbors(cell);
            }
        }
        n = myNeighborhood.getNeighbors();

        expected = new HashSet<>();
        expected.add(b);
        expected.add(test);

        assertTrue(n.containsAll(expected));
        assertTrue(expected.containsAll(n));
    }

    @Test
    public void testDiagonalNeighbor() {
        for (Cell cell : list) {
            if (myNeighborhood.isDiagonalNeighbor(cell)) {
                myNeighborhood.addNeighbors(cell);
            }
        }
        Set<Cell> n = myNeighborhood.getNeighbors();

        HashSet<Cell> expected = new HashSet<>();
        expected.add(b);
        expected.add(c);

        assertTrue(n.containsAll(expected));
        assertTrue(expected.containsAll(n));

        myNeighborhood = new TriangleNeighborhood(c,0,1);

        for (Cell cell : list) {
            if (myNeighborhood.isDiagonalNeighbor(cell)) {
                myNeighborhood.addNeighbors(cell);
            }
        }
        n = myNeighborhood.getNeighbors();

        expected = new HashSet<>();
        expected.add(a);
        expected.add(test);
        expected.add(e);

        assertTrue(n.containsAll(expected));
        assertTrue(expected.containsAll(n));
    }
}
