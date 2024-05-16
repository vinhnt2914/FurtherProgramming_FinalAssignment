package org.example.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TestFileClaim extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/components/form/fileClaimForm.fxml"));
        VBox root = loader.load();

        // Create a scene and set the root node
        Scene scene = new Scene(root, 819, 551);

        // Set the scene onto the stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("File Claim Test");
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
