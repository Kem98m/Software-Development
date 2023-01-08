package testing;

import backend.Cell;
import backend.GameOfLifeRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

public class GameOfLifeRuleTest {

    private static final ResourceBundle myResources = ResourceBundle.getBundle("properties/GameOfLife");

    private Cell test;
    private Cell a;
    private Cell b;
    private Cell c;
    private Cell d;
    private GameOfLifeRule myRule;

    @BeforeEach
    void setUp() {
        myRule = new GameOfLifeRule();
        test = new Cell(1, 1, 0, 500, 500);
        test.giveCellResources(myResources);
        a = new Cell(0, 1, 0, 500, 500);
        a.giveCellResources(myResources);
        b = new Cell(1, 2, 0, 500, 500);
        b.giveCellResources(myResources);
        c = new Cell(2, 1, 0, 500, 500);
        c.giveCellResources(myResources);
        d = new Cell(1, 0, 0, 500,500);
        d.giveCellResources(myResources);
        test.getMyNeighborhood().addNeighbors(a);
        test.getMyNeighborhood().addNeighbors(b);
        test.getMyNeighborhood().addNeighbors(c);
        test.getMyNeighborhood().addNeighbors(d);
    }

    @Test
    void testNoNeighbors() {
        test.setState(1);
        Cell actual = myRule.update(test);
        assertEquals(0, actual.getState());

        actual = myRule.update(test);
        assertEquals(0, actual.getState());
    }

    @Test
    void testOneNeighbor() {
        a.setState(1);
        Cell actual = myRule.update(test);
        assertEquals(0, actual.getState());

        test.setState(1);
        actual = myRule.update(test);
        assertEquals(0, actual.getState());
    }

    @Test
    void testTwoNeighbors() {
        a.setState(1);
        b.setState(1);
        Cell actual = myRule.update(test);
        assertEquals(0, actual.getState());

        test.setState(1);
        actual = myRule.update(test);
        assertEquals(1, actual.getState());
    }

    @Test
    void testThreeNeighbors() {
        a.setState(1);
        b.setState(1);
        c.setState(1);
        Cell actual = myRule.update(test);
        assertEquals(1, actual.getState());

        actual = myRule.update(test);
        assertEquals(1, actual.getState());
    }

    @Test
    void testFourNeighbors() {
        a.setState(1);
        b.setState(1);
        c.setState(1);
        d.setState(1);
        Cell actual = myRule.update(test);
        assertEquals(0, actual.getState());

        test.setState(1);
        actual = myRule.update(test);
        assertEquals(0, actual.getState());
    }

}
