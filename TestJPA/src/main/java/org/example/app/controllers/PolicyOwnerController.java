package org.example.app.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import org.example.app.components.buttonSet.ClaimButtonSet;
import org.example.app.components.buttonSet.DependantButtonSet;
import org.example.app.components.buttonSet.PolicyHolderButtonSet;
import org.example.app.components.table.ClaimTable;
import org.example.app.components.table.DependantTable;
import org.example.app.components.table.PolicyHolderTable;
import org.example.global.CustomerQueryType;

import java.net.URL;
import java.util.ResourceBundle;

public class PolicyOwnerController implements Initializable {
    @FXML
    private HBox buttonSetContainer;
    @FXML
    private ChoiceBox<String> swapTableChoiceBox;
    @FXML
    private HBox tableViewContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpPage();
    }

    private void setUpPage() {
        ObservableList<String> comboBoxOptions = FXCollections.observableArrayList();
        comboBoxOptions.addAll("Claim", "Dependant", "PolicyHolder");

        ClaimTable claimTable = new ClaimTable();
        ClaimButtonSet claimButtonSet = new ClaimButtonSet(claimTable);
        this.tableViewContainer.getChildren().add(claimTable);
        this.buttonSetContainer.getChildren().add(claimButtonSet);
        this.swapTableChoiceBox.getItems().addAll(comboBoxOptions);
        this.swapTableChoiceBox.getSelectionModel().select("Claim");
        this.swapTableChoiceBox.setOnAction(this::swapTable);
    }

    private void swapTable(Event event) {
        String tableType = swapTableChoiceBox.getValue();
        tableViewContainer.getChildren().clear();
        buttonSetContainer.getChildren().clear();

        if (tableType.equalsIgnoreCase("Claim")) {
            ClaimTable claimTable = new ClaimTable();
            ClaimButtonSet claimButtonSet = new ClaimButtonSet(claimTable);
            tableViewContainer.getChildren().add(claimTable);
            buttonSetContainer.getChildren().add(claimButtonSet);
        } else if (tableType.equalsIgnoreCase("Dependant")) {
            DependantTable dependantTable = new DependantTable(
                    CustomerQueryType.
                            QueryType.
                            GET_ALL_DEPENDANT_OF_POLICY_OWNER);
            DependantButtonSet dependantButtonSet = new DependantButtonSet(dependantTable);
            tableViewContainer.getChildren().add(dependantTable);
            buttonSetContainer.getChildren().add(dependantButtonSet);
        } else if (tableType.equalsIgnoreCase("PolicyHolder")) {
            PolicyHolderTable policyHolderTable = new PolicyHolderTable(
                    CustomerQueryType.QueryType.GET_ALL_POLICY_HOLDER_OF_POLICY_OWNER);
            PolicyHolderButtonSet policyHolderButtonSet = new PolicyHolderButtonSet(policyHolderTable);
            tableViewContainer.getChildren().add(policyHolderTable);
            buttonSetContainer.getChildren().add(policyHolderButtonSet);
        }
    }
}
