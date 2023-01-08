package frontend;

import backend.Grid;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import util.CSVFileException;
import util.CSVReader;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
SimulationViewer is the main class where it takes in components from both the front-end and the back-end to create a smooth
animation of the simulation.
 */
public class SimulationViewer extends Application {

    private static final Logger LOGGER = Logger.getAnonymousLogger(); // Logs errors/exceptions

    private static final String DEFAULT_STRING = "";
    public static final String DEFAULT_RESOURCE_PACKAGE = "properties/";

    static final int VIEWER_WIDTH = 550;
    static final int VIEWER_HEIGHT = 550;
    private static final int GRAPH_WIDTH= 600;
    private static final int GRAPH_HEIGHT= 300;
    private static final double GRAPH_SEC_DELAY= 0.04;

    private static final double OUTLINE_WIDTH = 0.5;
    private static final double DURATION_INCREMENT = 0.1;

    private double duration = 1.0;

    private int counter = 0;

    private String simType;
    private int numStates;

    private ResourceBundle myResources;
    private ResourceBundle myGUIResources;

    private Timeline animation;

    private CSVReader reader;
    private Grid currentCells;

    private AbstractGridMaker testGridCreator;
    private AnchorPane squarePane;

    private BorderPane myRoot;
    private HBox simButtons;

    private Scene startScene;

    private GUI myUI;
    private ImageView img;

    private String gridShape;

    private Color default1;
    private Color default2;
    private Color default3;

    private Color customColor1;
    private Color customColor2;
    private Color customColor3;

    private String defaultImg1 = DEFAULT_STRING;
    private String defaultImg2 = DEFAULT_STRING;
    private String defaultImg3 = DEFAULT_STRING;

    private String customImg1 = DEFAULT_STRING;
    private String customImg2 = DEFAULT_STRING;
    private String customImg3 = DEFAULT_STRING;

    /**
     * Override start method that initializes the GUI and the property files, as well as sets up the first menu where the user
     * selects the .csv configuration file for the simulation.
     * @param stage Stage that shows the scene
     */
    @Override
    public void start(Stage stage) {

        myUI = new GUI(this, stage);
        myGUIResources = myUI.getMyGUIResources();

        var myPane = myUI.getMyPane();

        FileChooser fileChooser = myUI.getConfigFileChooser();

        var selectFile = new Button(myGUIResources.getString("FILE_BUT"));
        selectFile.setOnAction( e -> handleFile(fileChooser, stage));

        var gpane = new GridPane();

        positionButton(myPane, selectFile, gpane);

        startScene = new Scene(myPane, VIEWER_WIDTH, VIEWER_HEIGHT);
        startScene.getStylesheets().add(getClass().getResource("startMenu.css").toExternalForm());
        stage.setScene(startScene);
        stage.show();

    }

    private void positionButton(BorderPane myPane, Button selectFile, GridPane gpane) {
        gpane.add(selectFile, 0, 0);

        myPane.setBottom(gpane);
        gpane.setAlignment(Pos.BOTTOM_CENTER);
        BorderPane.setMargin(gpane, new Insets(0, 0, 75, 0));
    }

