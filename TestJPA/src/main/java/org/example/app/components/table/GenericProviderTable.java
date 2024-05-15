package org.example.app.components.table;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.model.provider.Provider;

import java.io.IOException;

public abstract class GenericProviderTable<T extends Provider> extends TableView<T> implements RefreshableTable {
    @FXML
    protected TableView<T> providerTableView;
    @FXML
    private TableColumn<T, Integer> idCol;

    public GenericProviderTable() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/providerTable.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        setUpTableView();
        modifyTableView();
        populateTableView();
    }

    private void setUpTableView() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
    }
    abstract void modifyTableView();
    abstract void populateTableView();
}
