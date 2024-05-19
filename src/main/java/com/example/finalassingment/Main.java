package com.example.finalassingment;

import javafx.application.Application;
import javafx.stage.Stage;
import com.example.finalassingment.app.Login;

import java.sql.SQLException;

public class Main extends Application {
    public static void main(String[] args) throws SQLException {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        new Login();
    }
}