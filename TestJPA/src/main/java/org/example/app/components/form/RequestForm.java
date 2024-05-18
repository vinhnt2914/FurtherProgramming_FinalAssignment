package org.example.app.components.form;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.global.GlobalVariable;
import org.example.model.customer.Beneficiary;
import org.example.model.items.Claim;
import org.example.model.items.Request;
import org.example.model.provider.InsuranceSurveyor;
import org.example.repository.impl.RequestRepository;

import java.io.IOException;

public class RequestForm extends VBox {
    @FXML
    private Label customerLabel;
    @FXML
    private TextArea messageArea;
    @FXML
    private Button submitButton;
    private Claim claim;

    public RequestForm(Claim claim) {
        this.claim = claim;
        loadFormFromFXML();
        setUpForm();
    }

    private void loadFormFromFXML() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/form/requestForm.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            VBox rootPane = fxmlLoader.load();
            Scene scene = new Scene(rootPane);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void setUpForm() {
        Beneficiary customer = claim.getEntireInsuredPerson();
        this.customerLabel.setText(customer.getId() + " - " + customer.getFullName());
        this.submitButton.setOnAction(this::submitRequest);
    }

    private void submitRequest(ActionEvent actionEvent) {
        if (validateInput()) {
            RequestRepository repository = new RequestRepository();
            String message = messageArea.getText();
            if (message.isEmpty()) message = "Nothing";
            InsuranceSurveyor insuranceSurveyor = (InsuranceSurveyor) GlobalVariable.getUser();
            Request request = new Request(insuranceSurveyor, claim.getEntireInsuredPerson(), message);
            repository.add(request);
            repository.close();
        }
    }

    private boolean validateInput() {
        if (isFieldEmpty(messageArea)) {
            showAlert("Message field must be filled out.");
            return false;
        }
        return true;
    }

    private boolean isFieldEmpty(TextInputControl field) {
        return field.getText() == null || field.getText().trim().isEmpty();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
