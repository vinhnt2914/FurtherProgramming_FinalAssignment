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
import java.util.regex.Pattern;

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
            close();
        }
    }

    private void close() {
        stage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (isFieldEmpty(addressField)) {
            errorMessage += "No valid address!\n";
        }
        if (isFieldEmpty(emailField) || !isValidEmail(emailField.getText())) {
            errorMessage += "No valid email!\n";
        }
        if (isFieldEmpty(phoneField)) {
            errorMessage += "No valid phone!\n";
        }
        if (isFieldEmpty(passwordField)) {
            errorMessage += "No valid password!\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            showAlert("Invalid Fields", "Please correct invalid fields", errorMessage);
            return false;
        }
    }

    private boolean isFieldEmpty(TextField field) {
        return field.getText() == null || field.getText().trim().isEmpty();
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
