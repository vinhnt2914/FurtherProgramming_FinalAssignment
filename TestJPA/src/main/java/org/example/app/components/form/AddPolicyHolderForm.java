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
import org.example.app.controllers.CustomerAdminController;
import org.example.app.controllers.RefreshableController;
import org.example.global.GlobalVariable;
import org.example.global.Role;
import org.example.model.customer.PolicyHolder;
import org.example.model.customer.PolicyOwner;
import org.example.repository.impl.CustomerRepository;
import org.example.service.CustomerService;

import java.io.IOException;
import java.util.regex.Pattern;

public class AddPolicyHolderForm extends GenericAddForm {
    @FXML private TextField nameField, usernameField, addressField, emailField, phoneField, passwordField;
    @FXML private Button saveButton, cancelButton;
    private Stage stage;
    private RefreshableController controller;

    public AddPolicyHolderForm(RefreshableController controller) {
        super(controller);
    }

//    public AddPolicyHolderForm(RefreshableController controller) {
//        this.controller = controller;
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/addPolicyHolderForm.fxml"));
//            fxmlLoader.setRoot(this);
//            fxmlLoader.setController(this);
//            BorderPane rootPane = fxmlLoader.load();
//            Scene scene = new Scene(rootPane);
//            stage = new Stage();
//            stage.setScene(scene);
//            stage.show();
//        } catch (IOException exception) {
//            throw new RuntimeException(exception);
//        }
//
//        setUpForm();
//    }

//    private void setUpForm() {
//        this.saveButton.setOnAction(this::addPolicyHolder);
//        this.cancelButton.setOnAction(this::handleCancel);
//    }

    @Override
    void add(ActionEvent actionEvent) {
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

            if (GlobalVariable.getRole() == Role.PolicyOwner) {
                policyHolder.setPolicyOwner((PolicyOwner) GlobalVariable.getUser());
            }

            repository.add(policyHolder);
            repository.close();
            close();
            controller.refresh(); // Refresh the table after adding
        }
    }

//    private void addPolicyHolder(ActionEvent actionEvent) {
//
//    }
//
//    private void handleCancel(ActionEvent actionEvent) {
//        close();
//    }

    private boolean validateInput() {
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
