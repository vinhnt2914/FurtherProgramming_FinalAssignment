package org.example.app.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.example.app.components.stats.PolicyOwnerStats;
import org.example.global.GlobalVariable;
import org.example.global.Role;
import org.example.model.customer.Customer;
import org.example.repository.impl.CustomerRepository;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerDashboard implements Initializable {
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
    private Customer customer;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getDataFromDB();
        setUpPage();
    }

    private void setUpPage() {
        setUpLabels();
        setUpStats();
    }

    private void setUpLabels() {
        fullNameLabel.setText("Full Name: " + customer.getFullName());
        addressLabel.setText("Address: " + customer.getAddress());
        phoneLabel.setText("Phone: " + customer.getPhone());
        emailLabel.setText("Email: " + customer.getEmail());
    }

    private void setUpStats() {
        Role role = GlobalVariable.getRole();
        switch (role) {
            case PolicyOwner -> setUpPolicyOwnerStats();
        }
    }

    private void setUpPolicyOwnerStats() {
        statsContainer.getChildren().add(new PolicyOwnerStats());
    }

    private void getDataFromDB() {
        CustomerRepository repository = new CustomerRepository();
        customer = repository.findByID(GlobalVariable.getUserID());
        repository.close();
    }
}
