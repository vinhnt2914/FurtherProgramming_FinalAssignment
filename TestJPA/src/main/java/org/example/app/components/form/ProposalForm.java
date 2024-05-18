package org.example.app.components.form;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.app.components.alert.ErrorAlert;
import org.example.global.GlobalVariable;
import org.example.model.items.Claim;
import org.example.model.items.Proposal;
import org.example.model.provider.InsuranceManager;
import org.example.model.provider.InsuranceSurveyor;
import org.example.repository.impl.ProposalRepository;

import java.io.IOException;

public class ProposalForm extends VBox {
    @FXML
    private Label claimLabel;
    @FXML
    private Label managerLabel;
    @FXML
    private TextArea messageArea;
    @FXML
    private Button submitButton;
    private Claim claim;

    public ProposalForm(Claim claim) {
        this.claim = claim;
        loadFormFromFXML();
        setUpForm();
    }

    private void loadFormFromFXML() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/form/proposalForm.fxml"));
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
        InsuranceSurveyor surveyor = (InsuranceSurveyor) GlobalVariable.getUser();
        this.claimLabel.setText(claim.getId());
        this.managerLabel.setText(surveyor.getManager().getId() + " - " + surveyor.getManager().getFullName());
        this.submitButton.setOnAction(this::submitProposal);
    }


    private void submitProposal(ActionEvent actionEvent) {
        if (validateInput()) {
            ProposalRepository repository = new ProposalRepository();
            InsuranceSurveyor surveyor = (InsuranceSurveyor) GlobalVariable.getUser();
            InsuranceManager manager = surveyor.getManager();
            String message = messageArea.getText();
            if (message.isEmpty()) message = "Nothing";
            Proposal proposal = new Proposal(surveyor, claim, manager, message);
            repository.add(proposal);
            repository.close();
        }
    }


    private boolean validateInput() {
        if (isFieldEmpty(messageArea)) {
            new ErrorAlert("Please fill out all fields");
            return false;
        }

        return true;
    }

    private boolean isFieldEmpty(TextInputControl field) {
        return field.getText() == null || field.getText().trim().isEmpty();
    }

}
