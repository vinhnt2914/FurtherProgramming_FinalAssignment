package com.example.finalassingment.app.components.form;

import com.example.finalassingment.utility.InputValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import com.example.finalassingment.app.components.alert.ErrorAlert;
import com.example.finalassingment.app.components.table.SelectBeneficiaryTable;
import com.example.finalassingment.global.CustomerQueryType;
import com.example.finalassingment.global.GlobalVariable;
import com.example.finalassingment.global.Role;
import com.example.finalassingment.model.customer.Beneficiary;
import com.example.finalassingment.model.items.Claim;
import com.example.finalassingment.repository.impl.ClaimRepository;

import java.io.IOException;

public class UpdateClaimForm extends BorderPane implements SelectableForm{
    @FXML
    private Label insuredPersonLabel;
    @FXML
    private Label cardNumberLabel;
    @FXML
    private DatePicker claimDatePicker;
    @FXML
    private DatePicker examDatePicker;
    @FXML
    private TextField claimAmountField;
    @FXML
    private TextField bankingInfoField;
    @FXML
    private Button selectInsuredPersonButton;
    @FXML
    private Button updateButton;
    private Claim selectedClaim;
    private Beneficiary insuredPerson;
    private Stage stage;

    public UpdateClaimForm(Claim claim) {
        this.selectedClaim = claim;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/form/updateClaimForm.fxml"));
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
        Beneficiary insuredPerson = selectedClaim.getEntireInsuredPerson();
        insuredPersonLabel.setText(insuredPerson.getId() + " - " + insuredPerson.getFullName());
        claimDatePicker.setValue(selectedClaim.getClaimDate());
        examDatePicker.setValue(selectedClaim.getExamDate());
        claimAmountField.setText(String.valueOf(selectedClaim.getClaimAmount()));
        bankingInfoField.setText(selectedClaim.getBankingInfo());
        this.selectInsuredPersonButton.setOnAction(this::openSelectInsuredPerson);
        this.updateButton.setOnAction(this::updateClaim);
    }

    private void openSelectInsuredPerson(ActionEvent actionEvent) {
        Role role = GlobalVariable.getRole();
        switch (role) {
            case PolicyHolder ->
                    new SelectBeneficiaryTable(
                            CustomerQueryType.QueryType.GET_ALL_DEPENDANT_OF_POLICY_HOLDER,
                            this);
            case PolicyOwner ->
                    new SelectBeneficiaryTable(
                            CustomerQueryType.QueryType.GET_ALL_BENEFICIARY_OF_POLICY_OWNER,
                            this);
        }
    }

    private void updateClaim(ActionEvent actionEvent) {
        if (validateInput()) {
            ClaimRepository repository = new ClaimRepository();

            if (insuredPerson == null) {
                insuredPerson = selectedClaim.getEntireInsuredPerson();
            }

            selectedClaim.setInsuredPerson(insuredPerson);
            selectedClaim.setClaimDate(claimDatePicker.getValue());
            selectedClaim.setExamDate(examDatePicker.getValue());
            selectedClaim.setClaimAmount(Double.parseDouble(claimAmountField.getText()));
            selectedClaim.setBankingInfo(bankingInfoField.getText());

            repository.update(selectedClaim);
            repository.close();
        }

        close();
    }

    private void close() {
    }

    private boolean validateInput() {
        InputValidator validator = new InputValidator();
        if (validator.isEmpty(claimAmountField, bankingInfoField)) {
            new ErrorAlert("All fields must be filled out.");
            return false;
        }

        if (validator.isNull(claimDatePicker.getValue(), examDatePicker.getValue())) {
            new ErrorAlert("All fields must be filled out.");
            return false;
        }

        if (!validator.isDouble(claimAmountField)) {
            new ErrorAlert("Please enter a valid claim amount!");
            return false;
        }

        return true;
    }

    @Override
    public void setBeneficiary(Beneficiary beneficiary) {
        this.insuredPerson = beneficiary;
        insuredPersonLabel.setText(
                String.format("%s - %s",
                        insuredPerson.getId(),
                        insuredPerson.getFullName()));
        cardNumberLabel.setText(insuredPerson.getInsuranceCard().getCardNumber());
    }
}
