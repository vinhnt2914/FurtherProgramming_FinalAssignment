package com.example.finalassingment.app.components.sorting;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.finalassingment.app.components.alert.ErrorAlert;
import com.example.finalassingment.app.components.table.GenericCustomerTable;
import com.example.finalassingment.model.customer.Customer;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerSortingForm<T extends Customer> extends VBox {
    @FXML
    private ComboBox<String> idComboBox;
    @FXML
    private ComboBox<String> fullNameComboBox;
    @FXML
    private TextField idField;
    @FXML
    private TextField fullNameField;
    @FXML
    private Button sortButton;
    @FXML
    private Button cancelButton;

    private GenericCustomerTable<T> customerTable;
    private Stage stage;
    public CustomerSortingForm(GenericCustomerTable<T> customerTable) {
        try {
            this.customerTable = customerTable;

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/sorting/customerSortingForm.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            VBox rootPane = fxmlLoader.load();

            Scene scene = new Scene(rootPane);
            stage = new Stage();
            stage.setScene(scene);
            stage.show();

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        setUpSortingForm();
    }

    private void setUpSortingForm() {
        setUpComboBoxes();
        this.cancelButton.setOnAction(this::handleCancel);
        this.sortButton.setOnAction(this::sort);
    }

    private void setUpComboBoxes() {
        ObservableList<String> idOptions = FXCollections.observableArrayList();
        idOptions.addAll("Less Than", "Greater Than");

        ObservableList<String> fullNameOptions = FXCollections.observableArrayList();
        fullNameOptions.addAll("Starts With", "Ends With");

        this.idComboBox.setItems(idOptions);
        this.fullNameComboBox.setItems(fullNameOptions);
    }

    private void sort(ActionEvent actionEvent) {
        String idOption = idComboBox.getValue();
        String fullNameOption = fullNameComboBox.getValue();

        if (idOption == null && fullNameOption == null) {
            customerTable.refreshTable();
            close();
            return;
        }

        // Reload data before sorting
        customerTable.populateTableView(customerTable.queryType);

        List<T> customers = customerTable.getItems();

        final boolean[] errorDisplayed = {false};

        List<T> sortedCustomers = customers.stream()
                .sorted(Comparator.comparing(Customer::getId))
                .filter(customer -> {
                    if (idOption != null && !idOption.isEmpty()) {
                        try {
                            int id = Integer.parseInt(idField.getText());
                            if (idOption.equals("Less Than")) {
                                return customer.getId() < id;
                            } else if (idOption.equals("Greater Than")) {
                                return customer.getId() > id;
                            }
                        } catch (NumberFormatException e) {
                            if (!errorDisplayed[0]) {
                                errorDisplayed[0] = true;
                                new ErrorAlert("Invalid ID");
                            }
                            return false;
                        }
                    }
                    return true;
                })
                .filter(customer -> {
                    if (fullNameOption != null && !fullNameOption.isEmpty() && fullNameField.getText() != null) {
                        String fullName = fullNameField.getText().toLowerCase();
                        String customerFullName = customer.getFullName().toLowerCase();
                        if (fullNameOption.equals("Starts With")) {
                            return customerFullName.startsWith(fullName);
                        } else if (fullNameOption.equals("Ends With")) {
                            return customerFullName.endsWith(fullName);
                        }
                    }
                    return true;
                })
                .collect(Collectors.toList());

        ObservableList<T> sortedData = FXCollections.observableArrayList(sortedCustomers);
        customerTable.setItems(sortedData);
        close();
    }
    private void handleCancel(ActionEvent actionEvent) {
        close();
    }
    private void close() {
        stage.close();
    }
}
