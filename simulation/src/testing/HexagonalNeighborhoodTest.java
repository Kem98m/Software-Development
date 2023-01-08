package testing;

import backend.Cell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.HexagonNeighborhood;

public class HexagonalNeighborhoodTest {
    private HexagonNeighborhood myNeighborhood;
    private Cell a;
    private Cell b;
    private Cell c;
    private Cell d;
    private Cell e;
    private Cell test;
    private List<Cell> list;

    @BeforeEach
    void setUp() {
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

        myNeighborhood = new HexagonNeighborhood(test,0,1);
    }

    @Test
    void testCardinalNeighbors() {
        for (Cell c : list) {
            if (myNeighborhood.isCardinalNeighbor(c)) {
                myNeighborhood.addNeighbors(c);
            }
        }
        Set<Cell> n = myNeighborhood.getNeighbors();

        HashSet<Cell> expected = new HashSet<>();
        expected.add(b);
        expected.add(c);
        expected.add(d);

        assertTrue(n.containsAll(expected));
        assertTrue(expected.containsAll(n));
    }

    @Test
    void testToroidalNeighbors() {
        test = new Cell(0, 0, 0, 3, 2);
        a = new Cell(0, 1, 0, 3, 2);
        b = new Cell(0, 2, 0, 3, 2);
        c = new Cell(1, 0, 0, 3, 2);
        d = new Cell(1, 1, 0, 3, 2);
        e = new Cell(1, 2, 0, 3, 2);

        myNeighborhood = new HexagonNeighborhood(test,0,1);

        list = new ArrayList<>();
        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);
        list.add(e);

        for (Cell cell : list) {
            if (myNeighborhood.toroidalCardinal(cell)){
                myNeighborhood.addNeighbors(cell);
            }
        }
        Set<Cell> n = myNeighborhood.getNeighbors();

        HashSet<Cell> expected = new HashSet<>();
        expected.add(b);
        expected.add(c);
        expected.add(e);

        System.out.println(n.size());
        System.out.println(expected.size());

        assertTrue(n.containsAll(expected));
        assertTrue(expected.containsAll(n));
    }

    @Test
    void test() {
        test = new Cell(0, 0, 0, 3, 3);
        a = new Cell(0, 1, 0, 3, 3);
        b = new Cell(0, 2, 0, 3, 3);
        c = new Cell(2, 0, 0, 3, 3);
        d = new Cell(2, 1, 0, 3, 3);
        e = new Cell(1, 2, 0, 3, 3);

        myNeighborhood = new HexagonNeighborhood(a,0,1);

        list = new ArrayList<>();
        list.add(test);
        list.add(b);
        list.add(c);
        list.add(d);
        list.add(e);

        for (Cell cell : list) {
            System.out.println(myNeighborhood.toroidalCardinal(cell));
            if (myNeighborhood.toroidalCardinal(cell)) {
                myNeighborhood.addNeighbors(cell);
            }
        }
        Set<Cell> n = myNeighborhood.getNeighbors();

        HashSet<Cell> expected = new HashSet<>();
        expected.add(test);
        expected.add(b);
        expected.add(d);

        System.out.println(n.size());
        System.out.println(expected.size());

        assertTrue(n.containsAll(expected));
        assertTrue(expected.containsAll(n));
    }

}
