package org.example.app.components.menubar;

public class SurveyorMenuBar extends GenericMenuBar{
    @Override
    void modifyMenuBar() {
        menuBar.getChildren().removeAll(insuranceCardMenu, claimMenu, providerMenu);
    }
}
