package org.example.app.components.sortingSet;

import javafx.event.ActionEvent;
import org.example.app.components.sorting.CustomerSortingForm;
import org.example.app.components.table.GenericCustomerTable;
import org.example.model.customer.Customer;

import java.util.List;

public class CustomerSortingSet extends GenericSortingSet{
    private List<? extends Customer> originalData;
    public CustomerSortingSet(GenericCustomerTable<? extends Customer> customerTable, List<? extends Customer> originalData) {
        super();
        this.table = customerTable;
        this.originalData = originalData;
    }

    @Override
    void openSortingForm(ActionEvent actionEvent) {
        new CustomerSortingForm((GenericCustomerTable) table, originalData);
    }
}
