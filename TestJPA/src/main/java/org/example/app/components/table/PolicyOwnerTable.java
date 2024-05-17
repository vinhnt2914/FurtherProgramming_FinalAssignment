package org.example.app.components.table;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.global.CustomerQueryType;
import org.example.model.customer.PolicyOwner;
import org.example.repository.impl.CustomerRepository;

import java.util.List;

public class PolicyOwnerTable extends GenericCustomerTable<PolicyOwner> {

    public PolicyOwnerTable(CustomerQueryType.QueryType queryType) {
        super(queryType);
    }

    @Override
    void modifyTableView() {
        // No additional columns required for PolicyOwnerTable
    }

    @Override
    public void populateTableView(CustomerQueryType.QueryType queryType) {
        CustomerRepository repository = new CustomerRepository();
        List<PolicyOwner> policyOwnerList = null;
        switch (queryType) {
            case GET_ALL_POLICY_OWNER:
                policyOwnerList = repository.getAllPolicyOwners();
                break;
        }
        ObservableList<PolicyOwner> data = FXCollections.observableArrayList(policyOwnerList);
        customerTableView.setItems(data);
        repository.close();
    }

    @Override
    public void refreshTable() {
        populateTableView(queryType);
    }
}
