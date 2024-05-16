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
import org.example.global.GlobalVariable;
import org.example.model.customer.Customer;
import org.example.repository.impl.CustomerRepository;

import java.io.IOException;
import java.util.regex.Pattern;

public class UpdateInfoForm extends BorderPane {
    @FXML
    private TextField addressField, emailField, phoneField, passwordField;
    @FXML
    private Button saveButton, cancelButton;
    private Customer selectedCustomer;
    private Stage stage;
    private RefreshableController controller;

    public UpdateInfoForm(RefreshableController controller) {
        this.controller = controller;
        selectedCustomer = (Customer) GlobalVariable.getUser();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/form/updateDependantForm.fxml"));
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
        addressField.setText(selectedCustomer.getAddress());
        emailField.setText(selectedCustomer.getEmail());
        phoneField.setText(selectedCustomer.getPhone());
        passwordField.setText(selectedCustomer.getPassword());
        saveButton.setOnAction(this::updateInfo);
        cancelButton.setOnAction(this::handleCancel);
    }

    private void handleCancel(ActionEvent actionEvent) {
        close();
    }

    private void updateInfo(ActionEvent actionEvent) {
        if (isInputValid()) {
            CustomerRepository repository = new CustomerRepository();
            selectedCustomer.setAddress(addressField.getText());
            selectedCustomer.setEmail(emailField.getText());
            selectedCustomer.setPhone(phoneField.getText());
            selectedCustomer.setPassword(passwordField.getText());

            repository.update(selectedCustomer);
            repository.close();
            close();

            controller.refresh();
        }
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
