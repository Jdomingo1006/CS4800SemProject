package def;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import com.mongodb.MongoClient;
import com.mongodb.diagnostics.logging.Logger;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;

public class Main extends Application {
    private Menu fileMenu;
    private MenuItem newItem;
    private MenuItem exitItem;

    private VBox vBox1, vBox2;
    
    private HBox hBox4, hBox5, hBox8;

    private Label userLabel, passLabel, newUser, newPass, heightLabel, shoulderLabel, hipLabel, errorLabel;
    
    private Slider heightSlider, shoulderSlider, hipSlider;

    private Button display, choose, enterButton, enter1Button, cancelButton, newAccount, logoutButton, bmiButton, dressButton, addDressButton, profileButton;

    private TextField userText, newUserText;

    private PasswordField passText, newPassText;

    private Scene scene1, scene2, scene3;

    private BorderPane borderpane;
    
    private ChoiceBox<String> cb, cb1;
    
    private MongoClient mongoClient;
    
    private DB database;
    
    private DBCollection collection;
    
    private Account account;
    
    private FileInputStream inputStream;
    
    private List<String> dressList;
    
    private String imagePath;
    
    private Image image, image1, newImage;
    
    private ImageView imageView, imageView1;
    
    private static final int TOLERANCE_THRESHOLD = 0xCF;

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        launch(args);
    }

    public void start(Stage primaryStage) throws FileNotFoundException { 
    	// Sets up Database
    	java.util.logging.Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);
    	
    	mongoClient = new MongoClient("localhost", 27017);
    	
    	database = mongoClient.getDB("accountdatabase");
    	
    	collection = database.getCollection("accounts");

        // Create Controls
    	inputStream = new FileInputStream("src/figure_silhouette_png.png");
    	image = new Image(inputStream);
    	imageView = new ImageView(image);
    	imageView.setFitHeight(200);
    	imageView.setFitWidth(54);
    	
    	profileButton = new Button("Generate Profile");
    	
    	logoutButton = new Button ("Logout");
        bmiButton = new Button("Input BMI");
        dressButton = new Button("Choose Dress");
        addDressButton = new Button("Add Dress");
        addDressButton.setDisable(false);
    	
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

      /*  userLabel.setTooltip(new Tooltip("Username should be at least 7 characters long"));
        passLabel.setTooltip(new Tooltip("Password should be at least 7 characters long"));
        userText.setTooltip(new Tooltip("Username should be at least 7 characters long"));
        passText.setTooltip(new Tooltip("Password should be at least 7 characters long"));
	*/
        newUser = new Label("New User Name");
        newPass = new Label("New Password");

        newUserText = new TextField();
        newPassText = new PasswordField();

        newUser.setTooltip(new Tooltip("Username should be at least 7 characters long"));
        newPass.setTooltip(new Tooltip("Password should be at least 7 characters long"));
        newUserText.setTooltip(new Tooltip("Username should be at least 7 characters long"));
        newPassText.setTooltip(new Tooltip("Password should be at least 7 characters long"));
        
        newAccount = new Button("New Account");
        errorLabel = new Label("");
        cancelButton = new Button("Cancel");
        enter1Button = new Button("Enter");
        enter1Button.setDisable(true);
        
        enterButton = new Button("Enter");
        enterButton.setDisable(true);
        
        choose = new Button("Choose");
        display = new Button("Display");
        
        cb = new ChoiceBox<>();
        cb.getItems().addAll("User", "Admin" );
        cb.getSelectionModel().selectFirst();
        cb.setDisable(false);
        
        cb1 = new ChoiceBox<>();
        
        dressList = new ArrayList<String>();
        
        // Set events to Control
        dressButton.setOnAction(event -> {
        	DBObject query = new BasicDBObject("username", userText.getText());
        	DBCursor cursor = collection.find(query);
        	DBObject user = cursor.one();
        	
        	BasicDBList dresses = (BasicDBList) user.get("dresses");
        	//List<String> dressList = new ArrayList<String>();
        	
        	for (Object dress : dresses) {
        		dressList.add((String) dress);
        	}
        	
        	for (int i = 0; i < dressList.size(); i++) {
        		cb1.getItems().add(dressList.get(i));
        	}       	
        	  	
        	HBox hBox7 = new HBox(15, display, choose);
        	hBox7.setAlignment(Pos.CENTER);
        	
        	hBox8 = new HBox();
        	hBox8.setAlignment(Pos.CENTER);
        	
        	HBox hBox9 = new HBox(cb1);
        	hBox9.setAlignment(Pos.CENTER);
        	
        	VBox vBox4 = new VBox(10, hBox9, hBox8, hBox7);
        	vBox4.setAlignment(Pos.CENTER);
        	
        	borderpane.setCenter(vBox4);     	
        });
        
        choose.setOnAction(event -> {
        	//imageView1.setFitHeight(175);
        	//imageView1.setFitWidth(53.5);
        	
        	
        	int width = (int) image.getWidth();
        	int height = (int) image.getHeight();
        	
        	Image newNewImage = new Image(new File(cb1.getSelectionModel().getSelectedItem()).toURI().toString(), width, height - 100, false, false);
        	
        	int width1 = (int) newNewImage.getWidth();
        	int height1 = (int) newNewImage.getHeight();
        	
        	WritableImage wImage = new WritableImage(width, height);
        	PixelReader pixelReader = image.getPixelReader();
        	PixelWriter writer = wImage.getPixelWriter();
        	
        	PixelReader pixelReader1 = newNewImage.getPixelReader();
        	
        	for(int y = 0; y < height; y++) { 
                for(int x = 0; x < width; x++) { 
                   //Retrieving the color of the pixel of the loaded image   
                   Color color = pixelReader.getColor(x, y); 
                     
                   //Setting the color to the writable image 
                   writer.setColor(x, y, color.darker());              
                }
            }
        	
        	for(int y = 0; y < height1; y++) { 
                for(int x = 0; x < width1; x++) { 
                   //Retrieving the color of the pixel of the loaded image   
                   Color color = pixelReader1.getColor(x, y); 
                     
                   //Setting the color to the writable image 
                   writer.setColor(x, y, color.darker());              
                }
            }
        	
        	ImageView newImageView = new ImageView(wImage);
        	newImageView.setFitHeight(200);
        	newImageView.setFitWidth(53.5);
        	
        	//imageView1.setBlendMode(BlendMode.DIFFERENCE);
        	
        	//Group blend = new Group(imageView, imageView1);
        	
        	hBox4.getChildren().clear();
        	
        	hBox4.getChildren().addAll(newImageView);
        	
        	borderpane.setCenter(vBox2);
        });
        
        display.setOnAction(event -> {
        	try {
        		File imageFile = new File(cb1.getSelectionModel().getSelectedItem());
				inputStream = new FileInputStream(imageFile);
				
            } catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
            }
        	
            image1 = new Image(inputStream);
            newImage = makeTransparent(image1);
            imageView1 = new ImageView(newImage);
            //imageView.setFitHeight(200);
            //imageView.setFitWidth(53.5);
            
            hBox8.getChildren().clear();
            hBox8.getChildren().add(imageView1);
        });
        
        /*cb1.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() { 
        	  
            // if the item of the list is changed 
            public void changed(ObservableValue ov, Number value, Number new_value) 
            { 
            	// !null.equals(dressList.get(new_value.intValue())
                // set the text for the label to the selected item 
                if (!null.equals(dressList.get(new_value.intValue()))) {
                	try {
        				inputStream = new FileInputStream(cb1.getSelectionModel().getSelectedItem());
        				
                    } catch (FileNotFoundException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
                    }
                	
                    Image image1 = new Image(inputStream);
                    ImageView imageView1 = new ImageView(image);
                    imageView.setFitHeight(200);
                    imageView.setFitWidth(53.5);
                    	
                    gridPane3.addRow(1, imageView1);
                }
            } 
        }); */
        
        addDressButton.setOnAction(event -> {
        	FileChooser fileChooser = new FileChooser();
        	FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
        	fileChooser.getExtensionFilters().addAll(imageFilter);
        	File selectedFile = fileChooser.showOpenDialog(primaryStage);
        	
        	File imageFile = selectedFile;
        	GridFS gfsImage = new GridFS(database, "dresses");
        	
        	try {
				GridFSInputFile gfsFile = gfsImage.createFile(imageFile);
				gfsFile.setFilename(imageFile.toURI().toString());	
				
				imagePath = imageFile.getPath();
				
				DBObject findQuery = new BasicDBObject("username", userText.getText());
				//DBObject listItem = new BasicDBObject("dresses", imageFile.toURI().toURL().toString());
				DBObject listItem = new BasicDBObject("dresses", imagePath);
				DBObject updateQuery = new BasicDBObject("$push", listItem);
				
				collection.update(findQuery, updateQuery);

				gfsFile.save();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
        
        newAccount.setOnAction(event -> {
        	GridPane gridPane2 = new GridPane();
            gridPane2.addRow(0, newUser, newUserText);
            gridPane2.addRow(1, newPass, newPassText);
            gridPane2.setHgap(15);
            gridPane2.setVgap(15);
            gridPane2.setAlignment(Pos.CENTER);
            
        	HBox hBox6 = new HBox(15, enter1Button, cancelButton);
        	hBox6.setAlignment(Pos.CENTER);
        	
        	VBox vBox3 = new VBox(10, gridPane2, errorLabel, hBox6);
        	vBox3.setAlignment(Pos.CENTER);
        	
        	borderpane.setCenter(vBox3);
        });
        
        cancelButton.setOnAction(event -> {
        	borderpane.setCenter(vBox1);
        });
        
        enter1Button.setOnAction(event -> {   
        	DBObject query = new BasicDBObject("username", userText.getText());
        	DBCursor cursor = collection.find(query);
        	
        	if (cursor.itcount() == 0) 
        		errorLabel.setText("Username taken");
        	else {      	
        		account = new Account(newUserText.getText(), newPassText.getText());
        		collection.insert(AccountConversion.toDBObject(account));
        		borderpane.setCenter(vBox1);
                enter1Button.setDisable(true);
        	}
        });
        enter1Button.setStyle("-fx-background-color: linear-gradient(#86c1b9, #7cafc2); -fx-text-fill: #FFFFFF");
        
        enterButton.setOnAction(event -> {
        	DBObject query = new BasicDBObject("username", userText.getText());
        	DBCursor cursor = collection.find(query);
        	DBObject user = cursor.one();
        	
        	if (cursor.itcount() == 1) {
        		if (user.get("password").equals(passText.getText())) {
        			errorLabel.setText("");
        			
        			if(cb.getSelectionModel().getSelectedItem().equalsIgnoreCase("User")){
                        addDressButton.setDisable(true);
        			}
        		
        			borderpane.setCenter(vBox2);
        			enterButton.setDisable(true);
        			cb.setDisable(true);
        		}
        		else
        			errorLabel.setText("Password does not match Username");
        	}
        	else if (cursor.itcount() == 0) 
        		errorLabel.setText("Username does not exist");
        });
        enterButton.setStyle("-fx-background-color: linear-gradient(#86c1b9, #7cafc2); -fx-text-fill: #FFFFFF");

        // Create Layout Containers
        buildFileMenu(primaryStage);
        
        Menu cbMenu = new Menu();
        cbMenu.setGraphic(cb);
 
        borderpane = new BorderPane();
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, cbMenu);

        GridPane gridpane = new GridPane();
        gridpane.addRow(0,userLabel,userText);
        gridpane.addRow(1, passLabel, passText);
        gridpane.setHgap(15);
        gridpane.setVgap(15);
        
        hBox5 = new HBox(15, enterButton, newAccount);
        hBox5.setAlignment(Pos.CENTER);
        
        vBox1 = new VBox(10, gridpane, errorLabel, hBox5);
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

        // Set events to Controls
        logoutButton.setOnAction(event -> {
            userText.setText("");
            passText.setText("");
            addDressButton.setDisable(false);
            cb.setDisable(false);
            borderpane.setCenter(vBox1);
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
        	borderpane.setCenter(vBox2);
        	borderpane.setTop(menuBar);
        });
        
        sliderFunction(heightSlider, imageView);
        sliderFunction(shoulderSlider, imageView);
        sliderFunction(hipSlider, imageView);

        // Create Layout Containers
        HBox hBox3 = new HBox(15, bmiButton, dressButton, addDressButton, logoutButton);
        hBox3.setAlignment(Pos.CENTER);
        hBox4 = new HBox(imageView);
        hBox4.setAlignment(Pos.CENTER);

        vBox2 = new VBox(50, hBox3, hBox4);
        vBox2.setAlignment(Pos.CENTER);

        //setUpValidation(userText, passText, enterButton);
        //setUpValidation(newUserText, newPassText, enter1Button);
        
        // Create Controls
        // Set events to Controls
        // Create Layout Containers
        // Creates scene and set myStyles2.css
    }
    private void setUpValidation(final TextField tf, final TextField tf1, final Button button) {
        tf.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length() < 7){
                tf.setStyle("-fx-text-box-border: red; -fx-focus-color: red");
            }
            else{
                tf.setStyle("-fx-text-box-border: blue; -fx-focus-color: blue");
            }
            if(tf.getText().length() >= 7 && tf1.getText().length() >=7){
                button.setDisable(false);
            }
        });
        
        tf1.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length() < 7){
                tf1.setStyle("-fx-text-box-border: red; -fx-focus-color: red");
            }
            else{
                tf1.setStyle("-fx-text-box-border: blue; -fx-focus-color: blue");
            }
            if(tf.getText().length() >= 7 && tf1.getText().length() >=7){
                button.setDisable(false);
            }
        });
    }
    
    private void sliderFunction(final Slider slider, final ImageView imageView) {
    	slider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				if (newValue == null) {
					if (slider == hipSlider) {
						hipLabel.setText("");
						return;
					}
					else if (slider == shoulderSlider) {
						shoulderLabel.setText("");
						return;
					}
					else if (slider == heightSlider) {
						heightLabel.setText("");
						return;
					}
				}
				
				int oldVal = (int) Math.round(oldValue.doubleValue());
				int newVal = (int) Math.round(newValue.doubleValue());
				
				double heightFactor = imageView.getFitHeight();
				double factor = imageView.getFitWidth();
				
				if (slider == hipSlider) {
					hipLabel.setText("Hip: " + newVal);
				}
				else if (slider == shoulderSlider) {
					shoulderLabel.setText("Shoulder: " + newVal);
				}
				else if (slider == heightSlider) {
					heightLabel.setText("Height: " + newVal);
					
					if (newVal > oldVal) {
						heightFactor += (newVal - oldVal);
						imageView.setFitHeight(heightFactor);
					}
					else if (newVal < oldVal) {
						heightFactor -= (oldVal - newVal);
						imageView.setFitHeight(heightFactor);
					}
				}
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
        exitItem.setOnAction(event -> {
        	mongoClient.close();
        	primaryStage.close();
        });
        newItem.setOnAction(event -> {
            userText.setText("");
            passText.setText("");
            borderpane.setCenter(vBox1);
        });
        fileMenu.getItems().addAll(newItem, exitItem);
    }
    
    private Image makeTransparent(Image inputImage) {
        int W = (int) inputImage.getWidth();
        int H = (int) inputImage.getHeight();
        WritableImage outputImage = new WritableImage(W, H);
        PixelReader reader = inputImage.getPixelReader();
        PixelWriter writer = outputImage.getPixelWriter();
        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {
                int argb = reader.getArgb(x, y);

                int r = (argb >> 16) & 0xFF;
                int g = (argb >> 8) & 0xFF;
                int b = argb & 0xFF;

                if (r >= TOLERANCE_THRESHOLD 
                        && g >= TOLERANCE_THRESHOLD 
                        && b >= TOLERANCE_THRESHOLD) {
                    argb &= 0x00FFFFFF;
                }

                writer.setArgb(x, y, argb);
            }
        }

        return outputImage;
    }
}
