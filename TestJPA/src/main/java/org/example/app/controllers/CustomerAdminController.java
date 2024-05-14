package org.example.app.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import org.example.app.components.table.DependantTable;
import org.example.app.components.table.PolicyHolderTable;
import org.example.app.components.button.DependantButtonSet;
import org.example.app.components.button.PolicyHolderButtonSet;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerAdminController implements Initializable {
    @FXML
    private HBox buttonSetContainer;
    @FXML
    private HBox tableViewContainer;
    @FXML
    private ChoiceBox<String> swapTableChoiceBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpPage();
    }

    private void setUpPage() {
        ObservableList<String> comboBoxOptions = FXCollections.observableArrayList();
        comboBoxOptions.addAll("Policy Holders", "Dependants");
        this.swapTableChoiceBox.getItems().addAll(comboBoxOptions);
        // Set to Policy Holder Table by default
        this.swapTableChoiceBox.getSelectionModel().select("Policy Holders");
        this.tableViewContainer.getChildren().add(new PolicyHolderTable());
        this.buttonSetContainer.getChildren().add(new PolicyHolderButtonSet());
        this.swapTableChoiceBox.setOnAction(this::swapTable);
    }

    private void swapTable(Event event) {
        String tableType = swapTableChoiceBox.getValue();
        tableViewContainer.getChildren().clear();
        buttonSetContainer.getChildren().clear();

        if (tableType.equalsIgnoreCase("Policy Holders")) {
            tableViewContainer.getChildren().add(new PolicyHolderTable());
            buttonSetContainer.getChildren().add(new PolicyHolderButtonSet());
        } else if (tableType.equalsIgnoreCase("Dependants")) {
            tableViewContainer.getChildren().add(new DependantTable());
            buttonSetContainer.getChildren().add(new DependantButtonSet());
        }
    }
}
