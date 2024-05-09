package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.model.items.Claim;

public class UpdateClaimController {

    @FXML
    private TextField claimAmountTextField;

    @FXML
    private TextField bankingInfoTextField;

    private Claim claim;
    private Stage stage;

    public void initialize() {
        // Initialize text fields with claim data
        claimAmountTextField.setText(Double.toString(claim.getClaimAmount()));
        bankingInfoTextField.setText(claim.getBankingInfo());
    }

    public void setClaim(Claim claim) {
        this.claim = claim;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void updateClaim() {
        // Update the claim with new values from text fields
        try {
            double newClaimAmount = Double.parseDouble(claimAmountTextField.getText());
            String newBankingInfo = bankingInfoTextField.getText();

            // Update claim details
            claim.setClaimAmount(newClaimAmount);
            claim.setBankingInfo(newBankingInfo);

            // Close the popup window
            stage.close();
        } catch (NumberFormatException e) {
            // Handle invalid input for claim amount
            // You can show an error message here if needed
        }
    }
}
