package org.example.app.components.form;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.app.components.alert.ErrorAlert;
import org.example.app.controllers.RefreshableController;
import org.example.model.customer.PolicyOwner;
import org.example.repository.impl.CustomerRepository;

import java.io.IOException;
import java.util.regex.Pattern;

public class UpdatePolicyOwnerForm extends BorderPane {
    @FXML
    private TextField addressField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    private PolicyOwner selectedPolicyOwner;
    private Stage stage;
    private RefreshableController controller;

    public UpdatePolicyOwnerForm(PolicyOwner policyOwner, RefreshableController controller) {
        this.selectedPolicyOwner = policyOwner;
        this.controller = controller; // Store the controller reference

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/updatePolicyOwnerForm.fxml"));
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

        setUpForm();
    }

    private void setUpForm() {
        addressField.setText(selectedPolicyOwner.getAddress());
        emailField.setText(selectedPolicyOwner.getEmail());
        phoneField.setText(selectedPolicyOwner.getPhone());
        passwordField.setText(selectedPolicyOwner.getPassword());
        saveButton.setOnAction(this::updatePolicyOwner);
        cancelButton.setOnAction(this::handleCancel);
    }

    private void updatePolicyOwner(ActionEvent actionEvent) {
        if (isInputValid()) {
            CustomerRepository repository = new CustomerRepository();

            selectedPolicyOwner.setAddress(addressField.getText());
            selectedPolicyOwner.setEmail(emailField.getText());
            selectedPolicyOwner.setPhone(phoneField.getText());
            selectedPolicyOwner.setPassword(passwordField.getText());

            repository.update(selectedPolicyOwner);
            repository.close();
            close();
            controller.refresh();
        }
    }

    private void handleCancel(ActionEvent actionEvent) {
        close();
    }

    private void close() {
        stage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (isFieldEmpty(addressField)) {
            errorMessage += "No valid address!\n";
        }
        if (isFieldEmpty(emailField) || !isValidEmail(emailField.getText())) {
            errorMessage += "No valid email!\n";
        }
        if (isFieldEmpty(phoneField)) {
            errorMessage += "No valid phone!\n";
        }
        if (isFieldEmpty(passwordField)) {
            errorMessage += "No valid password!\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            new ErrorAlert(errorMessage);
            return false;
        }
    }

    private boolean isFieldEmpty(TextField field) {
        return field.getText() == null || field.getText().trim().isEmpty();
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
}
