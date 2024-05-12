package org.example.app.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.example.global.GlobalVariable;
import org.example.model.customer.Customer;
import org.example.repository.impl.CustomerRepository;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerDashboard implements Initializable {
    @FXML
    private Label fullNameLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label emailLabel;
    private CustomerRepository customerRepository;
    private Customer customer;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerRepository = new CustomerRepository();
    }

    private void setUpPage() {
        setUpLabels();
    }

    private void setUpLabels() {
        fullNameLabel.setText(customer.getFullName());
        addressLabel.setText(customer.getAddress());
        phoneLabel.setText(customer.getPhone());
        emailLabel.setText(customer.getEmail());
    }

    private void getDataFromDB() {
        customer = customerRepository.findByID(GlobalVariable.getUserID());
    }
}
