package org.example.app.components.buttonSet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.app.components.table.DependantTable;
import org.example.model.customer.Dependant;
import org.example.repository.impl.CustomerRepository;

import java.io.IOException;

public class DependantButtonSet extends HBox {
    @FXML
    private HBox dependantButtonSet;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button setPolicyHolderButton;
    private DependantTable dependantTable;
    public DependantButtonSet(DependantTable dependantTable) {
        this.dependantTable = dependantTable;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/dependantButtonSet.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void setUpButtonSet() {
        this.addButton.setOnAction(this::add);
        this.deleteButton.setOnAction(this::delete);
        this.updateButton.setOnAction(this::update);
        this.setPolicyHolderButton.setOnAction(this::setPolicyHolder);
    }

    private void setPolicyHolder(ActionEvent actionEvent) {
    }

    private void update(ActionEvent actionEvent) {

    }

    private void delete(ActionEvent actionEvent) {
        CustomerRepository repository = new CustomerRepository();
        Dependant dependant = dependantTable.getSelectionModel().getSelectedItem();
        repository.removeByID(dependant.getId());
        repository.close();
    }

    private void add(ActionEvent actionEvent) {
    }
}
