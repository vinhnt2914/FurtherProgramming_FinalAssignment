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
import org.example.app.components.alert.ErrorAlert;
import org.example.app.controllers.RefreshableController;
import org.example.global.GlobalVariable;
import org.example.global.Role;
import org.example.model.customer.PolicyHolder;
import org.example.model.customer.PolicyOwner;
import org.example.model.provider.InsuranceManager;
import org.example.model.provider.InsuranceSurveyor;
import org.example.repository.impl.ProviderRepository;
import org.example.service.ProviderService;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

public class AddSurveyorForm extends BorderPane{
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
    private ComboBox<InsuranceManager> managerComboBox;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    private Stage stage;
    private RefreshableController controller;

    public AddSurveyorForm(RefreshableController controller) {
        this.controller = controller;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/form/addSurveyorForm.fxml"));
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
        loadManagers();
    }

    private void setUpForm() {
        this.saveButton.setOnAction(this::addSurveyor);
        this.cancelButton.setOnAction(this::handleCancel);
    }

    private void loadManagers() {
        ProviderRepository repository = new ProviderRepository();
        List<InsuranceManager> managers = repository.getAllManager();
        ObservableList<InsuranceManager> managerList = FXCollections.observableArrayList(managers);
        managerComboBox.setItems(managerList);
    }

    private void addSurveyor(ActionEvent actionEvent) {
        if (validateInput()) {
            ProviderRepository repository = new ProviderRepository();
            ProviderService service = new ProviderService();

            InsuranceSurveyor surveyor = service.makeSurveyor()
                    .fullName(nameField.getText())
                    .username(usernameField.getText())
                    .address(addressField.getText())
                    .email(emailField.getText())
                    .phone(phoneField.getText())
                    .password(passwordField.getText())
                    .manager(managerComboBox.getValue())
                    .build();

            repository.add(surveyor);

            controller.refresh();
            repository.close();

            close();
        }
    }

    private boolean validateInput() {
        if (isFieldEmpty(nameField) || isFieldEmpty(usernameField) || isFieldEmpty(addressField) ||
                isFieldEmpty(emailField) || isFieldEmpty(phoneField) || isFieldEmpty(passwordField) ||
                isManagerNull()) {
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

    private boolean isManagerNull() {
        return managerComboBox.getValue() == null;
    }

    private void handleCancel(ActionEvent actionEvent) {
        close();
    }

    private void close() {
        stage.close();
    }
}
