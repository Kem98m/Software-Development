package frontend;

import backend.Grid;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import util.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.net.MalformedURLException;
import java.util.ResourceBundle;

/**
 * the GUI class handles the majority of the visualized user interface. It helps creates nodes, buttons, menus, while also serving as
 * a relay between SimulationViewer and user inputs.
 */
public class GUI {

    private static final Logger LOGGER = Logger.getAnonymousLogger();

    private Stage stage;
    private SimulationViewer sv;

    private String background;
    private BorderPane myPane;

    private HBox simOptions;

    private Color default1;
    private Color default2;
    private Color default3;

    private Color customColor1;
    private Color customColor2;
    private Color customColor3;

    private String customImg1 = "";
    private String customImg2 = "";
    private String customImg3 = "";


    private ResourceBundle myGUIResources;
    private ResourceBundle mySimResources;

    private FileChooser myFileChooser;

    private Button browse1;
    private Button browse2;
    private Button browse3;

    private String gridType;
    private String neighbor;
    private String edgeType;

    private int type1 = 1;
    private int type2 = 2;
    private int type3 = 3;

    /**
     * Constructor for the GUI
     * @param simView the current SimulationViewer instance
     * @param st Stage upon which to set the GUI scene in
     */
    public GUI(SimulationViewer simView, Stage st) {
        stage = st;
        sv = simView;

        myGUIResources = ResourceBundle.getBundle("/properties/GUI");
        background = myGUIResources.getString("BCKGD_IMG");

        initialize();
        simOptions();
    }

    /**
     * Gives the class the ResourceBundle for the chosen simulation and sets the default color variables.
     * @param simResources ResourceBundle for the simulation
     */
    void updateGUI(ResourceBundle simResources) {
        mySimResources = simResources;
        default1 = Color.web(mySimResources.getString("STATE1"));
        default2 = Color.web(mySimResources.getString("STATE2"));
        default3 = Color.web(mySimResources.getString("STATE3"));
    }

    private void initialize() {
        HBox box = new HBox(50);
        Text title = new Text(myGUIResources.getString("TITLE"));
        title.setFont(Font.font(myGUIResources.getString("FONT"), FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 25));
        title.setFill(Color.WHITE);

        box.getChildren().add(title);

        ImageView img = new ImageView(new Image(this.getClass().getResourceAsStream(background)));

        myPane = new BorderPane();
        myPane.getChildren().add(img);
        myPane.setCenter(box);

        box.setAlignment(Pos.CENTER);
    }

    /**
     * Returns an HBox that will be used to form the color and image customizer menu for the simulation grid
     * @return HBox that contains the color and image choosers.
     */
    HBox makeFullCustomizerMenu() {
        HBox menu = new HBox(10);

        VBox imgMenu = makeImgChooser();
        VBox colorMenu = makeColorChooser();

        menu.getChildren().addAll(colorMenu, imgMenu);

        return menu;
    }

    /**
     * Helper function that makes a VBox containing three image choosers for a maximum of three states
     * @return VBox containing three image choosers
     */
    private VBox makeImgChooser() {
        VBox imgMenu = new VBox(50);

        initializeFileChoosers();
        initializeBrowseButtons();

        VBox box1 = makeImgVBox("Select an image for state 1!", browse1);

        VBox box2 = makeImgVBox("Select an image for state 2!", browse2);

        VBox box3 = makeImgVBox("Select an image for state 3!", browse3);

        imgMenu.getChildren().addAll(box1, box2, box3);

        box1.setAlignment(Pos.CENTER);
        box2.setAlignment(Pos.CENTER);
        box3.setAlignment(Pos.CENTER);

        return imgMenu;
    }

    private VBox makeImgVBox(String s, Button browse1) {
        VBox box1 = new VBox(12.5);
        Label label1 = new Label(s);
        label1.setTextFill(Color.WHITE);
        box1.getChildren().addAll(label1, browse1);
        return box1;
    }

    private void initializeFileChoosers() {
        myFileChooser = new FileChooser();

        FileChooser.ExtensionFilter imageFilter
                = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg");

        myFileChooser.getExtensionFilters().add(imageFilter);
    }

