package Utility;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class SceneManager {
    private static SceneManager instance;
    private static  Stage primaryStage;

    private SceneManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public static synchronized SceneManager getInstance(Stage primaryStage) {
        if (instance == null) {
            instance = new SceneManager(primaryStage);
        }
        return instance;
    }

    public static void switchScene(String fxmlPath) {
        try {
        	int width = 696;
        	int height = 463;
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));
            Parent root = loader.load();
            
            // Set the scene
            Scene scene = new Scene(root, width, height);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}