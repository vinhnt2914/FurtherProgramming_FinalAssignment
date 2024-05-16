package org.example.app.components.buttonSet;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.event.ActionEvent;
import org.example.app.components.alert.ErrorAlert;
import org.example.app.components.form.AddDependantForm;
import org.example.app.components.form.AddPolicyHolderForm;
import org.example.app.components.form.UpdateDependantForm;
import org.example.app.components.form.UpdatePolicyHolderForm;
import org.example.app.components.table.DependantTable;
import org.example.app.components.table.PolicyHolderTable;
import org.example.app.components.table.PolicyOwnerTable;
import org.example.model.customer.Dependant;
import org.example.model.customer.PolicyHolder;
import org.example.repository.impl.CustomerRepository;

import java.io.IOException;

public class PolicyOwnerButtonSet extends HBox {
    @FXML
    public Button addButton;
    @FXML
    public Button deleteButton;
    @FXML
    public Button updateButton;
    private PolicyOwnerTable policyOwnerTable;
    public PolicyOwnerButtonSet(PolicyOwnerTable policyOwnerTable) {
        this.policyOwnerTable = policyOwnerTable;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/dependantButtonSet.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

}
