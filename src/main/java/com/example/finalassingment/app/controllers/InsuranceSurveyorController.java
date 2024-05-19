package com.example.finalassingment.app.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import com.example.finalassingment.app.components.form.ProposalForm;
import com.example.finalassingment.app.components.form.RequestForm;
import com.example.finalassingment.app.components.sortingSet.ClaimSortingSet;
import com.example.finalassingment.app.components.sortingSet.CustomerSortingSet;
import com.example.finalassingment.app.components.table.ClaimTable;
import com.example.finalassingment.app.components.table.CustomerTable;
import com.example.finalassingment.global.ClaimQueryType;
import com.example.finalassingment.global.CustomerQueryType;
import com.example.finalassingment.model.items.Claim;

import java.net.URL;
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
                CustomerSortingSet sortingSet = new CustomerSortingSet(customerTable);

                tableViewContainer.getChildren().setAll(customerTable);
                sortingContainer.getChildren().setAll(sortingSet);
            }
        }
    }
}
