package def;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {
	private Label userLabel, passLabel;
	
	private Button enterButton, logoutButton, bmiButton, dressButton, addDressButton;
	
	private TextField userText;
	
	private PasswordField passText;
	
	private Scene scene1, scene2;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	
	public void start(Stage primaryStage) {
		// Create Controls
		userLabel = new Label("User Name");
		passLabel = new Label("Password");
		
		userText = new TextField();
		passText = new PasswordField();
		
		enterButton = new Button("Enter");
		
		// Set events to Control
		enterButton.setOnAction(event -> {
			primaryStage.setScene(scene2);
		});
		
		// Create Layout Containers
		HBox hBox1 = new HBox(5, userLabel, userText);
		hBox1.setAlignment(Pos.CENTER);
		HBox hBox2 = new HBox(5, passLabel, passText);
		hBox2.setAlignment(Pos.CENTER);
		
		VBox vBox1 = new VBox(5, hBox1, hBox2, enterButton);
		vBox1.setAlignment(Pos.CENTER);
		
		// Creates scene and set mySyles1.css
		scene1 = new Scene(vBox1, 300, 300);
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
			primaryStage.setScene(scene1);
		});
		
		// Create Layout Containers
		HBox hBox3 = new HBox(15, bmiButton, dressButton, addDressButton, logoutButton);
		hBox3.setAlignment(Pos.CENTER);
		HBox hBox4 = new HBox();
		hBox4.setAlignment(Pos.CENTER);
		
		VBox vBox2 = new VBox(300, hBox3, hBox4);
		vBox2.setAlignment(Pos.CENTER);
		
		// Creates scene and set myStyles2.css
		scene2 = new Scene(vBox2, 700, 500);	
		scene2.getStylesheets().add("myStyles2.css");
		
		// Create Controls
		// Set events to Controls
		// Create Layout Containers
		// Creates scene and set myStyles2.css
	}
}
