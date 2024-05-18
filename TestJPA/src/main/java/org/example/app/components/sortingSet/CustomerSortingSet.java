package org.example.app.components.sortingSet;

import javafx.event.ActionEvent;
import org.example.app.components.sorting.CustomerSortingForm;
import org.example.app.components.table.GenericCustomerTable;
import org.example.model.customer.Customer;

import java.util.List;

public class CustomerSortingSet extends GenericSortingSet{
    public CustomerSortingSet(GenericCustomerTable<? extends Customer> customerTable) {
        super();
        this.table = customerTable;
    }

    @Override
    void openSortingForm(ActionEvent actionEvent) {
        new CustomerSortingForm((GenericCustomerTable) table);
    }
}
