package org.example.app.components.sortingSet;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.example.app.components.table.RefreshableTable;

import java.io.IOException;

public class GenericSortingSet extends HBox {
    @FXML
    private Label sortLabel;
    @FXML
    public ChoiceBox<String> sortOptionChoiceBox;
    public RefreshableTable table;
    public GenericSortingSet(String label) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/sorting/sortingSet.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        setUpSortingSet(label);
    }

    private void setUpSortingSet(String label) {
        sortLabel.setText(label);
    }
}
