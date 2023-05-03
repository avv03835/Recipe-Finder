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
    HBox[] dishArray;
    ImageView[] imageArray;
    TextFlow[] descArray;
    Button recipeButton;
    HBox progressPane;
    ProgressBar progressBar;
    Button setLocButton;

    Scene setLocScene;
    VBox setLocRoot;
    HBox setLocBackPane;
    Button setLocBack;
    HBox setLocPane;
    Label setLocLabel;
    TextField cityField;
    Button setButton;

    Scene recipeScene;
    VBox recipeRoot;
    HBox recipeBackPane;
    Button recipeBack;
    TextFlow recipeFlow;
    HBox recipePane;
    VBox ingrPane;
    ImageView recipeImage;
    ScrollPane ingrScroll;
    VBox ingrScrollPane;
    Hyperlink[] ingrList;
    TextFlow instrFlow;

    Scene ingrScene;
    VBox ingrBackPane;
    Button ingrBack;
    TextFlow ingrFlow;
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

        startScene = null;
        startRoot = new VBox();
        searchPane = new HBox(4);
        searchLabel = new Label("Search:");
        searchField = new TextField("Chicken");
        searchButton = new Button("Search");
        scrollVBox = new VBox();
        startScroll = new ScrollPane(scrollVBox);
        dishArray = new HBox[10];
        imageArray = new ImageView[10];
        descArray = new TextFlow[10];
        recipeButton = new Button("Look");
        progressPane = new HBox(4);
        progressBar = new ProgressBar();
        setLocButton = new Button("Set Location");

        setLocScene = null;
        setLocRoot = new VBox();
        setLocBackPane = new HBox(4);
        setLocBack = new Button("Back");
        setLocPane = new HBox(4);
        setLocLabel = new Label("City:");
        cityField = new TextField("Atlanta");
        setLocButton = new Button("Set City");

        recipeScene = null;
        recipeRoot = new VBox();
        recipeBackPane = new HBox(4);
        recipeBack = new Button("Back");
        recipeFlow = new TextFlow();
        recipePane = new HBox(4);
        ingrPane = new VBox();
        recipeImage = new ImageView();
        ingrScroll = new ScrollPane(ingrScrollPane);
        ingrScrollPane = new VBox();
        ingrList = new Hyperlink[20]; //should be variable?
        instrFlow = new TextFlow();

        ingrScene = null;
        ingrBackPane = new VBox();
        ingrBack = new Button("Back");
        ingrFlow = new TextFlow();
        ingrPaneList = new HBox[10];
        ingrImages = new ImageView[10];
        ingrNames = new TextFlow[10];
        ingrPrices = new TextFlow[10];
    } // ApiApp

    /** {@inheritDoc} */
    @Override
    public void init() {

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
        startRoot.getChildren().addAll(banner, notice);
        startScene = new Scene(startRoot);

        // setup stage
        stage.setTitle("ApiApp!");
        stage.setScene(startScene);
        stage.setOnCloseRequest(event -> Platform.exit());
        stage.sizeToScene();
        stage.show();

    } // start

} // ApiApp
