package com.example.finalassingment.app;
/**
 * @author Group 11
 */
import com.example.finalassingment.app.components.menubar.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import com.example.finalassingment.global.GlobalVariable;
import com.example.finalassingment.global.Role;
import com.example.finalassingment.utility.PageManager;

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

    public void logout() {
        System.out.println("Logging out. Resetting user and role...");

        // Log the current user and role before resetting
        System.out.println("Current user: " + (GlobalVariable.getUser() != null ? GlobalVariable.getUser().getClass().getSimpleName() : "None"));
        System.out.println("Current role: " + (GlobalVariable.getRole() != null ? GlobalVariable.getRole() : "None"));

        System.out.println("User and role have been reset.");
        System.out.println("Application will now exit.");

        close();
        new Login();
    }

    private void close() {
        mainStage.close();
        instance = null;
        PageManager.setInstance(null);
    }


}
