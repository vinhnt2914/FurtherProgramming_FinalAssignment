package org.example.test;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.app.components.table.RequestTable;

public class TestTable extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        RequestTable requestTable = new RequestTable();
        // Create a scene and set the root node
        Scene scene = new Scene(requestTable);

        // Set the scene onto the stage
        stage.setScene(scene);
        stage.setTitle("Table test");
        stage.show();
    }
}
