package org.example.app.components.table;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.model.customer.PolicyHolder;
import org.example.repository.impl.CustomerRepository;

import java.util.List;

public class PolicyHolderTable extends GenericCustomerTable<PolicyHolder> {
    @Override
    void modifyTableView() {
        // Do nothing for now, you can add custom modifications here if needed
    }

    @Override
    void populateTableView() {
        CustomerRepository repository = new CustomerRepository();
        List<PolicyHolder> policyHolderList = repository.getAllPolicyHolders();
        ObservableList<PolicyHolder> data = FXCollections.observableArrayList(policyHolderList);
        customerTableView.setItems(data);
        repository.close();
    }
}
