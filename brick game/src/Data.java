import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class Data {

    public static final String LEVEL_FOLDER = "resources/Level/";

    private TreeMap<Integer, Brick[][]> myLevels = new TreeMap<>();
    public static final int NUMBER_OF_LEVELS = 3;
    public static final int BRICK_COLUMNS = 10;
    public static final int BRICK_ROWS = 12;

    /**
     * This method reads from the resources/Level folder and constructs a 2D Brick object array for every level configuration file
     * found in the folder. It stores these 2D arrays in a TreeMap, myLevels.
     */
    public void getDataFromFile() {
        //ArrayList<String> fileNames = scanFolder(LEVEL_FOLDER);
        for (int level = 0; level < NUMBER_OF_LEVELS; level++) {
            File file = new File(LEVEL_FOLDER + "level" + level + ".txt");
            try {
                Scanner sc = new Scanner(file);
                Brick[][] grid = new Brick[BRICK_ROWS][BRICK_COLUMNS];

                int row = 0;
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    String[] entry = line.split(",");
                    for(int i = 0; i < entry.length; i++){
                        Integer value = Integer.parseInt(entry[i]);
                        Brick brick;
                        switch (value) {
                            case 1:
                                brick = new PowerBrick();
                                break;
                            case 2:
                                brick = new MagicBrick();
                                break;
                            case 3:
                                brick = new StoneBrick();
                                break;
                            default:
                                brick = null;
                                break;
                        }
                        grid[row][i] = brick;
                    }
                    row++;
                }
                myLevels.put(level, grid);
                sc.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

//    public ArrayList<String> scanFolder(String path){
//        ArrayList<String> fileNames = new ArrayList<>();
//        File folder = new File(path);
//        File[] listOfFiles = folder.listFiles();
//        for (int i = 0; i < listOfFiles.length; i++) {
//            if (listOfFiles[i].isFile()) {
//                fileNames.add(listOfFiles[i].getName());
//            }
//        }
//        return fileNames;
//    }

    /**
     * This method returns the configuration of bricks for a certain level
     * @param level the level number (level0.txt = level 0)
     * @return the 2D Brick array that describes the brick configuration for that level
     */
    public Brick[][] getData(int level){
        return myLevels.get(level);
    }

    /**
     * This method returns the number of level configuration files found in resources/Level
     * @return the number of levels who have a defined configuration
     */
    public int getNumberOfLevels(){
        return NUMBER_OF_LEVELS;
    }


}
