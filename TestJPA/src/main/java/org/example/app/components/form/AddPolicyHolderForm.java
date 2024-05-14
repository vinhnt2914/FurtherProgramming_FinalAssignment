package org.example.app.components.form;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.global.GlobalVariable;
import org.example.global.Role;
import org.example.model.customer.PolicyHolder;
import org.example.model.customer.PolicyOwner;
import org.example.repository.impl.CustomerRepository;
import org.example.service.CustomerService;

import java.io.IOException;

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
    private Button saveButton;
    private Stage stage;
    public AddPolicyHolderForm() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/addPolicyHolderForm.fxml"));
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
        this.saveButton.setOnAction(this::addPolicyHolder);
    }

    private void addPolicyHolder(ActionEvent actionEvent) {
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
    }

    private void close() {
        stage.close();
    }

}
