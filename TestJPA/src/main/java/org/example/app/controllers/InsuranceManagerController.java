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
import javafx.scene.control.Button;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class InsuranceManagerController implements Initializable {


    @FXML
    private Button approveButton;

    @FXML
    private Button rejectButton;

    @FXML
    private ComboBox<String> swapTableComboBox;
    @FXML
    private HBox tableViewContainer;
    private IClaimRepository claimRepository;
    private ICustomerRepository customerRepository;
    private ClaimTable claimTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        claimRepository = new ClaimRepository();
        customerRepository = new CustomerRepository();
        claimTable = new ClaimTable();
        setUpPage();
    }


    private void setUpPage() {
        ObservableList<String> comboBoxOptions = FXCollections.observableArrayList("Claims", "Customers");
        swapTableComboBox.setItems(comboBoxOptions);
        swapTableComboBox.getSelectionModel().selectFirst();
        swapTable(null); // Initialize table with default value
        rejectButton.setOnAction(this::handleRejectButton);
        approveButton.setOnAction(this::handleApproveButton);

        swapTableComboBox.setOnAction(this::swapTable);
    }


    @FXML
    private void handleApproveButton(ActionEvent actionEvent) {
        Claim selectedClaim = claimTable.getSelectionModel().getSelectedItem();
        if (selectedClaim != null) {
            boolean confirmation = promptForApprove(selectedClaim);
            if (confirmation) {
                approveClaim(selectedClaim);
                showSuccessMessage("Claim approved successfully.");
            } else {
                showErrorMessage("Approval canceled.");
            }
        } else {
            showErrorMessage("No claim selected. Please select a claim to approve.");
        }
    }

    @FXML
    private void handleRejectButton(ActionEvent actionEvent) {
        Claim selectedClaim = claimTable.getSelectionModel().getSelectedItem();
        if (selectedClaim != null) {
            boolean confirmation = promptForReject(selectedClaim);
            if (confirmation) {
                rejectClaim(selectedClaim);
                showSuccessMessage("Claim rejected successfully.");
            } else {
                showErrorMessage("Rejection canceled.");
            }
        } else {
            showErrorMessage("No claim selected. Please select a claim to reject.");
        }
    }

    private boolean promptForApprove(Claim claim) {
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Approve Claim");
        confirmationDialog.setHeaderText("Approve Claim");
        confirmationDialog.setContentText("Do you want to approve this claim?");
        Optional<ButtonType> result = confirmationDialog.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    private boolean promptForReject(Claim claim) {
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Reject Claim");
        confirmationDialog.setHeaderText("Reject Claim");
        confirmationDialog.setContentText("Do you want to reject this claim?");
        Optional<ButtonType> result = confirmationDialog.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    private void approveClaim(Claim claim) {
        IClaimRepository claimRepository = new ClaimRepository();
        claim.setStatus(ClaimStatus.APPROVED);
        claimRepository.update(claim);
    }

    private void rejectClaim(Claim claim) {
        IClaimRepository claimRepository = new ClaimRepository();
        claim.setStatus(ClaimStatus.REJECTED);
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
