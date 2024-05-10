package org.example.utility;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.example.global.Role;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class PageManager {
    private static PageManager instance;
    private Map<String, String> scenes;

    private PageManager() {
        scenes = new HashMap<>();
    }

    public static PageManager getInstance() {
        if (instance == null) {
            instance = new PageManager();
        }
        return instance;
    }

    public Parent getScene(String sceneName) throws IOException {
        URL pageURL = getClass().getResource("/views/" + scenes.get(sceneName));
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(pageURL);
        return loader.load();
    }

    public void setScenes(Role role) {
        switch (role) {
            case Dependant -> setScenesForDependant();
            case PolicyHolder -> setScenesForPolicyHolder();
        }
    }

    private void setScenesForDependant() {
        scenes.put("dashboard", "dependant.fxml");
    }

    private void setScenesForPolicyHolder() {
        scenes.put("dashboard", "policyHolder.fxml");
    }
}
