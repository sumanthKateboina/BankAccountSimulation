import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Bank - Core Management Service.
 * Manages accounts, facilitates deposits, withdrawals, fund transfers, and interest batch processing.
 */
public class Bank {
    private final String bankName;
    private final Map<String, Account> accounts;
    private int nextAccountNumber;

    public Bank(String bankName) {
        this.bankName = bankName;
        this.accounts = new LinkedHashMap<>();
        this.nextAccountNumber = 1001;
    }

    public String getBankName() {
        return bankName;
    }

    /**
     * Generates a unique account number, e.g. APEX-1001
     */
    public synchronized String generateAccountNumber() {
        return "APEX-" + (nextAccountNumber++);
    }

    /**
     * Creates and registers a new Savings Account.
     */
    public SavingsAccount openSavingsAccount(String holderName, double initialDeposit, double interestRate, double minBalance) {
        String accNum = generateAccountNumber();
        SavingsAccount account = new SavingsAccount(accNum, holderName, initialDeposit, interestRate, minBalance);
        accounts.put(accNum, account);
        return account;
    }

    /**
     * Creates and registers a new Current Account.
     */
    public CurrentAccount openCurrentAccount(String holderName, double initialDeposit, double overdraftLimit) {
        String accNum = generateAccountNumber();
        CurrentAccount account = new CurrentAccount(accNum, holderName, initialDeposit, overdraftLimit);
        accounts.put(accNum, account);
        return account;
    }

    /**
     * Retrieves an account by account number (case-insensitive).
     */
    public Account getAccount(String accountNumber) {
        if (accountNumber == null) return null;
        for (Map.Entry<String, Account> entry : accounts.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(accountNumber.trim())) {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * Returns all registered accounts.
     */
    public Collection<Account> getAllAccounts() {
        return accounts.values();
    }

    /**
     * Performs a deposit into the specified account.
     */
    public boolean deposit(String accountNumber, double amount, String description) {
        Account account = getAccount(accountNumber);
        if (account == null) {
            System.out.println("[ERROR] Account not found with number [" + accountNumber + "].");
            return false;
        }
        return account.deposit(amount, description);
    }

    /**
     * Performs a withdrawal from the specified account.
     * Demonstrates Runtime Polymorphism: invokes SavingsAccount.withdraw() or CurrentAccount.withdraw()
     * based on actual instance type.
     */
    public boolean withdraw(String accountNumber, double amount, String description) {
        Account account = getAccount(accountNumber);
        if (account == null) {
            System.out.println("[ERROR] Account not found with number [" + accountNumber + "].");
            return false;
        }
        return account.withdraw(amount, description);
    }

    /**
     * Executes a funds transfer between two accounts.
     */
    public boolean transfer(String fromAccountNumber, String toAccountNumber, double amount, String remarks) {
        Account source = getAccount(fromAccountNumber);
        Account destination = getAccount(toAccountNumber);

        if (source == null) {
            System.out.println("[ERROR] Sender account not found: [" + fromAccountNumber + "].");
            return false;
        }
        if (destination == null) {
            System.out.println("[ERROR] Beneficiary account not found: [" + toAccountNumber + "].");
            return false;
        }

        return source.transfer(destination, amount, remarks);
    }

    /**
     * Applies interest across all eligible Savings accounts.
     */
    public int applyInterestToAllSavingsAccounts() {
        int count = 0;
        System.out.println("\n--- [BATCH] Interest Application Process ---");
        for (Account account : accounts.values()) {
            if (account instanceof SavingsAccount) {
                SavingsAccount savings = (SavingsAccount) account;
                savings.applyInterest();
                count++;
            }
        }
        System.out.println("--- Completed: Interest credited to " + count + " savings account(s) ---\n");
        return count;
    }

    /**
     * Prints a formatted summary table of all accounts.
     */
    public void displayAllAccounts() {
        System.out.println("\n==================================================================================================");
        System.out.println("                              " + bankName.toUpperCase() + " - REGISTERED ACCOUNTS");
        System.out.println("==================================================================================================");
        System.out.printf("%-12s | %-22s | %-16s | %-14s | %s%n",
                "Account No.", "Account Holder", "Type", "Balance", "Special Terms");
        System.out.println("--------------------------------------------------------------------------------------------------");

        if (accounts.isEmpty()) {
            System.out.println("   No accounts have been registered yet.");
        } else {
            for (Account acc : accounts.values()) {
                String terms = "";
                if (acc instanceof SavingsAccount) {
                    SavingsAccount sa = (SavingsAccount) acc;
                    terms = String.format("Min Bal: Rs.%,.0f | Int: %.1f%%", sa.getMinimumBalance(), sa.getInterestRate());
                } else if (acc instanceof CurrentAccount) {
                    CurrentAccount ca = (CurrentAccount) acc;
                    terms = String.format("Overdraft Limit: Rs.%,.0f", ca.getOverdraftLimit());
                }
                System.out.printf("%-12s | %-22s | %-16s | Rs.%-10.2f | %s%n",
                        acc.getAccountNumber(),
                        acc.getAccountHolderName(),
                        acc.getAccountType(),
                        acc.getBalance(),
                        terms);
            }
        }
        System.out.println("==================================================================================================\n");
    }

    /**
     * Pre-populates the bank with realistic sample accounts for immediate testing.
     */
    public void seedSampleAccounts() {
        // Savings Account 1
        openSavingsAccount("Sumanth Kateboina", 15000.0, 4.5, 1000.0);
        // Current Account 2
        openCurrentAccount("Apex Tech Solutions", 25000.0, 10000.0);
        // Savings Account 3
        openSavingsAccount("Priya Sharma", 8000.0, 4.0, 500.0);
    }
}
