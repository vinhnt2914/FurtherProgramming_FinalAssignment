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
    public DependantTable(CustomerQueryType.QueryType queryType) {
        super(queryType);
    }


    @Override
    void modifyTableView() {
        policyHolderCol = new TableColumn<>("Policy Holder");
        policyHolderCol.setCellValueFactory(cellData -> {
            Dependant dependant = cellData.getValue();
            PolicyHolder policyHolder = dependant.getPolicyHolder();
            String id;
            if (policyHolder == null) id = "No policy holder";
            else id = String.valueOf(policyHolder.getId());
            return new ReadOnlyObjectWrapper<>(id);
        });

        policyHolderCol.setMinWidth(200);

        customerTableView.getColumns().add(policyHolderCol);
        customerTableView.resize(50, this.getHeight()); // Extra 50 for resize
    }

    @Override
    public void populateTableView(CustomerQueryType.QueryType queryType) {
        CustomerRepository repository = new CustomerRepository();
        List<Dependant> dependantList = null;
        switch (queryType) {
            case GET_ALL_DEPENDANT -> dependantList = repository.getAllDependants();
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
