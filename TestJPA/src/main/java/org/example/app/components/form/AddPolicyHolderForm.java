package org.example.app.components.form;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.app.components.alert.ErrorAlert;
import org.example.app.components.table.SelectPolicyOwnerTable;
import org.example.app.controllers.RefreshableController;
import org.example.global.CustomerQueryType;
import org.example.global.GlobalVariable;
import org.example.global.Role;
import org.example.model.User;
import org.example.model.customer.PolicyHolder;
import org.example.model.customer.PolicyOwner;
import org.example.repository.impl.CustomerRepository;
import org.example.service.CustomerService;

import java.io.IOException;
import java.util.List;
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
            CustomerRepository repository = new CustomerRepository();
            CustomerService customerService = new CustomerService();

            PolicyHolder policyHolder = customerService.makePolicyHolder()
                    .fullName(nameField.getText())
                    .username(usernameField.getText())
                    .address(addressField.getText())
                    .email(emailField.getText())
                    .phone(phoneField.getText())
                    .password(passwordField.getText())
                    .build();

            policyHolder.setPolicyOwner(policyOwner);

            repository.add(policyHolder);
            repository.close();

            // Dont put refresh after close(), or controller will be null
            controller.refresh();
            close();
        }
    }

    private boolean validateInput() {
        if (isFieldEmpty(nameField) || isFieldEmpty(usernameField) || isFieldEmpty(addressField) ||
                isFieldEmpty(emailField) || isFieldEmpty(phoneField) || isFieldEmpty(passwordField) ||
                isPolicyOwnerNull()) {
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
