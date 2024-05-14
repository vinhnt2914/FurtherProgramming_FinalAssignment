package org.example.app.components.form;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.model.customer.PolicyHolder;
import org.example.repository.impl.CustomerRepository;
import org.example.service.CustomerService;

import java.io.IOException;
import java.util.regex.Pattern;

public class AddPolicyHolderForm extends BorderPane {
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
    private Button saveButton;
    @FXML
    private Button cancelButton;
    private Stage stage;

    public AddPolicyHolderForm() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/addPolicyHolderForm.fxml"));
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
        this.saveButton.setOnAction(this::handleSave);
        this.cancelButton.setOnAction(this::handleCancel);
    }

    private void handleSave(ActionEvent actionEvent) {
        if (validateInput()) {
            CustomerRepository repository = new CustomerRepository();
            CustomerService customerService = new CustomerService();

            PolicyHolder policyHolder = customerService.makePolicyHolder()
                    .fullName(nameField.getText())
                    .username(usernameField.getText())
                    .address(addressField.getText())
                    .email(emailField.getText())
                    .phone(phoneField.getText())
                    .password(passwordField.getText()).build();

            repository.add(policyHolder);
            repository.close();
            close();
        }
    }

    private void handleCancel(ActionEvent actionEvent) {
        close();
    }

    private boolean validateInput() {
        if (isFieldEmpty(nameField) || isFieldEmpty(usernameField) || isFieldEmpty(addressField) ||
                isFieldEmpty(emailField) || isFieldEmpty(phoneField) || isFieldEmpty(passwordField)) {
            showAlert("All fields must be filled out.");
            return false;
        }

        if (!isValidEmail(emailField.getText())) {
            showAlert("Please enter a valid email address.");
            return false;
        }

        return true;
    }

    private boolean isFieldEmpty(TextField field) {
        return field.getText() == null || field.getText().trim().isEmpty();
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void close() {
        stage.close();
    }
}
