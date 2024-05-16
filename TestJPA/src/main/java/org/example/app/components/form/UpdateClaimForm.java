package org.example.app.components.form;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.app.components.alert.ErrorAlert;
import org.example.app.components.table.InsuredPersonTable;
import org.example.global.CustomerQueryType;
import org.example.global.GlobalVariable;
import org.example.global.Role;
import org.example.model.customer.Beneficiary;
import org.example.model.items.Claim;
import org.example.repository.impl.ClaimRepository;

import java.io.IOException;

public class UpdateClaimForm extends BorderPane implements ClaimForm{
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
                    new InsuredPersonTable(
                            CustomerQueryType.QueryType.GET_ALL_DEPENDANT_OF_POLICY_HOLDER,
                            this);
            case PolicyOwner ->
                    new InsuredPersonTable(
                            CustomerQueryType.QueryType.GET_ALL_BENEFICIARY_OF_POLICY_OWNER,
                            this);
        }
    }

    private void updateClaim(ActionEvent actionEvent) {
        if (isInputValid()) {
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

    private boolean isInputValid() {
        String errorMessage = "";

        if (insuredPersonLabel.getText().isEmpty()) {
            errorMessage += "Invalid insured person!\n";
        }
        if (claimDatePicker.getValue() == null) {
            errorMessage += "Invalid claim date!\n";
        }
        if (examDatePicker.getValue() == null) {
            errorMessage += "Invalid exam date!\n";
        }
        if (claimAmountField.getText().isEmpty()) {
            errorMessage += "Invalid claim amount!\n";
        }
        if (bankingInfoField.getText().isEmpty()) {
            errorMessage += "Invalid banking info!\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            new ErrorAlert(errorMessage);
            return false;
        }
    }

    @Override
    public void setInsuredPerson(Beneficiary insuredPerson) {
        this.insuredPerson = insuredPerson;
        insuredPersonLabel.setText(
                String.format("%s - %s",
                        insuredPerson.getId(),
                        insuredPerson.getFullName()));
        cardNumberLabel.setText(insuredPerson.getInsuranceCard().getCardNumber());
    }
}
