package org.example.app.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import org.example.app.components.table.ClaimTable;
import org.example.app.components.table.CustomerTable;
import org.example.model.customer.Customer;
import org.example.model.items.Claim;
import org.example.repository.IClaimRepository;
import org.example.repository.ICustomerRepository;
import org.example.repository.impl.ClaimRepository;
import org.example.repository.impl.CustomerRepository;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class InsuranceSurveyorController implements Initializable {
    @FXML
    private Button requestButton;
    @FXML
    private Button proposeButton;
    @FXML
    private ComboBox<String> swapTableComboBox;
    @FXML
    private HBox tableViewContainer;

    private IClaimRepository claimRepository;
    private ICustomerRepository customerRepository;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpPage();
        claimRepository = new ClaimRepository();
        customerRepository = new CustomerRepository();
    }

    private void setUpPage() {
        ObservableList<String> comboBoxOptions = FXCollections.observableArrayList("Claims", "Customers");
        swapTableComboBox.setItems(comboBoxOptions);
        swapTableComboBox.getSelectionModel().selectFirst();
        swapTable(null); // Initialize table with default value

        requestButton.setOnAction(this::handleRequest);
        proposeButton.setOnAction(this::handlePropose);
        swapTableComboBox.setOnAction(this::swapTable);
    }

    private void handleRequest(ActionEvent actionEvent) {
        // Retrieve all claims
        List<Claim> allClaims = claimRepository.getAllClaims();
        displayClaimsInUI(allClaims);
    }

    private void handlePropose(ActionEvent actionEvent) {
        // Additional logic for proposing a claim
        // For example: claimRepository.proposeClaim(claim);
        // Show success/failure message to the user
    }

    private void displayClaimsInUI(List<Claim> claims) {
        tableViewContainer.getChildren().clear();
        tableViewContainer.getChildren().add(new ClaimTable(claims));
    }

    private void displayCustomersInUI(List<Customer> customers) {
        tableViewContainer.getChildren().clear();
        tableViewContainer.getChildren().add(new CustomerTable(customers));
    }

    private void swapTable(ActionEvent event) {
        String selectedOption = swapTableComboBox.getValue();
        if (selectedOption != null) {
            if (selectedOption.equals("Claims")) {
                List<Claim> allClaims = claimRepository.getAllClaims();
                displayClaimsInUI(allClaims);
            } else if (selectedOption.equals("Customers")) {
                List<Customer> allCustomers = customerRepository.getAllCustomers();
                displayCustomersInUI(allCustomers);
            }
        }
    }
}
