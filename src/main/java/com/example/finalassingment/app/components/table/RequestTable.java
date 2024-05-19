package com.example.finalassingment.app.components.table;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.finalassingment.global.RequestQueryType;
import com.example.finalassingment.model.customer.Beneficiary;
import com.example.finalassingment.model.items.Request;
import com.example.finalassingment.repository.impl.RequestRepository;

import java.io.IOException;
import java.util.List;

public class RequestTable extends TableView<Request> implements RefreshableTable {
    @FXML
    private TableView<Request> requestTableView;
    @FXML
    private TableColumn<Request, Integer> idCol;
    @FXML
    private TableColumn<Request, Integer> fromCol;
    @FXML
    private TableColumn<Request, String> claimCol;
    @FXML
    private TableColumn<Request, String> messageCol;
    private RequestQueryType.QueryType queryType;
    private Beneficiary beneficiary;

    public RequestTable(RequestQueryType.QueryType queryType, Beneficiary beneficiary) {
        this.beneficiary = beneficiary;
        this.queryType = queryType;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/table/requestTable.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        setUpTableView();
    }

    private void setUpTableView() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        fromCol.setCellValueFactory(cellData -> {
            Request request = cellData.getValue();
            return new ReadOnlyObjectWrapper<>(request.getInsuranceSurveyor().getId());
        });
        claimCol.setCellValueFactory(cellData -> {
            Request request = cellData.getValue();
            return new ReadOnlyObjectWrapper<>(request.getClaim().getId());
        });
        messageCol.setCellValueFactory(new PropertyValueFactory<>("message"));
        populateTableView(queryType);

    }

    private void populateTableView(RequestQueryType.QueryType queryType) {
        RequestRepository repository = new RequestRepository();
        List<Request> requestList = null;

        switch (queryType) {
            case GET_ALL -> requestList = repository.getAll();
            case GET_ALL_TO_CUSTOMER -> requestList = repository.getAllToBeneficiary(beneficiary);
        }

        ObservableList<Request> data = FXCollections.observableArrayList(requestList);
        requestTableView.setItems(data);
        repository.close();
    }

    @Override
    public void refreshTable() {
        populateTableView(queryType);
    }
}
