package testing;

import backend.Cell;
import backend.RPSRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RPSRuleTest {

    private static final ResourceBundle myResources = ResourceBundle.getBundle("properties/RPS");

    private Cell test;
    private RPSRule myRule;

    @BeforeEach
    void setUp() {
        myRule = new RPSRule();

        test = new Cell(1, 1, 0, 3, 3);
        test.giveCellResources(myResources);

        Cell a = new Cell(0, 0, 2, 3, 3);
        a.giveCellResources(myResources);

        Cell b = new Cell(0, 1, 2, 3, 3);
        b.giveCellResources(myResources);

        Cell c = new Cell(0, 2, 2, 3, 3);
        c.giveCellResources(myResources);

        Cell d = new Cell(1, 0, 2, 3, 3);
        d.giveCellResources(myResources);

        Cell f = new Cell(1, 2, 2, 3, 3);
        f.giveCellResources(myResources);

        Cell g = new Cell(2, 0, 2, 3, 3);
        g.giveCellResources(myResources);

        Cell h = new Cell(2, 1, 2, 3, 3);
        h.giveCellResources(myResources);

        Cell i = new Cell(2, 2, 2, 3, 3);
        i.giveCellResources(myResources);

        test.addNeighbors(a);
        test.addNeighbors(b);
        test.addNeighbors(c);
        test.addNeighbors(d);
        test.addNeighbors(f);
        test.addNeighbors(g);
        test.addNeighbors(h);
        test.addNeighbors(i);

    }

    @Test
    void testPaperDominance() {
        Cell actual = myRule.update(test);
        assertEquals(2, actual.getState());

        test.setState(2);
        actual = myRule.update(test);
        assertEquals(2, actual.getState());
    }

}
