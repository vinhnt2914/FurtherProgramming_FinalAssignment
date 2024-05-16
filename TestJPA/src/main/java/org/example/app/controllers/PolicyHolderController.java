package org.example.app.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import org.example.app.components.table.ClaimTable;
import org.example.app.components.table.DependantTable;
import org.example.app.components.form.FileClaimForm;
import org.example.app.components.table.RefreshableTable;
import org.example.app.components.table.RequestTable;
import org.example.global.ClaimQueryType;
import org.example.global.CustomerQueryType;
import org.example.global.GlobalVariable;
import org.example.global.RequestQueryType;
import org.example.model.customer.PolicyHolder;

import java.net.URL;
import java.util.ResourceBundle;

public class PolicyHolderController implements Initializable, RefreshableController {
    @FXML
    private Button myClaimButton;
    @FXML
    private HBox tableViewContainer;
    @FXML
    private ChoiceBox<String> swapTableChoiceBox;
    @FXML
    private Button fileClaimButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpPage();
    }

    private void setUpPage() {
        ObservableList<String> comboBoxOptions = FXCollections.observableArrayList();
        comboBoxOptions.addAll("Claim", "Dependant", "Request");
        this.swapTableChoiceBox.getItems().addAll(comboBoxOptions);
        // Set to Claim Table by default
        this.swapTableChoiceBox.getSelectionModel().select("Claim");
        this.tableViewContainer.getChildren().add(new ClaimTable(ClaimQueryType.QueryType.GET_ALL_OF_POLICYHOLDER));
        this.fileClaimButton.setOnAction(this::openFileClaimForm);
        this.swapTableChoiceBox.setOnAction(this::swapTable);
    }

    private void openFileClaimForm(ActionEvent actionEvent) {
        new FileClaimForm(this);
    }

    private void swapTable(Event event) {
        String tableType = swapTableChoiceBox.getValue();
        tableViewContainer.getChildren().clear();

        if (tableType.equalsIgnoreCase("Claim")) {
            tableViewContainer.getChildren().add(new ClaimTable(ClaimQueryType.QueryType.GET_ALL_OF_POLICYHOLDER));
            // Swap to claim button (new ClaimButtonSet)
        } else if (tableType.equalsIgnoreCase("Dependant")) {
            tableViewContainer.getChildren().add(new DependantTable(
                    CustomerQueryType.
                    QueryType.
                    GET_ALL_DEPENDANT_OF_POLICY_HOLDER));
        } else if (tableType.equalsIgnoreCase("Request")) {
            PolicyHolder currentUser = (PolicyHolder) GlobalVariable.getUser();
            tableViewContainer.getChildren().add(new RequestTable(RequestQueryType.QueryType.GET_ALL_TO_CUSTOMER, currentUser));
        }
    }

    @Override
    public void refresh() {
        RefreshableTable tableView = (RefreshableTable) tableViewContainer.getChildren().get(0);
        tableView.refreshTable();
    }
}
