package com.example.finalassingment.app.components.form;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import com.example.finalassingment.app.components.alert.ErrorAlert;
import com.example.finalassingment.app.controllers.RefreshableController;
import com.example.finalassingment.model.User;
import com.example.finalassingment.repository.impl.UserRepository;

import java.io.IOException;
import java.util.regex.Pattern;

public class UpdateInfoForm extends BorderPane {
    @FXML
    private TextField addressField, emailField, phoneField, passwordField;
    @FXML
    private Button saveButton, cancelButton;
    private User user;
    private Stage stage;
    private RefreshableController controller;

    public UpdateInfoForm(User user, RefreshableController controller) {
        this.controller = controller;
        this.user = user;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/form/updateInfoForm.fxml"));
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
        System.out.println("This is the current user: " + user);

        addressField.setText(user.getAddress());
        emailField.setText(user.getEmail());
        phoneField.setText(user.getPhone());
        saveButton.setOnAction(this::updateInfo);
        cancelButton.setOnAction(this::handleCancel);
    }

    private void handleCancel(ActionEvent actionEvent) {
        close();
    }

    private void updateInfo(ActionEvent actionEvent) {
        if (isInputValid()) {
            UserRepository repository = new UserRepository();
            user.setAddress(addressField.getText());
            user.setEmail(emailField.getText());
            user.setPhone(phoneField.getText());

            String password = passwordField.getText();
            if (!password.isEmpty()) {
                user.setPassword(passwordField.getText());
            }

            repository.update(user);
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
