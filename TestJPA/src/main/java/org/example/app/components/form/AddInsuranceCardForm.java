package org.example.app.components.form;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.app.components.table.RefreshableTable;
import org.example.app.components.table.SelectBeneficiaryTable;
import org.example.app.controllers.RefreshableController;
import org.example.global.CustomerQueryType;
import org.example.model.customer.Beneficiary;

import java.io.IOException;

public class AddInsuranceCardForm extends VBox implements SelectableForm{
    @FXML
    private TextField cardNumberField;
    @FXML
    private DatePicker expireDatePicker;
    @FXML
    private Button selectCardHolderButton;
    @FXML
    private Label cardHolderLabel;
    @FXML
    private Label policyOwnerLabel;
    @FXML
    private Button submitButton;
    @FXML
    private Button cancelButton;
    private Beneficiary beneficiary;
    private Stage stage;
    private RefreshableController controller;
    public AddInsuranceCardForm(RefreshableController controller) {
        this.controller = controller;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/form/addDependantForm.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            BorderPane rootPane = fxmlLoader.load();
            Scene scene = new Scene(rootPane);
            stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        setUpForm();
    }

    private void setUpForm() {
        this.selectCardHolderButton.setOnAction(this::openSelectCardHolder);
        this.submitButton.setOnAction(this::addInsuranceCard);
    }

    private void openSelectCardHolder(ActionEvent actionEvent) {
        new SelectBeneficiaryTable(CustomerQueryType.QueryType.GET_ALL, this);
    }

    private void addInsuranceCard(ActionEvent actionEvent) {

    }

    @Override
    public void setBeneficiary(Beneficiary beneficiary) {
        this.beneficiary = beneficiary;
    }
}
