package org.example.calculation;

import org.example.model.customer.Dependant;
import org.example.model.customer.PolicyHolder;
import org.example.model.customer.PolicyOwner;
import org.example.model.enums.ClaimStatus;
import org.example.model.items.Claim;
import org.example.model.items.InsuranceCard;
import org.example.service.ClaimService;
import org.example.service.CustomerService;
import org.example.service.InsuranceCardService;
import org.example.service.ProviderService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class TotalClaimAmountTest {
    @Test
    void calculateTotalClaimAmount() {
        CustomerService customerService = new CustomerService();
        ClaimService claimService = new ClaimService();
        InsuranceCardService cardService = new InsuranceCardService();

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

        Claim claim2 = claimService.makeClaim()
                .id("f-000002")
                .insuredPerson(c1)
                .claimDate(LocalDate.of(2024, 2, 2))
                .examDate(LocalDate.of(2024, 6, 8))
                .claimAmount(3000)
                .status(ClaimStatus.DONE)
                .bankingInfo("TPBank-NguyenTheVinh-1234567")
                .build();

        Claim claim3 = claimService.makeClaim()
                .id("f-000003")
                .insuredPerson(c1)
                .claimDate(LocalDate.of(2024, 11, 14))
                .examDate(LocalDate.of(2024, 9, 25))
                .claimAmount(500)
                .status(ClaimStatus.DONE)
                .bankingInfo("TPBank-NguyenBaThai-7654321")
                .build();

        Claim claim4 = claimService.makeClaim()
                .id("f-000004")
                .insuredPerson(c1)
                .claimDate(LocalDate.of(2024, 1, 1))
                .examDate(LocalDate.of(2024, 10, 5))
                .claimAmount(5000)
                .status(ClaimStatus.PROCESSING)
                .bankingInfo("TPBank-CaoBaQuat-321654")
                .build();

        Claim claim5 = claimService.makeClaim()
                .id("f-000005")
                .insuredPerson(c1)
                .claimDate(LocalDate.of(2024, 3, 15))
                .examDate(LocalDate.of(2024, 4, 10))
                .claimAmount(2500)
                .status(ClaimStatus.PROCESSING)
                .bankingInfo("TPBank-TranVanA-9876543")
                .build();

        List<Claim> claimList = new ArrayList<>();
        claimList.add(claim1);
        claimList.add(claim2);
        claimList.add(claim3);
        claimList.add(claim4);
        claimList.add(claim5);

        double totalClaimAmount = claimList.stream()
                .filter(claim -> claim.getStatus() == ClaimStatus.PROCESSING || claim.getStatus() == ClaimStatus.DONE)
                .mapToDouble(Claim::getClaimAmount)
                .sum();

        assertEquals(11000, totalClaimAmount);
    }
}
