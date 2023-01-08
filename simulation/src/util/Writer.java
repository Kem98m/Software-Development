package util;

import backend.Cell;
import backend.Grid;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVWriter;

/**
 * Utility function that is used when the "Save" button is used while running the simulation. It writes the current configuration
 * of the running simulation to a save file associated with that particular simulation type, located in ./data/saves/
 */
public class Writer {
    private Grid myGrid;
    private String myPath;
    private ArrayList<String> toWrite = new ArrayList<>();

    private int gridWidth;
    private int gridHeight;

    /**
     * Constructor for the Writer class
     * @param cells Grid object of the current state of the cells
     * @param filePath File path to where we want to write the configuration file (.csv)
     */
    public Writer(Grid cells, String filePath) {
        myGrid = cells;
        myPath = filePath;

        gridWidth = myGrid.getCells().get(0).getWidth();
        gridHeight = myGrid.getCells().get(0).getHeight();
    }

    /**
     * Writes the .csv configuration to the save file
     */
    public void save() {
        toWrite.clear();
        getCells();

        writeCSVFile();
    }

    private void writeCSVFile() {
        File path = new File(myPath);
        try {
            Path file = Paths.get(myPath);
            Files.write(file, toWrite, Charset.forName("UTF-8"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getCells() {
        List<Cell> myCells = myGrid.getCells();
        gridWidth = myCells.get(0).getWidth();
        gridHeight = myCells.get(0).getHeight();

        toWrite.add(myGrid.getMySimType());
        String dim = gridWidth+","+gridHeight;
        toWrite.add(dim);
        int counter = 0;
        for (int i = 0; i < gridHeight; i++) {
            StringBuilder states = new StringBuilder();
            for (int j = 0; j < gridWidth; j++) {
                if (j == gridWidth - 1) {
                    states.append(myCells.get(counter).getState());
                }
                if (j != gridWidth -1) {
                    states.append(myCells.get(counter).getState() + ",");
                }
                counter++;
            }
            toWrite.add(states.toString());
        }
    }
}