    private void initializeBrowseButtons() {
        browse1 = new Button("Browse (.jpeg .jpg .png)");
        browse1.setOnAction(a -> handleBrowse(type1, stage));

        browse2 = new Button("Browse (.jpeg .jpg .png)");
        browse2.setOnAction(a -> handleBrowse(type2, stage));

        browse3 = new Button("Browse (.jpeg .jpg .png)");
        browse3.setOnAction(a -> handleBrowse(type3, stage));
    }

    private void handleBrowse(int type, Stage stage) {
        var file = myFileChooser.showOpenDialog(stage);

        if (file != null) {
            String path = "";
            try {
                path = file.toURI().toURL().toExternalForm();
            }
            catch (MalformedURLException e) {
                LOGGER.log(Level.SEVERE, "MalformedURLException", e);
            }
            switch (type) {
                case 1: customImg1 = path;
                        break;
                case 2: customImg2 = path;
                        break;
                case 3: customImg3 = path;
                        break;
            }
        }
    }

    private VBox makeColorChooser() {
        VBox colorMenu = new VBox(50);

        ColorPicker color1 = new ColorPicker(default1);
        ColorPicker color2 = new ColorPicker(default2);
        ColorPicker color3 = new ColorPicker(default3);

        VBox boxOne = setUpColorChooserVBox(color1);
        VBox boxTwo = setUpColorChooserVBox(color2);
        VBox boxThree = setUpColorChooserVBox(color3);

        color1.setOnAction(a -> customColor1 = color1.getValue());

        color2.setOnAction(b -> customColor2 = color2.getValue());

        color3.setOnAction(c -> customColor3 = color3.getValue());

        colorMenu.getChildren().addAll(boxOne, boxTwo, boxThree);

        return colorMenu;

    }

    private VBox setUpColorChooserVBox(ColorPicker picker) {
        VBox box = new VBox(10);
        Rectangle rec = new Rectangle(40, 20);
        rec.setFill(picker.getValue());

        picker.setOnAction(a -> rec.setFill(picker.getValue()));

        box.getChildren().addAll(rec, picker);

        box.setAlignment(Pos.CENTER);

        return box;
    }

    /**
     * Generic button maker to reduce duplicate code
     * @param s String that you want the button to show
     * @param imgPath Path for any image you want the button to show
     * @param isSquare If you want the button to be square
     * @return the desired button
     */
    Button makeButton(String s, String imgPath, boolean isSquare) {
        Button butt = new Button(s);
        butt.setWrapText(true);
        butt.setPrefHeight(20);
        if (! imgPath.equals("")) {
            ImageView img = new ImageView(new Image(this.getClass().getResourceAsStream(imgPath)));
            img.setFitHeight(20);

            if (isSquare) {
                img.setFitWidth(20);
            }

            if (!isSquare) {
                img.setFitWidth(30);
                butt.setMaxWidth(30);
            }

            butt = new Button("", img);
        }

        return butt;
    }

    private void simOptions() {
        simOptions = new HBox(5);

        Button play = makeButton("PLAY", myGUIResources.getString("PLAY_IMG"), true);
        play.setOnAction(p -> handlePlay());

        Button pause = makeButton("PAUSE", myGUIResources.getString("PAUSE_IMG"), true);
        pause.setOnAction(u -> handlePause());

        Button load = makeButton("LOAD", myGUIResources.getString("LOAD_IMG"), false);
        load.getStyleClass().add("loadButton");

        load.setOnAction(l -> handleLoad());

        Button save = makeButton("SAVE", myGUIResources.getString("SAVE_IMG"), false);
        save.setOnAction(s -> handleSave());

        simOptions.getChildren().add(load);
        simOptions.getChildren().add(save);
        simOptions.getChildren().add(play);
        simOptions.getChildren().add(pause);
    }

