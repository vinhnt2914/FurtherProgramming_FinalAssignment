package org.example;

import org.example.model.*;
import org.example.model.customer.Dependant;
import org.example.model.customer.PolicyHolder;
import org.example.model.customer.PolicyOwner;
import org.example.repository.impl.CustomerRepository;
import org.example.service.CustomerService;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        InsuranceCard card1 = new InsuranceCard("c-000001");
        InsuranceCard card2 = new InsuranceCard("c-000002");
        InsuranceCard card3 = new InsuranceCard("c-000003");
        InsuranceCard card4 = new InsuranceCard("c-000004");

//        PolicyHolder.PolicyHolderBuilder policyHolderBuilder = new PolicyHolder.PolicyHolderBuilder();
//        Dependant.DependantBuilder dependantBuilder = new Dependant.DependantBuilder();
//        PolicyOwner.PolicyOwnerBuilder policyOwnerBuilder = new PolicyOwner.PolicyOwnerBuilder();

        CustomerService customerService = new CustomerService();

        PolicyHolder c1 = customerService
                .makePolicyHolder()
                .username("vinhrmit1234")
                .password("Rmit@1234")
                .email("vinh@gmail.com")
                .phone("0818194444")
                .address("Hanoi")
                .fullName("Nguyen The Vinh")
                .insuranceCard(card4)
                .build();

        Dependant c2 = customerService
                .makeDependant()
                .fullName("Nguyen The Quang")
                .username("quangrmit1234")
                .password("Rmit@1234")
                .email("quang@gmail.com")
                .phone("123456789")
                .address("Haiphone")
                .insuranceCard(card1)
                .build();

        Dependant c3 = customerService
                .makeDependant()
                .username("khairmit1234")
                .password("Rmit@1234")
                .email("khai@gmail.com")
                .phone("123456812")
                .address("Sapa")
                .fullName("Tran Quang Khai")
                .insuranceCard(card2)
                .build();

        Dependant c4 = customerService
                .makeDependant()
                .username("quatrmit1234")
                .password("Rmit@1234")
                .email("quat@gmail.com")
                .phone("412389123")
                .address("Bac Ninh")
                .fullName("Cao Ba Quat")
                .insuranceCard(card3)
                .build();

        PolicyOwner c5 = customerService
                .makePolicyOwner()
                .username("kienrmit1234")
                .password("Rmit@1234")
                .email("kien@gmail.com")
                .phone("01421234112")
                .address("Thanh Hoa")
                .fullName("Dang Trung Kien")
                .build();

        // This line can cause the unsaved transient object error
        // c2, c3, c4 by the time of persisting
        // Solve using bulk adding, avoid persisting each object per transaction
        // Persist everything under one transaction
        c1.addDepdendants(c2, c3, c4);
        c5.addBeneficaries(c1, c2, c3);

        System.out.println("This can run");

        Claim claim1 = new Claim("f-0001", "No content", c1);
        Claim claim2 = new Claim("f-0002", "No content", c1);
        Claim claim3 = new Claim("f-0003", "No content", c2);
        Claim claim4 = new Claim("f-0004", "No content", c3);

        CustomerRepository customerRepository = new CustomerRepository();

        customerRepository.add(c1,c2,c3,c4,c5);

//        Customer customer = customerRepository.findCustomer(1);
//        customerRepository.removeByID(5);

//        Customer foundC1 = customerRepository.find(1);
////        Customer foundC2 = customerRepository.find(10);
////        Customer foundC3 = customerRepository.find(11);
////        Customer foundC4 = customerRepository.find(12);
//        foundC1.getClaimList().forEach(System.out::println);

//        List<Dependant> dependantList = customerRepository.getAllDependant();
//        for (Dependant d : dependantList) {
//            System.out.println(d);
//        }

//        foundC1.setFullName("Kien Loz");
//        customerRepository.update(foundC1);
////        System.out.println(foundC3);
////        System.out.println(foundC4);
////        System.out.println(foundC1 instanceof PolicyHolder);
////        System.out.println(foundC2 instanceof Dependant);
////        System.out.println(foundC3 instanceof Dependant);
////        System.out.println(foundC4 instanceof Dependant);
        customerRepository.close();

//        InsuranceCardRepository insuranceCardRepository = new InsuranceCardRepository();
//        InsuranceCard card = insuranceCardRepository.find("c-000001");
//        System.out.println(card);
    }
}