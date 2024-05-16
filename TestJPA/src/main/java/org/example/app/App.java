package org.example.app;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.app.components.menubar.*;
import org.example.global.GlobalVariable;
import org.example.global.Role;
import org.example.utility.PageManager;
import org.example.utility.PasswordUtil;

import java.io.IOException;

public class App {
    private static App instance; // Step 1: Singleton instance
    private Stage mainStage;
    private BorderPane layout;
    private PageManager pageManager;
    private GenericMenuBar menuBar;
    private App() {
        setUpStage();
    }

    private void setUpStage() {
        pageManager = PageManager.getInstance();
        // Set up the app
        mainStage = new Stage();
        layout = new BorderPane();

        menuBar = setUpMenuBar();
        layout.setTop(menuBar);

        Hyperlink firstMenuItem = (Hyperlink) menuBar.getMenuBar().getChildren().getFirst();
        System.out.println("FIRST MENU ITEM: " + firstMenuItem.getText());
        switchScene(firstMenuItem.getText());
        Scene scene = new Scene(layout);
        mainStage.setScene(scene);
        mainStage.show();
    }

    private GenericMenuBar setUpMenuBar() {
        Role role = GlobalVariable.getRole();

        switch (role) {
            case Dependant -> {
                return new DependantMenuBar();
            }
            case PolicyHolder -> {
                return new PolicyHolderMenuBar();
            }
            case Surveyor -> {
                return new SurveyorMenuBar();
            }
            case PolicyOwner -> {
                return new PolicyOwnerMenuBar();
            }
            case Manager -> {
                return new ManagerMenuBar();
            }
            case Admin -> {
                return new AdminMenuBar();
            }
        }
        return null;
    }

    public static App getInstance() {
        if (instance == null) {
            instance = new App();
        }
        return instance;
    }

    public void switchScene(String sceneName) {
        try {
            Parent scene = pageManager.getScene(sceneName);
            layout.setCenter(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
