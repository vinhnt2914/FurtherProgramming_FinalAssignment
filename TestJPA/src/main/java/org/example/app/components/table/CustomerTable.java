package org.example.app.components.table;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.global.CustomerQueryType;
import org.example.model.customer.Customer;
import org.example.model.customer.Dependant;
import org.example.repository.impl.CustomerRepository;

import java.util.List;

public class CustomerTable extends GenericCustomerTable<Customer>{
    public CustomerTable(CustomerQueryType.QueryType queryType) {
        super(queryType);
    }

    @Override
    void modifyTableView() {
        // Do nothing for now
    }

    @Override
    void populateTableView(CustomerQueryType.QueryType queryType) {
        CustomerRepository repository = new CustomerRepository();
        List<Customer> customerList = repository.getAll();
        ObservableList<Customer> data = FXCollections.observableArrayList(customerList);
        customerTableView.setItems(data);
        repository.close();
    }

    @Override
    public void refreshTable() {
        populateTableView(queryType);
    }
}
