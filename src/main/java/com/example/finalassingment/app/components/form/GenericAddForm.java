package com.example.finalassingment.app.components.form;
/**
 * @author Group 11
 */
import com.example.finalassingment.utility.InputValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import com.example.finalassingment.app.components.alert.ErrorAlert;
import com.example.finalassingment.app.controllers.RefreshableController;
import java.io.IOException;

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
        InputValidator validator = new InputValidator();
        if (validator.isEmpty(usernameField, passwordField, nameField, addressField, phoneField, emailField)) {
            new ErrorAlert("All fields must be filled out.");
            return false;
        }

        if (!validator.validateEmail(emailField)) {
            new ErrorAlert("Please enter a valid email address.");
            return false;
        }

        if (!validator.validatePhoneNumber(phoneField)) {
            new ErrorAlert("Your phone number is not valid");
            return false;
        }

        return true;
    }
}
