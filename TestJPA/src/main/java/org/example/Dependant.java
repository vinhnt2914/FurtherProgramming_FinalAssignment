package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Dependant extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Dependant.fxml"));
        BorderPane root = loader.load();

        // Create a scene and set the root node
        Scene scene = new Scene(root, 819, 551);

        // Set the scene onto the stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dependant test");
        primaryStage.show();

        // Get the controller
        DependantController controller = loader.getController();

        // Create example claims and populate the table
        ExampleClaimCreator claimCreator = new ExampleClaimCreator(controller);
        claimCreator.createExampleClaims();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
