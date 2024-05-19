package com.example.finalassingment.app.components.sorting;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.finalassingment.app.components.alert.ErrorAlert;
import com.example.finalassingment.app.components.table.ClaimTable;
import com.example.finalassingment.model.enums.ClaimStatus;
import com.example.finalassingment.model.items.Claim;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ClaimSortingForm extends VBox {
    @FXML
    private ComboBox<String> claimAmountComboBox;
    @FXML
    private ComboBox<String> claimDateComboBox;
    @FXML
    private ComboBox<String> examDateComboBox;
    @FXML
    private ComboBox<String> statusComboBox;
    @FXML
    private TextField claimAmountTextField;
    @FXML
    private DatePicker claimDatePicker;
    @FXML
    private DatePicker examDatePicker;
    @FXML
    private Button sortButton;
    @FXML
    private Button cancelButton;

    private ClaimTable claimTable;
    private Stage stage;

    public ClaimSortingForm(ClaimTable claimTable) {
        try {
            this.claimTable = claimTable;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/sorting/claimSortingForm.fxml"));
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
        this.sortButton.setOnAction(this::sort);
        this.cancelButton.setOnAction(this::handleCancel);
    }

    private void setUpComboBoxes() {
        ObservableList<String> claimAmountOptions = FXCollections.observableArrayList();
        claimAmountOptions.addAll("Greater than", "Less than");

        ObservableList<String> dateOptions = FXCollections.observableArrayList();
        dateOptions.addAll("Before", "After");

        ObservableList<String> statusOptions = FXCollections.observableArrayList();
        statusOptions.addAll("NEW", "PROCESSING", "DONE");

        this.claimAmountComboBox.setItems(claimAmountOptions);
        this.claimDateComboBox.setItems(dateOptions);
        this.examDateComboBox.setItems(dateOptions);
        this.statusComboBox.setItems(statusOptions);
    }

    private void sort(ActionEvent actionEvent) {
        String amountOption = claimAmountComboBox.getValue();
        String claimDateOption = claimDateComboBox.getValue();
        String examDateOption = examDateComboBox.getValue();
        String statusOption = statusComboBox.getValue();

        if (amountOption == null && claimDateOption == null && examDateOption == null && statusOption == null) {
            claimTable.refreshTable();
            close();
            return;
        }

        // Repopulate table in case of performing sort on an empty table
        claimTable.populateTableView(claimTable.queryType);
        List<Claim> claims = claimTable.getItems();

        final boolean[] errorDisplayed = {false};

        List<Claim> sortedClaims = claims.stream()
                .sorted(Comparator.comparingDouble(Claim::getClaimAmount))
                .filter(claim -> {
                    if (amountOption != null && !amountOption.isEmpty()) {
                        try {
                            double claimAmount = Double.parseDouble(claimAmountTextField.getText());
                            if (amountOption.equals("Greater than")) {
                                return claim.getClaimAmount() > claimAmount;
                            } else if (amountOption.equals("Less than")) {
                                return claim.getClaimAmount() < claimAmount;
                            }
                        } catch (NumberFormatException e) {
                            if (!errorDisplayed[0]) {
                                errorDisplayed[0] = true;
                                new ErrorAlert("Invalid claim amount");
                            }
                            return false;
                        }
                    }
                    return true;
                })
                .filter(claim -> {
                    if (claimDateOption != null && !claimDateOption.isEmpty() && claimDatePicker.getValue() != null) {
                        if (claimDateOption.equals("Before")) {
                            return claim.getClaimDate().isBefore(claimDatePicker.getValue());
                        } else if (claimDateOption.equals("After")) {
                            return claim.getClaimDate().isAfter(claimDatePicker.getValue());
                        }
                    }
                    return true;
                })
                .filter(claim -> {
                    if (examDateOption != null && !examDateOption.isEmpty() && examDatePicker.getValue() != null) {
                        if (examDateOption.equals("Before")) {
                            return claim.getExamDate().isBefore(examDatePicker.getValue());
                        } else if (examDateOption.equals("After")) {
                            return claim.getExamDate().isAfter(examDatePicker.getValue());
                        }
                    }
                    return true;
                })
                .filter(claim -> {
                    if (statusOption != null && !statusOption.isEmpty()) {
                        ClaimStatus status = ClaimStatus.valueOf(statusOption);
                        return claim.getStatus().equals(status);
                    }
                    return true;
                })
                .collect(Collectors.toList());

        claimTable.setItems(FXCollections.observableArrayList(sortedClaims));
        close();
    }
    private void handleCancel(ActionEvent actionEvent) {
        close();
    }

    private void close() {
        stage.close();
    }
}
