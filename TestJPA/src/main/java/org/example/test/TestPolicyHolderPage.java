package org.example.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
public class TestPolicyHolderPage extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/policyHolder.fxml"));
        BorderPane root = loader.load();

        // Create a scene and set the root node
        Scene scene = new Scene(root);

        // Set the scene onto the stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Policy Holder");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
