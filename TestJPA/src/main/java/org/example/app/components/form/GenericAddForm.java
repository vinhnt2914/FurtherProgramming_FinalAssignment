package org.example.app.components.form;
/**
 * @author Group 11
 */
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.app.components.alert.ErrorAlert;
import org.example.app.controllers.RefreshableController;

import java.io.IOException;
import java.util.regex.Pattern;

public abstract class GenericAddForm extends BorderPane {
    @FXML
    protected Label titleLabel;
    @FXML
    protected TextField nameField, usernameField, addressField, emailField, phoneField, passwordField;
    @FXML private Button saveButton, cancelButton;
    private Stage stage;
    protected RefreshableController controller;

    public GenericAddForm(RefreshableController controller) {
        this.controller = controller;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/form/addForm.fxml"));
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
        modifyForm();

        System.out.println("ADD FORM, CONTROLLER IS: " + controller);
    }
    private void setUpForm() {
        this.saveButton.setOnAction(this::add);
        this.cancelButton.setOnAction(this::cancel);
    }

    abstract void modifyForm();
    private void cancel(ActionEvent actionEvent) {
        close();
    }
    abstract void add(ActionEvent actionEvent);
    protected void close() {
        stage.close();
    }

    protected boolean validateInput() {
        if (isFieldEmpty(nameField) || isFieldEmpty(usernameField) || isFieldEmpty(addressField) ||
                isFieldEmpty(emailField) || isFieldEmpty(phoneField) || isFieldEmpty(passwordField)) {
            new ErrorAlert("All fields must be filled out.");
            return false;
        }

        if (!isValidEmail(emailField.getText())) {
            new ErrorAlert("Please enter a valid email address.");
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
}
