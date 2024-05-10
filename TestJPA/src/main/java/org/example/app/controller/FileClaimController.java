package org.example.app.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.model.customer.Beneficiary;
import org.example.model.enums.ClaimStatus;
import org.example.model.items.Claim;
import org.example.repository.impl.ClaimRepository;
import org.example.repository.impl.CustomerRepository;
import org.example.service.ClaimService;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class FileClaimController implements Initializable {

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

    private ClaimService claimService;

    public void setClaimService(ClaimService claimService) {
        this.claimService = claimService;
    }

    @FXML
    private void handleSubmission() {
        // Get data from fields
        String claimId = claimIdTextField.getText();
        int insuredPersonId = Integer.parseInt(insuredPersonIdTextField.getText());
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
        Claim claim = claimService.makeClaim()
                .id(claimId)
                .insuredPerson(insuredPerson)
                .claimDate(claimDate)
                .examDate(examDate)
                .claimAmount(claimAmount)
                .status(claimStatus)
                .bankingInfo(bankingInfo)
                .build();

//        // Save the claim using ClaimService
        ClaimRepository claimRepository = new ClaimRepository();
        claimRepository.add(claim);
        claimRepository.close();


        // Close the pop-up window
        Stage stage = (Stage) claimAmountTextField.getScene().getWindow();
        stage.close();
    }

    // Method to find insured person by ID (you may need to implement this)
    private Beneficiary findInsuredPersonById(int id) {
        // Your implementation to find the insured person by ID
        CustomerRepository repository = new CustomerRepository();
        return (Beneficiary) repository.findByID(id);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        claimService = new ClaimService();
    }
}
