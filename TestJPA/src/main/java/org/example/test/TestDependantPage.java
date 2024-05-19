package org.example.test;
/**
 * @author Group 11
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TestDependantPage extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/dependant.fxml"));
        BorderPane root = loader.load();

        // Create a scene and set the root node
        Scene scene = new Scene(root, 819, 551);

        // Set the scene onto the stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dependant test");
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
