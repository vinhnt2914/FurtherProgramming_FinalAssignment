package org.example.app.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.model.customer.Customer;
import org.example.repository.impl.CustomerRepository;

import java.io.IOException;
import java.net.URL;

public abstract class CustomerTable extends TableView<Customer> {
    @FXML
    protected TableView<Customer> customerTableView;
    @FXML
    private TableColumn<Customer, Integer> idCol;
    @FXML
    private TableColumn<Customer, String> nameCol;
    @FXML
    private TableColumn<Customer, String> emailCol;
    @FXML
    private TableColumn<Customer, String> phoneCol;
    @FXML
    private TableColumn<Customer, String> addressCol;
    private CustomerRepository repository;

    public CustomerTable(CustomerRepository repository) {
        this.repository = repository;
        FXMLLoader fxmlLoader = new FXMLLoader(getFXMLPath());
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        setUpTableView();
    }

    private URL getFXMLPath() {
        return getClass().getResource("/views/components/customerTable.fxml");
    }

    private void setUpTableView() {
//        // Set up table columns
//        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
//        nameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
//        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
//        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
//        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
    }
}
