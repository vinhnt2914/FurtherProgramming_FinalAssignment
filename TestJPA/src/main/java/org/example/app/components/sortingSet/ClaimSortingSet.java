package org.example.app.components.sortingSet;

import javafx.event.ActionEvent;
import org.example.app.components.sorting.ClaimSortingForm;
import org.example.app.components.table.ClaimTable;
import org.example.app.components.table.RefreshableTable;
import org.example.global.ClaimQueryType;

public class ClaimSortingSet extends GenericSortingSet{
    public ClaimSortingSet(String label, ClaimTable table) {
        super(label);
        this.table = table;
        setUpSortButton();
    }

    private void setUpSortButton() {
        this.sortButton.setOnAction(this::openSortingForm);
    }

    private void openSortingForm(ActionEvent actionEvent) {
        new ClaimSortingForm((ClaimTable) table);
    }

    public void sort(ClaimQueryType.QueryType queryType) {

    }
}
