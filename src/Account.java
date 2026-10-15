import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Account - Abstract Base Class representing a general Bank Account.
 * 
 * Core OOP Principles Demonstrated:
 * 1. Abstraction: Defines the abstract template and contracts for bank accounts.
 * 2. Encapsulation: State is protected (private/protected) with validated accessors.
 * 3. Constructor Overloading & 'this' keyword: Demonstrates constructor chaining
 *    and variable shadowing resolution.
 * 4. Method Overloading: Provides multiple variants of deposit(), withdraw(), and transfer().
 * 5. Static vs Instance Methods: Includes a static method to illustrate method hiding vs overriding.
 */
public abstract class Account {
    // Encapsulated instance variables
    private final String accountNumber;
    private String accountHolderName;
    private double balance;
    private final List<Transaction> transactionHistory;
    private int transactionCounter;

    // Static constant & static method (Class-level member)
    private static final String BANK_NAME = "Apex National Bank";

    /**
     * Constructor 1: Overloaded constructor with initial balance.
     * Demonstrates 'this' keyword to resolve variable shadowing between parameters and fields.
     */
    public Account(String accountNumber, String accountHolderName, double initialBalance) {
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Account number cannot be null or empty.");
        }
        if (accountHolderName == null || accountHolderName.trim().isEmpty()) {
            throw new IllegalArgumentException("Account holder name cannot be null or empty.");
        }
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative.");
        }

        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
        this.transactionCounter = 1;

        if (initialBalance > 0) {
            recordTransaction(TransactionType.DEPOSIT, initialBalance, initialBalance, "Initial Account Opening Deposit");
        }
    }

    /**
     * Constructor 2: Overloaded constructor without initial balance (defaults to 0.0).
     * Demonstrates constructor chaining using 'this(...)'.
     */
    public Account(String accountNumber, String accountHolderName) {
        this(accountNumber, accountHolderName, 0.0); // Constructor chaining
    }

    // --- Getters and Setters (Encapsulation) ---

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        if (accountHolderName == null || accountHolderName.trim().isEmpty()) {
            throw new IllegalArgumentException("Account holder name cannot be empty.");
        }
        this.accountHolderName = accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    /**
     * Protected setter for balance so only subclasses or internal operations modify it.
     */
    protected void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactionHistory() {
        return Collections.unmodifiableList(transactionHistory);
    }

    // --- Static Method (Belongs to Class, NOT instances) ---
    /**
     * Static method to illustrate Interview Question 8:
     * Static methods cannot be overridden in Java; they are resolved at compile time (Method Hiding).
     */
    public static String getBankName() {
        return BANK_NAME;
    }

    // --- Method Overloading: deposit() ---

    /**
     * Overloaded deposit variant 1: Takes amount with default description.
     */
    public boolean deposit(double amount) {
        return deposit(amount, "Standard Deposit");
    }

    /**
     * Overloaded deposit variant 2: Takes amount and a custom description.
     */
    public boolean deposit(double amount, String description) {
        if (amount <= 0) {
            System.out.println("[ERROR] Deposit Error: Amount must be greater than zero. Provided: Rs." + amount);
            return false;
        }

        this.balance += amount;
        recordTransaction(TransactionType.DEPOSIT, amount, this.balance, description);
        System.out.println("[SUCCESS] Deposit Successful! Rs." + String.format("%.2f", amount) +
                " credited to [" + accountNumber + "]. Updated Balance: Rs." + String.format("%.2f", this.balance));
        return true;
    }

    // --- Method Overloading: withdraw() ---

    /**
     * Overloaded withdraw variant 1: Calls polymorphic abstract withdraw with default description.
     */
    public boolean withdraw(double amount) {
        return withdraw(amount, "Standard Withdrawal");
    }

    /**
     * Abstract withdraw method: Must be overridden by subclasses (SavingsAccount, CurrentAccount).
     * This establishes Runtime Polymorphism / Dynamic Method Dispatch.
     */
    public abstract boolean withdraw(double amount, String description);

    // --- Method Overloading: transfer() ---

    /**
     * Overloaded transfer variant 1: Default remarks.
     */
    public boolean transfer(Account destinationAccount, double amount) {
        return transfer(destinationAccount, amount, "Fund Transfer");
    }

    /**
     * Overloaded transfer variant 2: Custom remarks.
     * Executes atomic transfer between accounts.
     */
    public boolean transfer(Account destinationAccount, double amount, String remarks) {
        if (destinationAccount == null) {
            System.out.println("[ERROR] Transfer Error: Destination account cannot be null.");
            return false;
        }
        if (destinationAccount.getAccountNumber().equalsIgnoreCase(this.accountNumber)) {
            System.out.println("[ERROR] Transfer Error: Cannot transfer funds to the same account.");
            return false;
        }
        if (amount <= 0) {
            System.out.println("[ERROR] Transfer Error: Amount must be greater than zero. Provided: Rs." + amount);
            return false;
        }

        // Attempt withdrawal from source account using polymorphic withdraw()
        boolean debitSuccess = this.withdraw(amount, "Transfer to " + destinationAccount.getAccountNumber() + " - " + remarks);
        if (!debitSuccess) {
            System.out.println("[ERROR] Transfer Failed: Debit failed from source account [" + this.accountNumber + "].");
            return false;
        }

        // Credit target account
        destinationAccount.balance += amount;
        destinationAccount.recordTransaction(
                TransactionType.TRANSFER_IN,
                amount,
                destinationAccount.balance,
                "Transfer from " + this.accountNumber + " - " + remarks
        );

        System.out.println("[SUCCESS] Transfer Completed Successfully!");
        System.out.println("   Sent Rs." + String.format("%.2f", amount) + " from [" + this.accountNumber + "] to [" + destinationAccount.getAccountNumber() + "].");
        return true;
    }

    // --- Transaction Recording Helper ---

    protected void recordTransaction(TransactionType type, double amount, double balanceAfter, String description) {
        String txnId = String.format("TXN-%s-%04d", accountNumber, transactionCounter++);
        Transaction txn = new Transaction(txnId, type, amount, balanceAfter, description);
        transactionHistory.add(txn);
    }

    // --- Account Details & Statement Presentation ---

    public abstract String getAccountType();

    public void displayAccountSummary() {
        System.out.println("--------------------------------------------------");
        System.out.println("Account Number : " + accountNumber);
        System.out.println("Account Type   : " + getAccountType());
        System.out.println("Account Holder : " + accountHolderName);
        System.out.printf("Current Balance: Rs.%,.2f%n", balance);
    }

    public void printAccountStatement() {
        System.out.println("\n==========================================================================================");
        System.out.println("                      ACCOUNT STATEMENT - " + BANK_NAME.toUpperCase());
        System.out.println("==========================================================================================");
        System.out.println(" Account Number: " + accountNumber + " | Type: " + getAccountType());
        System.out.println(" Holder Name   : " + accountHolderName);
        System.out.printf(" Current Balance: Rs.%,.2f%n", balance);
        System.out.println("------------------------------------------------------------------------------------------");
        System.out.printf("%-12s | %-19s | %-15s | %-14s | %-15s | %s%n",
                "Txn ID", "Date & Time", "Type", "Amount", "Balance After", "Description");
        System.out.println("------------------------------------------------------------------------------------------");

        if (transactionHistory.isEmpty()) {
            System.out.println("   No transactions recorded yet.");
        } else {
            for (Transaction txn : transactionHistory) {
                System.out.println(txn);
            }
        }
        System.out.println("==========================================================================================\n");
    }
}
