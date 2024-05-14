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
import org.example.model.customer.Dependant;
import org.example.repository.impl.CustomerRepository;

import java.io.IOException;

public class UpdateDependantForm extends BorderPane {
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
    private Dependant selectedDependant;
    private Stage stage;
    public UpdateDependantForm(Dependant dependant) {
        this.selectedDependant = dependant;
        System.out.println("SELECTED DEPENDANT: " + dependant.getId());
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/updateDependantForm.fxml"));
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
        addressField.setText(selectedDependant.getAddress());
        emailField.setText(selectedDependant.getEmail());
        phoneField.setText(selectedDependant.getPhone());
        passwordField.setText(selectedDependant.getPassword());

        saveButton.setOnAction(this::updateDependant);
    }

    private void updateDependant(ActionEvent actionEvent) {

        if (isInputValid()) {
            CustomerRepository repository = new CustomerRepository();

            selectedDependant.setAddress(addressField.getText());
            selectedDependant.setEmail(emailField.getText());
            selectedDependant.setPhone(phoneField.getText());
            selectedDependant.setPassword(passwordField.getText());

            System.out.println("UPDATED DEPENDANT: " + selectedDependant);

            repository.update(selectedDependant);
            repository.close();
        }

        close();
    }

    private void close() {
        stage.close();
    }

    // Update this in the future
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
