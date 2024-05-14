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
import org.example.model.customer.Dependant;
import org.example.model.customer.PolicyHolder;
import org.example.repository.impl.CustomerRepository;

import java.io.IOException;

public class PolicyHolderButtonSet extends HBox {
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button updateButton;
    private PolicyHolderTable policyHolderTable;
    public PolicyHolderButtonSet(PolicyHolderTable policyHolderTable) {
        this.policyHolderTable = policyHolderTable;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/dependantButtonSet.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        setUpButtonSet();
    }

    private void setUpButtonSet() {
        this.addButton.setOnAction(this::add);
        this.deleteButton.setOnAction(this::delete);
        this.updateButton.setOnAction(this::update);
    }

    private void update(ActionEvent actionEvent) {
        PolicyHolder selectedPolicyHolder = policyHolderTable.getSelectionModel().getSelectedItem();
        if (selectedPolicyHolder != null) new UpdatePolicyHolderForm(selectedPolicyHolder);
        else new ErrorAlert("Please select a policyHolder");
    }

    private void delete(ActionEvent actionEvent) {
        CustomerRepository repository = new CustomerRepository();
        PolicyHolder policyHolder = policyHolderTable.getSelectionModel().getSelectedItem();
        repository.removeByID(policyHolder.getId());
        repository.close();
    }

    private void add(ActionEvent actionEvent) {
        new AddPolicyHolderForm();
    }


}
