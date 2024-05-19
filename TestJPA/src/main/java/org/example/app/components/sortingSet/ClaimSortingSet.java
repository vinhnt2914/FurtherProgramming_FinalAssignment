package org.example.app.components.sortingSet;
/**
 * @author Group 11
 */
import javafx.event.ActionEvent;
import org.example.app.components.sorting.ClaimSortingForm;
import org.example.app.components.table.ClaimTable;
import org.example.global.ClaimQueryType;

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
