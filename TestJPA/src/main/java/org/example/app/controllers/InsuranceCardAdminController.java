package org.example.app.controllers;
/**
 * @author Group 11
 */
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import org.example.app.components.alert.ErrorAlert;
import org.example.app.components.buttonset.InsuranceCardButtonSet;
import org.example.app.components.form.AddInsuranceCardForm;
import org.example.app.components.form.UpdateInsuranceCardForm;
import org.example.app.components.table.InsuranceCardTable;
import org.example.app.components.table.RefreshableTable;
import org.example.model.items.InsuranceCard;
import org.example.repository.impl.InsuranceCardRepository;

import java.net.URL;
import java.util.ResourceBundle;

public class InsuranceCardAdminController implements Initializable, RefreshableController {
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
        setInsuranceCardButtonActions();
    }

    private void setInsuranceCardButtonActions() {
        InsuranceCardButtonSet buttonSet = (InsuranceCardButtonSet) buttonSetContainer.getChildren().get(0);

        buttonSet.addButton.setOnAction(event -> addInsuranceCard());
        buttonSet.updateButton.setOnAction(event -> updateInsuranceCard());
        buttonSet.deleteButton.setOnAction(event -> deleteInsuranceCard());
    }

    private void deleteInsuranceCard() {
        InsuranceCardTable tableView = (InsuranceCardTable) tableViewContainer.getChildren().get(0);
        InsuranceCard selectedCard = tableView.getSelectionModel().getSelectedItem();
        if (selectedCard != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirm Deletion");
            confirmationAlert.setHeaderText("Delete Policy Owner");
            confirmationAlert.setContentText("Are you sure you want to delete the selected policy owner?");

            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    InsuranceCardRepository repository = new InsuranceCardRepository();
                    repository.removeByID(selectedCard.getCardNumber());
                    repository.close();
                    refresh();
                }
            });
        } else {
            new ErrorAlert("Please select a dependant");
        }
    }

    private void updateInsuranceCard() {
        InsuranceCardTable tableView = (InsuranceCardTable) tableViewContainer.getChildren().get(0);
        InsuranceCard selectedCard = tableView.getSelectionModel().getSelectedItem();

        if (selectedCard != null) {
            new UpdateInsuranceCardForm(this, selectedCard);
        } else {
            new ErrorAlert("Please select an insurance card!");
        }
    }

    private void addInsuranceCard() {
        new AddInsuranceCardForm(this);
    }

    @Override
    public void refresh() {
        RefreshableTable tableView = (RefreshableTable) tableViewContainer.getChildren().get(0);
        tableView.refreshTable();
    }
}
