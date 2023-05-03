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
        root = new VBox();
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
        root.getChildren().addAll(banner, notice);
        scene = new Scene(root);

        // setup stage
        stage.setTitle("ApiApp!");
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> Platform.exit());
        stage.sizeToScene();
        stage.show();

    } // start

} // ApiApp
