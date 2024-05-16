package org.example.app.components.sortingSet;

import org.example.app.components.table.ClaimTable;
import org.example.app.components.table.RefreshableTable;
import org.example.global.ClaimQueryType;

public class ClaimSortingSet extends GenericSortingSet{
    public ClaimSortingSet(String label, ClaimTable table) {
        super(label);
        this.table = table;
    }

    public void sort(ClaimQueryType.QueryType queryType) {

    }
}
