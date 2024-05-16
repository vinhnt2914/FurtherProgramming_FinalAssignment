package org.example.app.components.form;

import javafx.event.ActionEvent;
import org.example.app.components.table.GenericProviderTable;
import org.example.app.controllers.RefreshableController;
import org.example.model.provider.InsuranceManager;
import org.example.model.provider.InsuranceSurveyor;
import org.example.repository.impl.ProviderRepository;
import org.example.service.ProviderService;

public class AddManagerForm extends GenericAddForm {
    public AddManagerForm(RefreshableController controller) {
        super(controller);
    }

    @Override
    void modifyForm() {
        titleLabel.setText("Add Surveyor Form");
    }

    @Override
    void add(ActionEvent actionEvent) {
        if (validateInput()) {
            ProviderRepository repository = new ProviderRepository();
            ProviderService service = new ProviderService();

            InsuranceManager surveyor = service.makeManager()
                    .fullName(nameField.getText())
                    .username(usernameField.getText())
                    .address(addressField.getText())
                    .email(emailField.getText())
                    .phone(phoneField.getText())
                    .password(passwordField.getText()).build();

            repository.add(surveyor);

            repository.close();
            close();
            controller.refresh(); // Refresh the table after adding
        }
    }
}
