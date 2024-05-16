package org.example.test;

import org.example.model.customer.Dependant;
import org.example.service.CustomerService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DependantTest {
    @Test
    void shouldCreateDependant() {
        CustomerService service = new CustomerService();
        Dependant dependant = service.makeDependant()
                .username("vinhrmit1234")
                .password("Rmit@1234")
                .email("vinh@gmail.com")
                .phone("0818194444")
                .address("Hanoi")
                .fullName("Nguyen The Vinh")
                .build();
        assertNotNull(dependant);
    }
}