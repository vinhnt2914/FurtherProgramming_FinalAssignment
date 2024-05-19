package com.example.finalassingment.app.components.form;

import com.example.finalassingment.app.components.alert.SuccessAlert;
import com.example.finalassingment.utility.InputValidator;
import jakarta.persistence.RollbackException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import com.example.finalassingment.app.components.alert.ErrorAlert;
import com.example.finalassingment.app.components.table.SelectBeneficiaryTable;
import com.example.finalassingment.app.controllers.RefreshableController;
import com.example.finalassingment.global.CustomerQueryType;
import com.example.finalassingment.global.GlobalVariable;
import com.example.finalassingment.global.Role;
import com.example.finalassingment.model.customer.Beneficiary;
import com.example.finalassingment.model.customer.PolicyHolder;
import com.example.finalassingment.model.enums.ClaimStatus;
import com.example.finalassingment.model.items.Claim;
import com.example.finalassingment.repository.impl.ClaimRepository;
import com.example.finalassingment.repository.impl.CustomerRepository;
import com.example.finalassingment.service.ClaimService;
import org.hibernate.exception.ConstraintViolationException;

import java.io.IOException;
import java.time.LocalDate;

public class FileClaimForm extends BorderPane implements SelectableForm{
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
    private RefreshableController controller;
    private Stage stage;
    public FileClaimForm(RefreshableController controller) {
        this.controller = controller;
        customerRepository = new CustomerRepository();
        claimRepository = new ClaimRepository();
        claimService = new ClaimService();
        loadFormFromFXML();
        setUpForm();
    }

    private void loadFormFromFXML() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/form/fileClaimForm.fxml"));
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
        close();
    }

    private void setToMyClaim(ActionEvent actionEvent) {
        PolicyHolder policyHolder = (PolicyHolder) GlobalVariable.getUser();
        if (policyHolder != null) {
            setBeneficiary(policyHolder);
        } else {
            new ErrorAlert("Data Retrieval Error");
        }
    }

    private void fileClaim(ActionEvent actionEvent) {
        if (validateInput()) {
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
            try {
                claimRepository.add(claim);
                claimRepository.close();
                close();
                new SuccessAlert("Claim filed successfully");
                controller.refresh();
            } catch (RollbackException e1) {
                new ErrorAlert("There's already a claim with this id!");
            }
        }
    }

    private void close() {
        stage.close();
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

    private boolean validateInput() {
        InputValidator validator = new InputValidator();
        if (validator.isEmpty(idField, claimAmountField, bankingInfoField)) {
            new ErrorAlert("All fields must be filled out.");
            return false;
        }

        if (validator.isNull(claimDatePicker.getValue(), examDatePicker.getValue(), statusComboBox.getValue())) {
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
        displayInsuredPerson();
    }
    private void displayInsuredPerson() {
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
}
