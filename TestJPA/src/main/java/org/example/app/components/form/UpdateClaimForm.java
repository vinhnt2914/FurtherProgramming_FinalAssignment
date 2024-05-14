package org.example.app.components.form;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.app.components.alert.ErrorAlert;
import org.example.model.customer.Beneficiary;
import org.example.model.items.Claim;
import org.example.repository.impl.ClaimRepository;
import org.example.repository.impl.CustomerRepository;

import java.io.IOException;

public class UpdateClaimForm extends BorderPane {
    @FXML
    private Label insuredPersonLabel;
    @FXML
    private DatePicker claimDatePicker;
    @FXML
    private DatePicker examDatePicker;
    @FXML
    private TextField claimAmountField;
    @FXML
    private TextField bankingInfoField;
    @FXML
    private Button updateButton;
    private Claim selectedClaim;
    private Beneficiary selectedInsuredPerson;
    private Stage stage;

    public UpdateClaimForm(Claim claim) {
        this.selectedClaim = claim;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/updateClaimForm.fxml"));
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
        Beneficiary insuredPerson = selectedClaim.getEntireInsuredPerson();
        insuredPersonLabel.setText(insuredPerson.getId() + " - " + insuredPerson.getFullName());
        claimDatePicker.setValue(selectedClaim.getClaimDate());
        examDatePicker.setValue(selectedClaim.getExamDate());
        bankingInfoField.setText(selectedClaim.getBankingInfo());
        this.updateButton.setOnAction(this::updateClaim);
    }

    private void updateClaim(ActionEvent actionEvent) {
        if (isInputValid()) {
            ClaimRepository repository = new ClaimRepository();

            selectedClaim.setInsuredPerson(selectedInsuredPerson);
            selectedClaim.setClaimDate(claimDatePicker.getValue());
            selectedClaim.setExamDate(examDatePicker.getValue());
            selectedClaim.setBankingInfo(bankingInfoField.getText());

            repository.update(selectedClaim);
            repository.close();
        }

        close();
    }

    public void setSelectedInsuredPerson(Beneficiary insuredPerson) {
        this.selectedInsuredPerson = insuredPerson;
    }

    private void close() {
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (insuredPersonLabel.getText().isEmpty()) {
            errorMessage += "Invalid insured person!\n";
        }
        if (claimDatePicker.getValue() == null) {
            errorMessage += "Invalid claim date!\n";
        }
        if (examDatePicker.getValue() == null) {
            errorMessage += "Invalid exam date!\n";
        }
        if (bankingInfoField.getText().isEmpty()) {
            errorMessage += "Invalid banking info!\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            new ErrorAlert(errorMessage);
            return false;
        }
    }
}
