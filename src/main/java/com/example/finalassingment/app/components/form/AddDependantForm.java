package com.example.finalassingment.app.components.form;
/**
 * @author Group 11
 */
import com.example.finalassingment.app.components.alert.SuccessAlert;
import com.example.finalassingment.utility.InputValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import com.example.finalassingment.app.components.alert.ErrorAlert;
import com.example.finalassingment.app.controllers.RefreshableController;
import com.example.finalassingment.global.GlobalVariable;
import com.example.finalassingment.model.Admin;
import com.example.finalassingment.model.User;
import com.example.finalassingment.model.customer.Dependant;
import com.example.finalassingment.model.customer.PolicyHolder;
import com.example.finalassingment.model.customer.PolicyOwner;
import com.example.finalassingment.repository.impl.CustomerRepository;
import com.example.finalassingment.repository.impl.UserRepository;
import com.example.finalassingment.service.CustomerService;
import com.example.finalassingment.utility.PasswordUtil;
import java.io.IOException;
import java.util.List;


public class AddDependantForm extends BorderPane {
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
    private ComboBox<PolicyHolder> policyHolderComboBox;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    private Stage stage;
    private RefreshableController controller;

    public AddDependantForm(RefreshableController controller) {
        this.controller = controller;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/form/addDependantForm.fxml"));
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
        loadPolicyHolders();
    }

    private void setUpForm() {
        setUpPolicyHolderComboBox();
        this.saveButton.setOnAction(this::handleSave);
        this.cancelButton.setOnAction(this::handleCancel);
    }

    private void handleSave(ActionEvent actionEvent) {
        if (validateInput()) {
            PolicyHolder selectedPolicyHolder = policyHolderComboBox.getValue();
            if (selectedPolicyHolder != null) {
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

                    Dependant dependant = customerService.makeDependant()
                            .fullName(nameField.getText())
                            .username(username)
                            .address(addressField.getText())
                            .email(emailField.getText())
                            .phone(phoneField.getText())
                            .password(password)
                            .build();

                    dependant.setPolicyHolder(selectedPolicyHolder);
                    repository.add(dependant);
                    repository.close();
                    close();
                    new SuccessAlert("Dependant added successfully!");
                    controller.refresh(); // Refresh the table after adding
                } catch (NumberFormatException e) {
                    new ErrorAlert("Please enter valid input values.");
                } finally {
                    repository.close();
                }
            } else {
                new ErrorAlert("Please select a policy holder.");
            }
        }
    }

    private void handleCancel(ActionEvent actionEvent) {
        close();
    }

    private boolean validateInput() {
        InputValidator validator = new InputValidator();
        if (validator.isEmpty(nameField, usernameField, addressField, emailField, phoneField, passwordField)) {
            new ErrorAlert("All fields must be filled out.");
            return false;
        }
        if (!validator.validateEmail(emailField)) {
            new ErrorAlert("Please enter a valid email address.");
            return false;
        }
        if (!validator.validatePhoneNumber(phoneField)) {
            new ErrorAlert("Not a valid phone number");
            return false;
        }
        return true;
    }

    private void close() {
        stage.close();
    }

    private void setUpPolicyHolderComboBox() {
        policyHolderComboBox.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(PolicyHolder item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.getId() + " - " + item.getFullName());
                } else {
                    setText(null);
                }
            }
        });

        // Set a custom button cell to display the selected policy holder's name
        policyHolderComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(PolicyHolder item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.getId() + " - " + item.getFullName());
                } else {
                    setText(null);
                }
            }
        });
    }

    private void loadPolicyHolders() {
        CustomerRepository repository = new CustomerRepository();
        List<PolicyHolder> policyHolders = null;
        User user = GlobalVariable.getUser();
        switch (user) {
            case PolicyOwner policyOwner ->
                    policyHolders = repository.getAllPolicyHoldersOfPolicyOwner(policyOwner);
            case Admin admin ->
                    policyHolders = repository.getAllPolicyHolders();
            default ->
                    new ErrorAlert("Something went wrong. Please try again");
        }
        ObservableList<PolicyHolder> policyHolderList = FXCollections.observableArrayList(policyHolders);
        policyHolderComboBox.setItems(policyHolderList);
    }
}
