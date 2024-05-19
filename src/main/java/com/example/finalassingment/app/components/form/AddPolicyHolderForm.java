package com.example.finalassingment.app.components.form;

import com.example.finalassingment.app.components.alert.SuccessAlert;
import com.example.finalassingment.utility.InputValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.finalassingment.app.components.alert.ErrorAlert;
import com.example.finalassingment.app.components.table.SelectPolicyOwnerTable;
import com.example.finalassingment.app.controllers.RefreshableController;
import com.example.finalassingment.global.CustomerQueryType;
import com.example.finalassingment.global.GlobalVariable;
import com.example.finalassingment.global.Role;
import com.example.finalassingment.model.User;
import com.example.finalassingment.model.customer.PolicyHolder;
import com.example.finalassingment.model.customer.PolicyOwner;
import com.example.finalassingment.repository.impl.UserRepository;
import com.example.finalassingment.service.CustomerService;
import com.example.finalassingment.utility.PasswordUtil;

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
    private VBox policyOwnerContainer;
    @FXML
    private Button selectPolicyOwnerButton;
    @FXML
    private Label policyOwnerLabel;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    private Stage stage;
    private RefreshableController controller;
    private PolicyOwner policyOwner;

    public AddPolicyHolderForm(RefreshableController controller) {
        this.controller = controller;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/form/addPolicyHolderForm.fxml"));
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
        User user = GlobalVariable.getUser();
        if (user instanceof PolicyOwner) {
            policyOwnerContainer.getChildren().remove(selectPolicyOwnerButton);
            policyOwnerLabel.setText(user.getId() + " - " + user.getFullName());
            this.policyOwner = (PolicyOwner) user;
        }

        this.selectPolicyOwnerButton.setOnAction(this::openSelectPolicyOwner);
        this.saveButton.setOnAction(this::addPolicyHolder);
        this.cancelButton.setOnAction(this::handleCancel);
    }

    private void openSelectPolicyOwner(ActionEvent actionEvent) {
        new SelectPolicyOwnerTable(CustomerQueryType.QueryType.GET_ALL_POLICY_OWNER, this);
    }

    void addPolicyHolder(ActionEvent actionEvent) {
        if (validateInput()) {
            UserRepository repository = new UserRepository();
            CustomerService customerService = new CustomerService();

            try {
                String username = usernameField.getText();
                String password = passwordField.getText();
                String hashedPassword = PasswordUtil.encrypt(password);

                if (repository.findUser(username, hashedPassword) != null) {
                    new ErrorAlert("This username is already taken!");
                    return;
                }

                PolicyHolder policyHolder = customerService.makePolicyHolder()
                        .fullName(nameField.getText())
                        .username(username)
                        .address(addressField.getText())
                        .email(emailField.getText())
                        .phone(phoneField.getText())
                        .password(password)
                        .build();

                policyHolder.setPolicyOwner(policyOwner);
                repository.add(policyHolder);
                repository.close();
                close();
                new SuccessAlert("Policy Holder added successfully!");
                controller.refresh();
            } catch (NumberFormatException e) {
                new ErrorAlert("Please enter valid input values.");
            } finally {
                repository.close();
            }
        }
    }

    private boolean validateInput() {
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

        if (isPolicyOwnerNull()) {
            new ErrorAlert("Please select a policy owner");
            return false;
        }

        return true;
    }

    private boolean isPolicyOwnerNull() {
        if (GlobalVariable.getRole() == Role.PolicyOwner) return false;
        return policyOwner == null;
    }

    private void handleCancel(ActionEvent actionEvent) {
        close();
    }

    private void close() {
        stage.close();
    }

    public void setPolicyOwner(PolicyOwner policyOwner) {
        this.policyOwner = policyOwner;
        policyOwnerLabel.setText(policyOwner.getId() + " - " + policyOwner.getFullName());
    }
}
