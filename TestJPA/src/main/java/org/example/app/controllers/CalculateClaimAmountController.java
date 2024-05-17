package org.example.app.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import org.example.model.enums.ClaimStatus;
import org.example.repository.IClaimRepository;

import java.math.BigDecimal;

public class CalculateClaimAmountController {

    @FXML
    private ComboBox<String> statusComboBox;

    @FXML
    private Label resultLabel;

    private IClaimRepository claimRepository;

    // No-argument constructor for FXMLLoader
    public CalculateClaimAmountController() {}

    public CalculateClaimAmountController(IClaimRepository claimRepository) {
        this.claimRepository = claimRepository;
    }

    @FXML
    public void initialize() {
        statusComboBox.getItems().addAll("NEW", "PROCESSING", "DONE");
    }

    @FXML
    public void handleCalculate(ActionEvent event) {
        String selectedStatus = statusComboBox.getValue();
        if (selectedStatus != null) {
            ClaimStatus status = ClaimStatus.valueOf(selectedStatus);
            BigDecimal totalAmount = claimRepository.getTotalClaimAmountByStatus(status);
            resultLabel.setText("Total Claim Amount: " + totalAmount.toString());
        } else {
            resultLabel.setText("Please select a status.");
        }
    }

    public void setClaimRepository(IClaimRepository claimRepository) {
        this.claimRepository = claimRepository;
    }
}
