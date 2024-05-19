package com.example.finalassingment.app.components.buttonSet;

import com.example.finalassingment.app.components.table.RefreshableTable;

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