    /**
     * Creates a VBox that is used to make the simulation settings page, where users can customize the grid shape, neighbor and edge
     * policies
     * @return VBox
     */
    VBox simSettingsPage() {
        VBox options = new VBox(20);

        Label l1 = new Label(myGUIResources.getString("SIMSETTINGS_LABEL1"));
        l1.setTextFill(Color.BLANCHEDALMOND);

        ChoiceBox gridShape = new ChoiceBox();
        addChoiceItems(gridShape, "SHAPETYPE1", "SHAPETYPE2",
                "SHAPETYPE3", "GRID_SHAPE");

        gridType = (String)gridShape.getValue();

        Label l2 = new Label(myGUIResources.getString("SIMSETTINGS_LABEL2"));
        l2.setTextFill(Color.BLANCHEDALMOND);

        ChoiceBox neighborType = new ChoiceBox();
        addChoiceItems(neighborType, "NEIGHBORTYPE1", "NEIGHBORTYPE2",
                "NEIGHBORTYPE3", "NEIGHBOR");

        neighbor = (String)neighborType.getValue();

        Label l3 = new Label(myGUIResources.getString("SIMSETTINGS_LABEL3"));
        l3.setTextFill(Color.BLANCHEDALMOND);

        ChoiceBox edgePolicy = new ChoiceBox();
        addChoiceItems(edgePolicy, "EDGETYPE1", "EDGETYPE2",
                "EDGETYPE3", "EDGE");

        edgeType = (String)edgePolicy.getValue();

        options.getChildren().addAll(l1, gridShape, l2, neighborType, l3, edgePolicy);

        return options;
    }

    private void addChoiceItems(ChoiceBox gridShape, String shapetype1, String shapetype2, String shapetype3, String grid_shape) {
        gridShape.getItems().addAll(myGUIResources.getString(shapetype1),
                myGUIResources.getString(shapetype2),
                myGUIResources.getString(shapetype3));
        gridShape.setValue(mySimResources.getObject(grid_shape));
    }

    private void handlePlay() {
        sv.animate(sv.getDuration());
        System.out.println(myGUIResources.getString("PLAYING"));
    }

    private void handlePause() {
        sv.getTimeline().pause();
        System.out.println(myGUIResources.getString("PAUSED"));
    }

    private void handleLoad() {
        sv.getTimeline().pause();
        sv.reset();

        FileChooser fileChooser = getConfigFileChooser();

        sv.handleFile(fileChooser, stage);
    }

    /**
     * Initializes a FileChooser object that aids in choosing a .csv configuration file
     * @return FileChooser object
     */
    FileChooser getConfigFileChooser() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter( myGUIResources.getString("EXT_FILTER"),myGUIResources.getString("EXT"));
        fileChooser.getExtensionFilters().add(extFilter);
        return fileChooser;
    }

    private void handleSave() {
        sv.getTimeline().pause();
        Grid currCells = sv.getCurrentCells();
        String type = sv.getSimType();
        Writer writer = new Writer(currCells, "./data/saves/"+type+"Save.csv");
        writer.save();
        System.out.println(myGUIResources.getString("SAVED"));
    }

    /**
     * Getter function for the simulation settings/options HBox
     * @return HBox
     */
    HBox getSimOptions() {
        return simOptions;
    }

    /**
     * Returns BorderPane containing the main customization menu
     * @return
     */
    BorderPane getMyPane() {
        return myPane;
    }

    /**
     * Returns the custom color selected by the user (or the default color)
     * @return Color for state 1
     */
    Color getState1() {
        return customColor1;
    }

    /**
     * Returns the custom color selected by the user (or the default color)
     * @return Color for state 2
     */
    Color getState2() {
        return customColor2;
    }

    /**
     * Returns the custom color selected by the user (or the default color)
     * @return Color for state 3
     */
    Color getState3() {
        return customColor3;
    }

    /**
     * Returns the custom image path selected by the user (or the default image)
     * @return Image path for state 1
     */
    String getStateImg1() {
        return customImg1;
    }

    /**
     * Returns the custom image path selected by the user (or the default image)
     * @return Image path for state 2
     */
    String getStateImg2() {
        return customImg2;
    }

    /**
     * Returns the custom image path selected by the user (or the default image)
     * @return Image path for state 3
     */
    String getStateImg3() {
        return customImg3;
    }

    /**
     * ResourceBundle getter for the GUI properties file
     * @return ResourceBundle for GUI
     */
    ResourceBundle getMyGUIResources() {
        return myGUIResources;
    }

    /**
     * Getter function for the grid shape
     * @return String
     */
    String getGridType() {
        return gridType;
    }

    /**
     * Getter function for the neighbor type for the simulation
     * @return String
     */
    public String getNeighbor() {
        return neighbor;
    }

    /**
     * Returns edge policy selected by the user
     * @return String
     */
    public String getEdgeType() {
        return edgeType;
    }

}
