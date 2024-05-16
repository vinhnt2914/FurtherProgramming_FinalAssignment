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
import org.example.model.customer.PolicyHolder;
import org.example.repository.impl.CustomerRepository;
import org.example.repository.impl.UserRepository;

import java.io.IOException;
import java.util.regex.Pattern;

public class UpdatePolicyHolderForm extends BorderPane {
    @FXML private TextField addressField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private TextField passwordField;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    private PolicyHolder selectedPolicyHolder;
    private Stage stage;
    private RefreshableController controller;  // Reference to the controller
    public UpdatePolicyHolderForm(PolicyHolder policyHolder, RefreshableController controller) {
        this.selectedPolicyHolder = policyHolder;
        this.controller = controller;  // Store the controller reference

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/form/updatePolicyHolderForm.fxml"));
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
        addressField.setText(selectedPolicyHolder.getAddress());
        emailField.setText(selectedPolicyHolder.getEmail());
        phoneField.setText(selectedPolicyHolder.getPhone());
        passwordField.setText(selectedPolicyHolder.getPassword());
        saveButton.setOnAction(this::updatePolicyHolder);
        cancelButton.setOnAction(this::handleCancel);
    }

    private void updatePolicyHolder(ActionEvent actionEvent) {
        if (isInputValid()) {
            UserRepository repository = new UserRepository();

            selectedPolicyHolder.setAddress(addressField.getText());
            selectedPolicyHolder.setEmail(emailField.getText());
            selectedPolicyHolder.setPhone(phoneField.getText());
            selectedPolicyHolder.setPassword(passwordField.getText());

            repository.update(selectedPolicyHolder);
            repository.close();
            close();
            controller.refresh();  // Refresh the table here
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
