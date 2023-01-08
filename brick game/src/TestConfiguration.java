import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class TestConfiguration {

    public String filename;
    public TestConfiguration(String inputFile) {
        filename = inputFile;
    }

    /**
     * Used for testing the GameHandler class. This method constructs a HashMap of Strings mapped to Strings, with the keys being
     * initial condition names and the values being the specified values for the corresponding variables. Thus, all test files must be formatted
     * in the same way.
     * @return a Map of condition names to their respective values
     */
    public HashMap<String, String> getConditions() {

        HashMap<String, String> initialConditions = new HashMap<>();
        File testConfigurationFile = new File("resources/Tests/" + filename);
        try {
            Scanner sc = new Scanner(testConfigurationFile);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] conditions = line.split(",");
                initialConditions.put(conditions[0], (conditions[1]));
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return initialConditions;
    }

}

