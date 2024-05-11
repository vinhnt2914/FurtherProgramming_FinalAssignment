package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.model.customer.Dependant;
import org.example.model.customer.PolicyHolder;
import org.example.model.customer.PolicyOwner;
import org.example.model.enums.ClaimStatus;
import org.example.model.items.Claim;
import org.example.model.items.InsuranceCard;
import org.example.model.items.Proposal;
import org.example.model.items.Request;
import org.example.model.provider.InsuranceManager;
import org.example.model.provider.InsuranceSurveyor;
import org.example.repository.impl.*;
import org.example.service.ClaimService;
import org.example.service.CustomerService;
import org.example.service.InsuranceCardService;

import java.sql.SQLException;
import java.time.LocalDate;

public class Main extends Application {
    public static void main(String[] args) throws SQLException {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
        Parent root = loader.load();

        // Create a scene and set the root node
        Scene scene = new Scene(root, 819, 551);

        // Set the scene onto the stage
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }
}