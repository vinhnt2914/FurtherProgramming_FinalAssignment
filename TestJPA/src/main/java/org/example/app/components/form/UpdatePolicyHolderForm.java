package org.example.app.components.form;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.model.customer.PolicyHolder;
import org.example.repository.impl.CustomerRepository;

import java.io.IOException;

public class UpdatePolicyHolderForm extends BorderPane {
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
    private PolicyHolder selectedPolicyHolder;
    private Stage stage;

    public UpdatePolicyHolderForm(PolicyHolder policyHolder) {
        this.selectedPolicyHolder = policyHolder;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/updatePolicyHolderForm.fxml"));
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
        addressField.setText(selectedPolicyHolder.getAddress());
        emailField.setText(selectedPolicyHolder.getEmail());
        phoneField.setText(selectedPolicyHolder.getPhone());
        passwordField.setText(selectedPolicyHolder.getPassword());

        saveButton.setOnAction(this::updatePolicyHolder);
    }

    private void updatePolicyHolder(ActionEvent actionEvent) {

        if (isInputValid()) {
            CustomerRepository repository = new CustomerRepository();

            selectedPolicyHolder.setAddress(addressField.getText());
            selectedPolicyHolder.setEmail(emailField.getText());
            selectedPolicyHolder.setPhone(phoneField.getText());
            selectedPolicyHolder.setPassword(passwordField.getText());

            System.out.println("UPDATED DEPENDANT: " + selectedPolicyHolder);

            repository.update(selectedPolicyHolder);
            repository.close();
        }

        close();
    }

    private void close() {
        stage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (addressField.getText() == null || addressField.getText().isEmpty()) {
            errorMessage += "No valid address!\n";
        }
        if (emailField.getText() == null || emailField.getText().isEmpty()) {
            errorMessage += "No valid email!\n";
        }
        if (phoneField.getText() == null || phoneField.getText().isEmpty()) {
            errorMessage += "No valid phone!\n";
        }
        if (passwordField.getText() == null || passwordField.getText().isEmpty()) {
            errorMessage += "No valid password!\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();

            return false;
        }
    }
}
