package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class FileClaimController {

    @FXML
    private DatePicker claimDatePicker;

    @FXML
    private TextField claimAmountTextField;

    @FXML
    private void handleSubmission() {

        LocalDate claimDate = claimDatePicker.getValue();
        String claimAmount = claimAmountTextField.getText();


        // Close the pop-up window
        Stage stage = (Stage) claimAmountTextField.getScene().getWindow();
        stage.close();
    }
}
