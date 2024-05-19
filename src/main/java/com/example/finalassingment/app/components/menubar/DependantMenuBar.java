package com.example.finalassingment.app.components.menubar;
/**
 * @author Group 11
 */
public class DependantMenuBar extends GenericMenuBar{
    @Override
    void modifyMenuBar() {
        menuBar.getChildren().removeAll(providerMenu, insuranceCardMenu, claimMenu, customerMenu, requestMenu, proposalMenu);
    }
}
