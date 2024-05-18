package org.example.app.components.buttonSet;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import org.example.app.components.table.DependantTable;
import org.example.app.components.table.RefreshableTable;

import java.io.IOException;

public class DependantButtonSet extends GenericButtonSet {
    public DependantButtonSet(RefreshableTable table) {
        super(table);
    }
//    @FXML
//    private HBox dependantButtonSet;
//    @FXML
//    public Button addButton;
//    @FXML
//    public Button deleteButton;
//    @FXML
//    public Button updateButton;
//    private DependantTable dependantTable;
//    public DependantButtonSet(DependantTable dependantTable) {
//        this.dependantTable = dependantTable;
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/buttonSet/dependantButtonSet.fxml"));
//        fxmlLoader.setRoot(this);
//        fxmlLoader.setController(this);
//
//        try {
//            fxmlLoader.load();
//        } catch (IOException exception) {
//            throw new RuntimeException(exception);
//        }
//
////        setUpButtonSet();
//    }

//    private void setUpButtonSet() {
//        this.addButton.setOnAction(this::add);
//        this.deleteButton.setOnAction(this::delete);
//        this.updateButton.setOnAction(this::update);
//    }
//
//    private void update(ActionEvent actionEvent) {
//        Dependant selectedDependant = dependantTable.getSelectionModel().getSelectedItem();
//        if (selectedDependant != null) new UpdateDependantForm(selectedDependant);
//        else new ErrorAlert("Please select a dependant");
//    }
//
//    private void delete(ActionEvent actionEvent) {
//        CustomerRepository repository = new CustomerRepository();
//        Dependant dependant = dependantTable.getSelectionModel().getSelectedItem();
//        repository.removeByID(dependant.getId());
//        repository.close();
//    }
//
//    private void add(ActionEvent actionEvent) {
//        new AddDependantForm();
//    }
}
