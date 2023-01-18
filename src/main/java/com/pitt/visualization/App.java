package com.pitt.visualization;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import loci.formats.FormatException;
import loci.formats.IFormatReader;
import loci.formats.ImageReader;

import java.awt.image.BufferedImage;

//import ij.ImagePlus;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.geometry.Pos;
import javafx.scene.input.MouseButton;



public class App extends Application {
    private static Stage stage;

    /*@Override
    public void start(@SuppressWarnings("exports") Stage s) throws IOException {
        stage=s;
        setRoot("primary","");
    }*/

    static void setRoot(String fxml) throws IOException {
        setRoot(fxml,stage.getTitle());
    }

    static void setRoot(String fxml, String title) throws IOException {
        Scene scene = new Scene(loadFXML(fxml));
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/"+fxml + ".fxml"));
        return fxmlLoader.load();
    }


    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws IOException, FormatException {
    	
    	primaryStage.setTitle("Image Viewer");
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Open Image File");
    	fileChooser.getExtensionFilters().addAll(
    	        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.tif"),
    	        new ExtensionFilter("TIFF", "*.tif"));
    	File file = fileChooser.showOpenDialog(primaryStage);
    	if (file != null) {
    		ImageView imageView = new ImageView();
    		if (file.getName().endsWith(".TIF")) {
    			IFormatReader reader = new ImageReader();
    			//reader.setId("path/to/image.tif");

    			File tiffFile = new File("/Users/priyankasarkate/Downloads/20210823_175820_S1_C902_P99_N99_F001_Z001.TIF");
    	        BufferedImage bufferedImage = ImageIO.read(file);
    	        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
    	        //ImageView imageView = new ImageView(image);
    			//ImageView imageView = new ImageView();
    			imageView.setImage(image);
    		} else {
       // File file = new File("/Users/priyankasarkate/Documents/output.jpg");
    		
    		    	Image image = new Image(file.toURI().toString());
    	            //ImageView imageView = new ImageView(image);
    		    	imageView.setImage(image);
        
       // imageView.setFitWidth(primaryStage.getWidth()*0.8);
        //imageView.setPreserveRatio(true);
    		}
        imageView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                imageView.setScaleX(imageView.getScaleX() * 1.1);
                imageView.setScaleY(imageView.getScaleY() * 1.1);
            } else if (event.getButton() == MouseButton.SECONDARY) {
                imageView.setScaleX(imageView.getScaleX() / 1.1);
                imageView.setScaleY(imageView.getScaleY() / 1.1);
            }
        });
        imageView.setOnScroll(event -> {
            if (event.getDeltaY() > 0) {
                imageView.setScaleX(imageView.getScaleX() * 1.1);
                imageView.setScaleY(imageView.getScaleY() * 1.1);
            } else {
                imageView.setScaleX(imageView.getScaleX() / 1.1);
                imageView.setScaleY(imageView.getScaleY() / 1.1);
            }
        });
        StackPane root = new StackPane();
       // StackPane.setAlignment(imageView, Pos.TOP_CENTER);
        root.getChildren().add(imageView);
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    
    	}
    }
}
