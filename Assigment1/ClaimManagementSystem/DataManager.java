package ClaimManagementSystem;

import ClaimManagementSystem.Model.*;
import ClaimManagementSystem.Utility.IDComparator;
import ClaimManagementSystem.Utility.CustomerComparator;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DataManager {

    private static TreeSet<Customer> customers = new TreeSet<>(new CustomerComparator());
    private static TreeMap<String, InsuranceCard> insuranceCards = new TreeMap<>(new IDComparator());
    private static TreeMap<String, Claim> claims = new TreeMap<>(new IDComparator());
    private static final String CUSTOMER_FILE_PATH = "Assigment1/ClaimManagementSystem/Data/customers.txt";
    private static final String INSURANCE_FILE_PATH = "Assigment1/ClaimManagementSystem/Data/insurances.txt";
    private static final String CLAIM_FILE_PATH = "Assigment1/ClaimManagementSystem/Data/claims.txt";
    public static void readCustomer() {
        File file = new File(CUSTOMER_FILE_PATH);
        // If file doesn't exists, we create a new file.
        try {
            if (file.createNewFile()) {
                System.out.println("Customers file created!");
            }
            // If file already exists, read from file and populate to ClaimProcessSystem
            else {
                loadCustomer(file);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Since we have to add dependants to policy holder. There will be cases where the policy
     * holder appear before the dependants in the text file. Because reading is linear, getCustomer()
     * can only return customers that are read before. So a practical approach is to load all
     * dependants first before policy holders. Another approach is to restructure text file so that.
     * All dependants are written first before policy holders.
     * */
    private static void loadCustomer(File file) {
        Map<String, Dependant> dependants = new HashMap<>();
        Map<String, PolicyHolder> policyHolders = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while (reader.ready()) {
                String[] str = reader.readLine().split(",");
                customers.add(createCustomer(str));
            }

//            customers.putAll();
//            customers.
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Customer createCustomer(String[] str) {
        String type = str[0];
        String id = str[1];
        String name = str[2];

        // If is dependant
        if (type.equals("D")) {
            return new Dependant(id, name);
        } else {
            PolicyHolder customer = new PolicyHolder(id, name);
            // Add all dependants if there are any
            for (int i = 3; i < str.length; i++) {
                if (str[i].startsWith("c-")) {
                    customer.addDependant((Dependant) getCustomer(str[i]));
                }
            }
            return customer;
        }
    }

    public static Customer getCustomer(String id) {
        for (Customer customer : customers) {
            if (customer.getId().equals(id))
                return customer;
        }
        return null;
    }

//    public static void writeCustomer(Customer customer) {
//        try {
//            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(CUSTOMER_FILE_PATH, true));
//            StringBuffer content = new StringBuffer();
//            if (customer instanceof PolicyHolder) {
//                content.append("PH,");
//            } else if (customer instanceof Dependant) {
//                content.append("D,");
//            }
//            content.append(customer.toData());
//            content.append("\n");
//            bufferedWriter.write(content.toString());
//            bufferedWriter.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public static void overWriteCustomer() {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(CUSTOMER_FILE_PATH));
            StringBuffer content = new StringBuffer();

            for (Customer customer : DataManager.getCustomers()) {
                if (customer instanceof PolicyHolder) {
                    content.append("PH,");
                } else if (customer instanceof Dependant) {
                    content.append("D,");
                }
                content.append(customer.toData());
                System.out.println("Content: " + content);
                content.append("\n");
            }

            bufferedWriter.write(content.toString());
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void readInsuranceCard() {
        File file = new File(INSURANCE_FILE_PATH);
        // If file doesn't exists, we create a new file.
        try {
            if (file.createNewFile()) {
                System.out.println("Insurances file created!");
            }
            // If file already exists, read from file and populate to ClaimProcessSystem
            else {
                loadInsuranceCard(file);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void loadInsuranceCard(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while (reader.ready()) {
                String[] str = reader.readLine().split(",");
                insuranceCards.put(str[0], createCard(str));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static InsuranceCard getInsuranceCard(String id) {
        return insuranceCards.get(id);
    }

    /**
     * <p>
     *     Since we are getting the customer from the HashMap itself. They will be pointing
     *     to the same object.
     * </p>
     * @param str array of attributes taken from the txt file
     * @return Insurance card of the input id
     * */
    private static InsuranceCard createCard(String[] str) {
        String cardNumber = str[0];
        Customer cardHolder = getCustomer(str[1]);
        Customer policyOwner = getCustomer(str[2]);
        LocalDate expirationDate = LocalDate.parse(str[3]);
        return new InsuranceCard(cardNumber, cardHolder, (PolicyHolder) policyOwner, expirationDate);
    }

    public static void writeInsuranceCard(InsuranceCard card) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(INSURANCE_FILE_PATH, true));
            StringBuffer content = new StringBuffer();
            content.append(card.toData());
            content.append("\n");
            bufferedWriter.write(content.toString());
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void readClaim() {
        File file = new File(CLAIM_FILE_PATH);
        // If file doesn't exists, we create a new file.
        try {
            if (file.createNewFile()) {
                System.out.println("Claims file created!");
            }
            // If file already exists, read from file and populate to ClaimProcessSystem
            else {
                loadClaim(file);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void loadClaim(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while (reader.ready()) {
                String[] str = reader.readLine().split(",");
                claims.put(str[0], createClaim(str));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Claim getClaim(String id) {
        return claims.get(id);
    }

    private static Claim createClaim(String[] str) {
        String id = str[0];
        LocalDate claimDate = LocalDate.parse(str[1]);
        Customer insuredPerson = getCustomer(str[2]);
        String cardNumber = str[3];
        LocalDate examDate = LocalDate.parse(str[4]);

        int i = 5;
        List<String> documents = new ArrayList<>();
        while (i < str.length) {
            if (str[i].endsWith(".pdf")) {
                documents.add(str[i]);
                i++;
            } else break;
        }

        double claimAmount = Double.parseDouble(str[i++]);
        Claim.ClaimStatus claimStatus = Claim.ClaimStatus.valueOf(str[i++]);
        String bankName = str[i++];
        String receiverName = str[i++];
        String bankNumber = str[i];

        return new Claim(id, claimDate, insuredPerson, cardNumber, examDate, documents, claimAmount, claimStatus, bankName, receiverName, bankNumber);
    }

    public static void writeClaim(Claim claim) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(CLAIM_FILE_PATH, true));
            StringBuffer content = new StringBuffer();
            content.append(claim.toData());
            content.append("\n");
            bufferedWriter.write(content.toString());
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static TreeSet<Customer> getCustomers() {
        return customers;
    }

    public static Map<String, InsuranceCard> getInsuranceCards() {
        return insuranceCards;
    }

    public static Map<String, Claim> getClaims() {
        return claims;
    }
}
