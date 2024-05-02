package ClaimManagementSystem.UI;

import java.util.Scanner;

public class HomePage {
    public static void run() {
        System.out.println("Welcome to the Claim Management System!");
        System.out.println("=======================================");
        displayOptions();
    }

    private static void displayOptions() {
        System.out.println("Please select an option:");
        System.out.println("1. Add a claim");
        System.out.println("2. Update a claim");
        System.out.println("3. Delete a claim");
        System.out.println("4. Find claim of a customer");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        switch (choice) {
            case 1:
                AddClaim.run();
                break;
            case 2:
                // Process insurance claims logic
                break;
            case 3:
                // View reports and statistics logic
                break;
            case 4:
                break;
            case 5:
                System.out.println("Thank you for using the Claim Management System. Goodbye!");
                System.exit(0);
            default:
                System.out.println();
                System.out.println("⚠️ Invalid choice. Please select a valid option.");
                displayOptions();
                break;
        }
    }
}
