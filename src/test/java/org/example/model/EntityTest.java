package org.example.model;

import org.example.model.customer.Dependant;
import org.example.model.customer.PolicyHolder;
import org.example.model.customer.PolicyOwner;
import org.example.model.enums.ClaimStatus;
import org.example.model.items.Claim;
import org.example.model.items.InsuranceCard;
import org.example.model.items.Proposal;
import org.example.model.items.Request;
import org.example.model.provider.InsuranceManager;
import org.example.model.provider.InsuranceSurveyor;
import org.example.service.ClaimService;
import org.example.service.CustomerService;
import org.example.service.InsuranceCardService;
import org.example.service.ProviderService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EntityTest {

    @Test
    void shouldWork() {
        InsuranceCardService cardService = new InsuranceCardService();
        CustomerService customerService = new CustomerService();
        ClaimService claimService = new ClaimService();
        ProviderService providerService = new ProviderService();

        PolicyOwner c3 = customerService
                .makePolicyOwner()
                .username("kienrmit1234")
                .password("Rmit@1234")
                .email("kien@gmail.com")
                .phone("01421234112")
                .address("Thanh Hoa")
                .fullName("Dang Trung Kien")
                .fee(500)
                .build();

        PolicyHolder c1 = customerService
                .makePolicyHolder()
                .username("vinhrmit1234")
                .password("Rmit@1234")
                .email("vinh@gmail.com")
                .phone("0818194444")
                .address("Hanoi")
                .fullName("Nguyen The Vinh")
                .policyOwner(c3)
                .build();

        Dependant c2 = customerService
                .makeDependant()
                .username("khairmit1234")
                .password("Rmit@1234")
                .email("khai@gmail.com")
                .phone("123456812")
                .address("Sapa")
                .fullName("Tran Quang Khai")
                .build();

        c1.addDependants(c2); // Add dependants

        InsuranceCard card1 = cardService.makeCard()
                .cardNumber("0000000001")
                .expireDate(LocalDate.of(2024, 5, 5))
                .cardHolder(c1)
                .policyOwner(c1.getPolicyOwner())
                .build();

        Claim claim1 = claimService.makeClaim()
                .id("f-000001")
                .insuredPerson(c1)
                .claimDate(LocalDate.of(2024, 5, 7))
                .examDate(LocalDate.of(2024, 8, 8))
                .claimAmount(2000)
                .status(ClaimStatus.NEW)
                .bankingInfo("TPBank-NguyenTheVinh-1234567")
                .build();

        InsuranceManager m1 = providerService.makeManager()
                .username("manager1")
                .password("Rmit@1234")
                .email("manager@gmail.com")
                .phone("092139129")
                .address("HaNoi")
                .fullName("Nguyen Vinh Vinh")
                .build();

        InsuranceSurveyor s1 = providerService.makeSurveyor()
                .username("survey1")
                .password("Rmit@1234")
                .email("surveyor@gmail.com")
                .phone("091231231")
                .address("HaiPhong")
                .fullName("Nguyen The The")
                .manager(m1)
                .build();

        Request r1 = s1.makeRequest(c1, "Check insurance details");
        Proposal p1 = s1.propose(m1, claim1, "Review this claim");

        assertNotNull(c1);
        assertNotNull(c2);
        assertNotNull(c3);
        assertNotNull(card1);
        assertNotNull(claim1);
        assertNotNull(s1);
        assertNotNull(m1);
        assertNotNull(r1);
        assertNotNull(p1);
        assertSame(c1.getPolicyOwner(), c3);
        assertSame(c2.getPolicyHolder(), c1);
        assertSame(claim1.getEntireInsuredPerson(), c1);
        assertSame(card1.getCardHolder(), c1);
        assertSame(card1.getPolicyOwner(), c3);
        assertSame(s1.getManager(), m1);
        assertSame(r1.getCustomer(), c1);
        assertSame(r1.getInsuranceSurveyor(), s1);
        assertSame(p1.getInsuranceSurveyor(), s1);
        assertSame(p1.getInsuranceManager(), m1);
    }

}