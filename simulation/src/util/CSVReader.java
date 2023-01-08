package util;

import backend.Cell;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static frontend.SimulationViewer.DEFAULT_RESOURCE_PACKAGE;

/**
 * Utility class that reads in a .csv configuration file and provides the program with all of the necessary information. It also
 * initializes the cells for the grid.
 */
public class CSVReader {

    private ResourceBundle myResources;
    private String simulationType;

    private int width;
    private int height;

    private List<Cell> cells = new ArrayList<>();

    /**
     * Constructor for the CSVReader
     * @param file String of the file path for the configuration .csv file
     * @throws CSVFileException Handles the various errors that may come about when reading in files
     */
    public CSVReader(String file) throws CSVFileException {

        Map<String, Integer> myMaxStateCount = new HashMap<>();

        String[] SIMULATION_TYPES = {"Fire", "GameOfLife", "RPS", "Percolation", "Segregation", "PredatorPrey"};

        for (String s: SIMULATION_TYPES) {
            if (s.equals("GameOfLife")) {
                myMaxStateCount.put(s, 1);
            }
            else {
                myMaxStateCount.put(s, 2);
            }
        }

        Scanner sc;
        try {
            sc = new Scanner(CSVReader.class.getResourceAsStream(file));
        }catch (NullPointerException e){
            throw new CSVFileException("Chosen file or file path is wrong");
        }

        simulationType = sc.nextLine();

        try {
            myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + simulationType);
        }catch (MissingResourceException me){
            throw new CSVFileException("Wrong Simulation Type");
        }

        String[] dim = sc.nextLine().split(",");

        width = Integer.parseInt(dim[0]);
        height = Integer.parseInt(dim[1]);

        if(!Arrays.asList(SIMULATION_TYPES).contains(simulationType)){
            throw new CSVFileException("Wrong Simulation Type");
        }
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + simulationType);

        int rowCounter = 0;

        boolean isRandom = false;

        while (sc.hasNextLine()) {
            String[] current = sc.nextLine().split(",");

            if (current[0].equalsIgnoreCase("random")) {
                isRandom = !isRandom;
                random(sc);
                break;
            }

            if (width <current.length) {
                throw new CSVFileException("Values exceed grid size.");
            }

            else if (width >current.length) {
                throw new CSVFileException("Values unavailable for specified grid size");
            }

            initCells(myMaxStateCount, rowCounter, current);

            rowCounter++;
        }

        if(height >rowCounter && !isRandom){
            throw new CSVFileException("Values unavailable for specified grid size");
        }

        else if(height <rowCounter && !isRandom) {
            throw new CSVFileException("Values exceed grid size.");
        }
    }

    private void initCells(Map<String, Integer> myMaxStateCount, int rowCounter, String[] current) throws CSVFileException {
        for (int i = 0; i < width; i++) {
            Integer state = Integer.parseInt(current[i]);

            if(state<0 || state> myMaxStateCount.get(simulationType)) {
                throw new CSVFileException("Wrong state value");
            }

            Cell newCell = new Cell(rowCounter, i, Integer.parseInt(current[i]), width, height);
            newCell.giveCellResources(myResources);
            cells.add(newCell);
        }
    }

    private void random(Scanner sc) {
        int prob1 = 33;
        int prob2 = 33;
        boolean input = false;

        if (sc.hasNextLine()) {
            input = !input;
            prob1 = sc.nextInt();
            prob2 = sc.nextInt();
        }

        initRandomCells(prob1, prob2, input);
    }

    private void initRandomCells(int prob1, int prob2, boolean input) {
        for (int i = 0; i < width; i ++) {
            for (int j = 0; j < height; j ++) {
                if (simulationType.equals("GameOfLife") && !input) {
                    Cell newCell = new Cell(j, i, randomState(50, 50), width, height);
                    newCell.giveCellResources(myResources);
                    cells.add(newCell);
                } else {
                    Cell newCell = new Cell(j, i, randomState(prob1, prob2), width, height);
                    newCell.giveCellResources(myResources);
                    cells.add(newCell);
                }
            }
        }
    }

    private int randomState(int prob1, int prob2) {
        int rand = ThreadLocalRandom.current().nextInt(0,100);
        if (rand < prob1) {
            return 0;
        } else if (rand < (prob2 + prob1)) {
            return 1;
        } else {
            return 2;
        }
    }

    /**
     * Getter function for the list of cells created from the .csv values
     * @return List of Cell types
     */
    public List<Cell> getCells() {
        return new ArrayList<>(cells);
    }

    /**
     * Returns a string representing the simulation type
     * @return String
     */
    public String getType() {
        return simulationType;
    }

    /**
     * Returns the width of the grid (number of columns)
     * @return int num of columns
     */
    public int getWidth() {return width; }

    /**
     * Returns the height of the grid (number of rows)
     * @return int num of rows
     */
    public int getHeight() { return height; }

}
