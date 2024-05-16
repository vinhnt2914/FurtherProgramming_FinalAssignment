package org.example.app.components.stats;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.example.global.GlobalVariable;
import org.example.model.customer.PolicyOwner;

import java.io.IOException;

public class PolicyOwnerStats extends HBox {
    @FXML
    private Label insuranceFeeLabel;
    @FXML
    private Label numOfBeneficiaryLabel;

    public PolicyOwnerStats() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/stats/policyOwnerStats.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        setUpStats();
    }

    private void setUpStats() {
        PolicyOwner policyOwner = (PolicyOwner) GlobalVariable.getUser();
        insuranceFeeLabel.setText("$" + policyOwner.calculateFee());
        numOfBeneficiaryLabel.setText(String.valueOf(policyOwner.getBeneficiarySet().size()));
    }
}
