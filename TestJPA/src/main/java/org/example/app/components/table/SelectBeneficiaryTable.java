package org.example.app.components.table;
/**
 * @author Group 11
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import org.example.app.components.alert.ErrorAlert;
import org.example.app.components.form.SelectableForm;
import org.example.global.CustomerQueryType;
import org.example.global.GlobalVariable;
import org.example.model.customer.Beneficiary;
import org.example.model.customer.Dependant;
import org.example.model.customer.PolicyHolder;
import org.example.model.customer.PolicyOwner;
import org.example.repository.impl.CustomerRepository;

import java.util.ArrayList;
import java.util.List;

public class SelectBeneficiaryTable extends GenericCustomerTable<Beneficiary> {
    private TableColumn<Beneficiary, String> actionCol;
    private SelectableForm form;
    private Stage stage;

    public SelectBeneficiaryTable(CustomerQueryType.QueryType queryType, SelectableForm form) {
        super(queryType);
        this.form = form;
        setUpStage();
    }

    private void setUpStage() {
        stage = new Stage();

        // Create a scene and set the root node
        Scene scene = new Scene(this);

        // Set the scene onto the stage
        stage.setScene(scene);
        stage.setTitle("Select Beneficiary Table");
        stage.show();
    }

    @Override
    void modifyTableView() {
        // Create the action col with a select button
        actionCol = new TableColumn<>("Action");
        actionCol.setCellFactory(param -> new TableCell<>() {
            final Button btn = createSelectButton();

            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    btn.setOnAction(event -> setSelectedInsuredPerson(getTableRow().getItem()));
                    setGraphic(btn);
                }
            }
        });

        customerTableView.getColumns().add(actionCol);
    }

    @Override
    public void populateTableView(CustomerQueryType.QueryType queryType) {
        CustomerRepository repository = new CustomerRepository();
        List<Beneficiary> beneficiaryList = new ArrayList<>();
        switch (queryType) {
            case GET_ALL_DEPENDANT_AND_POLICY_HOLDER -> {
                List<Beneficiary> data = repository.getAllPolicyHoldersAndDependants();
                beneficiaryList.addAll(data);
            }
            case GET_ALL_DEPENDANT_OF_POLICY_HOLDER -> {
                List<Dependant> data = repository.getAllDependantsOfPolicyHolder((PolicyHolder) GlobalVariable.getUser());
                beneficiaryList.addAll(data);
            }
            case GET_ALL_BENEFICIARY_OF_POLICY_OWNER -> {
                List<Beneficiary> data = repository.getAllBeneficiaryOfPolicyOwner((PolicyOwner) GlobalVariable.getUser());
                beneficiaryList.addAll(data);
            }
        }
        ObservableList<Beneficiary> data = FXCollections.observableArrayList(beneficiaryList);
        customerTableView.setItems(data);
        repository.close();
    }

    private Button createSelectButton() {
        Button button = new Button("Select");
        button.setPadding(new Insets(10,20,10,20));
        return button;
    }

    private void setSelectedInsuredPerson(Beneficiary dependant) {
        if (dependant.getInsuranceCard() != null) {
            new ErrorAlert("This person already have an insurance card. Please select someone else");
            return;
        }
        form.setBeneficiary(dependant);
        close();
    }

    private void close() {
        stage.close();
    }

    @Override
    public void refreshTable() {
        populateTableView(queryType);
    }
}
