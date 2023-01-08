package testing;

import backend.Cell;
import backend.SquareNeighborhood;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SquareNeighborhoodTest {
    private SquareNeighborhood myNeighborhood;
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
        e = new Cell(2, 0,0,3,3);

        list = new ArrayList<>();
        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);
        list.add(e);
        list.add(test);

        myNeighborhood = new SquareNeighborhood(test,0,1);
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
        expected.add(b);
        expected.add(d);

        assertTrue(n.containsAll(expected));
        assertTrue(expected.containsAll(n));
    }

    @Test
    public void testDiagonalNeighbors() {
        for (Cell cell : list) {
            if (myNeighborhood.isDiagonalNeighbor(cell)) {
                myNeighborhood.addNeighbors(cell);
            }
        }

        Set<Cell> n = myNeighborhood.getNeighbors();

        HashSet<Cell> expected = new HashSet<>();
        expected.add(a);
        expected.add(c);
        expected.add(e);

        assertTrue(n.containsAll(expected));
        assertTrue(expected.containsAll(n));
    }

    @Test
    public void testToroidalCardinal() {
        myNeighborhood = new SquareNeighborhood(a,0,1);

        for (Cell cell : list) {
            if (myNeighborhood.toroidalCardinal(cell)) {
                myNeighborhood.addNeighbors(cell);
            }
        }

        Set<Cell> n = myNeighborhood.getNeighbors();

        HashSet<Cell> expected = new HashSet<>();
        expected.add(c);
        expected.add(e);

        assertTrue(n.containsAll(expected));
        assertTrue(expected.containsAll(n));
    }

    @Test
    public void testToroidalDiagonal() {
        list.remove(e);
        list.remove(c);
        list.remove(test);
        e = new Cell(1, 2, 0, 3, 3);
        c = new Cell(2, 1, 0, 3, 3);
        test = new Cell(2, 2, 0, 3, 3);
        list.add(e);
        list.add(c);
        list.add(test);

        myNeighborhood = new SquareNeighborhood(a,0,1);

        for (Cell cell : list) {
            if (myNeighborhood.toroidalDiagonal(cell)) {
                myNeighborhood.addNeighbors(cell);
            }
        }

        Set<Cell> n = myNeighborhood.getNeighbors();

        HashSet<Cell> expected = new HashSet<>();
        expected.add(e);
        expected.add(c);
        expected.add(test);

        assertTrue(n.containsAll(expected));
        assertTrue(expected.containsAll(n));
    }

    @Test
    public void testCustom() {
        myNeighborhood = new SquareNeighborhood(a,0,1);

        for (Cell cell : list) {
            if (myNeighborhood.customPolicy(cell)) {
                myNeighborhood.addNeighbors(cell);
            }
        }

        Set<Cell> n = myNeighborhood.getNeighbors();

        HashSet<Cell> expected = new HashSet<>();
        expected.add(a);
        expected.add(d);
        expected.add(b);

        assertTrue(n.containsAll(expected));
        assertTrue(expected.containsAll(n));
    }
}
