package org.example.app.components.sortingSet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.example.app.components.sorting.ClaimSortingForm;
import org.example.app.components.table.ClaimTable;
import org.example.app.components.table.RefreshableTable;

import java.io.IOException;

public abstract class GenericSortingSet extends HBox {
    @FXML
    protected Button sortButton;
    @FXML
    public ChoiceBox<String> sortOptionChoiceBox;
    public RefreshableTable table;
    public GenericSortingSet() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/sorting/sortingSet.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        setUpSortButton();
    }

    private void setUpSortButton() {
        this.sortButton.setOnAction(this::openSortingForm);
    }

    abstract void openSortingForm(ActionEvent actionEvent);
}
