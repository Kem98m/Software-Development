## API Discussion document

#### Discovering
      
- public SquareGridMaker(Collection<Cell> cells) -external
- public void makeGrid() -external
- public AnchorPane getMyPane() -internal
 
- public TriangleGridMaker(Collection<Cell> cells) -external
- public void makeGrid() -external
- public AnchorPane getMyPane() -internal

- public AbstractGridMaker(Collection<Cell> cells) -external
- public void updateGridMaker(Collection<Cell> cells, int numberOfStates) -external
- public void giveGridDefaultColors(Color c1, Color c2, Color c3) -internal
- public void giveGridDefaultImages(String i1, String i2, String i3) -internal
- public void giveGridCustomColor(Color c1, Color c2, Color c3) -internal
- public void giveGridCustomImages(String i1, String i2, String i3) -internal
- public void setCellColor(int myState, Shape myCellShape) -external
- public void setCellImage(int myState, Shape myCellShape) -external
- public void setOutline(Shape shape) -external
- public void changeState(List<Shape> myShapeList, Shape shape) 0e
- abstract public void makeGrid();-external
- abstract public AnchorPane getMyPane();-internal
- public int getNumStates() -internal
 
- public HexagonGridMaker(Collection<Cell> cells) -external
- public void makeGrid() -external
- public AnchorPane getMyPane() -internal

- public GUI(SimulationViewer simView, Stage st) -external
- public void updateGUI(ResourceBundle simResources) -internal
- public HBox makeFullCustomizerMenu() -external
- public VBox makeImgChooser() -external
- public Button makeButton(String s, String imgPath, boolean isSquare)-external 
- public VBox simSettingsPage() -external
- public FileChooser getConfigFileChooser() -internal
- public HBox getSimOptions() -internal
- public BorderPane getPane() -internal
- public Color getState1() -internal
- public Color getState2() -internal
- public Color getState3() -internal
- public String getStateImg1()-internal 
- public String getStateImg2() -internal
- public String getStateImg3() -internal
- public ResourceBundle getMyGUIResources() -internal
- public String getGridType() -internal
- public String getNeighbor() -internal
- public String getEdgeType() -internal

- public void start(Stage stage) -internal
- public void handleFile(FileChooser f, Stage stage) -internal
- public void animate(double duration) -internal
- public void reset() -internal
- public Grid getCurrentCells() -internal
- public Timeline getTimeline() -internal
- public double getDuration() -internal
- public String getSimType() -internal
 
- public Writer(Grid cells, String filePath) - external
- public void save() - external
 
- public CSVReader(String file) throws CSVFileException - external
- public List<Cell> getCells()  - external
- public String getType() - external
- public int getWidth() - external
- public int getHeight() - external
 
- public CSVFileException(String message) - external
- public CSVFileException(String message, Throwable cause) - external
 

- public Neighborhood(int neighborType, int edgeType) - external
- public int getLikeNeighborNum() -internal
- public void addNeighbors(Cell c) - external
- public Set<Cell> getNeighbors() - external
- public boolean isNeighbor(Cell c) -internal
- public abstract boolean isCardinalNeighbor(Cell c); -internal
- public abstract boolean isDiagonalNeighbor(Cell c); -internal
- public abstract boolean toroidalCardinal(Cell c); -internal
- public abstract boolean toroidalDiagonal(Cell c); -internal
- public boolean customPolicy(Cell c) -internal
 
- public Cell update(Cell cell) -internal
 
- public Cell update(Cell cell) -internal

 
- public Cell update(Cell cell) -internal
- public void giveStates(Map<Integer, Set<Cell>> states) - external
 
- public SquareNeighborhood(Cell c, int neighbor, int edge) - external
- public boolean isCardinalNeighbor(Cell c) -internal
- public boolean isDiagonalNeighbor(Cell c) -internal
- public boolean toroidalCardinal(Cell c) -internal
- public boolean toroidalDiagonal(Cell c) -internal
 
