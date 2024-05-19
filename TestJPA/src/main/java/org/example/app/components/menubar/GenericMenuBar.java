package org.example.app.components.menubar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.HBox;
import org.example.app.App;

import java.io.IOException;
import java.net.URL;

public abstract class GenericMenuBar extends HBox {
    @FXML
    protected HBox menuBar;
    @FXML
    protected Hyperlink infoMenu;
    @FXML
    protected Hyperlink dashboardMenu;
    @FXML
    protected Hyperlink claimMenu;
    @FXML
    protected Hyperlink insuranceCardMenu;
    @FXML
    protected Hyperlink customerMenu;
    @FXML
    protected Hyperlink providerMenu;
    @FXML
    protected Hyperlink requestMenu;
    @FXML
    protected Hyperlink proposalMenu;
    @FXML
    protected Hyperlink logoutMenu;

    public GenericMenuBar() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource("/views/components/menuBar.fxml");
        if (url == null) {
            throw new IllegalStateException("Cannot find FXML file at /views/components/menuBar.fxml");
        }
        fxmlLoader.setLocation(url);
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException("Failed to load menu bar FXML", exception);
        }

        setUpMenuBar();
        modifyMenuBar();
    }

    abstract void modifyMenuBar();

    private void setUpMenuBar() {
        infoMenu.setOnAction(this::openInfo);
        dashboardMenu.setOnAction(this::openDashboard);
        claimMenu.setOnAction(this::openClaim);
        insuranceCardMenu.setOnAction(this::openInsuranceCard);
        customerMenu.setOnAction(this::openCustomer);
        providerMenu.setOnAction(this::openProvider);
        requestMenu.setOnAction(this::openRequest);
        proposalMenu.setOnAction(this::openProposal);
        logoutMenu.setOnAction(this::logout);
        System.out.println("Menubar set up complete");
    }

    private void openProposal(ActionEvent actionEvent) {
        App.getInstance().switchScene("proposal");
    }

    private void openRequest(ActionEvent actionEvent) {
        App.getInstance().switchScene("request");
    }

    private void openProvider(ActionEvent actionEvent) {
        App.getInstance().switchScene("provider");
    }

    private void openInfo(ActionEvent actionEvent) {
        App.getInstance().switchScene("info");
    }


    private void openCustomer(ActionEvent actionEvent) {
        App.getInstance().switchScene("customer");
        System.out.println("Switching scenes");
    }

    private void openInsuranceCard(ActionEvent actionEvent) {
        App.getInstance().switchScene("insuranceCard");
    }

    private void openClaim(ActionEvent actionEvent) {
        App.getInstance().switchScene("claim");
    }

    private void openDashboard(ActionEvent actionEvent) {
        App.getInstance().switchScene("dashboard");
    }

    public HBox getMenuBar() {
        return menuBar;
    }
    private void logout(ActionEvent actionEvent) {
        App.getInstance().logout();
    }
}
