package com.example.finalassingment.app.components.form;

import com.example.finalassingment.app.components.alert.SuccessAlert;
import com.example.finalassingment.utility.InputValidator;
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
import com.example.finalassingment.model.customer.PolicyOwner;
import com.example.finalassingment.repository.impl.UserRepository;
import com.example.finalassingment.director.CustomerDirector;
import com.example.finalassingment.utility.PasswordUtil;

import java.io.IOException;

public class AddPolicyOwnerForm extends BorderPane {
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
    private TextField feeField;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    private Stage stage;
    private RefreshableController controller;

    public AddPolicyOwnerForm(RefreshableController controller) {
        this.controller = controller;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/form/addPolicyOwnerForm.fxml"));
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
        this.saveButton.setOnAction(this::addPolicyOwner);
        this.cancelButton.setOnAction(this::handleCancel);
    }

    private void addPolicyOwner(ActionEvent actionEvent) {
        if (validateInput()) {
            UserRepository repository = new UserRepository();
            CustomerDirector customerDirector = new CustomerDirector();
            try {

                String username = usernameField.getText();
                String password = passwordField.getText();
                String hashedPassword = PasswordUtil.encrypt(password);

                if (repository.findUser(username, hashedPassword) != null) {
                    new ErrorAlert("This username is already taken!");
                    return;
                }

                PolicyOwner policyOwner = customerDirector.makePolicyOwner()
                        .fullName(nameField.getText())
                        .username(username)
                        .address(addressField.getText())
                        .email(emailField.getText())
                        .phone(phoneField.getText())
                        .password(password)
                        .fee(Double.parseDouble(feeField.getText()))
                        .build();

                repository.add(policyOwner);
                repository.close();
                close();
                new SuccessAlert("Policy Owner added successfully!");
                controller.refresh(); // Refresh the table after adding
            } catch (NumberFormatException e) {
                new ErrorAlert("Fee must be a valid number");
            }
        }
    }

    private boolean validateInput() {
        InputValidator validator = new InputValidator();
        if (validator.isEmpty(usernameField, passwordField, nameField,
                addressField, phoneField, emailField, feeField)) {
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

        if (!validator.isDouble(feeField)) {
            new ErrorAlert("Invalid fee, please enter a number");
            return false;
        }

        return true;
    }

    private void handleCancel(ActionEvent actionEvent) {
        close();
    }
    private void close() {
        stage.close();
    }
}
