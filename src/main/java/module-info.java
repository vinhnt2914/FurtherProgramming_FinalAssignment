module com.example.finalassingment {
    /**
     * @author Group 11
     */
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.desktop;

    exports com.example.finalassingment;
    exports com.example.finalassingment.app;
    exports com.example.finalassingment.app.controllers;
    exports com.example.finalassingment.model;
    exports com.example.finalassingment.model.customer;
    exports com.example.finalassingment.model.provider;

    opens com.example.finalassingment;
    opens com.example.finalassingment.app;
    opens com.example.finalassingment.app.components.table;
    opens com.example.finalassingment.app.components.sortingSet;
    opens com.example.finalassingment.app.components.form;
    opens com.example.finalassingment.app.components.buttonSet;
    opens com.example.finalassingment.app.components.sorting;
    opens com.example.finalassingment.app.components.stats;
    opens com.example.finalassingment.app.components.alert;
    opens com.example.finalassingment.app.components.menubar;
    opens com.example.finalassingment.app.controllers;
    opens com.example.finalassingment.model;
    opens com.example.finalassingment.model.items;
    opens com.example.finalassingment.model.customer;
    opens com.example.finalassingment.model.provider;
}
