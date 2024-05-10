package org.example.app;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.global.GlobalVariable;
import org.example.utility.PageManager;

import java.io.IOException;

public class App {
    private static App instance; // Step 1: Singleton instance
    private Stage mainStage;
    private BorderPane layout;
    private PageManager pageManager;
    private App() {
        // Set up page manager
        pageManager = PageManager.getInstance();
        // Load up the scenes for page manager
        pageManager.setScenes(GlobalVariable.getRole());
        // Set up the app
        mainStage = new Stage();
        mainStage.setMaximized(false);
        layout = new BorderPane();

        switchScene("dashboard");
        mainStage.setMaximized(false);

        Scene scene = new Scene(layout);
        mainStage.setScene(scene);

        mainStage.show();
    }

    public static App getInstance() {
        if (instance == null) {
            instance = new App();
        }
        return instance;
    }

    private void switchScene(String sceneName) {
        try {
            Parent scene = pageManager.getScene(sceneName);
            layout.setCenter(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
