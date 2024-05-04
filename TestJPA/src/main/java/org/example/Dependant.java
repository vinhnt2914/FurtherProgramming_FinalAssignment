package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Dependant extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file
        Parent root = FXMLLoader.load(getClass().getResource("/views/Dependant.fxml"));

        // Set up the stage and scene
        primaryStage.setTitle("Dependant");
        primaryStage.setScene(new Scene(root, 819, 551));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
