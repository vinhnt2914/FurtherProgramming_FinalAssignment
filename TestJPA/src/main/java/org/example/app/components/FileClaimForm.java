package org.example.app.components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.model.customer.Beneficiary;
import org.example.model.customer.Customer;
import org.example.model.customer.Dependant;
import org.example.model.enums.ClaimStatus;
import org.example.model.items.Claim;
import org.example.repository.impl.ClaimRepository;
import org.example.repository.impl.CustomerRepository;
import org.example.service.ClaimService;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class FileClaimForm implements Initializable {
    public Label insuredPersonLabel;
    public Label cardNumberLabel;
    @FXML
    private Button submitButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField idField;
    @FXML
    private Button selectInsuredPersonButton;
    @FXML
    private DatePicker claimDatePicker;
    @FXML
    private DatePicker examDatePicker;
    @FXML
    private TextField claimAmountField;
    @FXML
    private ComboBox<String> statusComboBox;
    @FXML
    private TextField bankingInfoField;
    private ClaimService claimService;
    private Dependant insuredPerson;
    private CustomerRepository customerRepository;
    private ClaimRepository claimRepository;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerRepository = new CustomerRepository();
        claimRepository = new ClaimRepository();
        claimService = new ClaimService();
        setUpForm();
    }

    private void setUpForm() {
        ObservableList<String> statusOptions = FXCollections.observableArrayList("NEW", "PROCESSING", "DONE");
        this.statusComboBox.setItems(statusOptions);
        this.selectInsuredPersonButton.setOnAction(this::openSelectInsuredPerson);
        this.submitButton.setOnAction(this::fileClaim);
    }

    private void fileClaim(ActionEvent actionEvent) {
        String id = idField.getText();
        LocalDate claimDate = claimDatePicker.getValue();
        LocalDate examDate = examDatePicker.getValue();
        double claimAmount = Double.parseDouble(claimAmountField.getText());
        ClaimStatus status = ClaimStatus.valueOf(statusComboBox.getValue());
        String bankingInfo = bankingInfoField.getText();
        Claim claim = claimService.makeClaim()
                .id(id)
                .insuredPerson(insuredPerson)
                .claimDate(claimDate)
                .examDate(examDate)
                .claimAmount(claimAmount)
                .status(status)
                .bankingInfo(bankingInfo)
                .build();
        claimRepository.add(claim);
        claimRepository.close();
    }

    private void openSelectInsuredPerson(ActionEvent actionEvent) {
        System.out.println("HI");
        new SelectInsuredPersonTable(this);
        System.out.println("HELLO");
    }

    public void setInsuredPerson(Dependant insuredPerson) {
        this.insuredPerson = insuredPerson;
        insuredPersonLabel.setText(
                String.format("%s - %s",
                        insuredPerson.getId(),
                        insuredPerson.getFullName()));
        cardNumberLabel.setText(insuredPerson.getInsuranceCard().getCardNumber());
    }
}
