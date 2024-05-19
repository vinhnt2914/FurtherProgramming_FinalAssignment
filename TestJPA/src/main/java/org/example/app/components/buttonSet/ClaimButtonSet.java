package org.example.app.components.buttonset;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import org.example.app.components.table.ClaimTable;
import org.example.app.components.table.RefreshableTable;

import java.io.IOException;


public class ClaimButtonSet extends GenericButtonSet {
//    @FXML
//    private HBox claimButtonSet;
//    @FXML
//    public Button addButton;
//    @FXML
//    public Button deleteButton;
//    @FXML
//    public Button updateButton;
//    private ClaimTable claimTable;

    public ClaimButtonSet(RefreshableTable table) {
        super(table);
    }

//    public ClaimButtonSet(ClaimTable claimTable) {
//        this.claimTable = claimTable;
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/buttonSet/claimButtonSet.fxml"));
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

}
