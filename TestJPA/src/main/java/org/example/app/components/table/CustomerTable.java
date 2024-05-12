package org.example.app.components.table;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.model.customer.Customer;
import org.example.model.customer.Dependant;

import java.util.List;

public class CustomerTable extends GenericCustomerTable<Customer> {
    @Override
    void populateTableView() {
        // Data is not formatted
        List<Customer> customerList = repository.getAll();
        // Format the data
        ObservableList<Customer> data = FXCollections.observableArrayList(customerList);
        customerTableView.setItems(data);
        repository.close();
    }
}
