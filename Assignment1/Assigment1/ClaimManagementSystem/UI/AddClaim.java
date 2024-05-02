package ClaimManagementSystem.UI;

import ClaimManagementSystem.ClaimSystem;
import ClaimManagementSystem.DataManager;
import ClaimManagementSystem.Model.Claim;
import ClaimManagementSystem.Model.Customer;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AddClaim {
    public static void run() {
        displayOptions();
    }

    private static void displayOptions() {
        System.out.println("Please enter the customer id that you want to add claims:");

        Scanner scanner = new Scanner(System.in);
        String customerID = scanner.nextLine();

        if (DataManager.getCustomer(customerID) != null) {
            String claimID = getId(scanner);
            LocalDate claimDate = getClaimDate(scanner);
            Customer insuredPerson = DataManager.getCustomer(customerID);
            String cardNumber = getCardNumber(scanner);
            LocalDate examDate = getExamDate(scanner);
            List<String> document = getDocuments(scanner);
            double claimAmount = getClaimAmount(scanner);
            Claim.ClaimStatus claimStatus = getClaimStatus(scanner);
            String bankName = getBankName(scanner);
            String receiverName = getReceiverName(scanner);
            String bankNumber = getBankNumber(scanner);
            // Create the claim
            Claim claim = new Claim(claimID, claimDate, insuredPerson, cardNumber,
                                    examDate, document, claimAmount, claimStatus,
                                    bankName, receiverName, bankNumber);
            // Add the claim into system data.
            DataManager.getClaims().put(claimID, claim);
            ClaimSystem.displayClaims();

            // Add the claim into the database
            DataManager.writeClaim(claim);
            System.out.println("CLAIM ADDED!");
            HomePage.run();
        } else {

        }
    }

    private static String getId(Scanner scanner) {
        String id;
        while (true) {
            System.out.println("Enter claim id: ");
            id = scanner.nextLine();
            if (id.matches("^f-\\d{10}$")) {
                if (DataManager.getClaims().containsKey(id)) {
                    System.out.println("There is already a claim with this id!");
                } else return id;

            } else {
                System.out.println("ID is invalid. Please enter a valid claim ID.");
            }
        }
    }

    private static LocalDate getClaimDate(Scanner scanner) {
        LocalDate date;
        while (true) {
            System.out.println("Enter claim date (yyyy-MM-dd): ");
            try {
                date = LocalDate.parse(scanner.nextLine());
                return date;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
            }
        }
    }

    private static String getCardNumber(Scanner scanner) {
        while (true) {
            System.out.println("Enter card number:");
            String cardNumber = scanner.nextLine();
            if (cardNumber.matches("^\\d{10}$")) {
                return cardNumber;
            } else {
                System.out.println("Invalid card number format. Please enter a 10-digit card number.");
            }
        }
    }

    private static LocalDate getExamDate(Scanner scanner) {
        LocalDate date;
        while (true) {
            System.out.println("Enter exam date (yyyy-MM-dd): ");
            try {
                date = LocalDate.parse(scanner.nextLine());
                return date;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
            }
        }
    }

    private static List<String> getDocuments(Scanner scanner) {
        List<String> documents = new ArrayList<>();
        while (true) {
            System.out.println("Enter document: ('q' to exit)");
            String document = scanner.nextLine();
            if (document.equals("q")) return documents;
            if (document.matches("^f-\\d{10}_\\d{10}_\\w+\\.pdf$")) {
                documents.add(document);
            } else {
                System.out.println("Invalid document format. Please follow the format: 'ClaimID_CardNumber_documentName.pdf'");
            }
        }
    }

    private static double getClaimAmount(Scanner scanner) {
        while (true) {
            System.out.println("Enter claim amount:");
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private static Claim.ClaimStatus getClaimStatus(Scanner scanner) {
        while (true) {
            System.out.println("Enter claim status (New/Processing/Done):");
            String userInput = scanner.nextLine().trim().toLowerCase(); // Convert input to lowercase and trim whitespace
            switch (userInput) {
                case "new":
                    return Claim.ClaimStatus.New;
                case "processing":
                    return Claim.ClaimStatus.Processing;
                case "done":
                    return Claim.ClaimStatus.Done;
                default:
                    System.out.println("Invalid status. Please enter New, Processing, or Done.");
            }
        }
    }

    private static String getBankName(Scanner scanner) {
        while (true) {
            System.out.println("Enter receiver bank name:");
            String bankName = scanner.nextLine();
            if (!bankName.isEmpty()) {
                return bankName;
            }
        }
    }

    private static String getReceiverName(Scanner scanner) {
        while (true) {
            System.out.println("Enter receiver name:");
            String receiverName = scanner.nextLine();
            if (!receiverName.isEmpty()) {
                return receiverName;
            }
        }
    }

    private static String getBankNumber(Scanner scanner) {
        while (true) {
            System.out.println("Enter credit card number:");
            String bankNumber = scanner.nextLine();
            if (!bankNumber.isEmpty()) {
                return bankNumber;
            }
        }
    }



}
