package testing;

import backend.Cell;
import util.CSVFileException;
import util.CSVReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class CSVReaderTest {

    private CSVReader reader;

    @BeforeEach
    private void initialize() throws CSVFileException {
        reader = new CSVReader("/configurations/PercolationTest.csv");
    }

    @Test
    void testSuccessfulCellInitialization() {
        int expected = reader.getHeight() * reader.getWidth();
        assertEquals(expected, reader.getCells().size());
    }

    @Test
    void testCellStates() { //This test needs to be updated after any change to PercolationTest
        assertEquals(2, reader.getCells().get(0).getState());
        assertEquals(0, reader.getCells().get(1).getState());
        assertEquals(1, reader.getCells().get(2).getState());
        assertEquals(1, reader.getCells().get(3).getState());
        assertEquals(0, reader.getCells().get(4).getState());
    }

    @Test
    void testCellPosition() {
        Cell c = reader.getCells().get(0);
        assertEquals(0, c.getRow());
        assertEquals(0, c.getCol());
        c = reader.getCells().get(19);
        assertEquals(1, c.getRow()); //Needs to be updated after every change to PercolationTest
        assertEquals(9, c.getCol()); //Needs to be updated after every cahnge to PercolationTest
    }

    @Test
    void testSimulationType() {
        String name = "Percolation";
        assertEquals(name, reader.getType());
    }
}
