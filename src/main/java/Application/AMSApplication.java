package Application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import Utility.SceneManager;

public class AMSApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        SceneManager.getInstance(stage);
        SceneManager.switchScene("/Views/SignIn.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}