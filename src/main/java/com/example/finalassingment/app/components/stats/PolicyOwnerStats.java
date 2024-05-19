package com.example.finalassingment.app.components.stats;
/**
 * @author Group 11
 */
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import com.example.finalassingment.global.GlobalVariable;
import com.example.finalassingment.model.customer.PolicyOwner;

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
        numOfBeneficiaryLabel.setText(String.valueOf(policyOwner.calculateTotalBeneficiaries()));
    }
}
