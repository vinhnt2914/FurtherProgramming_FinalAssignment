package com.example.finalassingment.utility;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import com.example.finalassingment.global.GlobalVariable;
import com.example.finalassingment.global.Role;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class PageManager {
    private static PageManager instance;
    private Map<String, String> scenes;

    private PageManager() {
        scenes = new HashMap<>();
        setScenes(GlobalVariable.getRole());
    }

    public static PageManager getInstance() {
        if (instance == null) {
            instance = new PageManager();
        }
        return instance;
    }

    public Parent getScene(String sceneName) throws IOException {
        URL pageURL = getClass().getResource("/views/" + scenes.get(sceneName.toLowerCase()));

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(pageURL);
        return loader.load();
    }

    public void setScenes(Role role) {
        switch (role) {
            case Dependant -> setScenesForDependant();
            case PolicyHolder -> setScenesForPolicyHolder();
            case PolicyOwner -> setScenesForPolicyOwner();
            case Surveyor -> setScenesForSurveyor();
            case Manager -> setScenesForManager();
            case Admin -> setScenesForAdmin();
        }
    }

    private void setScenesForDependant() {
        scenes.put("info", "genericInfo.fxml");
        scenes.put("dashboard", "dependant.fxml");
    }
    private void setScenesForPolicyHolder() {
        scenes.put("info", "genericInfo.fxml");
        scenes.put("dashboard", "policyHolder.fxml");
    }
    private void setScenesForPolicyOwner() {
        scenes.put("info", "genericInfo.fxml");
        scenes.put("dashboard", "policyOwner.fxml");
    }
    private void setScenesForSurveyor() {
        scenes.put("info", "genericInfo.fxml");
        scenes.put("dashboard", "insuranceSurveyor.fxml");
    }
    private void setScenesForManager() {
        scenes.put("info", "genericInfo.fxml");
        scenes.put("dashboard", "insuranceManager.fxml");
    }
    private void setScenesForAdmin() {
        scenes.put("customer", "customerAdmin.fxml");
        scenes.put("provider", "providerAdmin.fxml");
        scenes.put("request", "requestAdmin.fxml");
        scenes.put("proposal", "proposalAdmin.fxml");
        scenes.put("claim", "claimAdmin.fxml");
        scenes.put("insurancecard", "insuranceCardAdmin.fxml");
    }

    public static void setInstance(PageManager instance) {
        PageManager.instance = instance;
    }
}
