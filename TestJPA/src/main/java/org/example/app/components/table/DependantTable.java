package org.example.app.components.table;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import org.example.global.CustomerQueryType;
import org.example.global.GlobalVariable;
import org.example.model.customer.Dependant;
import org.example.model.customer.PolicyHolder;
import org.example.model.customer.PolicyOwner;
import org.example.repository.impl.CustomerRepository;

import java.util.List;

public class DependantTable extends GenericCustomerTable<Dependant> {
    private TableColumn<Dependant, String> policyHolderCol;
    private TableColumn<Dependant, String> policyOwnerCol;
    public DependantTable(CustomerQueryType.QueryType queryType) {
        super(queryType);
    }


    @Override
    void modifyTableView() {
        policyHolderCol = new TableColumn<>("Policy Holder");
        policyOwnerCol = new TableColumn<>("Policy Owner");
        policyHolderCol.setCellValueFactory(cellData -> {
            Dependant dependant = cellData.getValue();
            PolicyHolder policyHolder = dependant.getPolicyHolder();
            String id;
            if (policyHolder == null) id = "No policy holder";
            else id = String.valueOf(policyHolder.getId());
            return new ReadOnlyObjectWrapper<>(id);
        });
        policyOwnerCol.setCellValueFactory(cellData -> {
            Dependant dependant = cellData.getValue();
            PolicyOwner policyHolder = dependant.getPolicyOwner();
            String id;
            if (policyHolder == null) id = "No policy owner";
            else id = String.valueOf(policyHolder.getId());
            return new ReadOnlyObjectWrapper<>(id);
        });

        policyHolderCol.setMinWidth(200);
        policyOwnerCol.setMinWidth(200);

        double newWidth = customerTableView.getWidth() + policyHolderCol.getWidth() + policyOwnerCol.getWidth();

        customerTableView.getColumns().add(policyHolderCol);
        customerTableView.getColumns().add(policyOwnerCol);
        customerTableView.resize(newWidth + 50, this.getHeight()); // Extra 50 for resize
    }

    @Override
    public void populateTableView(CustomerQueryType.QueryType queryType) {
        CustomerRepository repository = new CustomerRepository();
        List<Dependant> dependantList = null;
        switch (queryType) {
            case GET_ALL_DEPENDANT -> dependantList = repository.getAllDependant();
            case GET_ALL_DEPENDANT_OF_POLICY_HOLDER ->
                dependantList = repository.getAllDependantsOfPolicyHolder((PolicyHolder) GlobalVariable.getUser());
            case GET_ALL_DEPENDANT_OF_POLICY_OWNER ->
                dependantList = repository.getAllDependantsOfPolicyOwner((PolicyOwner) GlobalVariable.getUser());
        }
        ObservableList<Dependant> data = FXCollections.observableArrayList(dependantList);
        customerTableView.setItems(data);
        repository.close();
    }

    @Override
    public void refreshTable() {
        populateTableView(queryType);
    }
}
