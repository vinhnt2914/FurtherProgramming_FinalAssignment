package com.example.finalassingment.app.components.table;
/**
 * @author Group 11
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.finalassingment.global.CustomerQueryType;
import com.example.finalassingment.model.customer.Customer;
import com.example.finalassingment.repository.impl.CustomerRepository;

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
    public void populateTableView(CustomerQueryType.QueryType queryType) {
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
