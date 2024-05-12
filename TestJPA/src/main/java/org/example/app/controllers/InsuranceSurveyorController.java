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
import org.example.model.enums.ClaimStatus;
import org.example.model.items.Claim;
import org.example.repository.IClaimRepository;
import org.example.repository.ICustomerRepository;
import org.example.repository.impl.ClaimRepository;
import org.example.repository.impl.CustomerRepository;

import java.net.URL;
import java.util.List;
import java.util.Optional;
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
    private ClaimTable claimTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        claimRepository = new ClaimRepository(); // Initialize claimRepository
        customerRepository = new CustomerRepository(); // Initialize customerRepository
        claimTable = new ClaimTable(); // Initialize claimTable
        setUpPage(); // Call setUpPage after initializing claimTable
    }


    private void setUpPage() {
        ObservableList<String> comboBoxOptions = FXCollections.observableArrayList("Claims", "Customers");
        swapTableComboBox.setItems(comboBoxOptions);
        swapTableComboBox.getSelectionModel().selectFirst();
        swapTable(null); // Initialize table with default value

        proposeButton.setOnAction(this::handlePropose);
        requestButton.setOnAction(this::handleRequest);
        swapTableComboBox.setOnAction(this::swapTable);
    }

    private void handleRequest(ActionEvent actionEvent) {
        Claim selectedClaim = claimTable.getSelectionModel().getSelectedItem();
        if (selectedClaim != null) {
            boolean additionalInfoProvided = promptForRequest(selectedClaim);
            if (additionalInfoProvided) {
                updateClaimStatus(selectedClaim);
                showSuccessMessage("Additional information requested successfully.");
            } else {
                showErrorMessage("Request for additional information canceled or no information provided.");
            }
        } else {
            showErrorMessage("No claim selected. Please select a claim to request additional information.");
        }
    }

    private void handlePropose(ActionEvent actionEvent) {
        Claim selectedClaim = claimTable.getSelectionModel().getSelectedItem();
        if (selectedClaim != null) {
            boolean additionalInfoProvided = promptForPropose(selectedClaim);
            if (additionalInfoProvided) {
                proposeClaim(selectedClaim);
                showSuccessMessage("Proposed claim successfully.");
            } else {
                showErrorMessage("There was an error during the process of proposing the claim");
            }
        } else {
            showErrorMessage("No claim selected. Please select a claim to request additional information.");
        }
    }

    private boolean promptForPropose(Claim claim) {
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Propose Claim");
        confirmationDialog.setHeaderText("Propose Claim");
        confirmationDialog.setContentText("Do you want to propose this claim to the manager?");

        Optional<ButtonType> result = confirmationDialog.showAndWait();


        return result.isPresent() && result.get() == ButtonType.OK;
    }

    private void proposeClaim(Claim claim) {

        IClaimRepository claimRepository = new ClaimRepository();


        claim.setStatus(ClaimStatus.PROPOSED_TO_MANAGER);


        claimRepository.update(claim);
    }

    private boolean promptForRequest(Claim claim) {

        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Additional Information Request");
        confirmationDialog.setHeaderText("Additional Information Request");
        confirmationDialog.setContentText("Do you want to request additional information for this claim?");

        confirmationDialog.showAndWait();

        return confirmationDialog.getResult() == ButtonType.OK;
    }

    private void updateClaimStatus(Claim claim) {

        IClaimRepository claimRepository = new ClaimRepository();


        claim.setStatus(ClaimStatus.INFORMATION_REQUESTED);


        claimRepository.update(claim);
    }

    private void showSuccessMessage(String message) {

        Alert successDialog = new Alert(Alert.AlertType.INFORMATION);
        successDialog.setTitle("Success");
        successDialog.setHeaderText("Success");
        successDialog.setContentText(message);
        successDialog.showAndWait();
    }


    private void showErrorMessage(String message) {

        Alert errorDialog = new Alert(Alert.AlertType.ERROR);
        errorDialog.setTitle("Error");
        errorDialog.setHeaderText("Error");
        errorDialog.setContentText(message);
        errorDialog.showAndWait();
    }


    private void displayClaimsInUI(List<Claim> claims) {
        claimTable.setItems(FXCollections.observableArrayList(claims));
        tableViewContainer.getChildren().clear();
        tableViewContainer.getChildren().add(claimTable);
    }

    private void displayCustomersInUI(List<Customer> customers) {
        CustomerTable.ConcreteCustomerTable customerTable = new CustomerTable.ConcreteCustomerTable();
        customerTable.setItems(FXCollections.observableArrayList(customers));
        tableViewContainer.getChildren().clear();
        tableViewContainer.getChildren().add(customerTable);
    }

    private void swapTable(ActionEvent event) {
        String selectedOption = swapTableComboBox.getValue();
        if (selectedOption != null) {
            if (selectedOption.equals("Claims")) {
                List<Claim> allClaims = claimRepository.getAll();
                displayClaimsInUI(allClaims);
            } else if (selectedOption.equals("Customers")) {
                List<Customer> allCustomers = customerRepository.getAll();
                displayCustomersInUI(allCustomers);
            }
        }
    }
}
