package testing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.CSVFileException;
import util.CSVReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CSVExceptionTest {

    private CSVReader reader;

    @BeforeEach
    private void initialize() throws CSVFileException {

    }

    @Test
    void testInvalidSimType() {
        int actual;
        String error = "";
        try {
            reader = new CSVReader("/configurations/errorFiles/CSVSimTypeError.csv");
            actual = 1;
        }
        catch (CSVFileException e) {
            error = e.getMessage();
            actual = 0;
        }

        assertEquals(0, actual);
        assertEquals("Wrong Simulation Type", error);
    }

    @Test
    void testTooWideGrid() {
        int actual;
        String error = "";
        try {
            reader = new CSVReader("/configurations/errorFiles/CSVWidthError.csv");
            actual = 1;
        }
        catch (CSVFileException e) {
            error = e.getMessage();
            actual = 0;
        }

        assertEquals(0, actual);
        assertEquals("Values exceed grid size.", error);

    }

    @Test
    void testNotWideEnoughGrid() {
        int actual;
        String error = "";
        try {
            reader = new CSVReader("/configurations/errorFiles/CSVShortWidthError.csv");
            actual = 1;
        }
        catch (CSVFileException e) {
            error = e.getMessage();
            actual = 0;
        }

        assertEquals(0, actual);
        assertEquals("Values unavailable for specified grid size", error);
    }

    @Test
    void testTooTallGrid() {
        int actual;
        String error = "";
        try {
            reader = new CSVReader("/configurations/errorFiles/CSVTooTallGridError.csv");
            actual = 1;
        }
        catch (CSVFileException e) {
            error = e.getMessage();
            actual = 0;
        }

        assertEquals(0, actual);
        assertEquals("Values exceed grid size.", error);
    }

    @Test
    void testTooShortGrid() {
        int actual;
        String error = "";
        try {
            reader = new CSVReader("/configurations/errorFiles/CSVTooShortGridError.csv");
            actual = 1;
        }
        catch (CSVFileException e) {
            error = e.getMessage();
            actual = 0;
        }

        assertEquals(0, actual);
        assertEquals("Values unavailable for specified grid size", error);
    }

    @Test
    void testWrongStateError() {
        int actual;
        String error = "";
        try {
            reader = new CSVReader("/configurations/errorFiles/CSVWrongStateError.csv");
            actual = 1;
        }
        catch (CSVFileException e) {
            error = e.getMessage();
            actual = 0;
        }

        assertEquals(0, actual);
        assertEquals("Wrong state value", error);
    }

}
