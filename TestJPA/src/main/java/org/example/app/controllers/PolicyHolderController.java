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
import org.example.app.components.table.RequestTable;
import org.example.global.CustomerQueryType;
import org.example.model.customer.PolicyHolder;

import java.net.URL;
import java.util.ResourceBundle;

public class PolicyHolderController implements Initializable {
    @FXML
    private Button myClaimButton;
    @FXML
    private HBox tableViewContainer;
    @FXML
    private ChoiceBox<String> swapTableChoiceBox;
    @FXML
    private Button fileClaimButton;

    private PolicyHolder loggedInPolicyHolder;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize loggedInPolicyHolder with the actual logged-in user's PolicyHolder object
        // For example:
        // this.loggedInPolicyHolder = SessionService.getLoggedInPolicyHolder();
        setUpPage();
    }

    private void setUpPage() {
        ObservableList<String> comboBoxOptions = FXCollections.observableArrayList();
        comboBoxOptions.addAll("Claim", "Dependant", "Request");
        this.swapTableChoiceBox.getItems().addAll(comboBoxOptions);
        this.swapTableChoiceBox.getSelectionModel().select("Claim");
        this.tableViewContainer.getChildren().add(new ClaimTable());
        this.fileClaimButton.setOnAction(this::openFileClaimForm);
        this.swapTableChoiceBox.setOnAction(this::swapTable);
        this.myClaimButton.setOnAction(this::showMyClaims);
    }

    private void openFileClaimForm(ActionEvent actionEvent) {
        new FileClaimForm();
    }

    private void swapTable(Event event) {
        String tableType = swapTableChoiceBox.getValue();
        tableViewContainer.getChildren().clear();

        if (tableType.equalsIgnoreCase("Claim")) {
            tableViewContainer.getChildren().add(new ClaimTable());
        } else if (tableType.equalsIgnoreCase("Dependant")) {
            tableViewContainer.getChildren().add(new DependantTable(
                    CustomerQueryType.QueryType.GET_ALL_DEPENDANT_OF_POLICY_HOLDER));
        } else if (tableType.equalsIgnoreCase("Request")) {
            tableViewContainer.getChildren().add(new RequestTable());
        }
    }

    private void showMyClaims(ActionEvent actionEvent) {
        tableViewContainer.getChildren().clear();
        ClaimTable claimTable = new ClaimTable();
        tableViewContainer.getChildren().add(claimTable);
        claimTable.populateTableViewForPolicyHolder(loggedInPolicyHolder);
    }
}
