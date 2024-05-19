package com.example.finalassingment.app.components.table;
/**
 * @author Group 11
 */
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.finalassingment.model.customer.Beneficiary;
import com.example.finalassingment.model.customer.PolicyOwner;
import com.example.finalassingment.model.items.InsuranceCard;
import com.example.finalassingment.repository.impl.InsuranceCardRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class InsuranceCardTable extends TableView<InsuranceCard> implements RefreshableTable{
    @FXML
    private TableView<InsuranceCard> insuranceCardTableView;
    @FXML
    private TableColumn<InsuranceCard, String> cardNumberCol;
    @FXML
    private TableColumn<InsuranceCard, LocalDate> expireDateCol;
    @FXML
    private TableColumn<InsuranceCard, String> cardHolderCol;
    @FXML
    private TableColumn<InsuranceCard, String> policyOwnerCol;
    private ObservableList<InsuranceCard> data;

    public InsuranceCardTable() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/table/InsuranceCardTable.fxml"));
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
        cardNumberCol.setCellValueFactory(new PropertyValueFactory<>("cardNumber"));
        expireDateCol.setCellValueFactory(new PropertyValueFactory<>("expireDate"));
        cardHolderCol.setCellValueFactory(cellData -> {
            InsuranceCard card = cellData.getValue();
            Beneficiary cardHolder = card.getCardHolder();
            return new ReadOnlyObjectWrapper<>(cardHolder.getId() + " - " + cardHolder.getFullName());
        });
        policyOwnerCol.setCellValueFactory(cellData -> {
            InsuranceCard card = cellData.getValue();
            PolicyOwner policyOwner = card.getPolicyOwner();
            if (policyOwner == null) return new ReadOnlyObjectWrapper<>("No Policy Owner");
            return new ReadOnlyObjectWrapper<>(policyOwner.getId() + " - " + policyOwner.getFullName());
        });
    }

    private void populateTableView() {
        InsuranceCardRepository repository = new InsuranceCardRepository();
        List<InsuranceCard> cardList = repository.getAll();
        data = FXCollections.observableArrayList(cardList);
        insuranceCardTableView.setItems(data);
    }

    @Override
    public void refreshTable() {
        populateTableView();
    }
}
