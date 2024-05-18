package org.example.app.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import org.example.app.components.form.ProposalForm;
import org.example.app.components.form.RequestForm;
import org.example.app.components.sortingSet.ClaimSortingSet;
import org.example.app.components.sortingSet.CustomerSortingSet;
import org.example.app.components.table.ClaimTable;
import org.example.app.components.table.CustomerTable;
import org.example.app.components.table.ProposalTable;
import org.example.global.ClaimQueryType;
import org.example.global.CustomerQueryType;
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
    private ChoiceBox<String> swapTableChoiceBox;
    @FXML
    private Button requestButton;
    @FXML
    private Button proposeButton;
    @FXML
    private HBox tableViewContainer;
    @FXML
    private HBox sortingContainer;
    private List<Customer> originalData;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpPage(); // Call setUpPage after initializing claimTable
    }


    private void setUpPage() {
        ObservableList<String> comboBoxOptions = FXCollections.observableArrayList("Claim", "Customer");
        swapTableChoiceBox.setItems(comboBoxOptions);
        swapTableChoiceBox.getSelectionModel().selectFirst();
        proposeButton.setOnAction(this::handlePropose);
        requestButton.setOnAction(this::handleRequest);
        swapTableChoiceBox.setOnAction(this::swapTable);

        ClaimTable claimTable = new ClaimTable(ClaimQueryType.QueryType.GET_ALL);
        ClaimSortingSet sortingSet = new ClaimSortingSet(claimTable);

        tableViewContainer.getChildren().setAll(claimTable);
        sortingContainer.getChildren().setAll(sortingSet);
    }

    private void handleRequest(ActionEvent actionEvent) {
        ClaimTable table = checkIfClaimTableView();
        if (table != null) {
            Claim claim = table.getSelectionModel().getSelectedItem();
            if (claim != null) {
                new RequestForm(claim);
            } else {
                showErrorMessage("You select nothing");
            }
        } else {
            showErrorMessage("This is not claim table");
        }
    }

    private void handlePropose(ActionEvent actionEvent) {
        ClaimTable table = checkIfClaimTableView();
        if (table != null) {
            Claim claim = table.getSelectionModel().getSelectedItem();
            if (claim != null) {
                new ProposalForm(claim);
            } else {
                showErrorMessage("You select nothing");
            }
        } else {
            showErrorMessage("This is not claim table");
        }
    }

    private ClaimTable checkIfClaimTableView() {
        Node tableView = tableViewContainer.getChildren().getFirst();
        if (tableView instanceof ClaimTable) return (ClaimTable) tableView;
        return null;
    }

    private void showErrorMessage(String message) {

        Alert errorDialog = new Alert(Alert.AlertType.ERROR);
        errorDialog.setTitle("Error");
        errorDialog.setHeaderText("Error");
        errorDialog.setContentText(message);
        errorDialog.showAndWait();
    }

    private void swapTable(ActionEvent event) {
        String selectedOption = swapTableChoiceBox.getValue();
        if (selectedOption != null) {
            if (selectedOption.equals("Claim")) {
                ClaimTable claimTable = new ClaimTable(ClaimQueryType.QueryType.GET_ALL);
                ClaimSortingSet sortingSet = new ClaimSortingSet(claimTable);

                tableViewContainer.getChildren().setAll(claimTable);
                sortingContainer.getChildren().setAll(sortingSet);
            } else if (selectedOption.equals("Customer")) {
                CustomerTable customerTable = new CustomerTable(CustomerQueryType.QueryType.GET_ALL_DEPENDANT_AND_POLICY_HOLDER);
                this.originalData = customerTable.getItems();
                CustomerSortingSet sortingSet = new CustomerSortingSet(customerTable, originalData);

                tableViewContainer.getChildren().setAll(customerTable);
                sortingContainer.getChildren().setAll(sortingSet);
            }
        }
    }
}
