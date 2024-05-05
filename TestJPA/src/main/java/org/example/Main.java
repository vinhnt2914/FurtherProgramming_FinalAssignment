package org.example;

import org.example.model.User;
import org.example.model.customer.Dependant;
import org.example.model.customer.PolicyHolder;
import org.example.model.customer.PolicyOwner;
import org.example.model.enums.ClaimStatus;
import org.example.model.items.Claim;
import org.example.model.items.InsuranceCard;
import org.example.model.provider.InsuranceSurveyor;
import org.example.repository.impl.CustomerRepository;
import org.example.repository.impl.InsuranceCardRepository;
import org.example.service.ClaimService;
import org.example.service.CustomerService;
import org.example.service.InsuranceCardService;

import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws SQLException {
        InsuranceCardService cardService = new InsuranceCardService();
        CustomerService customerService = new CustomerService();
        ClaimService claimService = new ClaimService();

        InsuranceCard card1 = cardService.makeCard()
                .cardNumber("0000000001")
                .expireDate(LocalDate.of(2024,5,5))
                .build();

        InsuranceCard card2 = cardService.makeCard()
                .cardNumber("0000000002")
                .expireDate(LocalDate.of(2024,5,6))
                .build();

        InsuranceCard card3 = cardService.makeCard()
                .cardNumber("0000000003")
                .expireDate(LocalDate.of(2024,6,5))
                .build();

        InsuranceCard card4 = cardService.makeCard()
                .cardNumber("0000000004")
                .expireDate(LocalDate.of(2024,7,5))
                .build();

        PolicyHolder c1 = customerService
                .makePolicyHolder()
                .username("vinhrmit1234")
                .password("Rmit@1234")
                .email("vinh@gmail.com")
                .phone("0818194444")
                .address("Hanoi")
                .fullName("Nguyen The Vinh")
                .build();

        Dependant c2 = customerService
                .makeDependant()
                .fullName("Nguyen The Quang")
                .username("quangrmit1234")
                .password("Rmit@1234")
                .email("quang@gmail.com")
                .phone("123456789")
                .address("Haiphone")
                .build();

        Dependant c3 = customerService
                .makeDependant()
                .username("khairmit1234")
                .password("Rmit@1234")
                .email("khai@gmail.com")
                .phone("123456812")
                .address("Sapa")
                .fullName("Tran Quang Khai")
                .build();

        Dependant c4 = customerService
                .makeDependant()
                .username("quatrmit1234")
                .password("Rmit@1234")
                .email("quat@gmail.com")
                .phone("412389123")
                .address("Bac Ninh")
                .fullName("Cao Ba Quat")
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
        c1.addDepdendants(c2, c3, c4); // Add dependants
        c5.addBeneficaries(c1, c2, c3); // Add beneficiaries
        c1.setInsuranceCard(card1);
        c2.setInsuranceCard(card2);
        c3.setInsuranceCard(card3);
        c4.setInsuranceCard(card4);

        Claim claim1 = claimService.makeClaim()
                .id("f-000001")
                .insuredPerson(c1)
                .claimDate(LocalDate.of(2024,5,7))
                .examDate(LocalDate.of(2024,8,8))
                .claimAmount(2000)
                .status(ClaimStatus.NEW)
                .bankingInfo("TPBank-NguyenTheVinh-1234567")
                .build();

        Claim claim2 = claimService.makeClaim()
                .id("f-000002")
                .insuredPerson(c1)
                .claimDate(LocalDate.of(2024,2,2))
                .examDate(LocalDate.of(2024,6,8))
                .claimAmount(3000)
                .status(ClaimStatus.NEW)
                .bankingInfo("TPBank-NguyenTheVinh-1234567")
                .build();

        Claim claim3 = claimService.makeClaim()
                .id("f-000003")
                .insuredPerson(c3)
                .claimDate(LocalDate.of(2024,11,14))
                .examDate(LocalDate.of(2024,9,25))
                .claimAmount(500)
                .status(ClaimStatus.NEW)
                .bankingInfo("TPBank-NguyenBaThai-7654321")
                .build();

        Claim claim4 = claimService.makeClaim()
                .id("f-000004")
                .insuredPerson(c1)
                .claimDate(LocalDate.of(2024,1,1))
                .examDate(LocalDate.of(2024,10,5))
                .claimAmount(5000)
                .status(ClaimStatus.NEW)
                .bankingInfo("TPBank-CaoBaQuat-321654")
                .build();

//        System.out.println(claim1);
//        System.out.println(claim1.getInsuredPerson());
//        System.out.println(c1);

        CustomerRepository customerRepository = new CustomerRepository();
        InsuranceCardRepository cardRepository = new InsuranceCardRepository();

        // This could throw a no relation error
        // If add a policyOwner, who does not have an insurance card
        customerRepository.add(c1,c2,c3,c4,c5);

//        User user = customerRepository.findByID(1);
//        System.out.println(user);
//        System.out.println(user instanceof PolicyHolder);
//        System.out.println(customerRepository.getAll());
//        System.out.println(cardRepository.findByID("0000000002"));

        customerRepository.close();
        cardRepository.close();

    }
}