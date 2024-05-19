package com.example.finalassingment.test;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TestInsuranceSurveyorPage extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file
        Parent root = FXMLLoader.load(getClass().getResource("/views/InsuranceSurveyor.fxml"));

        // Set up the primary stage
        primaryStage.setTitle("Insurance Surveyor");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
