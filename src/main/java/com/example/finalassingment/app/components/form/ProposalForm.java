package com.example.finalassingment.app.components.form;
/**
 * @author Group 11
 */
import com.example.finalassingment.app.components.alert.SuccessAlert;
import com.example.finalassingment.repository.impl.ClaimRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.finalassingment.app.components.alert.ErrorAlert;
import com.example.finalassingment.global.GlobalVariable;
import com.example.finalassingment.model.items.Claim;
import com.example.finalassingment.model.items.Proposal;
import com.example.finalassingment.model.provider.InsuranceManager;
import com.example.finalassingment.model.provider.InsuranceSurveyor;
import com.example.finalassingment.repository.impl.ProposalRepository;
import org.hibernate.exception.ConstraintViolationException;

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

            ClaimRepository claimRepository = new ClaimRepository();

            if (claimRepository.findByID(claim.getId()) == null) {
                new ErrorAlert("There's already a proposal for this claim!");
                claimRepository.close();
                return;
            }

            ProposalRepository proposalRepository = new ProposalRepository();
            InsuranceSurveyor surveyor = (InsuranceSurveyor) GlobalVariable.getUser();
            InsuranceManager manager = surveyor.getManager();
            String message = messageArea.getText();
            if (message.isEmpty()) message = "Nothing";
            Proposal proposal = new Proposal(surveyor, claim, manager, message);
            try {
                proposalRepository.add(proposal);
                proposalRepository.close();
                new SuccessAlert("Proposal filed successfully");
            } catch (ConstraintViolationException e) {
                new ErrorAlert("There's already a proposal with this claim!");
            }
        }
    }


    private boolean validateInput() {
        if (isEmpty(messageArea)) {
            new ErrorAlert("Please fill out all fields");
            return false;
        }

        return true;
    }

    private boolean isEmpty(TextArea field) {
        return field.getText() == null || field.getText().trim().isEmpty();
    }

}
