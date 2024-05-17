package org.example.app.components.form;

import javafx.event.ActionEvent;
import org.example.app.controllers.RefreshableController;
import org.example.model.customer.PolicyOwner;
import org.example.repository.impl.CustomerRepository;
import org.example.service.CustomerService;

public class AddPolicyOwnerForm extends GenericAddForm{
    public AddPolicyOwnerForm(RefreshableController controller) {
        super(controller);
    }

    @Override
    void modifyForm() {

    }

    @Override
    void add(ActionEvent actionEvent) {
        if (validateInput()) {
            CustomerRepository repository = new CustomerRepository();
            CustomerService customerService = new CustomerService();

            PolicyOwner policyOwner = customerService.makePolicyOwner()
                    .fullName(nameField.getText())
                    .username(usernameField.getText())
                    .address(addressField.getText())
                    .email(emailField.getText())
                    .phone(phoneField.getText())
                    .password(passwordField.getText())
                    .build();

            repository.add(policyOwner);
            repository.close();
            close();
            controller.refresh(); // Refresh the table after adding
        }
    }
}