- public Grid(String type) - e
- public Grid(List<Cell> cellList, String type) - e
- public void updateGrid() - e
- public List<Cell> getCells() -external
- public Set<Cell> getStateCells(int state) -external
- public Set<Integer> getKeySet() - e
- public boolean isState(Cell c, int state)  - e
- public String getMySimType() - e
- public void neighbors() - e
- public void add(Cell cell) -external

 
- public Cell update(Cell cell) -internal

 
- public Cell update(Cell cell)  -internal
- public void giveStates(Map<Integer, Set<Cell>> states) -internal

 
- public TriangleNeighborhood(Cell c, int neighbor, int edge)- e
- public boolean isCardinalNeighbor(Cell c) -internal
- public boolean isDiagonalNeighbor(Cell c) -internal
- public boolean toroidalCardinal(Cell c) -internal
- public boolean toroidalDiagonal(Cell c) -internal
- public boolean customPolicy(Cell c) -internal

 

- public HexagonNeighborhood(Cell c, int neighbor, int edge) -external
- public boolean isCardinalNeighbor(Cell c) -internal
- public boolean isDiagonalNeighbor(Cell c) -internal
- public boolean toroidalCardinal(Cell c) -internal
- public boolean toroidalDiagonal(Cell c) -internal
- public boolean customPolicy(Cell c) -internal

 

- public Cell(int r, int c, int s) -external
- public Cell(int r, int c, int s, int w, int h) -external
- public void giveCellResources(ResourceBundle resources) -external

- public Neighborhood getMyNeighborhood() -internal
- public Set<Cell> getNeighbors() -internal
- public void addNeighbors(Cell c) -internal
- public boolean isNeighbor(Cell c) -internal
- public void setState(int state) -internal
- public boolean equals(Cell other) -internal
- public int getRow() -external
- public int getCol() -external
- public int getWidth() -external
- public int getHeight() -external
- public int getState() -external
- public void setReproNum(int num) -internal
- public void setEnergy(int num) -internal
- public void eatFish() -internal
- public void age() -internal
- public int getMyReproNum() -internal 
- public int getEnergy() -internal
- public ResourceBundle getSimResources() -external


- abstract public Cell update(Cell cell); -internal
- public void giveStates(Map<Integer, Set<Cell>> states) -internal

 
- public Cell update(Cell cell) -internal


### API INTERNAL
HOW TO: add a new simulation

- Step one: create new Rule class
    * This is the main difference between simulation types.
    * Your rule class will need to extend the abstract Class Rule, and therefore requires implementation of the update(Cell cell) method. The purpose of this method is, given a cell at any time, it will look at it's neighbors, and determine the state of that cell in the next generation of the grid.
    * The method giveStates(Map...) in Rule can help you if you're simulation requires knowing every cell's state in the beginning.
    * When your update(Cell cell) method returns, it's return statements should all utilize the makeCell(int updatedState, Cell oldCell) method. If the cell requires no change, update should return makeCell(cell.getsState(), cell).
- Step two: update Grid class.
    * In the constructor in the grid class, you must write an extra if else statement:
    ```java
    else if (type.equals("NewType")) {
                    myRule = new NewTypeRule();
                }
    ```
- Step three: write a file
    * Make sure to update the configuration files and write your csv file for the grid layout.


### API External
external: as a client of the backend, the frontend selects an existing simulation to display and then starts running that kind of simulation, updating its own grid visualization

Steps:
1. We first need to select a file that represents the simulation we want to view. We 
can do this with a FileChooser object.
You can do this by using the getter function for the FileChooser from the GUI class.
With this object, we can select our file.
Methods used:
- SimulationViewer.start();
- GUI.getConfigFileChooser();
2. Once the file has been selected, the file must be parsed and processed to obtain
the relevant information, such as the simulation type, the initial configuration, etc.
CSVReader contains the necessary methods to do this.
Methods used:
- SimulationViewer.handleFile();
- CSVReader class
- CSVReader.getType()
3. The GUI class and Simulation Viewer class should interact with each other
and give each other the relevant information that is needed to visualize the grid. Once that
has been done, then we need to animate and update each frame for the simulation.
Each cell in the grid must be updated in terms of their state and image, and the Grid's method
updateGrid does exactly this.
Methods used:
- SimulationViewer.animate();
- SimulationViewer.step();
- Grid.updateGrid();
 