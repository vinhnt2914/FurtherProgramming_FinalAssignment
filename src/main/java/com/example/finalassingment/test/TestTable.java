package com.example.finalassingment.test;
/**
 * @author Group 11
 */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.finalassingment.app.components.table.RequestTable;
import com.example.finalassingment.global.RequestQueryType;

public class TestTable extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        RequestTable requestTable = new RequestTable(RequestQueryType.QueryType.GET_ALL, null);
        // Create a scene and set the root node
        Scene scene = new Scene(requestTable);

        // Set the scene onto the stage
        stage.setScene(scene);
        stage.setTitle("Table test");
        stage.show();
    }
}
