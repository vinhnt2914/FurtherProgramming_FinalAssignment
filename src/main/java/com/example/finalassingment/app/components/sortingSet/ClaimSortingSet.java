package com.example.finalassingment.app.components.sortingSet;

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
