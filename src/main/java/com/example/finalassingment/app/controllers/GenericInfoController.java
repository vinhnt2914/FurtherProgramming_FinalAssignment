package com.example.finalassingment.app.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import com.example.finalassingment.app.components.form.UpdateInfoForm;
import com.example.finalassingment.app.components.stats.PolicyOwnerStats;
import com.example.finalassingment.global.GlobalVariable;
import com.example.finalassingment.global.Role;
import com.example.finalassingment.model.User;
import com.example.finalassingment.repository.impl.UserRepository;

import java.net.URL;
import java.util.ResourceBundle;

public class GenericInfoController implements Initializable, RefreshableController {
    public BorderPane genericDashboard;
    @FXML
    private Button updateInfoButton;
    @FXML
    private HBox statsContainer;
    @FXML
    private Label fullNameLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label emailLabel;
    private User user;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpPage();
    }

    private void setUpPage() {
        getDataFromDB();
        setUpLabels();
        setUpStats();
        this.updateInfoButton.setOnAction(this::openUpdateInfo);
    }

    private void openUpdateInfo(ActionEvent actionEvent) {
        new UpdateInfoForm(user, this);
    }

    private void setUpLabels() {
        fullNameLabel.setText("Full Name: " + user.getFullName());
        addressLabel.setText("Address: " + user.getAddress());
        phoneLabel.setText("Phone: " + user.getPhone());
        emailLabel.setText("Email: " + user.getEmail());
    }

    private void setUpStats() {
        Role role = GlobalVariable.getRole();
        switch (role) {
            case PolicyOwner -> setUpPolicyOwnerStats();
        }
    }

    private void setUpPolicyOwnerStats() {
        statsContainer.getChildren().setAll(new PolicyOwnerStats());
    }

    private void getDataFromDB() {
        UserRepository repository = new UserRepository();
        user = repository.findByID(GlobalVariable.getUserID());
        repository.close();
    }

    @Override
    public void refresh() {
        setUpPage();
    }
}
