package com.example.finalassingment.app.components.menubar;

public class SurveyorMenuBar extends GenericMenuBar{
    @Override
    void modifyMenuBar() {
        menuBar.getChildren().removeAll(insuranceCardMenu, claimMenu, providerMenu, customerMenu, requestMenu, proposalMenu);
    }
}
