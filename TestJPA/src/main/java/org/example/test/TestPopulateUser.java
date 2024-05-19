package org.example.test;
/**
 * @author Group 11
 */
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
import org.example.repository.impl.*;
import org.example.service.ClaimService;
import org.example.service.CustomerService;
import org.example.service.InsuranceCardService;
import org.example.service.ProviderService;

import java.time.LocalDate;

public class TestPopulateUser {
    public static void main(String[] args) {
        InsuranceCardService cardService = new InsuranceCardService();
        CustomerService customerService = new CustomerService();
        ClaimService claimService = new ClaimService();
        ProviderService providerService = new ProviderService();

        PolicyHolder c1 = customerService
                .makePolicyHolder()
                .username("vinhrmit1234")
                .password("Rmit@1234")
                .email("vinh@gmail.com")
                .phone("0818194444")
                .address("Hanoi")
                .fullName("Nguyen The Vinh")
                .build();

        PolicyHolder c2 = customerService
                .makePolicyHolder()
                .username("mairmit1234")
                .password("Rmit@1234")
                .email("mai@gmail.com")
                .phone("0841241232")
                .address("Hanoi")
                .fullName("Pham Thanh Mai")
                .build();

        Dependant c3 = customerService
                .makeDependant()
                .fullName("Nguyen The Quang")
                .username("quangrmit1234")
                .password("Rmit@1234")
                .email("quang@gmail.com")
                .phone("123456789")
                .address("Haiphong")
                .build();

        Dependant c4 = customerService
                .makeDependant()
                .username("khairmit1234")
                .password("Rmit@1234")
                .email("khai@gmail.com")
                .phone("123456812")
                .address("Sapa")
                .fullName("Tran Quang Khai")
                .build();

        Dependant c5 = customerService
                .makeDependant()
                .username("quatrmit1234")
                .password("Rmit@1234")
                .email("quat@gmail.com")
                .phone("412389123")
                .address("Bac Ninh")
                .fullName("Cao Ba Quat")
                .build();

        PolicyOwner c6 = customerService
                .makePolicyOwner()
                .username("kienrmit1234")
                .password("Rmit@1234")
                .email("kien@gmail.com")
                .phone("01421234112")
                .address("Thanh Hoa")
                .fullName("Dang Trung Kien")
                .fee(500)
                .build();

        Dependant c7 = customerService
                .makeDependant()
                .username("trungrmit1234")
                .password("Rmit@1234")
                .email("trung@gmail.com")
                .phone("0987654321")
                .address("Da Nang")
                .fullName("Tran Van Trung")
                .build();

        c1.addDependants(c3, c4); // Add dependants
        c2.addDependants(c5);
        c6.addPolicyHolders(c1); // Add beneficiaries

        InsuranceCard card1 = cardService.makeCard()
                .cardNumber("0000000001")
                .expireDate(LocalDate.of(2024, 5, 5))
                .cardHolder(c1)
                .policyOwner(c1.getPolicyOwner())
                .build();

        InsuranceCard card2 = cardService.makeCard()
                .cardNumber("0000000002")
                .expireDate(LocalDate.of(2024, 5, 6))
                .cardHolder(c2)
                .policyOwner(c2.getPolicyOwner())
                .build();

        InsuranceCard card3 = cardService.makeCard()
                .cardNumber("0000000003")
                .expireDate(LocalDate.of(2024, 6, 5))
                .cardHolder(c3)
                .policyOwner(c3.getPolicyOwner())
                .build();

        InsuranceCard card4 = cardService.makeCard()
                .cardNumber("0000000004")
                .expireDate(LocalDate.of(2024, 7, 5))
                .cardHolder(c4)
                .policyOwner(c4.getPolicyOwner())
                .build();

        InsuranceCard card5 = cardService.makeCard()
                .cardNumber("0000000005")
                .expireDate(LocalDate.of(2024, 8, 5))
                .cardHolder(c5)
                .policyOwner(c5.getPolicyOwner())
                .build();

        InsuranceCard card6 = cardService.makeCard()
                .cardNumber("0000000006")
                .expireDate(LocalDate.of(2024, 9, 5))
                .cardHolder(c7)
                .policyOwner(c7.getPolicyOwner())
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
                .status(ClaimStatus.NEW)
                .bankingInfo("TPBank-NguyenTheVinh-1234567")
                .build();

        Claim claim3 = claimService.makeClaim()
                .id("f-000003")
                .insuredPerson(c3)
                .claimDate(LocalDate.of(2024, 11, 14))
                .examDate(LocalDate.of(2024, 9, 25))
                .claimAmount(500)
                .status(ClaimStatus.NEW)
                .bankingInfo("TPBank-NguyenBaThai-7654321")
                .build();

        Claim claim4 = claimService.makeClaim()
                .id("f-000004")
                .insuredPerson(c1)
                .claimDate(LocalDate.of(2024, 1, 1))
                .examDate(LocalDate.of(2024, 10, 5))
                .claimAmount(5000)
                .status(ClaimStatus.NEW)
                .bankingInfo("TPBank-CaoBaQuat-321654")
                .build();

        Claim claim5 = claimService.makeClaim()
                .id("f-000005")
                .insuredPerson(c7)
                .claimDate(LocalDate.of(2024, 3, 15))
                .examDate(LocalDate.of(2024, 4, 10))
                .claimAmount(2500)
                .status(ClaimStatus.NEW)
                .bankingInfo("TPBank-TranVanA-9876543")
                .build();

//        c2.addClaim(claim1);
//        c2.addClaim(claim2);
//        c3.addClaim(claim3);
//        c4.addClaim(claim4);
//        c7.addClaim(claim5);

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

        Request r1 = s1.makeRequest(c1, claim1, "Check insurance details");
        Proposal p1 = s1.propose(m1, claim1, "Review this claim");
        Proposal p2 = s1.propose(m1, claim2, "Please check this claim");

        CustomerRepository customerRepository = new CustomerRepository();
        InsuranceCardRepository cardRepository = new InsuranceCardRepository();
        ClaimRepository claimRepository = new ClaimRepository();
        ProviderRepository providerRepository = new ProviderRepository();
        ProposalRepository proposalRepository = new ProposalRepository();
        RequestRepository requestRepository = new RequestRepository();

        customerRepository.add(c1, c2, c3, c4, c5, c6, c7);
//        cardRepository.add(card1, card2, card3, card4, card5, card6);
//        claimRepository.add(claim1, claim2, claim3, claim4, claim5);

        providerRepository.add(m1);
        providerRepository.add(s1);
        requestRepository.add(r1);
        proposalRepository.add(p1);
        proposalRepository.add(p2);

        customerRepository.close();
        cardRepository.close();
        claimRepository.close();
        providerRepository.close();
        proposalRepository.close();
        requestRepository.close();
    }
}
