package org.example.app.components.table;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.model.items.Request;
import org.example.model.provider.InsuranceSurveyor;
import org.example.repository.impl.InsuranceSurveyorRepository;
import org.example.repository.impl.ProviderRepository;

import java.io.IOException;
import java.util.List;

public class SurveyorTable extends GenericProviderTable<InsuranceSurveyor>{
    private TableColumn<InsuranceSurveyor, List<Integer>> requestsCol;
    private TableColumn<InsuranceSurveyor, List<Integer>> proposalsCol;

//    public SurveyorTable() {
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/surveyorTable.fxml"));
//        fxmlLoader.setRoot(this);
//        fxmlLoader.setController(this);
//
//        try {
//            fxmlLoader.load();
//        } catch (IOException exception) {
//            throw new RuntimeException(exception);
//        }
//
//        setUpTableView();
//        populateTableView();
//    }

    @Override
    void modifyTableView() {
        requestsCol = new TableColumn<>("Requests");
        proposalsCol = new TableColumn<>("Proposals");
        requestsCol.setCellValueFactory(cellData -> {
            InsuranceSurveyor surveyor = cellData.getValue();
            return new ReadOnlyObjectWrapper<>(surveyor.getRequestIDs());
        });
        proposalsCol.setCellValueFactory(cellData -> {
            InsuranceSurveyor surveyor = cellData.getValue();
            return new ReadOnlyObjectWrapper<>(surveyor.getProposalIDs());
        });

        requestsCol.setMinWidth(200);
        proposalsCol.setMinWidth(200);

        providerTableView.getColumns().addAll(requestsCol, proposalsCol);
    }

    @Override
    void populateTableView() {
        ProviderRepository repository = new ProviderRepository();
        List<InsuranceSurveyor> surveyorList = repository.getAllSurveyor();
        ObservableList<InsuranceSurveyor> data = FXCollections.observableArrayList(surveyorList);
        providerTableView.setItems(data);
        repository.close();
    }

//    private void populateTableView() {
//        ProviderRepository repository = new ProviderRepository();
//        List<InsuranceSurveyor> surveyorList = repository.getAllSurveyor();
//        ObservableList<InsuranceSurveyor> data = FXCollections.observableArrayList(surveyorList);
//        surveyorTableView.setItems(data);
//        repository.close();
//    }

    @Override
    public void refreshTable() {
        populateTableView();
    }
}
