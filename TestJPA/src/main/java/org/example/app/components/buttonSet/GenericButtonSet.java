package org.example.app.components.buttonset;
/**
 * @author Group 11
 */

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import org.example.app.components.table.RefreshableTable;

import java.io.IOException;

public abstract class GenericButtonSet extends HBox {
    @FXML
    protected HBox buttonSet;
    @FXML
    public Button addButton;
    @FXML
    public Button deleteButton;
    @FXML
    public Button updateButton;
    private RefreshableTable table;
    public GenericButtonSet(RefreshableTable table) {
        this.table = table;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/buttonSet/buttonSet.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

//        setUpButtonSet();
    }
}
