package backend;

import java.util.*;

/**
 * The purpose of this class is to hold all the cells so that they can be visualized later on.
 */
public class Grid {

//    public enum Grid {
//        GAMEOFLIFE("GameOfLife", 2, new GameOfLifeRule()),
//        PERCOLATION("Percolation", 3, new PercolationRule()),
//        FIRE("Fire", 3, new FireRule()),
//        RPS("RPS", 3, new RPSRule()),
//        SEGREGATION("Segregation", 3, new SegregationRule());


    private Map<Integer, Set<Cell>> myStates;
    private ArrayList<Cell> myTotal;

    private int myStateCount;
    private Rule myRule;
    private String mySim;

    /**
     * Constructs a new Grid
     * @param type is a String representing the type of simulation
     */
    public Grid(String type) {

        myStates = new HashMap<>();
        myTotal = new ArrayList<>();

        mySim = type;

        String DEFAULT_RESOURCE_PATH = "properties/";
        ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PATH + mySim);
//            ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PATH + "individualProperties/analysis_jwg33");
//            ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PATH + "individualProperties/analysis_am641");

        myStateCount = Integer.parseInt(myResources.getString("STATES"));

        ruleMaker(type);
        initializeStates();
    }

    private void ruleMaker(String type) {
        if (type.equals("GameOfLife")) {
            myRule = new GameOfLifeRule();
        } else if (type.equals("Percolation")) {
            myRule = new PercolationRule();
        } else if (type.equals("RPS")) {
            myRule = new RPSRule();
        } else if (type.equals("Fire")) {
            myRule = new FireRule();
        } else if (type.equals("Segregation")) {
            myRule = new SegregationRule();
        } else if (type.equals("PredatorPrey")) {
            myRule = new PredatorPreyRule();
        }
    }

    /**
     * Constructs a new grid based on a simulation type and a specific list of cells
     * @param cellList a list of Cell objects that will be contained in the grid
     * @param type the type of simulation
     */
    public Grid(List<Cell> cellList, String type) {
            this(type);
            myTotal = (ArrayList<Cell>) cellList;
            updateStateMap();
        }

    /**
     * updates the grid based on the simulation type and current cell states
     */
    public void updateGrid() {
        Grid updated = new Grid(mySim);
        myRule.giveStates(myStates);
        for (Cell cell : myTotal) {
            updated.add(myRule.update(cell));
        }
        updated.neighbors();
        myTotal = (ArrayList<Cell>) updated.getCells();
        initializeStates();
        updateStateMap();
    }

    private void initializeStates() {
        for (int i = 0; i < myStateCount; i++) {
            myStates.put(i, new HashSet<>());
        }
    }

    private void updateStateMap() {
        for (Cell c : myTotal) {
            Set<Cell> temp = myStates.get(c.getState());
            temp.add(c);
            myStates.put(c.getState(), temp);
        }
        neighbors();
    }

    public List<Cell> getCells() {
        return myTotal;
    }

    public Set<Cell> getStateCells(int state) {
        return myStates.get(state);
    }

    public Set<Integer> getKeySet(){
        return myStates.keySet();
    }

    public boolean isState(Cell c, int state) {
        return (getStateCells(state).contains(c));
    }

    public String getMySimType() {
        return mySim;
    }

    /**
     * updates the neighborhoods for each cell in the grid
     */
    public void neighbors() {
        Neighborhood n;
        for (Cell c : myTotal) {
            n = c.getMyNeighborhood();
            for (Cell other : myTotal) {
                if (!c.equals(other) && n.isNeighbor(other)) {
                    n.addNeighbors(other);
                    other.getMyNeighborhood().addNeighbors(c);
                }
            }
        }
    }

    /**
     * adds a single cell to the grid
     * @param cell a Cell object to be added to the grid
     */
    public void add(Cell cell) {
        myTotal.add(cell);
        //updateStateMap();
    }

}

