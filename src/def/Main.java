package def;


import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {
    private Menu fileMenu;
    private MenuItem newItem;
    private MenuItem exitItem;

    private VBox vBox1, vBox2;

    private Label userLabel, passLabel, heightLabel, shoulderLabel, hipLabel;
    
    private Slider heightSlider, shoulderSlider, hipSlider;

    private Button enterButton, logoutButton, bmiButton, dressButton, addDressButton, profileButton;

    private TextField userText;

    private PasswordField passText;

    private Scene scene1, scene2, scene3;

    private BorderPane borderpane;

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        launch(args);
    }

    public void start(Stage primaryStage) throws FileNotFoundException {   	
        // Create Controls
    	FileInputStream inputStream = new FileInputStream("src/figure_silhouette_png.png");
    	Image image = new Image(inputStream);
    	ImageView imageView = new ImageView(image);
    	imageView.setFitHeight(200);
    	imageView.setPreserveRatio(true);
    	
    	profileButton = new Button("Generate Profile");
    	
    	heightLabel = new Label("Height: ");
    	shoulderLabel = new Label("Shoulder: ");
    	hipLabel = new Label("Hip: ");
    	
    	shoulderSlider = new Slider(0, 100, 50);
    	shoulderSlider.setShowTickLabels(true);
    	shoulderSlider.setShowTickMarks(true);
    	shoulderSlider.setMajorTickUnit(25.0f);
    	shoulderSlider.setBlockIncrement(1.0f);
    	
    	hipSlider = new Slider(0, 100, 50);
    	hipSlider.setShowTickLabels(true);
    	hipSlider.setShowTickMarks(true);
    	hipSlider.setMajorTickUnit(25.0f);
    	hipSlider.setBlockIncrement(1.0f);
    	
    	heightSlider = new Slider(0, 100, 50);
    	heightSlider.setShowTickLabels(true);
    	heightSlider.setShowTickMarks(true);
    	heightSlider.setMajorTickUnit(25.0f);
    	heightSlider.setBlockIncrement(1.0f);

        userLabel = new Label("User Name");
        passLabel = new Label("Password");

        userText = new TextField();
        passText = new PasswordField();

        userLabel.setTooltip(new Tooltip("Username should be at least 7 characters long"));
        passLabel.setTooltip(new Tooltip("Password should be at least 7 characters long"));
        userText.setTooltip(new Tooltip("Username should be at least 7 characters long"));
        passText.setTooltip(new Tooltip("Password should be at least 7 characters long"));

        enterButton = new Button("Enter");
        enterButton.setDisable(true);
        
        // Set events to Control
        enterButton.setOnAction(event -> {
            MenuBar menuBar = new MenuBar();
            menuBar.getMenus().add(fileMenu);
            
            borderpane.setCenter(vBox2);
            borderpane.setTop(menuBar);

        });
        enterButton.setStyle("-fx-background-color: linear-gradient(#86c1b9, #7cafc2); -fx-text-fill: #FFFFFF");

        // Create Layout Containers
        buildFileMenu(primaryStage);

        borderpane = new BorderPane();
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu);

        GridPane gridpane = new GridPane();
        gridpane.addRow(0,userLabel,userText);
        gridpane.addRow(1, passLabel, passText);
        gridpane.setHgap(15);
        gridpane.setVgap(15);
        vBox1 = new VBox(10, gridpane, enterButton);
        vBox1.setAlignment(Pos.CENTER);

        gridpane.setAlignment(Pos.CENTER);
        // Creates scene and set mySyles1.css
        borderpane.setTop(menuBar);
        borderpane.setCenter(vBox1);

        scene1 = new Scene(borderpane, 550, 400);
        scene1.getStylesheets().add("myStyles1.css");



        // Sets scene onto primaryStage
        primaryStage.setScene(scene1);

        // Show the stage
        primaryStage.show();



        // Create Controls
        logoutButton = new Button ("Logout");
        bmiButton = new Button("Input BMI");
        dressButton = new Button("Choose Dress");
        addDressButton = new Button("Add Dress");

        // Set events to Controls
        logoutButton.setOnAction(event -> {
            userText.setText("");
            passText.setText("");
            borderpane.setCenter(vBox1);
            enterButton.setDisable(true);
        });

        bmiButton.setOnAction(event -> {
        	GridPane gridPane1 = new GridPane();
            
            gridPane1.addRow(0, heightLabel, heightSlider);
            gridPane1.addRow(1, shoulderLabel, shoulderSlider);
            gridPane1.addRow(2,  hipLabel, hipSlider);
            gridPane1.setHgap(15);
            gridPane1.setVgap(15);
            
            VBox vBox10 = new VBox(15, gridPane1, profileButton);
            vBox10.setAlignment(Pos.CENTER);
            
            gridPane1.setAlignment(Pos.CENTER);
            
            borderpane.setCenter(vBox10);
            borderpane.setTop(menuBar);

        });
        
        profileButton.setOnAction(event -> {
        	vBox2.getChildren().add(imageView);
        	borderpane.setCenter(vBox2);
        	borderpane.setTop(menuBar);
        });
        
        heightSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				if (newValue == null) {
					heightLabel.setText("");
					return;
				}
				heightLabel.setText("Height: " + Math.round(newValue.doubleValue()));
			}
        	
        });
        
        shoulderSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				if (newValue == null) {
					shoulderLabel.setText("");
					return;
				}
				shoulderLabel.setText("Shoulder: " + Math.round(newValue.doubleValue()));
			}
        	
        });
        
        hipSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				if (newValue == null) {
					hipLabel.setText("");
					return;
				}
				hipLabel.setText("Hip: " + Math.round(newValue.doubleValue()));
			}
        	
        });

        // Create Layout Containers
        HBox hBox3 = new HBox(15, bmiButton, dressButton, addDressButton, logoutButton);
        hBox3.setAlignment(Pos.CENTER);
        //HBox hBox4 = new HBox();
        //hBox4.setAlignment(Pos.CENTER);

        vBox2 = new VBox(150, hBox3);
        vBox2.setAlignment(Pos.CENTER);

        setUpValidation(userText);
        setUpValidation(passText);
        // Create Controls
        // Set events to Controls
        // Create Layout Containers
        // Creates scene and set myStyles2.css
    }
    private void setUpValidation(final TextField tf) {
        tf.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length() < 7){
                tf.setStyle("-fx-text-box-border: red; -fx-focus-color: red");
            }
            else{
                tf.setStyle("-fx-text-box-border: blue; -fx-focus-color: blue");
            }
            if(passText.getText().length() >= 7 && userText.getText().length() >=7){
                enterButton.setDisable(false);

            }

        });
    }
    private void buildFileMenu(Stage primaryStage)
    {
        fileMenu = new Menu("File");
        newItem = new MenuItem("New");
        exitItem = new MenuItem("Exit");
        newItem.setAccelerator(KeyCombination.keyCombination("shortcut+N"));
        exitItem.setAccelerator(KeyCombination.keyCombination("shortcut+Q"));
        exitItem.setOnAction(event -> primaryStage.close());
        newItem.setOnAction(event -> {
            userText.setText("");
            passText.setText("");
            borderpane.setCenter(vBox1);
        });
        fileMenu.getItems().addAll(newItem, exitItem);
    }
}
