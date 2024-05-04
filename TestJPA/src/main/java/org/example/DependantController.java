package org.example;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.model.customer.Dependant;
import org.example.model.items.Claim;
import org.example.service.ClaimService;
import java.time.LocalDate;

public class DependantController {

    @FXML
    private Label dependantEmail;

    @FXML
    private Label dependantAddress;

    @FXML
    private Label dependantPhone;

    @FXML
    private TableView<Claim> dependantTable;

    @FXML
    private TableColumn<Claim, String> claimIdColumn;

    @FXML
    private TableColumn<Claim, LocalDate> claimDateColumn;

    @FXML
    private TableColumn<Claim, LocalDate> examDateColumn;

    @FXML
    private TableColumn<Claim, Double> claimAmountColumn;

    @FXML
    private TableColumn<Claim, String> claimStatusColumn;

    private Dependant dependant;

    private ObservableList<Claim> claimsData;

    public void setClaimsData(ObservableList<Claim> claimsData) {
        this.claimsData = claimsData;
        dependantTable.setItems(claimsData);
    }
}
