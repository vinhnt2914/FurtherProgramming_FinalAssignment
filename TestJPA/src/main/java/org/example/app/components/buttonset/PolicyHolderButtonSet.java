package org.example.app.components.buttonSet;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import org.example.app.components.table.PolicyHolderTable;

import java.io.IOException;

public class PolicyHolderButtonSet extends HBox {
    @FXML
    public Button addButton;
    @FXML
    public Button deleteButton;
    @FXML
    public Button updateButton;
    private PolicyHolderTable policyHolderTable;
    public PolicyHolderButtonSet(PolicyHolderTable policyHolderTable) {
        this.policyHolderTable = policyHolderTable;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/buttonSet/dependantButtonSet.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

//        setUpButtonSet();
    }

//    private void setUpButtonSet() {
//        this.addButton.setOnAction(this::add);
//        this.deleteButton.setOnAction(this::delete);
//        this.updateButton.setOnAction(this::update);
//    }
//
//    private void update(ActionEvent actionEvent) {
//        PolicyHolder selectedPolicyHolder = policyHolderTable.getSelectionModel().getSelectedItem();
//        if (selectedPolicyHolder != null) new UpdatePolicyHolderForm(selectedPolicyHolder, null);
//        else new ErrorAlert("Please select a policyHolder");
//    }
//
//    private void delete(ActionEvent actionEvent) {
//        CustomerRepository repository = new CustomerRepository();
//        PolicyHolder policyHolder = policyHolderTable.getSelectionModel().getSelectedItem();
//        repository.removeByID(policyHolder.getId());
//        repository.close();
//    }
//
//    private void add(ActionEvent actionEvent) {
//        new AddPolicyHolderForm();
//    }


}
