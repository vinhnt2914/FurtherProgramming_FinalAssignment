package com.example.finalassingment.app.components.form;

import com.example.finalassingment.app.components.alert.ErrorAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.finalassingment.app.components.alert.SuccessAlert;
import com.example.finalassingment.app.controllers.RefreshableController;
import com.example.finalassingment.model.customer.Beneficiary;
import com.example.finalassingment.model.customer.PolicyOwner;
import com.example.finalassingment.model.items.InsuranceCard;
import com.example.finalassingment.repository.impl.InsuranceCardRepository;

import java.io.IOException;

public class UpdateInsuranceCardForm extends VBox implements SelectableForm{
    @FXML
    private DatePicker expireDatePicker;
    @FXML
    private Label cardHolderLabel;
    @FXML
    private Label policyOwnerLabel;
    @FXML
    private Button submitButton;
    @FXML
    private Button cancelButton;
    private InsuranceCard selectedCard;
    private Beneficiary cardHolder;
    private Stage stage;
    private RefreshableController controller;

    public UpdateInsuranceCardForm(RefreshableController controller, InsuranceCard card) {
        this.selectedCard = card;
        this.cardHolder = card.getCardHolder();
        this.controller = controller;

        System.out.println("SET CARD: " + this.selectedCard);
        System.out.println("CARD NUMBER: " + this.selectedCard.getCardNumber());
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/form/updateInsuranceCardForm.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            VBox rootPane = fxmlLoader.load();
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
        expireDatePicker.setValue(selectedCard.getExpireDate());
        cardHolderLabel.setText(cardHolder.getId() + " - " + cardHolder.getFullName());

        if (cardHolder.getPolicyOwner() == null) policyOwnerLabel.setText("No policy owner");
        else {
            policyOwnerLabel.setText(
                    cardHolder.getPolicyOwner().getId()
                            + " - "
                            + cardHolder.getPolicyOwner().getFullName());
        }

        this.submitButton.setOnAction(this::updateInsuranceCard);
    }

    private void updateInsuranceCard(ActionEvent actionEvent) {
        if (expireDatePicker.getValue() == null) {
            new ErrorAlert("Please select an expire date");
            return;
        }

        InsuranceCardRepository repository = new InsuranceCardRepository();
        selectedCard.setExpireDate(expireDatePicker.getValue());
        repository.update(selectedCard);
        repository.close();
        controller.refresh();
        new SuccessAlert("Insurance Card Updated!");
        close();
    }

    private void close() {
        stage.close();
    }

    @Override
    public void setBeneficiary(Beneficiary beneficiary) {
        this.cardHolder = beneficiary;
        displayBeneficiary();
    }

    private void displayBeneficiary() {
        this.cardHolderLabel.setText(cardHolder.getId() + " - " + cardHolder.getFullName());
        PolicyOwner policyOwner = cardHolder.getPolicyOwner();
        if (policyOwner == null) {
            policyOwnerLabel.setText("No policy owner");
            return;
        }
        this.policyOwnerLabel.setText(policyOwner.getId() + " - " + policyOwner.getFullName());
    }
}
