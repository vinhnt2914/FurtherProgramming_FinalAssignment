package com.example.finalassingment.app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Login {
    private Stage stage;
    public Login() {
        // Load the FXML file

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
