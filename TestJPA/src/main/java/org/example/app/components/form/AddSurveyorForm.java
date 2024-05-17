package org.example.app.components.form;


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
import org.example.app.components.alert.ErrorAlert;
import org.example.app.components.table.SelectManagerTable;
import org.example.app.components.table.SelectPolicyOwnerTable;
import org.example.app.controllers.RefreshableController;
import org.example.global.CustomerQueryType;
import org.example.global.GlobalVariable;
import org.example.global.ProviderQueryType;
import org.example.global.Role;
import org.example.model.customer.PolicyHolder;
import org.example.model.customer.PolicyOwner;
import org.example.model.provider.InsuranceManager;
import org.example.model.provider.InsuranceSurveyor;
import org.example.repository.impl.CustomerRepository;
import org.example.repository.impl.ProviderRepository;
import org.example.service.CustomerService;
import org.example.service.ProviderService;

import java.io.IOException;
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
    private VBox managerContainer;
    @FXML
    private Button selectManagerButton;
    @FXML
    private Label managerLabel;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    private Stage stage;
    private RefreshableController controller;
    private InsuranceManager manager;

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
    }

    private void setUpForm() {
        this.selectManagerButton.setOnAction(this::openSelectManager);
        this.saveButton.setOnAction(this::addSurveyor);
        this.cancelButton.setOnAction(this::handleCancel);
    }

    private void openSelectManager(ActionEvent actionEvent) {
        new SelectManagerTable(ProviderQueryType.QueryType.GET_ALL, this);
    }

    void addSurveyor(ActionEvent actionEvent) {
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
                    .manager(manager)
                    .build();

            repository.add(surveyor);
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
        return manager == null;
    }

    private void handleCancel(ActionEvent actionEvent) {
        close();
    }
    private void close() {
        stage.close();
    }

    public void setManager(InsuranceManager manager) {
        this.manager = manager;
        managerLabel.setText(manager.getId() + " - " + manager.getFullName());
    }
}
