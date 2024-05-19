package org.example.app.components.menubar;
/**
 * @author Group 11
 */
public class PolicyOwnerMenuBar extends GenericMenuBar{
    @Override
    void modifyMenuBar() {
        menuBar.getChildren().removeAll(providerMenu, insuranceCardMenu, claimMenu, customerMenu, requestMenu, proposalMenu);
    }
}
