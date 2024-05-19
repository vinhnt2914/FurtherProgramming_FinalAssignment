package com.example.finalassingment.test;
/**
 * @author Group 11
 */
import com.example.finalassingment.model.items.Claim;
import com.example.finalassingment.model.items.Proposal;
import com.example.finalassingment.model.provider.InsuranceManager;
import com.example.finalassingment.model.provider.InsuranceSurveyor;
import com.example.finalassingment.repository.impl.ClaimRepository;
import com.example.finalassingment.repository.impl.ProposalRepository;
import com.example.finalassingment.repository.impl.ProviderRepository;

import java.util.Scanner;

public class TestHibernate {
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
