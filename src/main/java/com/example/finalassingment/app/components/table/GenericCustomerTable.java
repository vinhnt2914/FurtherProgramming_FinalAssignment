package com.example.finalassingment.app.components.table;
/**
 * @author Group 11
 */
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.finalassingment.global.CustomerQueryType;
import com.example.finalassingment.model.customer.Customer;

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
    public CustomerQueryType.QueryType queryType;

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
    public abstract void populateTableView(CustomerQueryType.QueryType queryType);
}
