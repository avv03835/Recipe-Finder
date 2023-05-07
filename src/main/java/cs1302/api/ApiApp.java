package cs1302.api;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.URL;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.net.URLEncoder;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.URI;
import java.io.IOException;
import java.io.FileInputStream;
import java.util.Properties;
import java.net.http.HttpRequest.BodyPublishers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.Node;

/**
 * REPLACE WITH NON-SHOUTING DESCRIPTION OF YOUR APP.
 */
public class ApiApp extends Application {

    /** HTTP client. */
    public static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)           // uses HTTP protocol version 2 where possible
        .followRedirects(HttpClient.Redirect.NORMAL)  // always redirects, except from HTTPS to HTTP
        .build();                                     // builds and returns a HttpClient object

    /** Google {@code Gson} object for parsing JSON-formatted strings. */
    public static Gson GSON = new GsonBuilder()
        .setPrettyPrinting()                          // enable nice output when printing
        .create();                                    // builds and returns a Gson object

    private static final String EDAMAM_API = "https://api.edamam.com/api/recipes/v2";

    private static final String CONFIG_PATH = "resources/config.properties";

    /**
     * Represents the search results from Edamam.
     */
    private static class EdamamResponse {
        int count;
        RecipeObj[] hits;
    }

    /**
     * Represents a recipe object from Edamam.
     */
    private static class RecipeObj {
        Recipe recipe;
    }

    /**
     * Represents a recipe from Edamam.
     */
    private static class Recipe {
        String label;
        String image;
        Ingredient[] ingredients;
        String shareAs;
        double calories;
        double totalTime;
    }

    /**
     * Represents an ingredient in a recipe.
     */
    private static class Ingredient {
        String food;
        Double quantity;
        String measure;
    }

    /**
     * Represents the Kroger Access Token.
     */
    private static class KrogerAccess {
        @SerializedName("access_token") String token;
    }

    /**
     * Represents a list of Kroger locations.
     */
    private static class LocationList {
        Location[] data;
    }

    /**
     * Represents a Kroger location.
     */
    private static class Location {
        String locationId;
        String name;
    }

    /**
     * Represents a list of products from Kroger.
     */
    private static class ProductList {
        Product[] data;
    }

    /**
     * Represents a product from Kroger.
     */
    private static class Product {
        String description;
        Items[] items;
        ImageUrl[] images;
    }

    /**
     * Represents a product's images.
     */
    private static class ImageUrl {
        String url;
    }

    /**
     * Represents a product's price.
     */
    private static class Items {
        Price price;
    }

    /**
     * Represents a product's price.
     */
    private static class Price {
        Double regular;
    }

    String token;
    Location krogerLoc;
    String locName;
    String locId;
    String[][] foodArr;
    String[] foodArr2;

    private final String defaultImage = "file:resources/default.png";

    Stage stage;

    Scene startScene;
    VBox startRoot;
    HBox searchPane;
    Label searchLabel;
    TextField searchField;
    Button searchButton;
    ScrollPane startScroll;
    VBox scrollVBox;
    ImageView[] ivArray;
    Image[] imageArray;
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
    TextField zipField;
    String zip;
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
    ScrollPane recipeScroll;
    VBox recipeScrollPane;
    String[][] ingrNameList;
    Hyperlink[] ingrList;
    String[] instrText;
    TextFlow instrFlow;

    Scene ingrScene;
    VBox ingrRoot;
    HBox ingrBackPane;
    Button ingrBack;
    TextFlow ingrFlow;
    ScrollPane ingrScroll;
    VBox ingrScrollPane;
    BorderPane[] ingrPaneList;
    ImageView[] ingrImages;
    TextFlow[] ingrNames;
    TextFlow[] ingrPrices;
    String currIngr;

    /**
     * Constructs an {@code ApiApp} object. This default (i.e., no argument)
     * constructor is executed in Step 2 of the JavaFX Application Life-Cycle.
     */
    public ApiApp() {
        token = null;
        locName = "Kroger Landen";
        locId = "01400376";
        foodArr = new String[20][20];
        foodArr2 = new String[20];
        stage = null;
        startRoot = new VBox();
        startScene = new Scene(startRoot, 800, 600);
        searchPane = new HBox(4);
        searchLabel = new Label("Search:");
        searchField = new TextField("Chicken");
        searchButton = new Button("Search");
        scrollVBox = new VBox();
        startScroll = new ScrollPane(scrollVBox);
        ivArray = new ImageView[20];
        imageArray = new Image[20];
        bpArray = new BorderPane[20];
        descArray = new TextFlow[20];
        recipeButton = new Button[20];
        progressPane = new HBox(4);
        progressBar = new ProgressBar();
        setLocButton = new Button("Set Location");
        setLocRoot = new VBox();
        setLocScene = new Scene(setLocRoot, 800, 600);
        setLocBackPane = new HBox(4);
        setLocBack = new Button("Back");
        setLocTitle = new Label("Set Your Location");
        setLocPane = new HBox(4);
        setLocLabel = new Label("Zip Code:");
        zipField = new TextField("30609");
        zip = "30609";
        setButton = new Button("Set Zip Code");
        recipeRoot = new VBox();
        recipeScene = new Scene(recipeRoot, 800, 600);
        recipeBackPane = new HBox(4);
        recipeBack = new Button("Back");
        recipeFlow = new TextFlow();
        recipeName = "Chicken";
        recipePane = new BorderPane();
        ingrPane = new VBox();
        recipeImage = new ImageView();
        recipeScrollPane = new VBox();
        recipeScroll = new ScrollPane(recipeScrollPane);
        ingrNameList = new String[20][20];
        ingrList = new Hyperlink[20];
        instrText = new String[20];
        instrFlow = new TextFlow();
        ingrRoot = new VBox();
        ingrScene = new Scene(ingrRoot, 800, 600);
        ingrBackPane = new HBox();
        ingrBack = new Button("Back");
        ingrFlow = new TextFlow();
        ingrScrollPane = new VBox();
        ingrScroll = new ScrollPane(ingrScrollPane);
        ingrPaneList = new BorderPane[10];
        ingrImages = new ImageView[10];
        ingrNames = new TextFlow[10];
        ingrPrices = new TextFlow[10];
        currIngr = "Chicken";
    } // ApiApp

    /** {@inheritDoc} */
    @Override
    public void init() {
        retrieveToken();
        initStart();
        initSetLoc();
        initRecipe();
        initIngr();
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
        getNearestKroger();
        locName = krogerLoc.name;
        locId = krogerLoc.locationId;
        HBox.setHgrow(this.searchField, Priority.ALWAYS);
        progressBar.setMaxWidth(Double.MAX_VALUE);
        progressPane.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(this.progressBar, Priority.ALWAYS);
        searchPane.setAlignment(Pos.CENTER);
        scrollVBox.setMaxWidth(startScene.getWidth());
        recipeImage.setFitHeight(200);
        recipeImage.setFitWidth(200);
        for (int i = 0 ; i < bpArray.length; i++) {
            ivArray[i] = new ImageView();
            ivArray[i].setFitHeight(100);
            ivArray[i].setFitWidth(100);
            imageArray[i] = new Image("file:resources/default.png");
            ivArray[i].setImage(imageArray[i]);
            descArray[i] = new TextFlow();
            bpArray[i] = new BorderPane();
            recipeButton[i] = new Button("View\nRecipe");
            recipeButton[i].setOnAction(initRecipeButton(i));
            recipeButton[i].setDisable(true);
            bpArray[i].setLeft(ivArray[i]);
            bpArray[i].setCenter(descArray[i]);
            bpArray[i].setRight(recipeButton[i]);
            Separator sep = new Separator();
            sep.setPrefWidth(Double.MAX_VALUE);
            scrollVBox.getChildren().addAll(bpArray[i], sep);
        }

        EventHandler<ActionEvent> setLocHandler = (ActionEvent e) -> {
            stage.setScene(setLocScene);
            zipField.setText(zip);
        };
        setLocButton.setOnAction(setLocHandler);
        EventHandler<ActionEvent> searchHandler = (ActionEvent e) -> {
            getRecipes();
        };
        searchButton.setOnAction(searchHandler);

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
            zip = zipField.getText();
            getNearestKroger();
            locName = krogerLoc.name;
            locId = krogerLoc.locationId;
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
        setLocPane.getChildren().addAll(setLocLabel, zipField, setButton);
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

        recipePane.setPrefHeight(recipeScene.getHeight());
        instrFlow.setMaxWidth(200);

        VBox.setVgrow(recipeScroll, Priority.ALWAYS);

        Region r1 = new Region();
        HBox.setHgrow(r1, Priority.ALWAYS);
        Region r2 = new Region();
        HBox.setHgrow(r2, Priority.ALWAYS);

        ingrPane.getChildren().add(recipeImage);

        for (int i = 0; i < ingrNameList.length; i++) {
            for (int j = 0; j < ingrNameList[i].length; j++) {
                ingrNameList[i][j] = "";
            }
            ingrList[i] = new Hyperlink();
            ingrList[i].setWrapText(true);

            ingrList[i].setOnAction(initIngrButton(i));
            recipeScrollPane.getChildren().add(ingrList[i]);
        }

        ingrPane.getChildren().add(instrFlow);
        recipeBackPane.getChildren().addAll(recipeBack, r1, recipeFlow, r2);
        recipePane.setCenter(recipeScroll);
        recipePane.setLeft(ingrPane);
        recipeRoot.getChildren().addAll(recipeBackPane, recipePane);
    }

    /**
     * Initializes the ingredients scene.
     */
    public void initIngr() {
        EventHandler<ActionEvent> ingrBackHandler = (ActionEvent e) -> {
            stage.setScene(recipeScene);
        };

        ingrBack.setOnAction(ingrBackHandler);

        Region r1 = new Region();
        HBox.setHgrow(r1, Priority.ALWAYS);
        Region r2 = new Region();
        HBox.setHgrow(r2, Priority.ALWAYS);

        ingrScrollPane.setMaxWidth(ingrScene.getWidth());

        for (int i = 0; i < ingrPaneList.length; i++) {
            ingrPaneList[i] = new BorderPane();
            ingrImages[i] = new ImageView();
            ingrNames[i] = new TextFlow();
            ingrPrices[i] = new TextFlow();
            Image image = new Image(defaultImage);
            ingrImages[i].setImage(image);
            ingrNames[i].getChildren().clear();
            ingrNames[i].getChildren().add(new Text("Chicken"));
            ingrPrices[i].getChildren().clear();
            ingrPrices[i].getChildren().add(new Text("$100.00"));
            ingrPaneList[i].setLeft(ingrImages[i]);
            ingrPaneList[i].setCenter(ingrNames[i]);
            ingrPaneList[i].setRight(ingrPrices[i]);
            Separator sep = new Separator();
            sep.setPrefWidth(Double.MAX_VALUE);
            ingrScrollPane.getChildren().addAll(ingrPaneList[i], sep);
        }

        ingrBackPane.getChildren().addAll(ingrBack, r1, ingrFlow, r2);
        ingrRoot.getChildren().addAll(ingrBackPane, ingrScroll);
    }

    /**
     * Uses data from the Edamam API to fill the recipes.
     */
    private void getRecipes() {
        progressBar.setProgress(0);
        runThread(() -> {
            EdamamResponse edamamResponse = retrieveEdamam();
            RecipeObj[] recipeObjArr = edamamResponse.hits;
            Recipe[] recipeArr = new Recipe[recipeObjArr.length];
            for (int i = 0; i < recipeObjArr.length; i++) {
                recipeArr[i] = recipeObjArr[i].recipe;
            }
            try {
                if (edamamResponse.count < 20) {
                    String exceptionText = edamamResponse.count + " distinct results found, " +
                        "but 20 or more are needed.";
                    throw new IllegalArgumentException(exceptionText);
                } else {
                    for (int i = 0; i < recipeArr.length; i++) {
                        recipeButton[i].setDisable(false);
                        TextFlow temp = descArray[i];
                        String name = recipeArr[i].label;
                        Platform.runLater(() -> {
                            temp.getChildren().clear();
                            temp.getChildren().add(new Text(name));
                        });
                        Image image = new Image(recipeArr[i].image);
                        ivArray[i].setImage(image);
                        Ingredient[] x = recipeArr[i].ingredients;
                        String url = "Recipe URL: " + recipeArr[i].shareAs;
                        String cal = "Calories: " + recipeArr[i].calories;
                        String time = "Time to Cook: " + recipeArr[i].totalTime + "minutes";
                        instrText[i] = String.format("%s\n\n%s\n\n%s", url, cal, time);
                        for (int j = 0; j < 20; j++) {
                            if (j < recipeArr[i].ingredients.length) {
                                Double q = x[j].quantity;
                                String m = " " + x[j].measure + " of ";
                                String food = x[j].food;
                                foodArr[i][j] = food;
                                String str = "";
                                if (m.equals(" <unit> of ")) {
                                    str = String.format("%s %s.", q, food);
                                } else if (x[j].quantity == 0) {
                                    str = food;
                                } else {
                                    str = String.format("%s%s%s.", q, m, food);
                                }
                                ingrNameList[i][j] = str;
                            } else {
                                ingrNameList[i][j] = "";
                            }
                        }
                        Platform.runLater(() -> progressBar.setProgress(progressBar.getProgress() +
                            (1.0 / edamamResponse.hits.length)));
                    }
                }
            } catch (IllegalArgumentException ex) {
                Platform.runLater(() -> {
                    progressBar.setProgress(1);
                    alertError(ex);
                });
            }
        });
    }

    /**
     * Retrieves data of the search from Edamam.
     *
     * @return the data from Edamam in a GSON format
     */
    private EdamamResponse retrieveEdamam() {
        EdamamResponse edamamResponse = null;
        try (FileInputStream configFileStream = new FileInputStream(CONFIG_PATH)) {
            Properties config = new Properties();
            config.load(configFileStream);
            // form URI
            String type = URLEncoder.encode("public", StandardCharsets.UTF_8);
            String search = URLEncoder.encode(searchField.getText(),
                StandardCharsets.UTF_8);
            String appId = URLEncoder.encode(config.getProperty("project.edamamId"),
                StandardCharsets.UTF_8);
            String appKey = URLEncoder.encode(config.getProperty("project.edamamKey"),
                StandardCharsets.UTF_8);
            String random = URLEncoder.encode("true", StandardCharsets.UTF_8);
            String query = String.format("?type=%s&q=%s&app_id=%s", type, search, appId);
            String uri = EDAMAM_API + query;
            query = String.format("&app_key=%s&random=%s", appKey, random);
            uri += query;
            // build request
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .build();
            HttpResponse<String> response = HTTP_CLIENT
                .send(request, BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new IOException(response.toString());
            } // if
            String jsonString = response.body();
            edamamResponse = GSON
                .fromJson(jsonString, EdamamResponse.class);
        } catch (IOException | InterruptedException e) {
            alertError(e);
        } // try
        return edamamResponse;
    }

    /**
     * Uses data from Kroger to fill the Ingredient scene.
     */
    private void updateIngr() {
        Product[] products = retrieveKroger();
        //runThread(() -> {
        for (int i = 0; i < ingrImages.length; i++) {
            if (i < products.length) {
                if (products[i].images[0].url != null) {
                    ingrImages[i].setImage(new Image(products[i].images[0].url));
                }
                ingrNames[i].getChildren().clear();
                ingrNames[i].getChildren().add(new Text(products[i].description));
                ingrPrices[i].getChildren().clear();
                if (products[i].items[0].price != null) {
                    String price = Double.valueOf(products[i].items[0].price.regular).toString();
                    ingrPrices[i].getChildren().add(new Text(price));
                }
            } else {
                ingrImages[i].setImage(new Image(defaultImage));
                ingrNames[i].getChildren().clear();
                ingrPrices[i].getChildren().clear();
            }
        }
            //});
    }

    /**
     * Retrieves data of the search from Kroger.
     *
     * @return the data from Kroger in a GSON format
     */
    private Product[] retrieveKroger() {
        ProductList products = null;
        try {
            // form URI
            String term = URLEncoder.encode(currIngr, StandardCharsets.UTF_8);//ChangeJSKLAJSKLA
            String loc = URLEncoder.encode(locId, StandardCharsets.UTF_8);
            String query = String.format("?filter.term=%s&filter.locationId=%s&filter.limit=%s",
                term, loc, "10");
            URI apiUri = URI.create("https://api.kroger.com/v1/products" + query);
            // build request
            HttpRequest request = HttpRequest.newBuilder()
                .uri(apiUri)
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .build();
            HttpResponse<String> response = HTTP_CLIENT
                .send(request, BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new IOException(response.toString());
            } // if
            String jsonString = response.body();
            //System.out.println(jsonString);
            products = GSON
                .fromJson(jsonString, ProductList.class);
        } catch (IOException | InterruptedException e) {
            alertError(e);
        } // try
        return products.data;
    }

    /**
     * Sets the nearest Kroger to the current Kroger.
     */
    private void getNearestKroger() {
        try {
            String nearZip = URLEncoder.encode(zip, StandardCharsets.UTF_8);
            String query = String.format("?filter.zipCode.near=%s&filter.limit=%s", nearZip, "1");
            URI apiUri = URI.create("https://api.kroger.com/v1/locations" + query);
            HttpRequest request = HttpRequest.newBuilder()
                .uri(apiUri)
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .build();
            HttpResponse<String> response = HTTP_CLIENT
                .send(request, BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new IOException(response.toString());
            } // if
            String jsonString = response.body();
            LocationList locList = GSON
                .fromJson(jsonString, LocationList.class);
            if (locList.data.length == 0) {
                throw new IllegalArgumentException("No Krogers found nearby.");
            }
            krogerLoc = locList.data[0];
        } catch (IOException | InterruptedException | IllegalArgumentException e) {
            alertError(e);
        } // try
    }

    /**
     * Retrieves the access token for the Kroger api.
     */
    private void retrieveToken() {
        if (token == null) {
            try (FileInputStream configFileStream = new FileInputStream(CONFIG_PATH)) {
                Properties config = new Properties();
                config.load(configFileStream);
                String apiUri = "https://api.kroger.com/v1/connect/oauth2/token";
                String requestBody = "grant_type=client_credentials&scope=product.compact";
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUri))
                    .POST(BodyPublishers.ofString(requestBody))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Authorization", "Basic " + config.getProperty("project.krogerKey"))
                    .build();
                HttpResponse<String> response = HTTP_CLIENT
                    .send(request, BodyHandlers.ofString());
                if (response.statusCode() != 200) {
                    System.out.println(response.body());
                    throw new IOException(response.toString());
                } // if
                String jsonString = response.body();
                token = GSON
                    .fromJson(jsonString, KrogerAccess.class).token;
            } catch (IOException | InterruptedException e) {
                alertError(e);
            } // try
        }
    }

    /**
     * Returns the event handler for the view recipe buttons.
     *
     * @param i the button to initialize
     * @return an event handler
     */
    private EventHandler<ActionEvent> initRecipeButton(int i) {
        EventHandler<ActionEvent> recipeHandler = (ActionEvent e) -> {
            for (Node node : descArray[i].getChildren()) {
                recipeName = ((Text)node).getText();
            }
            Text t1 = new Text("Recipe for " + recipeName);
            recipeFlow.getChildren().clear();
            recipeFlow.getChildren().add(t1);
            for (int j = 0; j < ingrList.length; j++) {
                ingrList[j].setText(ingrNameList[i][j]);
                foodArr2[j] = foodArr[i][j];
                ingrList[j].setDisable(false);
                if (ingrList[j].getText().equals("")) {
                    ingrList[j].setDisable(true);
                }
            }
            recipeImage.setImage(imageArray[i]);
            instrFlow.getChildren().clear();
            instrFlow.getChildren().add(new Text(instrText[i]));
            stage.setScene(recipeScene);
        };
        return recipeHandler;
    }

    /**
     * Returns the event handler for the ingredient hyperlinks.
     *
     * @param i the button to initialize
     * @return an event handler
     */
    private EventHandler<ActionEvent> initIngrButton(int i) {
        EventHandler<ActionEvent> ingrListHandler = (ActionEvent e) -> {
            ingrFlow.getChildren().clear();
            currIngr = foodArr2[i].substring(0, 1).toUpperCase() + foodArr2[i].substring(1);
            ingrFlow.getChildren().add(new Text(currIngr + " at " + locName));
            updateIngr();
            stage.setScene(ingrScene);
        };
        return ingrListHandler;
    }

    /**
     * Show a modal alert based on {@code cause}.
     *
     * @param cause a {@link java.lang.throwable Throwable} that caused the alert
     */
    private void alertError(Throwable cause) {
        String errorText = "\n\nException: " + cause.toString();
        TextArea text = new TextArea(errorText);
        text.setEditable(false);
        Alert alert = new Alert(AlertType.ERROR);
        alert.getDialogPane().setContent(text);
        alert.setResizable(true);
        alert.showAndWait();
    }

    /**
     * Runs a runnable command on a separate thread.
     *
     * @param target the command to run
     */
    private void runThread(Runnable target) {
        Thread t = new Thread(target);
        t.setDaemon(true);
        t.start();
    }

} // ApiApp
