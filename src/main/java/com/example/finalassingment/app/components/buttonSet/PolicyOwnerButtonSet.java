package com.example.finalassingment.app.components.buttonSet;
/**
 * @author Group 11
 */
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import com.example.finalassingment.app.components.table.RefreshableTable;

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
