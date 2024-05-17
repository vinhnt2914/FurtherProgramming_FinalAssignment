package org.example.app.components.buttonset;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.example.app.components.table.RefreshableTable;

import java.io.IOException;

public class PolicyOwnerButtonSet extends GenericButtonSet {
    @FXML
    public Button addButton;
    @FXML
    public Button deleteButton;
    @FXML
    public Button updateButton;
    public PolicyOwnerButtonSet(RefreshableTable table) {
        super(table);
    }
}
