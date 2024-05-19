package com.example.finalassingment.app.components.sortingSet;
/**
 * @author Group 11
 */
import javafx.event.ActionEvent;
import com.example.finalassingment.app.components.sorting.ClaimSortingForm;
import com.example.finalassingment.app.components.table.ClaimTable;

public class ClaimSortingSet extends GenericSortingSet{
    public ClaimSortingSet(ClaimTable table) {
        super();
        this.table = table;
    }
    @Override
    void openSortingForm(ActionEvent actionEvent) {
        new ClaimSortingForm((ClaimTable) table);
    }
}
