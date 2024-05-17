package org.example.app.components.form;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.app.controllers.RefreshableController;
import org.example.global.GlobalVariable;
import org.example.global.Role;
import org.example.model.User;
import org.example.model.customer.Dependant;
import org.example.model.customer.PolicyHolder;
import org.example.model.customer.PolicyOwner;
import org.example.repository.impl.CustomerRepository;
import org.example.repository.impl.UserRepository;
import org.example.service.CustomerService;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
                CustomerService customerService = new CustomerService();

                Dependant dependant = customerService.makeDependant()
                        .fullName(nameField.getText())
                        .username(usernameField.getText())
                        .address(addressField.getText())
                        .email(emailField.getText())
                        .phone(phoneField.getText())
                        .password(passwordField.getText()).build();

                dependant.setPolicyHolder(selectedPolicyHolder);

                // Handle policy owner for PolicyOwner role and Admin role
                if (GlobalVariable.getRole() == Role.PolicyOwner) {
                    dependant.setPolicyOwner((PolicyOwner) GlobalVariable.getUser());
                } else dependant.setPolicyOwner(dependant.getPolicyHolder().getPolicyOwner());

                UserRepository repository = new UserRepository();
                repository.add(dependant);
                repository.close();
                close();
                controller.refresh();  // Call to refresh the table
            } else {
                showAlert("Please select a policy holder.");
            }
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
        User currentUser = User.getCurrentUser();
        System.out.println("Current User: " + currentUser);
        if (currentUser instanceof PolicyOwner) {
            PolicyOwner policyOwner = (PolicyOwner) currentUser;
            System.out.println("Policy Owner: " + policyOwner.getFullName());
            List<PolicyHolder> policyHolders = repository.getAllPolicyHolders()
                    .stream()
                    .filter(ph -> ph.getPolicyOwner() != null && ph.getPolicyOwner().getId() == policyOwner.getId())
                    .collect(Collectors.toList());
            System.out.println("Filtered Policy Holders: " + policyHolders);
            ObservableList<PolicyHolder> policyHolderList = FXCollections.observableArrayList(policyHolders);
            policyHolderComboBox.setItems(policyHolderList);
        } else {

            List<PolicyHolder> policyHolders = repository.getAllPolicyHolders();
            System.out.println("All Policy Holders: " + policyHolders);
            ObservableList<PolicyHolder> policyHolderList = FXCollections.observableArrayList(policyHolders);
            policyHolderComboBox.setItems(policyHolderList);
        }
    }

}
