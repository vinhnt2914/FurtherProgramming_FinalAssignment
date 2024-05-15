package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.model.customer.Beneficiary;
import org.example.model.customer.PolicyOwner;
import org.example.model.customer.BeneficiaryBuilder;
import org.example.model.items.Claim;
import org.example.model.items.InsuranceCard;
import org.example.repository.EMFactory;
import org.example.repository.impl.BeneficiaryRepository;
import org.example.repository.impl.ClaimRepository;
import org.example.repository.impl.PolicyOwnerRepository;
import org.example.service.ClaimService;
import org.example.service.CustomerService;
import org.example.service.InsuranceCardService;
import org.example.model.enums.ClaimStatus;

import java.time.LocalDate;

public class Main {

    private static final int BATCH_SIZE = 50;

    public static void main(String[] args) {
        EntityManager em = EMFactory.getInstance().createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            // Create PolicyOwner
            CustomerService customerService = new CustomerService();
            PolicyOwner policyOwner = customerService.makePolicyOwner()
                    .username("policyowner123")
                    .password("password123")
                    .email("policyowner@example.com")
                    .phone("123-456-7890")
                    .address("123 Main St")
                    .fullName("John Doe")
                    .build();

            em.persist(policyOwner);

            // Create InsuranceCard
            InsuranceCardService cardService = new InsuranceCardService();
            InsuranceCard insuranceCard = cardService.makeCard()
                    .cardNumber("1234-5678-9101-1121")
                    .expireDate(LocalDate.of(2025, 12, 31))
                    .policyOwner(policyOwner)
                    .build();

            em.persist(insuranceCard);

            // Create Beneficiary
            Beneficiary beneficiary = new BeneficiaryBuilder()
                    .username("beneficiary123")
                    .password("password123")
                    .email("beneficiary@example.com")
                    .phone("098-765-4321")
                    .address("456 Secondary St")
                    .fullName("Jane Smith")
                    .build();
            beneficiary.setPolicyOwner(policyOwner);
            beneficiary.setInsuranceCard(insuranceCard);

            em.persist(beneficiary);

            // Create Claim
            ClaimService claimService = new ClaimService();
            Claim claim = claimService.makeClaim()
                    .id("claim123")
                    .insuredPerson(beneficiary)
                    .claimDate(LocalDate.now())
                    .examDate(LocalDate.now().plusDays(7))
                    .claimAmount(500.00)
                    .status(ClaimStatus.NEW)
                    .bankingInfo("Bank XYZ, Account: 123456789")
                    .build();

            em.persist(claim);

            // Flush and clear the persistence context periodically to manage memory
            em.flush();
            em.clear();

            transaction.commit();

            System.out.println("PolicyOwner: " + policyOwner);
            System.out.println("Beneficiary: " + beneficiary);
            System.out.println("InsuranceCard: " + insuranceCard);
            System.out.println("Claim: " + claim);

        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
