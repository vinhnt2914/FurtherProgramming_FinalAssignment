package org.example.app.components.menubar;
/**
 * @author Group 11
 */
public class SurveyorMenuBar extends GenericMenuBar{
    @Override
    void modifyMenuBar() {
        menuBar.getChildren().removeAll(insuranceCardMenu, claimMenu, providerMenu, customerMenu, requestMenu, proposalMenu);
    }
}
