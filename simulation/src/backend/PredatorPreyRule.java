package backend;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class implements the rules for Predator and Prey Simulation
 */
public class PredatorPreyRule extends Rule {

    private Map<Integer, Set<Cell>> myMap;

    private List<Cell> sharks;
    private List<Cell> fish;
    private List<Cell> open;
    private Map<Cell, Cell> replacements;

    @Override
    protected Cell makeCell(int updatedState, Cell oldCell){
        Cell newCell =  new Cell(oldCell.getRow(),oldCell.getCol(),updatedState,oldCell.getWidth(),oldCell.getHeight());

        newCell.giveCellResources(oldCell.getSimResources());
        newCell.setReproNum(oldCell.getMyReproNum());

        newCell.setEnergy(oldCell.getEnergy());
        return newCell;
    }

    public Cell update(Cell cell) {
        for (Cell c : replacements.keySet()) {
            if (c.equals(cell)) {
                return makeCell(replacements.get(c).getState(), cell);
            }
        }
        return makeCell(cell.getState(), cell);
    }

    private void movement(Cell cell) {
        sharks = new ArrayList<>();
        fish = new ArrayList<>();
        open = new ArrayList<>();
        Set<Cell> neighbors = prune(cell.getMyNeighborhood().getNeighbors());

        for(Cell c : neighbors) {
            if (c.getState() == 0) {
                open.add(c);
            } else if (c.getState() == 1) {
                fish.add(c);
            } else {
                sharks.add(c);
            }
        }
    }

    @Override
    public void giveStates(Map<Integer, Set<Cell>> states) {
        myMap = states;
        generateReplacements();
    }

    private void generateReplacements() {
        replacements = new HashMap<Cell, Cell>();
        Set<Cell> animals = new HashSet<>();

        //build animals
        for (Integer i : myMap.keySet()) {
            if (i > 0)
            animals.addAll(myMap.get(i));
        }

        for (Cell c : animals) {
            c.age();
        }

        sharkEatingHabits();
        animalToOpen(animals);
    }

    private void animalToOpen(Set<Cell> animals) {
        for (Cell c : animals) {
            movement(c);
            Cell replace;

            if (doneAlready(c)) {
                continue;
            }

            if (open.size() > 0) {
                replace = getRandCell(c, open);
                if (c.getMyReproNum() <= 0) {
                    c.setReproNum(3);
                    replacements.put(c, new Cell(0,0,c.getState()));
                } else {
                    replacements.put(c, replace);
                }
            }
        }
    }

    private Cell getRandCell(Cell c, List<Cell> type) {
        int randInd;
        Cell replace;
        randInd = ThreadLocalRandom.current().nextInt(0, type.size());
        replace = type.get(randInd);
        type.remove(randInd);

        replacements.put(replace, c);
        return replace;
    }

    private void sharkEatingHabits() {
        for (Cell c : myMap.get(2)) {
            movement(c);

            if (c.getEnergy() <= 0) {
                replacements.put(c, new Cell(0,0,0));
            }

            if (fish.size() > 0) {
                c.eatFish();
                getRandCell(c, fish);
                if (c.getMyReproNum() <= 0) {
                    c.setReproNum(3);
                    replacements.put(c, new Cell(0,0,2));
                } else {
                    replacements.put(c, new Cell(0, 0, 0));
                }
            }
        }
    }

    private Set<Cell> prune(Set<Cell> set) {
        Set<Cell> update = new HashSet<>(set);
        for (Cell c : set) {
            for (Cell i : replacements.keySet()) {
                if (i.equals(c)) {
                    update.remove(c);
                }
            }
        }

        return update;
    }

    private boolean doneAlready(Cell cell) {
        for (Cell c : replacements.keySet()) {
            if (c.equals(cell)) {
                return true;
            }
        }
        return false;
    }
}


// 0 --> empty
// 1 --> fish
// 2 --> shark