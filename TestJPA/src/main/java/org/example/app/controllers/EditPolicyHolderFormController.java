package org.example.app.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.model.customer.PolicyHolder;
import org.example.repository.impl.CustomerRepository;

public class EditPolicyHolderFormController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    private PolicyHolder policyHolder;
    private CustomerRepository customerRepository;
    private Stage dialogStage;

    public EditPolicyHolderFormController() {
        customerRepository = new CustomerRepository();
    }

    public void setPolicyHolder(PolicyHolder policyHolder) {
        this.policyHolder = policyHolder;
        nameField.setText(policyHolder.getFullName());
        addressField.setText(policyHolder.getAddress());
        emailField.setText(policyHolder.getEmail());
        phoneField.setText(policyHolder.getPhone());
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void initialize() {
        saveButton.setOnAction(event -> handleSave());
        cancelButton.setOnAction(event -> handleCancel());
    }

    @FXML
    private void handleSave() {
        policyHolder.setFullName(nameField.getText());
        policyHolder.setAddress(addressField.getText());
        policyHolder.setEmail(emailField.getText());
        policyHolder.setPhone(phoneField.getText());

        customerRepository.update(policyHolder);

        dialogStage.close();
    }


    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
}
