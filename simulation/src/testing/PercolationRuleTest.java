package testing;

import backend.Cell;
import backend.PercolationRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PercolationRuleTest {

    private static final ResourceBundle myResources = ResourceBundle.getBundle("properties/Percolation");

    private Cell test;
    private Cell a;
    private Cell b;
    private Cell c;
    private Cell d;
    private Cell e;
    private PercolationRule myRule;

    @BeforeEach
    void setUp() {
        myRule = new PercolationRule();
        test = new Cell(1, 1, 0, 500, 500);
        test.giveCellResources(myResources);
        a = new Cell(0, 1, 2, 500, 500);
        a.giveCellResources(myResources);
        b = new Cell(1, 0, 2, 500, 500);
        b.giveCellResources(myResources);
        c = new Cell(1, 2, 2, 500, 500);
        c.giveCellResources(myResources);
        d = new Cell(2, 1, 2, 500, 500);
        d.giveCellResources(myResources);
        e = new Cell(0, 0, 0, 500, 500);
        e.giveCellResources(myResources);
        test.addNeighbors(a);
        test.addNeighbors(b);
        test.addNeighbors(c);
        test.addNeighbors(d);
        test.addNeighbors(e);
    }

    @Test
    void testNoFlow() {
        Cell actual = myRule.update(test);
        assertEquals(0, actual.getState());

        test.setState(2);
        actual = myRule.update(test);
        assertEquals(2, actual.getState());
    }

    @Test
    void testFlow() {
        a.setState(1);
        Cell actual = myRule.update(test);
        assertEquals(1, actual.getState());

        b.setState(1);
        a.setState(0);
        test.setState(0);
        actual = myRule.update(test);
        assertEquals(1, actual.getState());

        test.setState(0);
        b.setState(2);
        e.setState(1);
        actual = myRule.update(test);
        assertEquals(0, actual.getState());
    }
}
