package com.example.finalassingment.app.components.sortingSet;
/**
 * @author Group 11
 */
import javafx.event.ActionEvent;
import com.example.finalassingment.app.components.sorting.CustomerSortingForm;
import com.example.finalassingment.app.components.table.GenericCustomerTable;
import com.example.finalassingment.model.customer.Customer;

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
