package org.example.app.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.model.customer.PolicyHolder;
import org.example.repository.impl.CustomerRepository;

public class AddPolicyHolderFormController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField passwordField;

    private Stage dialogStage;
    private CustomerRepository customerRepository;

    public AddPolicyHolderFormController() {
        customerRepository = new CustomerRepository();
    }

    @FXML
    private void handleSave() {
        PolicyHolder policyHolder = new PolicyHolder();
        policyHolder.setFullName(nameField.getText());
        policyHolder.setUsername(usernameField.getText());
        policyHolder.setAddress(addressField.getText());
        policyHolder.setEmail(emailField.getText());
        policyHolder.setPhone(phoneField.getText());
        policyHolder.setPassword(passwordField.getText());

        customerRepository.add(policyHolder);
        dialogStage.close();
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}
