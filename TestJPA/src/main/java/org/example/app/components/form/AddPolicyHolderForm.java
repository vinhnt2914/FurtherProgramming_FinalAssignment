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
import org.example.app.controllers.RefreshableController;
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
    private ComboBox<PolicyOwner> policyOwnerComboBox;
    @FXML
    private Label policyOwnerLabel;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    private Stage stage;
    private RefreshableController controller;

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

        policyOwnerLabel.setVisible(false);

        User user = GlobalVariable.getUser();
        if (user instanceof PolicyOwner) {
            policyOwnerContainer.getChildren().remove(policyOwnerComboBox);
            policyOwnerLabel.setVisible(true);
            policyOwnerLabel.setText(user.getId() + " - " + user.getFullName());
        } else {
            setUpPolicyOwnerComboBox();
        }

        this.saveButton.setOnAction(this::addPolicyHolder);
        this.cancelButton.setOnAction(this::handleCancel);
    }

    private void setUpPolicyOwnerComboBox() {
        policyOwnerComboBox.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(PolicyOwner item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.getId() + " - " + item.getFullName());
                } else {
                    setText(null);
                }
            }
        });

        policyOwnerComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(PolicyOwner item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.getId() + " - " + item.getFullName());
                } else {
                    setText(null);
                }
            }
        });

        loadPolicyOwners();

    }

    private void loadPolicyOwners() {
        CustomerRepository repository = new CustomerRepository();
        List<PolicyOwner> policyOwnerList = repository.getAllPolicyOwners();
        ObservableList<PolicyOwner> data = FXCollections.observableArrayList(policyOwnerList);
        policyOwnerComboBox.setItems(data);
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

            if (GlobalVariable.getRole() == Role.PolicyOwner) {
                policyHolder.setPolicyOwner((PolicyOwner) GlobalVariable.getUser());
            } else if (GlobalVariable.getRole() == Role.Admin){
                System.out.println("READ VALUE: " + policyOwnerComboBox.getValue());
                PolicyOwner policyOwner = policyOwnerComboBox.getValue();
                if (policyOwner != null) {
                    System.out.println("SELECTED POLICY OWNER: " + policyOwner);
                    policyHolder.setPolicyOwner(policyOwner);
                } else {
                    new ErrorAlert("Please select an policy owner");
                    return;
                }
            }

            repository.add(policyHolder);
            repository.close();

            // Dont put refresh after close(), or controller will be null
            controller.refresh();
            close();
        }
    }

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

    private void handleCancel(ActionEvent actionEvent) {
        close();
    }
    private void close() {
        stage.close();
    }





}
