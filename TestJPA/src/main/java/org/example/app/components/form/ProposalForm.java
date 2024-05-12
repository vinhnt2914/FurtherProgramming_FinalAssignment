package org.example.app.components.form;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.app.components.table.SelectManagerTable;
import org.example.global.GlobalVariable;
import org.example.model.customer.Beneficiary;
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
    @FXML
    private Button selectManagerButton;
    private Claim claim;
    private InsuranceManager manager;
    public ProposalForm(Claim claim) {
        this.claim = claim;
        loadFormFromFXML();
        setUpForm();
    }

    private void loadFormFromFXML() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/proposalForm.fxml"));
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
        this.claimLabel.setText(claim.getId());
        this.submitButton.setOnAction(this::submitProposal);
        this.selectManagerButton.setOnAction(this::openSelectManager);
    }

    private void openSelectManager(ActionEvent actionEvent) {
        new SelectManagerTable(this);
    }

    private void submitProposal(ActionEvent actionEvent) {
        ProposalRepository repository = new ProposalRepository();
        InsuranceSurveyor surveyor = (InsuranceSurveyor) GlobalVariable.getUser();
        String message = messageArea.getText();
        if (message.isEmpty()) message = "Nothing";
        Proposal proposal = new Proposal(surveyor, claim, manager, message);
        repository.add(proposal);
        repository.close();
    }

    public void setManager(InsuranceManager manager) {
        this.manager = manager;
        managerLabel.setText(String.valueOf(manager.getId()));
    }
}