    /**
     * Allows the user to choose a configuration file and sets up the program for that specific simulation.
     * @param f FileChooser object that allows user to select their own .csv file
     * @param stage the Stage
     */
    void handleFile(FileChooser f, Stage stage) {
        var file = f.showOpenDialog(stage);
        if (file != null) {
            String configName = file.getName();
            myRoot = new BorderPane();

            try {
                reader = new CSVReader("/configurations/" + configName);
//                reader = new CSVReader("/configurations/individualSimulations/" + configName);
                simType = reader.getType();
            } catch (CSVFileException csve){
                showError(csve.getMessage());
            }

            myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + simType);
//            myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "individualProperties/analysis_jwg33");
//            myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "individualProperties/analysis_am641");

            numStates = Integer.parseInt(myResources.getString("STATES"));
            myUI.updateGUI(myResources);

            getColors();
            getStateImages();

            customizeSim(stage);
        }
    }

    private void getColors() {
        default1 = Color.web(myResources.getString("STATE1"));
        default2 = Color.web(myResources.getString("STATE2"));
        default3 = Color.web(myResources.getString("STATE3"));
    }

    private void getStateImages() {
        if (myResources.containsKey("IMG1")) {
            defaultImg1 = myResources.getString("IMG1");
        }
        if (myResources.containsKey("IMG2")) {
            defaultImg2 = myResources.getString("IMG2");
        }
        if (myResources.containsKey("IMG3")) {
            defaultImg3 = myResources.getString("IMG3");
        }
    }

    private void customizeSim(Stage stage) {
        stage.close();

        img = new ImageView(new Image(this.getClass().getResourceAsStream(myGUIResources.getString("BCKGD_IMG"))));

        BorderPane root = new BorderPane();
        root.getChildren().add(img);

        HBox colorOptions = new HBox(10);

        root.setCenter(colorOptions);
        colorOptions.setAlignment(Pos.CENTER);

        var customSim = new Button(myGUIResources.getString("CUSTOM_SIM"));
        customSim.setOnAction(s -> handleSettings(stage));

        var color = new Button(myGUIResources.getString("CUSTOM_BUT"));
        color.setOnAction(c -> handleCustomization(stage));

        var go = new Button(myGUIResources.getString("GO_BUT"));
        go.setOnAction(g -> setUpSim(stage));

        var back = new Button(myGUIResources.getString("MAIN_BACK"));
        back.setOnAction(b -> handleBackToMenu(stage));

        colorOptions.getChildren().addAll(customSim, color, go, back);

        Scene customizeScene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);

        stage.setScene(customizeScene);
        stage.show();

    }

    private void handleSettings(Stage stage) {
        img = new ImageView(new Image(this.getClass().getResourceAsStream(myGUIResources.getString("BCKGD_IMG"))));

        BorderPane root = new BorderPane();
        root.getChildren().add(img);

        Button back = new Button(myGUIResources.getString("COLOR_BACK"));
        back.setOnAction(b -> handleBackToCustomize(stage));

        VBox options = myUI.simSettingsPage();

        options.getChildren().add(back);

        root.setCenter(options);
        options.setAlignment(Pos.CENTER);

        Scene simOptionsScene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);

        stage.close();
        stage.setTitle(myGUIResources.getString("OPTIONS_TITLE"));
        stage.setScene(simOptionsScene);

        stage.show();
    }

    private void handleCustomization(Stage stage) {
        var colorChooser = myUI.makeFullCustomizerMenu();
        BorderPane root = new BorderPane();
        root.getChildren().add(img);

        root.setCenter(colorChooser);
        colorChooser.setAlignment(Pos.CENTER);
        BorderPane.setMargin(colorChooser, new Insets(75,0,0,0));

        Button back = new Button(myGUIResources.getString("COLOR_BACK"));
        back.setOnAction(b -> handleBackToCustomize(stage));

        var gpane = new GridPane();
        positionButton(root, back, gpane);


        Scene myScene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);

        stage.close();
        stage.setTitle(myGUIResources.getString("COLOR_TITLE"));
        stage.setScene(myScene);
        stage.show();

    }

    private void showError (String message) {
        var alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(myGUIResources.getString("ERROR_TITLE"));
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void setUpSim(Stage stage) {

        // Gets sim settings from the options UI
        gridShape = myUI.getGridType();

        // Gets most updated colors from ColorChooser UI
        customColor1 = myUI.getState1();
        customColor2 = myUI.getState2();
        customColor3 = myUI.getState3();

        customImg1 = myUI.getStateImg1();
        customImg2 = myUI.getStateImg2();
        customImg3 = myUI.getStateImg3();
        currentCells = new Grid(reader.getCells(), simType);

        initializeGridCreator();

        updateGridCreator();

        if (simButtons == null) { // to prevent extra buttons from being made when loading another simulation
            simButtons = myUI.getSimOptions();

            addSimButtons(stage);
        }

        myRoot.setBottom(simButtons);
        simButtons.setAlignment(Pos.BOTTOM_CENTER);
        BorderPane.setMargin(simButtons, new Insets(0, 0, 15, 0));

        var myScene = new Scene(myRoot, VIEWER_WIDTH, VIEWER_HEIGHT);
        stage.close();
        stage.setTitle(String.format("Running: %s", simType));
        stage.setScene(myScene);
        stage.show();

        animate(duration);
    }

    private void initializeGridCreator() {
        gridShape = myResources.getString("GRID_SHAPE");
        if (myUI.getGridType() != null) {
            gridShape = myUI.getGridType();
        }

        switch (gridShape) {
            case "Square":
               testGridCreator = new SquareGridMaker(currentCells.getCells()); break;
            case "Hexagonal":
                testGridCreator = new HexagonGridMaker(currentCells.getCells()); break;
            case "Triangular":
               testGridCreator = new TriangleGridMaker(currentCells.getCells()); break;
        }


        testGridCreator.giveGridDefaultColors(default1, default2, default3);
        testGridCreator.giveGridCustomColor(customColor1, customColor2, customColor3);

        testGridCreator.giveGridDefaultImages(defaultImg1, defaultImg2, defaultImg3);
        testGridCreator.giveGridCustomImages(customImg1, customImg2, customImg3);
    }

    private void addSimButtons(Stage stage) {
        Button stepper = myUI.makeButton(myGUIResources.getString("STEP_BUT"),
                myGUIResources.getString("FWD_IMG"), true);
        stepper.setOnAction(s -> handleStep());

        Button fast = myUI.makeButton(myGUIResources.getString("FAST_BUT"), "", false);
        fast.setOnAction(f -> handleFast());

        Button slow = myUI.makeButton(myGUIResources.getString("SLOW_BUT"), "", false);
        slow.setOnAction(w -> handleSlow());

        Button backMenu = new Button(myGUIResources.getString("QUIT_BUT"));
        backMenu.setOnAction(b -> handleBackToMenu(stage));

        Button graph = new Button(myGUIResources.getString("GRAPH_BUT"));
        graph.setOnAction(z -> handleGraph());

        simButtons.getChildren().addAll(stepper, fast, slow, backMenu,graph);
    }

    private void handleGraph() {

        numStates = getCurrentCells().getKeySet().size();

        Stage stage = new Stage();

        stage.setX(GRAPH_HEIGHT/3);
        stage.setY(GRAPH_WIDTH/6);

        int graphStep = GRAPH_WIDTH/getCurrentCells().getCells().size();

        Group root = new Group();
        Scene scene = new Scene(root, GRAPH_WIDTH, GRAPH_HEIGHT);
        stage.setTitle(myGUIResources.getString("GRAPH_TITLE"));
        stage.setScene(scene);
        stage.show();

        Color[] colors  = {default1,default2,default3};

        Rectangle[] bars = new Rectangle[numStates];
        for(int i=0; i<numStates; i++){
            Rectangle r1 = initGraphRectangle(getCurrentCells().getStateCells(i).size() * graphStep, colors[i], i);
            root.getChildren().add(r1);
            bars[i]=r1;
        }

        animateGraph(graphStep, root, bars);
    }

    private void animateGraph(int graphStep, Group root, Rectangle[] bars) {
        var frame = new KeyFrame(Duration.seconds(GRAPH_SEC_DELAY), e -> updateGraph(bars,root,graphStep));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    private Rectangle initGraphRectangle(int width, Color color, int state) {
        Rectangle r1 = new Rectangle();
        r1.setWidth(width);
        r1.setHeight(GRAPH_HEIGHT/numStates);
        r1.setX(0);
        r1.setY(state*GRAPH_HEIGHT/numStates);
        r1.setFill(color);
        r1.setStroke(Color.BLACK);
        r1.setStrokeWidth(OUTLINE_WIDTH);
        return r1;
    }


    private void updateGraph(Rectangle[] bars, Group groot, int graphStep) {
        groot.getChildren().removeAll(bars);
        for(int i=0; i<bars.length; i++){
            try {
                bars[i].setWidth(getCurrentCells().getStateCells(i).size() * graphStep);
            } catch (NullPointerException n){
                LOGGER.log(Level.SEVERE, "Grid is null", n);
            }
        }
        groot.getChildren().addAll(bars);

    }

    /**
     * Animates the simulation by calling the step method every "duration" length
     * @param duration
     */
    void animate(double duration) {
        var frame = new KeyFrame(Duration.seconds(duration), e -> step());
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    private void step() {
        myRoot.getChildren().remove(squarePane);
        currentCells.updateGrid();
        counter++;
        updateGridCreator();
    }

    private void updateGridCreator() {
        testGridCreator.updateGridMaker(currentCells.getCells(), numStates);

        testGridCreator.makeGrid();
        squarePane = testGridCreator.getMyPane();

        myRoot.getChildren().add(squarePane);
    }

    private void handleBackToMenu(Stage stage) {
        if (animation != null) {
            animation.stop();
        }
        stage.close();
        stage.setScene(startScene);
        stage.show();
    }

    private void handleBackToCustomize(Stage stage) {
        stage.close();
        customizeSim(stage);
    }

    private void handleStep() {
        animation.stop();
        var frame = new KeyFrame(Duration.seconds(.01), e -> step());
        var frame2 = new KeyFrame(Duration.INDEFINITE, f -> step());
        animation = new Timeline();
        animation.setCycleCount(1);
        animation.getKeyFrames().add(frame);
        animation.getKeyFrames().add(frame2);
        animation.play();

    }

    private void handleFast() {
        animation.stop();
        if (duration > 0.15) {
            duration -= DURATION_INCREMENT;
        }
        System.out.println(String.format("Speed = %s", duration));
        animate(duration);
    }

    private void handleSlow() {
        animation.stop();
        duration += DURATION_INCREMENT;
        System.out.println(String.format("Speed = %s", duration));
        animate(duration);
    }

    /**
     * Resets the image paths to the default string to prevent previous customizations from seeping into the next run.
     */
    void reset() {
        defaultImg1 = DEFAULT_STRING;
        defaultImg2 = DEFAULT_STRING;
        defaultImg3 = DEFAULT_STRING;

        customImg1 = DEFAULT_STRING;
        customImg2 = DEFAULT_STRING;
        customImg3 = DEFAULT_STRING;
    }

    /**
     * Returns the Grid object of the current cells.
     * @return Grid object of current cells
     */
    Grid getCurrentCells() {
        return currentCells;
    }

    /**
     * Returns animation timeline
     * @return Timeline object
     */
    Timeline getTimeline() {
        return animation;
    }

    /**
     * Returns duration of each frame of the animation
     * @return double value of frame duration
     */
    double getDuration() {
        return duration;
    }

    /**
     * Returns a string representing the type of simulation
     * @return String of simulation type
     */
    String getSimType() {
        return simType;
    }

    /*
    Launches the application, LET'S GOOO!
     */
    public static void main(String[] args){
        launch(args);
    }
}
