package org.example.app.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.example.model.customer.Dependant;
import org.example.model.customer.PolicyHolder;
import org.example.repository.impl.CustomerRepository;

import java.util.List;

public class UpdateDependantFormController {
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
    @FXML
    private ComboBox<PolicyHolder> policyHolderComboBox;

    private Stage dialogStage;
    private Dependant dependant;
    private CustomerRepository customerRepository;

    public void initialize() {
        customerRepository = new CustomerRepository();
        loadPolicyHolders();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setDependant(Dependant dependant) {
        this.dependant = dependant;

        nameField.setText(dependant.getFullName());
        usernameField.setText(dependant.getUsername());
        addressField.setText(dependant.getAddress());
        emailField.setText(dependant.getEmail());
        phoneField.setText(dependant.getPhone());
        passwordField.setText(dependant.getPassword());
        policyHolderComboBox.setValue(dependant.getPolicyHolder());
    }

    @FXML
    private void handleSave() {
        if (isInputValid()) {
            dependant.setFullName(nameField.getText());
            dependant.setUsername(usernameField.getText());
            dependant.setAddress(addressField.getText());
            dependant.setEmail(emailField.getText());
            dependant.setPhone(phoneField.getText());
            dependant.setPassword(passwordField.getText());
            dependant.setPolicyHolder(policyHolderComboBox.getValue());

            customerRepository.update(dependant);
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private void loadPolicyHolders() {
        List<PolicyHolder> policyHolders = customerRepository.getAllPolicyHolders();
        ObservableList<PolicyHolder> observablePolicyHolders = FXCollections.observableArrayList(policyHolders);
        policyHolderComboBox.setItems(observablePolicyHolders);
        policyHolderComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(PolicyHolder policyHolder) {
                return policyHolder != null ? policyHolder.getFullName() : "";
            }

            @Override
            public PolicyHolder fromString(String string) {
                return policyHolderComboBox.getItems().stream().filter(ph -> ph.getFullName().equals(string)).findFirst().orElse(null);
            }
        });
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (nameField.getText() == null || nameField.getText().length() == 0) {
            errorMessage += "No valid name!\n";
        }
        if (usernameField.getText() == null || usernameField.getText().length() == 0) {
            errorMessage += "No valid username!\n";
        }
        if (addressField.getText() == null || addressField.getText().length() == 0) {
            errorMessage += "No valid address!\n";
        }
        if (emailField.getText() == null || emailField.getText().length() == 0) {
            errorMessage += "No valid email!\n";
        }
        if (phoneField.getText() == null || phoneField.getText().length() == 0) {
            errorMessage += "No valid phone!\n";
        }
        if (passwordField.getText() == null || passwordField.getText().length() == 0) {
            errorMessage += "No valid password!\n";
        }
        if (policyHolderComboBox.getValue() == null) {
            errorMessage += "No valid policy holder selected!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();

            return false;
        }
    }
}
