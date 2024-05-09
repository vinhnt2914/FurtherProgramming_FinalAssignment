package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.model.customer.Beneficiary;
import org.example.model.enums.ClaimStatus;
import org.example.model.items.Claim;
import org.example.service.ClaimService;

import java.time.LocalDate;

public class FileClaimController {

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

    @FXML
    private void handleSubmission() {
        // Get data from fields
        String claimId = claimIdTextField.getText();
        String insuredPersonId = insuredPersonIdTextField.getText();
        String cardNumber = cardNumberTextField.getText();
        LocalDate claimDate = claimDatePicker.getValue();
        LocalDate examDate = examDatePicker.getValue();
        double claimAmount = Double.parseDouble(claimAmountTextField.getText());
        ClaimStatus claimStatus = null;
        if (claimStatusComboBox.getValue() != null) {
            claimStatus = ClaimStatus.valueOf(claimStatusComboBox.getValue());
        }
        String bankingInfo = bankingInfoTextField.getText();

        // Find insured person by ID (you may have a repository method for this)
        Beneficiary insuredPerson = findInsuredPersonById(insuredPersonId);

        // Create new Claim object
        Claim claim = new Claim.ClaimBuilder()
                .id(claimId)
                .insuredPerson(insuredPerson)
                .cardNumber(cardNumber)
                .claimDate(claimDate)
                .examDate(examDate)
                .claimAmount(claimAmount)
                .status(claimStatus)
                .bankingInfo(bankingInfo)
                .build();

        // Save or process the claim, for example:
        // ClaimService claimService = new ClaimService();
        // claimService.addClaim(claim);

        // Close the pop-up window
        Stage stage = (Stage) claimAmountTextField.getScene().getWindow();
        stage.close();
    }

    // Method to find insured person by ID (you may need to implement this)
    private Beneficiary findInsuredPersonById(String id) {
        // Your implementation to find the insured person by ID
        return null;
    }
}
