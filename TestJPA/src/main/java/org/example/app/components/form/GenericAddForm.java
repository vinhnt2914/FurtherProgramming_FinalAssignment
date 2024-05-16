package org.example.app.components.form;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.app.controllers.RefreshableController;

import java.io.IOException;

public abstract class GenericAddForm extends BorderPane {
    @FXML
    private Label titleLabel;
    @FXML
    private TextField nameField, usernameField, addressField, emailField, phoneField, passwordField;
    @FXML private Button saveButton, cancelButton;
    private Stage stage;
    private RefreshableController controller;

    public GenericAddForm(RefreshableController controller) {
        this.controller = controller;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/form/addForm.fxml"));
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
        this.saveButton.setOnAction(this::add);
        this.cancelButton.setOnAction(this::cancel);
    }
    private void cancel(ActionEvent actionEvent) {
        close();
    }
    abstract void add(ActionEvent actionEvent);
    protected void close() {
        stage.close();
    }


}
