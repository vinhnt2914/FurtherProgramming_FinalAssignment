package org.example.app.components.table;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.model.provider.InsuranceSurveyor;
import org.example.repository.impl.InsuranceSurveyorRepository;

import java.io.IOException;
import java.util.List;

public class SurveyorTable extends TableView<InsuranceSurveyor> implements RefreshableTable {
    @FXML
    private TableView<InsuranceSurveyor> surveyorTableView;
    @FXML
    private TableColumn<InsuranceSurveyor, Integer> idCol;
    @FXML
    private TableColumn<InsuranceSurveyor, String> nameCol;
    @FXML
    private TableColumn<InsuranceSurveyor, String> addressCol;
    @FXML
    private TableColumn<InsuranceSurveyor, String> emailCol;
    @FXML
    private TableColumn<InsuranceSurveyor, String> phoneCol;
    private InsuranceSurveyorRepository repository;

    public SurveyorTable() {
        this.repository = new InsuranceSurveyorRepository();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/surveyorTable.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        setUpTableView();
        populateTableView();
    }

    private void setUpTableView() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
    }

    private void populateTableView() {
        List<InsuranceSurveyor> surveyorList = repository.getAll();
        ObservableList<InsuranceSurveyor> data = FXCollections.observableArrayList(surveyorList);
        surveyorTableView.setItems(data);
        repository.close();

    }

    @Override
    public void refreshTable() {
        populateTableView();
    }
}
