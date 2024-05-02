package ClaimManagementSystem;

import ClaimManagementSystem.Model.*;
import ClaimManagementSystem.Utility.CustomerComparator;

import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
//        LocalDate date = LocalDate.of(2024, 3,15);
//        System.out.println(date);
//        Dependant dependant1 = new Dependant("c-1111111", "Nguyen The Vinh");
//        PolicyHolder policyHolder1 = new PolicyHolder("c-2222222", "Nguyen Thanh Tung");
//        InsuranceCard insuranceCard = new InsuranceCard("1234567890", dependant1, policyHolder1, date);
//
//        policyHolder1.addDependant(dependant1);
//
//        Claim claim = new Claim("f-0123456789", date, dependant1, "1234567890", date, 100000, Claim.ClaimStatus.New);

//        DataManager.writeCustomer(policyHolder1);
//        DataManager.writeCustomer(dependant1);
//        DataManager.writeInsuranceCard(insuranceCard);
//        DataManager.writeClaim(claim);

        // Load data
        DataManager.readCustomer();
        DataManager.readInsuranceCard();
        DataManager.readClaim();
//
        ClaimSystem.displayCustomers();
        ClaimSystem.displayClaims();
//
        ClaimSystem.run();

//        DataManager.getInsuranceCards().forEach((s, val) -> System.out.println(val));
//        DataManager.getClaims().forEach((s, val) -> System.out.println(val));

//        DataManager.getCustomers().get("c-1111111").delete(claim);
//
//        System.out.println("________DATA IN SYSTEM_________");
//        System.out.println(DataManager.getCustomers().get("c-1111111"));




    }

}
