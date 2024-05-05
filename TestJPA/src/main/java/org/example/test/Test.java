package org.example.test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.model.items.Claim;
import org.example.model.items.Proposal;
import org.example.model.provider.InsuranceManager;
import org.example.model.provider.InsuranceSurveyor;
import org.example.repository.impl.ClaimRepository;
import org.example.repository.impl.ProposalRepository;
import org.example.repository.impl.ProviderRepository;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProviderRepository providerRepository = new ProviderRepository();
        ProposalRepository proposalRepository = new ProposalRepository();
        ClaimRepository claimRepository = new ClaimRepository();

        System.out.println("WELCUM TO SYSTEM HEHE");

        System.out.println("Enter claim id: ");
        String id = scanner.nextLine();

        InsuranceSurveyor s1 = (InsuranceSurveyor) providerRepository.findByID(1);
        InsuranceManager m1 = (InsuranceManager) providerRepository.findByID(2);
        Claim c1 = claimRepository.findByID(id);
        Proposal p1 = s1.propose(m1, c1, "TESTING PURPOSE");
        proposalRepository.add(p1);

        providerRepository.close();
        proposalRepository.close();
        claimRepository.close();

//        EntityManagerFactory emFactory1 = Persistence.createEntityManagerFactory("customer_pu");
//        EntityManagerFactory emFactory2 = Persistence.createEntityManagerFactory("customer_pu");
//        EntityManagerFactory emFactory3 = Persistence.createEntityManagerFactory("customer_pu");
//        EntityManager em1 = emFactory1.createEntityManager();
//        EntityManager em2 = emFactory1.createEntityManager();
//        EntityManager em3 = emFactory1.createEntityManager();
//        EntityManager em4 = emFactory1.createEntityManager();
//        EntityManager em5 = emFactory1.createEntityManager();
//        EntityManager em6 = emFactory1.createEntityManager();
//        EntityManager em7 = emFactory1.createEntityManager();
    }
}
