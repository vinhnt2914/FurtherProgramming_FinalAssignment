package com.example.finalassingment.app.components.form;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.finalassingment.app.components.alert.SuccessAlert;
import com.example.finalassingment.app.components.table.SelectBeneficiaryTable;
import com.example.finalassingment.app.controllers.RefreshableController;
import com.example.finalassingment.global.CustomerQueryType;
import com.example.finalassingment.global.GlobalVariable;
import com.example.finalassingment.global.Role;
import com.example.finalassingment.model.customer.Beneficiary;
import com.example.finalassingment.model.customer.PolicyOwner;
import com.example.finalassingment.model.items.InsuranceCard;
import com.example.finalassingment.repository.impl.InsuranceCardRepository;
import com.example.finalassingment.service.InsuranceCardService;

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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/form/addInsuranceCardForm.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            VBox rootPane = fxmlLoader.load();
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
        Role role = GlobalVariable.getRole();
        if (role == Role.Admin) {
            new SelectBeneficiaryTable(CustomerQueryType.QueryType.GET_ALL_DEPENDANT_AND_POLICY_HOLDER, this);
        } else new SelectBeneficiaryTable(CustomerQueryType.QueryType.GET_ALL_BENEFICIARY_OF_POLICY_OWNER, this);
    }

    private void addInsuranceCard(ActionEvent actionEvent) {
        InsuranceCardService service = new InsuranceCardService();
        InsuranceCardRepository repository = new InsuranceCardRepository();
        InsuranceCard card = service.makeCard()
                .cardNumber(cardNumberField.getText())
                .expireDate(expireDatePicker.getValue())
                .cardHolder(beneficiary)
                .policyOwner(beneficiary.getPolicyOwner())
                .build();

        repository.add(card);
        repository.close();
        controller.refresh();
        new SuccessAlert("Insurance card added successfully");
        close();
    }

    private void close() {
        stage.close();
    }

    @Override
    public void setBeneficiary(Beneficiary beneficiary) {
        this.beneficiary = beneficiary;
        displayBeneficiary();
    }

    private void displayBeneficiary() {
        this.cardHolderLabel.setText(beneficiary.getId() + " - " + beneficiary.getFullName());
        PolicyOwner policyOwner = beneficiary.getPolicyOwner();
        if (policyOwner == null) {
            policyOwnerLabel.setText("No policy owner");
            return;
        }
        this.policyOwnerLabel.setText(policyOwner.getId() + " - " + policyOwner.getFullName());
    }
}
