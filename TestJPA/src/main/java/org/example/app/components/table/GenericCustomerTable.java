package org.example.app.components.table;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.global.CustomerQueryType;
import org.example.model.customer.Customer;

import java.io.IOException;
import java.net.URL;

public abstract class GenericCustomerTable<T extends Customer> extends TableView<T> implements RefreshableTable {
    @FXML
    protected TableView<T> customerTableView;
    @FXML
    protected TableColumn<T, Integer> idCol;
    @FXML
    protected TableColumn<T, String> nameCol;
    @FXML
    protected TableColumn<T, String> emailCol;
    @FXML
    protected TableColumn<T, String> phoneCol;
    @FXML
    protected TableColumn<T, String> addressCol;
    protected CustomerQueryType.QueryType queryType;

    public GenericCustomerTable(CustomerQueryType.QueryType queryType) {
        this.queryType = queryType;
        FXMLLoader fxmlLoader = new FXMLLoader(getFXMLPath());
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        setUpTableView();
        modifyTableView();
        populateTableView(queryType);
    }

    private URL getFXMLPath() {
        return getClass().getResource("/views/components/table/customerTable.fxml");
    }

    private void setUpTableView() {
        // Set up table columns
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    abstract void modifyTableView();
    abstract void populateTableView(CustomerQueryType.QueryType queryType);
}
