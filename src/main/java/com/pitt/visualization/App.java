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
import loci.formats.ImageReader;
import loci.formats.MetadataTools;
import ome.xml.meta.MetadataRetrieve;

import java.awt.image.BufferedImage;

//import ij.ImagePlus;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
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
    	        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.tif", "*"),
    	        new ExtensionFilter("TIFF", "*.tif"));
    	File file = fileChooser.showOpenDialog(primaryStage);
    	if (file != null) {
    		ImageView imageView = new ImageView();
    		if (file.getName().endsWith(".TIF") || file.getName().endsWith(".tif")) {
    			// Create a new Bio-Formats reader
    			ImageReader reader = new ImageReader();
    			reader.setId(file.getAbsolutePath());

    			// Specify the input file
    			//File file = new File("path/to/pyramidal/image");
    			MetadataRetrieve meta = MetadataTools.createOMEXMLMetadata();
    			BufferedImage image = new BufferedImage(reader.getSizeX(), reader.getSizeY(), BufferedImage.TYPE_BYTE_GRAY);

    			
    			// Loop over each image level and channel
    			for (int level = 0; level < reader.getResolutionCount(); level++) {
    			    for (int channel = 0; channel < reader.getSizeC(); channel++) {
    			        // Get the image data for the current level and channel
    			        byte[] bytes = new byte[reader.getSizeX() * reader.getSizeY()];

    			        reader.openBytes(channel, bytes);

    			        // Convert the image data to an array of integers
    			        int[] pixels = new int[bytes.length];
    			        for (int i = 0; i < pixels.length; i++) {
    			            pixels[i] = bytes[i] & 0xff;
    			        }

    			        // Set the image data for the current level and channel
    			        image.setRGB(0, 0, reader.getSizeX(), reader.getSizeY(), pixels, 0, reader.getSizeX());
    			    }
    			}

    			// Create a JavaFX Image from the BufferedImage
    			Image fxImage = SwingFXUtils.toFXImage(image, null);

    			// Display the image in a JavaFX ImageView
    			 imageView = new ImageView(fxImage);
    		} else {
       // File file = new File("/Users/priyankasarkate/Documents/output.jpg");
    		
    		    	Image image = new Image(file.toURI().toString());
    	            //ImageView imageView = new ImageView(image);
    		    	imageView.setImage(image);
        
       // imageView.setFitWidth(primaryStage.getWidth()*0.8);
        //imageView.setPreserveRatio(true);
    		}
    		final ImageView finalImageView = imageView;
    		finalImageView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
            	finalImageView.setScaleX(finalImageView.getScaleX() * 1.1);
            	finalImageView.setScaleY(finalImageView.getScaleY() * 1.1);
            } else if (event.getButton() == MouseButton.SECONDARY) {
            	finalImageView.setScaleX(finalImageView.getScaleX() / 1.1);
            	finalImageView.setScaleY(finalImageView.getScaleY() / 1.1);
            }
        });
    		finalImageView.setOnScroll(event -> {
            if (event.getDeltaY() > 0) {
            	finalImageView.setScaleX(finalImageView.getScaleX() * 1.1);
            	finalImageView.setScaleY(finalImageView.getScaleY() * 1.1);
            } else {
            	finalImageView.setScaleX(finalImageView.getScaleX() / 1.1);
            	finalImageView.setScaleY(finalImageView.getScaleY() / 1.1);
            }
        });
        StackPane root = new StackPane();
       // StackPane.setAlignment(imageView, Pos.TOP_CENTER);
        root.getChildren().add(finalImageView);
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    
    	}
    }
}
