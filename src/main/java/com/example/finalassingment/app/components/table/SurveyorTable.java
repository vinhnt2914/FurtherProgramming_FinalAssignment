package com.example.finalassingment.app.components.table;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import com.example.finalassingment.global.GlobalVariable;
import com.example.finalassingment.global.ProviderQueryType;
import com.example.finalassingment.model.provider.InsuranceManager;
import com.example.finalassingment.model.provider.InsuranceSurveyor;
import com.example.finalassingment.repository.impl.ProviderRepository;

import java.util.List;

public class SurveyorTable extends GenericProviderTable<InsuranceSurveyor>{
    private TableColumn<InsuranceSurveyor, List<Integer>> requestsCol;
    private TableColumn<InsuranceSurveyor, List<Integer>> proposalsCol;

    public SurveyorTable(ProviderQueryType.QueryType queryType) {
        super(queryType);
    }

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
    void populateTableView(ProviderQueryType.QueryType queryType) {
        ProviderRepository repository = new ProviderRepository();
        List<InsuranceSurveyor> surveyorList = null;

        switch (queryType) {
            case GET_ALL_SURVEYOR ->
                surveyorList = repository.getAllSurveyor();
            case GET_ALL_SURVEYOR_OF_MANAGER -> {
                InsuranceManager manager = (InsuranceManager) GlobalVariable.getUser();
                surveyorList = repository.getAllSurveyorOfManager(manager);
            }
        }

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
        populateTableView(queryType);
    }
}
