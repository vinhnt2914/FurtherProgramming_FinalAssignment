package org.example.app.components.form;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import org.example.model.customer.PolicyHolder;
import org.example.model.enums.ClaimStatus;
import org.example.model.items.Claim;
import org.example.repository.impl.ClaimRepository;
import org.example.repository.impl.CustomerRepository;
import org.example.service.ClaimService;

import java.io.IOException;
import java.time.LocalDate;

public class FileClaimForm extends BorderPane implements ClaimForm{
    @FXML
    private Label insuredPersonLabel;
    @FXML
    private Label cardNumberLabel;
    @FXML
    private Button selectMyClaimButton;
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
    private Beneficiary insuredPerson;
    private CustomerRepository customerRepository;
    private ClaimRepository claimRepository;
    public FileClaimForm() {
        customerRepository = new CustomerRepository();
        claimRepository = new ClaimRepository();
        claimService = new ClaimService();
        loadFormFromFXML();
        setUpForm();
    }

    private void loadFormFromFXML() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/fileClaimForm.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            BorderPane rootPane = fxmlLoader.load();
            Scene scene = new Scene(rootPane);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }


    private void setUpForm() {
        Role role = GlobalVariable.getRole();
        ObservableList<String> statusOptions = FXCollections.observableArrayList("NEW", "PROCESSING", "DONE");
        this.statusComboBox.setItems(statusOptions);
        this.selectInsuredPersonButton.setOnAction(this::openSelectInsuredPerson);
        this.selectMyClaimButton.setOnAction(this::setToMyClaim);
        this.submitButton.setOnAction(this::fileClaim);
        this.cancelButton.setOnAction(this::handleCancel);
        if (role == Role.PolicyOwner) {
            selectMyClaimButton.setVisible(false);
        }
    }

    private void handleCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private void setToMyClaim(ActionEvent actionEvent) {
        PolicyHolder policyHolder = (PolicyHolder) customerRepository.findByID(GlobalVariable.getUserID());
        if (policyHolder != null) {
            setInsuredPerson(policyHolder);
        } else {
            new ErrorAlert("Data Retrieval Error");
        }
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

    @Override
    public void setInsuredPerson(Beneficiary insuredPerson) {
        this.insuredPerson = insuredPerson;

        insuredPersonLabel.setText(
                String.format("%s - %s",
                        insuredPerson.getId(),
                        insuredPerson.getFullName()));


        if (insuredPerson.getInsuranceCard() != null) {
            cardNumberLabel.setText(insuredPerson.getInsuranceCard().getCardNumber());
        } else {

            cardNumberLabel.setText("No insurance card available");
        }
    }

    private boolean validateInput() {
        if (isFieldEmpty(idField) || claimDatePicker.getValue() == null || examDatePicker.getValue() == null ||
                isFieldEmpty(claimAmountField) || statusComboBox.getValue() == null || isFieldEmpty(bankingInfoField)) {
            new ErrorAlert("All fields must be filled out.");
            return false;
        }

        if (!isValidAmount(claimAmountField.getText())) {
            new ErrorAlert("Please enter a valid claim amount.");
            return false;
        }

        return true;
    }

    private boolean isFieldEmpty(TextField field) {
        return field.getText() == null || field.getText().trim().isEmpty();
    }

    private boolean isValidAmount(String amount) {
        try {
            Double.parseDouble(amount);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
