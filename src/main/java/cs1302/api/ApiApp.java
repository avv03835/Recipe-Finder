package cs1302.api;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.Priority;
import javafx.geometry.Pos;
import javafx.scene.control.Separator;
import javafx.scene.layout.Region;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.layout.BorderPane;

/**
 * REPLACE WITH NON-SHOUTING DESCRIPTION OF YOUR APP.
 */
public class ApiApp extends Application {
    Stage stage;

    Scene startScene;
    VBox startRoot;
    HBox searchPane;
    Label searchLabel;
    TextField searchField;
    Button searchButton;
    ScrollPane startScroll;
    VBox scrollVBox;
    ImageView[] imageArray;
    BorderPane[] bpArray;
    TextFlow[] descArray;
    Button[] recipeButton;
    HBox progressPane;
    ProgressBar progressBar;
    Button setLocButton;

    Scene setLocScene;
    VBox setLocRoot;
    HBox setLocBackPane;
    Button setLocBack;
    Label setLocTitle;
    HBox setLocPane;
    Label setLocLabel;
    TextField cityField;
    String city;
    Button setButton;

    Scene recipeScene;
    VBox recipeRoot;
    HBox recipeBackPane;
    Button recipeBack;
    TextFlow recipeFlow;
    String recipeName;
    BorderPane recipePane;
    VBox ingrPane;
    ImageView recipeImage;
    ScrollPane ingrScroll;
    VBox ingrScrollPane;
    String[] ingrNameList;
    Hyperlink[] ingrList;
    TextFlow instrFlow;

    Scene ingrScene;
    VBox ingrRoot;
    HBox ingrBackPane;
    Button ingrBack;
    TextFlow ingrNameFlow;
    HBox[] ingrPaneList;
    ImageView[] ingrImages;
    TextFlow[] ingrNames;
    TextFlow[] ingrPrices;

    /**
     * Constructs an {@code ApiApp} object. This default (i.e., no argument)
     * constructor is executed in Step 2 of the JavaFX Application Life-Cycle.
     */
    public ApiApp() {
        stage = null;

        startRoot = new VBox();
        startScene = new Scene(startRoot, 800, 600);
        searchPane = new HBox(4);
        searchLabel = new Label("Search:");
        searchField = new TextField("Chicken");
        searchButton = new Button("Search");
        scrollVBox = new VBox();
        startScroll = new ScrollPane(scrollVBox);
        imageArray = new ImageView[10];
        bpArray = new BorderPane[10];
        descArray = new TextFlow[10];
        recipeButton = new Button[10];
        progressPane = new HBox(4);
        progressBar = new ProgressBar();
        setLocButton = new Button("Set Location");

        setLocRoot = new VBox();
        setLocScene = new Scene(setLocRoot, 800, 600);
        setLocBackPane = new HBox(4);
        setLocBack = new Button("Back");
        setLocTitle = new Label("Set Your Location");
        setLocPane = new HBox(4);
        setLocLabel = new Label("City:");
        cityField = new TextField("Atlanta");
        city = "Atlanta";
        setButton = new Button("Set City");

        recipeRoot = new VBox();
        recipeScene = new Scene(recipeRoot, 800, 600);
        recipeBackPane = new HBox(4);
        recipeBack = new Button("Back");
        recipeFlow = new TextFlow();
        recipeName = "Chicken";
        recipePane = new BorderPane();
        ingrPane = new VBox();
        recipeImage = new ImageView();
        ingrScroll = new ScrollPane(ingrScrollPane);
        ingrScrollPane = new VBox();
        ingrNameList = new String[20];
        ingrList = new Hyperlink[20]; //should be variable?
        instrFlow = new TextFlow();

        ingrRoot = new VBox();
        ingrScene = new Scene(ingrRoot, 800, 600);
        ingrBackPane = new HBox();
        ingrBack = new Button("Back");
        ingrNameFlow = new TextFlow();
        ingrPaneList = new HBox[10];
        ingrImages = new ImageView[10];
        ingrNames = new TextFlow[10];
        ingrPrices = new TextFlow[10];
    } // ApiApp

    /** {@inheritDoc} */
    @Override
    public void init() {
        initStart();
        initSetLoc();
        initRecipe();
    }

    /** {@inheritDoc} */
    @Override
    public void start(Stage stage) {

        this.stage = stage;

        // demonstrate how to load local asset using "file:resources/"
        Image bannerImage = new Image("file:resources/readme-banner.png");
        ImageView banner = new ImageView(bannerImage);
        banner.setPreserveRatio(true);
        banner.setFitWidth(640);

        // some labels to display information
        Label notice = new Label("Modify the starter code to suit your needs.");

        // setup scene
        //startRoot.getChildren().addAll(banner, notice);
        //startScene = new Scene(startRoot);

        // setup stage
        stage.setTitle("ApiApp!");
        stage.setScene(startScene);
        stage.setOnCloseRequest(event -> Platform.exit());
        stage.sizeToScene();
        stage.show();
        Platform.runLater(() -> this.stage.setResizable(false));
    } // start

