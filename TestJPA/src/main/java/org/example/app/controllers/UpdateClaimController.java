package org.example.app.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.model.items.Claim;
import org.example.model.enums.ClaimStatus;

import java.time.LocalDate;

public class UpdateClaimController {

    @FXML
    private TextField claimIdTextField;

    @FXML
    private TextField insuredPersonIdTextField;

    @FXML
    private TextField cardNumberTextField;

    @FXML
    private DatePicker claimDatePicker;

    @FXML
    private DatePicker examDatePicker;

    @FXML
    private TextField claimAmountTextField;

    @FXML
    private ComboBox<String> claimStatusComboBox;

    @FXML
    private TextField bankingInfoTextField;

    private Claim claim;
    private Stage stage;

    public void initialize() {
        // Initialize fields with claim data
        claimIdTextField.setText(claim.getId());
        insuredPersonIdTextField.setText(String.valueOf(claim.getInsuredPerson().getId()));
        cardNumberTextField.setText(claim.getCardNumber());
        claimDatePicker.setValue(claim.getClaimDate());
        examDatePicker.setValue(claim.getExamDate());
        claimAmountTextField.setText(Double.toString(claim.getClaimAmount()));
        claimStatusComboBox.setValue(claim.getStatus().toString());
        bankingInfoTextField.setText(claim.getBankingInfo());
    }

    public void setClaim(Claim claim) {
        this.claim = claim;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void updateInformation() {
        // Update the claim with new values from fields
        double newClaimAmount = Double.parseDouble(claimAmountTextField.getText());
        LocalDate newClaimDate = claimDatePicker.getValue();
        LocalDate newExamDate = examDatePicker.getValue();
        ClaimStatus newStatus = ClaimStatus.valueOf(claimStatusComboBox.getValue());
        String newBankingInfo = bankingInfoTextField.getText();

        // Set updated values to the claim
        claim.setClaimAmount(newClaimAmount);
        claim.setClaimDate(newClaimDate);
        claim.setExamDate(newExamDate);
        claim.setStatus(newStatus);
        claim.setBankingInfo(newBankingInfo);

        // Close the popup window
        stage.close();
    }
}
