package org.example.app.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import org.example.app.components.buttonSet.DependantButtonSet;
import org.example.app.components.buttonSet.PolicyHolderButtonSet;
import org.example.app.components.table.ClaimTable;
import org.example.app.components.table.DependantTable;
import org.example.app.components.table.PolicyHolderTable;
import org.example.app.components.table.RequestTable;
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
        this.tableViewContainer.getChildren().add(new ClaimTable()); // Set by default
        this.swapTableChoiceBox.getItems().addAll(comboBoxOptions);
        this.swapTableChoiceBox.getSelectionModel().select("Claim");
        this.swapTableChoiceBox.setOnAction(this::swapTable);
    }

    private void swapTable(Event event) {
        String tableType = swapTableChoiceBox.getValue();
        tableViewContainer.getChildren().clear();

        if (tableType.equalsIgnoreCase("Claim")) {
            tableViewContainer.getChildren().add(new ClaimTable());
            buttonSetContainer.getChildren().add(new DependantButtonSet());
        } else if (tableType.equalsIgnoreCase("Dependant")) {
            tableViewContainer.getChildren().add(new DependantTable(
                    CustomerQueryType.
                            QueryType.
                            GET_ALL_DEPENDANT_OF_POLICY_OWNER));
            buttonSetContainer.getChildren().add(new PolicyHolderButtonSet());
        } else if (tableType.equalsIgnoreCase("PolicyHolder")) {
            tableViewContainer.getChildren().add(new PolicyHolderTable(
                    CustomerQueryType.QueryType.GET_ALL_POLICY_HOLDER_OF_POLICY_OWNER));
//            buttonSetContainer.getChildren().add()
        }
    }
}