    /**
     * Initializes the start scene.
     */
    public void initStart() {
        HBox.setHgrow(this.searchField, Priority.ALWAYS);
        progressBar.setMaxWidth(Double.MAX_VALUE);
        progressPane.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(this.progressBar, Priority.ALWAYS);
        searchPane.setAlignment(Pos.CENTER);
        scrollVBox.setMaxWidth(startScene.getWidth());
        for (int i = 0 ; i < bpArray.length; i++) {
            imageArray[i] = new ImageView();
            imageArray[i].setFitHeight(100);
            imageArray[i].setFitWidth(100);
            Image image = new Image("file:resources/default.png");
            imageArray[i].setImage(image);
            descArray[i] = new TextFlow();
            bpArray[i] = new BorderPane();
            recipeButton[i] = new Button("View\nRecipe");
            recipeButton[i].setMinWidth(100);
            EventHandler<ActionEvent> recipeHandler = (ActionEvent e) -> {
                Text t1 = new Text("Recipe for " + recipeName);
                recipeFlow.getChildren().clear();
                recipeFlow.getChildren().add(t1);
                for (int j = 0; j < ingrList.length; j++) {
                    ingrNameList[j] = "Chicken";
                    ingrList[j].setText(ingrNameList[j]);
                }
                recipeImage = new ImageView(image);
                stage.setScene(recipeScene);
            };
            recipeButton[i].setOnAction(recipeHandler);
            bpArray[i].setLeft(imageArray[i]);
            bpArray[i].setCenter(descArray[i]);
            bpArray[i].setRight(recipeButton[i]);
            Separator sep = new Separator();
            sep.setPrefWidth(Double.MAX_VALUE);
            scrollVBox.getChildren().addAll(bpArray[i], sep);
        }

        EventHandler<ActionEvent> setLocHandler = (ActionEvent e) -> {
            stage.setScene(setLocScene);
            cityField.setText(city);
        };
        setLocButton.setOnAction(setLocHandler);

        searchPane.getChildren().addAll(searchLabel, searchField, searchButton);
        progressPane.getChildren().addAll(progressBar, setLocButton);
        startRoot.getChildren().addAll(searchPane, startScroll, progressPane);
    }

    /**
     * Initializes the scene for setting your location.
     */
    public void initSetLoc() {
        setLocRoot.setAlignment(Pos.CENTER);
        setLocBackPane.setAlignment(Pos.CENTER);
        setLocPane.setAlignment(Pos.CENTER);

        EventHandler<ActionEvent> setLocBackHandler = (ActionEvent e) -> {
            stage.setScene(startScene);
        };
        EventHandler<ActionEvent> setButtonHandler = (ActionEvent e) -> {
            city = cityField.getText();
        };
        setLocBack.setOnAction(setLocBackHandler);
        setButton.setOnAction(setButtonHandler);

        Region r1 = new Region();
        VBox.setVgrow(r1, Priority.ALWAYS);
        Region r2 = new Region();
        VBox.setVgrow(r2, Priority.ALWAYS);

        Region r3 = new Region();
        HBox.setHgrow(r3, Priority.ALWAYS);
        Region r4 = new Region();
        HBox.setHgrow(r4, Priority.ALWAYS);

        setLocBackPane.getChildren().addAll(setLocBack, r3, setLocTitle, r4);
        setLocPane.getChildren().addAll(setLocLabel, cityField, setButton);
        setLocRoot.getChildren().addAll(setLocBackPane, r1, setLocPane, r2);
    }

    /**
     * Initializes recipe scene.
     */
    public void initRecipe() {
        EventHandler<ActionEvent> recipeBackHandler = (ActionEvent e) -> {
            stage.setScene(startScene);
        };

        recipeBack.setOnAction(recipeBackHandler);

        recipePane.setPrefHeight(Double.MAX_VALUE);

        Region r1 = new Region();
        HBox.setHgrow(r1, Priority.ALWAYS);
        Region r2 = new Region();
        HBox.setHgrow(r2, Priority.ALWAYS);

        ingrScrollPane.getChildren().add(recipeImage);

        for (int i = 0; i < ingrList.length; i++) {
            ingrNameList[i] = "Chicken";
            ingrList[i] = new Hyperlink();
            EventHandler<ActionEvent> ingrListHandler = (ActionEvent e) -> {
                stage.setScene(ingrScene);
            };
            ingrScrollPane.getChildren().add(ingrList[i]);
        }

        recipeBackPane.getChildren().addAll(recipeBack, r1, recipeFlow, r2);
        recipePane.setCenter(instrFlow);
        recipePane.setLeft(ingrScroll);
        recipeRoot.getChildren().addAll(recipeBackPane, recipePane);
    }

} // ApiApp
