package org.example.app.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import org.example.app.components.buttonSet.InsuranceCardButtonSet;
import org.example.app.components.buttonSet.PolicyHolderButtonSet;
import org.example.app.components.table.InsuranceCardTable;

import java.net.URL;
import java.util.ResourceBundle;

public class InsuranceCardAdminController implements Initializable {
    @FXML
    private HBox tableViewContainer;
    @FXML
    private HBox sortingContainer;
    @FXML
    private HBox buttonSetContainer;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpPage();
    }

    private void setUpPage() {
        InsuranceCardTable cardTable = new InsuranceCardTable();
        InsuranceCardButtonSet buttonSet = new InsuranceCardButtonSet(cardTable);
        this.tableViewContainer.getChildren().add(cardTable);

        this.buttonSetContainer.getChildren().add(buttonSet);
    }

    private void setInsuranceCardButtonActions() {
        InsuranceCardButtonSet buttonSet = (InsuranceCardButtonSet) buttonSetContainer.getChildren().get(0);

        buttonSet.addButton.setOnAction(event -> addInsuranceCard());
        buttonSet.updateButton.setOnAction(event -> updateInsuranceCard());
        buttonSet.deleteButton.setOnAction(event -> deleteInsuranceCard());
    }

    private void deleteInsuranceCard() {

    }

    private void updateInsuranceCard() {

    }

    private void addInsuranceCard() {

    }
}
