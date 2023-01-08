package backend;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class implements the rules for Segregation Simulation
 */
public class SegregationRule extends Rule {

    private Map<Integer, Set<Cell>> myMap;

    private final static int SIMILAR = 3; //parameter for number of like-colored cells surrounding it in order for it to be satisfied
    private ArrayList<Cell> replaceMe;
    private ArrayList<Cell> zeros;
    private int counter;

    public Cell update(Cell cell) {
        int randNum;
        int randInd;

        if (cell.getState() == 0) {
            if (replaceMe.size() > 0) {
                randInd = ThreadLocalRandom.current().nextInt(0, zeros.size());
                Cell location = zeros.get(randInd);
                zeros.remove(randInd);

                randNum = ThreadLocalRandom.current().nextInt(0, replaceMe.size());
                Cell temp = replaceMe.get(randNum);
                replaceMe.remove(randNum);
                return makeCell(temp.getState(), location);
            } else {
                Cell location = zeros.get(0);
                zeros.remove(0);
                return makeCell(cell.getState(), location);
            }
        } else if ((cell.getMyNeighborhood().getLikeNeighborNum()) < SIMILAR && counter > 0) {
            counter --;
            return makeCell(0, cell);
        }
        return makeCell(cell.getState(), cell);
    }



    @Override
    public void giveStates(Map<Integer, Set<Cell>> states) {
        myMap = states;
        generateReplaceMe();
    }

    private void generateReplaceMe() {
        counter = 0;
        replaceMe = new ArrayList<Cell>();
        zeros = new ArrayList<Cell>();
        Set<Cell> full = new HashSet<>();
        full.addAll(myMap.get(1));
        full.addAll(myMap.get(2));
        for (Cell c : full) {
            if ((c.getMyNeighborhood().getLikeNeighborNum()) < SIMILAR) {
                replaceMe.add(c);
//                System.out.println(c.getState());
            }
        }
        for (Cell c : myMap.get(0)) {
            zeros.add(c);
            counter ++;
        }
    }
}
