package com.example.finalassingment.app.components.form;
/**
 * @author Group 11
 */
import com.example.finalassingment.app.components.alert.ErrorAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.finalassingment.global.GlobalVariable;
import com.example.finalassingment.model.customer.Beneficiary;
import com.example.finalassingment.model.items.Claim;
import com.example.finalassingment.model.items.Request;
import com.example.finalassingment.model.provider.InsuranceSurveyor;
import com.example.finalassingment.repository.impl.RequestRepository;
import org.hibernate.exception.ConstraintViolationException;

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
            Request request = new Request(insuranceSurveyor, claim.getEntireInsuredPerson(), claim, message);

            try {
                repository.add(request);
            } catch (ConstraintViolationException e) {
                new ErrorAlert("There's already a request with this claim!");
            } finally {
                repository.close();
            }
        }
    }

    private boolean validateInput() {
        if (isFieldEmpty(messageArea)) {
            new ErrorAlert("Message field must be filled out.");
            return false;
        }
        return true;
    }

    private boolean isFieldEmpty(TextInputControl field) {
        return field.getText() == null || field.getText().trim().isEmpty();
    }
}
