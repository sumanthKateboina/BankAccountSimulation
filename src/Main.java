import java.util.Scanner;

/**
 * Main - Entry point for the Bank Account Simulation Console Application.
 * 
 * Features:
 * - Interactive Terminal CLI Menu
 * - Account Creation (Savings / Current)
 * - Deposits, Withdrawals, Inter-Account Transfers
 * - Transaction Statement Ledger
 * - Automated Real-World Simulation runner
 * - Built-in Interview Questions & OOP Reference Guide
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Bank bank = new Bank("Apex National Bank");

    public static void main(String[] args) {
        // Seed realistic accounts for immediate testing
        bank.seedSampleAccounts();

        // Check if headless or demo flag passed
        if (args.length > 0 && (args[0].equalsIgnoreCase("--demo") || args[0].equalsIgnoreCase("--test"))) {
            runAutomatedSimulation();
            return;
        }

        printBanner();

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readIntChoice("Enter your choice (1-11): ");

            switch (choice) {
                case 1:
                    handleOpenAccount();
                    break;
                case 2:
                    handleDeposit();
                    break;
                case 3:
                    handleWithdraw();
                    break;
                case 4:
                    handleTransfer();
                    break;
                case 5:
                    handleCheckBalance();
                    break;
                case 6:
                    handlePrintStatement();
                    break;
                case 7:
                    handleApplyInterest();
                    break;
                case 8:
                    bank.displayAllAccounts();
                    break;
                case 9:
                    runAutomatedSimulation();
                    break;
                case 10:
                    displayInterviewQuestionsGuide();
                    break;
                case 11:
                    System.out.println("\nThank you for using " + Account.getBankName() + " Simulation. Goodbye!\n");
                    running = false;
                    break;
                default:
                    System.out.println("[ERROR] Invalid option. Please select a valid number between 1 and 11.");
            }
        }
    }

    private static void printBanner() {
        System.out.println("==========================================================================================");
        System.out.println("                   APEX NATIONAL BANK - ACCOUNT SIMULATION SYSTEM                         ");
        System.out.println("                  Simulating Real-World Banking with Java OOP Principles                  ");
        System.out.println("==========================================================================================");
        System.out.println("  Core Concepts: Classes & Objects | Encapsulation | Inheritance | Polymorphism | History ");
        System.out.println("==========================================================================================\n");
    }

    private static void printMenu() {
        System.out.println("------------------------------------ MAIN MENU -------------------------------------------");
        System.out.println(" [1]  Open New Bank Account (Savings / Current)");
        System.out.println(" [2]  Deposit Funds (Demonstrating Method Overloading)");
        System.out.println(" [3]  Withdraw Funds (Demonstrating Runtime Polymorphism & Overriding)");
        System.out.println(" [4]  Transfer Funds Between Accounts");
        System.out.println(" [5]  Check Account Balance & Details");
        System.out.println(" [6]  View Detailed Transaction Statement / Ledger");
        System.out.println(" [7]  Apply Interest to All Savings Accounts");
        System.out.println(" [8]  Display All Bank Accounts");
        System.out.println(" [9]  Run Comprehensive Real-World Simulation (Automated Test Demo)");
        System.out.println(" [10] View Java OOP Concepts & 10 Interview Questions Guide");
        System.out.println(" [11] Exit");
        System.out.println("------------------------------------------------------------------------------------------");
    }

    // --- Menu Handlers ---

    private static void handleOpenAccount() {
        System.out.println("\n--- Open a New Bank Account ---");
        System.out.println("Select Account Type:");
        System.out.println("  1. Savings Account (Interest-bearing, Minimum balance protection)");
        System.out.println("  2. Current Account (Commercial, Overdraft facility)");
        int typeChoice = readIntChoice("Choose type (1 or 2): ");

        if (typeChoice != 1 && typeChoice != 2) {
            System.out.println("[ERROR] Invalid account type selected.");
            return;
        }

        System.out.print("Enter Account Holder Full Name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("[ERROR] Account holder name cannot be empty.");
            return;
        }

        if (typeChoice == 1) {
            double deposit = readDouble("Enter Initial Deposit (Min Rs.500): ");
            double interest = readDouble("Enter Annual Interest Rate % (e.g. 4.5): ");
            double minBal = readDouble("Enter Minimum Balance requirement (e.g. 500): ");

            try {
                SavingsAccount sa = bank.openSavingsAccount(name, deposit, interest, minBal);
                System.out.println("[SUCCESS] Savings Account opened successfully!");
                sa.displayAccountSummary();
            } catch (IllegalArgumentException e) {
                System.out.println("[ERROR] Failed to open account: " + e.getMessage());
            }
        } else {
            double deposit = readDouble("Enter Initial Deposit (e.g. 1000): ");
            double overdraft = readDouble("Enter Approved Overdraft Limit (e.g. 5000): ");

            try {
                CurrentAccount ca = bank.openCurrentAccount(name, deposit, overdraft);
                System.out.println("[SUCCESS] Current Account opened successfully!");
                ca.displayAccountSummary();
            } catch (IllegalArgumentException e) {
                System.out.println("[ERROR] Failed to open account: " + e.getMessage());
            }
        }
    }

    private static void handleDeposit() {
        System.out.println("\n--- Deposit Money ---");
        System.out.print("Enter Account Number (e.g. APEX-1001): ");
        String accNum = scanner.nextLine().trim();
        Account acc = bank.getAccount(accNum);

        if (acc == null) {
            System.out.println("[ERROR] Account not found: " + accNum);
            return;
        }

        double amount = readDouble("Enter Deposit Amount: Rs.");
        System.out.print("Enter Description / Remark (press Enter for default): ");
        String desc = scanner.nextLine().trim();

        // Demonstrates Method Overloading
        if (desc.isEmpty()) {
            acc.deposit(amount); // Overloaded variant 1
        } else {
            acc.deposit(amount, desc); // Overloaded variant 2
        }
    }

    private static void handleWithdraw() {
        System.out.println("\n--- Withdraw Money ---");
        System.out.print("Enter Account Number (e.g. APEX-1001): ");
        String accNum = scanner.nextLine().trim();
        Account acc = bank.getAccount(accNum);

        if (acc == null) {
            System.out.println("[ERROR] Account not found: " + accNum);
            return;
        }

        double amount = readDouble("Enter Withdrawal Amount: Rs.");
        System.out.print("Enter Purpose / Remark (press Enter for default): ");
        String desc = scanner.nextLine().trim();

        // Demonstrates Runtime Polymorphism: withdraw() dynamically calls
        // SavingsAccount.withdraw() or CurrentAccount.withdraw()
        if (desc.isEmpty()) {
            acc.withdraw(amount);
        } else {
            acc.withdraw(amount, desc);
        }
    }

    private static void handleTransfer() {
        System.out.println("\n--- Fund Transfer ---");
        System.out.print("Enter Source Account Number: ");
        String fromAcc = scanner.nextLine().trim();
        System.out.print("Enter Destination Account Number: ");
        String toAcc = scanner.nextLine().trim();
        double amount = readDouble("Enter Transfer Amount: Rs.");
        System.out.print("Enter Transfer Remarks (press Enter for default): ");
        String remarks = scanner.nextLine().trim();

        if (remarks.isEmpty()) {
            bank.transfer(fromAcc, toAcc, amount, "Fund Transfer");
        } else {
            bank.transfer(fromAcc, toAcc, amount, remarks);
        }
    }

    private static void handleCheckBalance() {
        System.out.print("\nEnter Account Number: ");
        String accNum = scanner.nextLine().trim();
        Account acc = bank.getAccount(accNum);

        if (acc == null) {
            System.out.println("[ERROR] Account not found: " + accNum);
            return;
        }

        acc.displayAccountSummary();
    }

    private static void handlePrintStatement() {
        System.out.print("\nEnter Account Number to View Statement: ");
        String accNum = scanner.nextLine().trim();
        Account acc = bank.getAccount(accNum);

        if (acc == null) {
            System.out.println("[ERROR] Account not found: " + accNum);
            return;
        }

        acc.printAccountStatement();
    }

    private static void handleApplyInterest() {
        bank.applyInterestToAllSavingsAccounts();
    }

    // --- Automated Real-World Simulation Runner ---

    public static void runAutomatedSimulation() {
        System.out.println("\n==========================================================================================");
        System.out.println("               EXECUTING AUTOMATED REAL-WORLD BANKING SIMULATION                          ");
        System.out.println("==========================================================================================");

        Bank simBank = new Bank("Apex National Bank (Simulated Branch)");

        System.out.println("\n[SCENARIO 1: Object Instantiation & Constructor Overloading]");
        System.out.println("Opening Savings Account for 'Rohit Verma' with initial balance Rs.10,000, 4.5% interest, Rs.1,000 min balance...");
        SavingsAccount rohitSavings = simBank.openSavingsAccount("Rohit Verma", 10000.0, 4.5, 1000.0);
        rohitSavings.displayAccountSummary();

        System.out.println("\nOpening Current Account for 'NexGen Traders' with Rs.5,000 balance and Rs.10,000 overdraft limit...");
        CurrentAccount nexGenCurrent = simBank.openCurrentAccount("NexGen Traders", 5000.0, 10000.0);
        nexGenCurrent.displayAccountSummary();

        System.out.println("\n[SCENARIO 2: Method Overloading (deposit)]");
        System.out.println("1. Rohit deposits Rs.3,000 (default description variant):");
        rohitSavings.deposit(3000.0);

        System.out.println("2. Rohit deposits Rs.5,000 with custom remark (overloaded variant):");
        rohitSavings.deposit(5000.0, "Freelance Client Payment");

        System.out.println("\n[SCENARIO 3: Encapsulation & Validation]");
        System.out.println("Attempting negative deposit of Rs.-500 (Encapsulated guard):");
        rohitSavings.deposit(-500.0);

        System.out.println("\n[SCENARIO 4: Method Overriding & Runtime Polymorphism (Savings Account Withdraw)]");
        System.out.println("Rohit has balance: Rs." + rohitSavings.getBalance() + " with min balance: Rs." + rohitSavings.getMinimumBalance());
        System.out.println("a) Rohit attempts valid withdrawal of Rs.6,000:");
        rohitSavings.withdraw(6000.0, "Laptop EMI Payment");

        System.out.println("\nb) Rohit attempts excessive withdrawal of Rs.11,500 that violates minimum balance (Rs.1,000):");
        rohitSavings.withdraw(11500.0, "Luxury Watch");

        System.out.println("\n[SCENARIO 5: Method Overriding in Current Account (Overdraft Facility)]");
        System.out.println("NexGen Traders has balance: Rs." + nexGenCurrent.getBalance() + " and overdraft limit: Rs." + nexGenCurrent.getOverdraftLimit());
        System.out.println("a) NexGen withdraws Rs.8,000 (exceeds balance of Rs.5,000, utilizing Rs.3,000 overdraft credit):");
        nexGenCurrent.withdraw(8000.0, "Inventory Supplier Restock");

        System.out.println("\nb) NexGen attempts to withdraw additional Rs.8,000 (exceeds remaining overdraft cushion):");
        nexGenCurrent.withdraw(8000.0, "Bulk Equipment Purchase");

        System.out.println("\n[SCENARIO 6: Inter-Account Funds Transfer & Polymorphic Transaction Recording]");
        System.out.println("Rohit transfers Rs.4,000 to NexGen Traders:");
        rohitSavings.transfer(nexGenCurrent, 4000.0, "B2B Web Services Invoice");

        System.out.println("\n[SCENARIO 7: Interest Calculation & Batch Application]");
        simBank.applyInterestToAllSavingsAccounts();

        System.out.println("\n[SCENARIO 8: Complete Transaction History & Audit Ledger]");
        rohitSavings.printAccountStatement();
        nexGenCurrent.printAccountStatement();

        System.out.println("==========================================================================================");
        System.out.println("               [SUCCESS] AUTOMATED REAL-WORLD SIMULATION COMPLETED!                       ");
        System.out.println("==========================================================================================\n");
    }

    // --- Interview Questions Quick Guide ---

    private static void displayInterviewQuestionsGuide() {
        System.out.println("\n==========================================================================================");
        System.out.println("                         TOP 10 JAVA OOP INTERVIEW QUESTIONS & ANSWERS                     ");
        System.out.println("==========================================================================================");

        System.out.println("\n1. What is inheritance in Java?");
        System.out.println("   Inheritance is an OOP mechanism where a subclass (child) acquires state and behavior");
        System.out.println("   from a superclass (parent) using the 'extends' keyword. It promotes code reusability");
        System.out.println("   and establishes an 'IS-A' relationship (e.g., SavingsAccount IS-A Account).");

        System.out.println("\n2. Why use the 'this' keyword?");
        System.out.println("   'this' is a reference variable that refers to the current invoking object.");
        System.out.println("   Uses: (1) Disambiguate shadowed instance variables (this.balance = balance),");
        System.out.println("   (2) Constructor chaining via this(...) to reuse constructor logic,");
        System.out.println("   (3) Pass current object as a method argument.");

        System.out.println("\n3. What is method overriding vs overloading?");
        System.out.println("   * Method Overloading (Compile-time polymorphism): Same method name, different parameter");
        System.out.println("     list within the same class (e.g., deposit(double) vs deposit(double, String)).");
        System.out.println("   * Method Overriding (Runtime polymorphism): Subclass redefines a method with identical");
        System.out.println("     signature and return type as in its parent class (e.g., SavingsAccount.withdraw()).");

        System.out.println("\n4. What is object instantiation?");
        System.out.println("   Object instantiation is creating a physical concrete instance of a class in JVM heap");
        System.out.println("   memory using the 'new' keyword and invoking a constructor (e.g. Account acc = new SavingsAccount(...)).");

        System.out.println("\n5. Explain single vs multiple inheritance.");
        System.out.println("   * Single Inheritance: A class inherits from exactly one parent class (Class B extends A).");
        System.out.println("     Java directly supports single class inheritance.");
        System.out.println("   * Multiple Inheritance: A class inherits from multiple parents. Java does NOT support");
        System.out.println("     multiple inheritance with classes to avoid the Diamond Problem (ambiguity), but");
        System.out.println("     achieves it using interfaces.");

        System.out.println("\n6. What is encapsulation?");
        System.out.println("   Encapsulation is bundling data (variables) and methods operating on that data into a");
        System.out.println("   single unit while hiding internal state by declaring fields 'private' and providing");
        System.out.println("   controlled access via public getters/setters with validation.");

        System.out.println("\n7. What is constructor overloading?");
        System.out.println("   Defining multiple constructors within the same class, each having a different parameter");
        System.out.println("   list (type, count, or order), allowing flexible object creation depending on data available.");

        System.out.println("\n8. Can we override static methods?");
        System.out.println("   NO. Static methods cannot be overridden because they belong to the class rather than");
        System.out.println("   instances, and method binding happens at compile-time (Method Hiding, not overriding).");

        System.out.println("\n9. What is runtime polymorphism?");
        System.out.println("   Also known as Dynamic Method Dispatch. It occurs when a call to an overridden method is");
        System.out.println("   resolved at runtime based on the actual object type in heap memory, rather than reference type.");

        System.out.println("\n10. Difference between class and object?");
        System.out.println("   * Class: A logical blueprint or template defining variables and methods (no memory allocated).");
        System.out.println("   * Object: A physical, concrete real-world entity created in heap memory representing an");
        System.out.println("     instance of the class with specific state and behavior.");
        System.out.println("==========================================================================================\n");
    }

    // --- Helper Input Utilities ---

    private static int readIntChoice(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Invalid input! Please enter a valid integer number.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                double val = Double.parseDouble(line);
                if (val < 0) {
                    System.out.println("[ERROR] Amount cannot be negative. Try again.");
                    continue;
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Invalid input! Please enter a valid numeric amount (e.g. 1500.50).");
            }
        }
    }
}
