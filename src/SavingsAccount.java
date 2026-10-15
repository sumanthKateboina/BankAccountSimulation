/**
 * SavingsAccount represents an interest-bearing personal savings bank account.
 * 
 * Core OOP Principles Demonstrated:
 * 1. Inheritance: Extends the Account superclass (is-a relationship).
 * 2. Method Overriding (@Override): Re-implements withdraw() to enforce minimum balance rule.
 * 3. Constructor Overloading & super(): Demonstrates parent constructor invocation.
 * 4. Runtime Polymorphism: Instances can be manipulated uniformly via Account references.
 */
public class SavingsAccount extends Account {
    private double interestRate;      // Annual interest rate percentage (e.g., 4.5%)
    private double minimumBalance;   // Required minimum balance threshold (e.g., Rs.500.0)

    public static final double DEFAULT_INTEREST_RATE = 4.0;
    public static final double DEFAULT_MINIMUM_BALANCE = 500.0;

    /**
     * Parameterized Constructor with custom interest rate and minimum balance.
     * Uses 'super' to invoke the parent Account constructor.
     */
    public SavingsAccount(String accountNumber, String accountHolderName, double initialBalance,
                          double interestRate, double minimumBalance) {
        super(accountNumber, accountHolderName, initialBalance);

        if (interestRate < 0) {
            throw new IllegalArgumentException("Interest rate cannot be negative.");
        }
        if (minimumBalance < 0) {
            throw new IllegalArgumentException("Minimum balance cannot be negative.");
        }
        if (initialBalance < minimumBalance) {
            throw new IllegalArgumentException("Initial balance (Rs." + initialBalance +
                    ") cannot be less than minimum balance requirement (Rs." + minimumBalance + ").");
        }

        this.interestRate = interestRate;
        this.minimumBalance = minimumBalance;
    }

    /**
     * Overloaded Constructor with default interest rate and minimum balance.
     */
    public SavingsAccount(String accountNumber, String accountHolderName, double initialBalance) {
        this(accountNumber, accountHolderName, initialBalance, DEFAULT_INTEREST_RATE, DEFAULT_MINIMUM_BALANCE);
    }

    /**
     * Overloaded Constructor with minimal parameters.
     */
    public SavingsAccount(String accountNumber, String accountHolderName) {
        this(accountNumber, accountHolderName, DEFAULT_MINIMUM_BALANCE, DEFAULT_INTEREST_RATE, DEFAULT_MINIMUM_BALANCE);
    }

    // Getters and Setters
    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        if (interestRate < 0) {
            throw new IllegalArgumentException("Interest rate cannot be negative.");
        }
        this.interestRate = interestRate;
    }

    public double getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(double minimumBalance) {
        if (minimumBalance < 0) {
            throw new IllegalArgumentException("Minimum balance cannot be negative.");
        }
        this.minimumBalance = minimumBalance;
    }

    @Override
    public String getAccountType() {
        return "Savings Account";
    }

    /**
     * METHOD OVERRIDING:
     * Overrides Account.withdraw(double, String) to enforce the Savings Account
     * minimum balance constraint.
     */
    @Override
    public boolean withdraw(double amount, String description) {
        if (amount <= 0) {
            System.out.println("[ERROR] Withdrawal Error: Amount must be positive. Provided: Rs." + amount);
            return false;
        }

        double currentBal = getBalance();
        double remainingBalance = currentBal - amount;

        if (remainingBalance < minimumBalance) {
            System.out.println("[ERROR] Withdrawal Rejected: Insufficient Funds!");
            System.out.printf("   Current Balance : Rs.%,.2f%n", currentBal);
            System.out.printf("   Requested Amount: Rs.%,.2f%n", amount);
            System.out.printf("   Required Minimum: Rs.%,.2f%n", minimumBalance);
            System.out.printf("   Shortfall       : Rs.%,.2f%n", (minimumBalance - remainingBalance));
            return false;
        }

        setBalance(remainingBalance);
        recordTransaction(TransactionType.WITHDRAWAL, amount, remainingBalance, description);
        System.out.printf("[SUCCESS] Withdrawal Successful: Rs.%,.2f deducted from Savings Account [%s]. Updated Balance: Rs.%,.2f%n",
                amount, getAccountNumber(), remainingBalance);
        return true;
    }

    /**
     * Calculates interest for this account based on current balance and interest rate.
     */
    public double calculateInterest() {
        return (getBalance() * interestRate) / 100.0;
    }

    /**
     * Calculates and applies interest to the account, recording it in transaction history.
     */
    public double applyInterest() {
        double interest = calculateInterest();
        if (interest > 0) {
            double newBalance = getBalance() + interest;
            setBalance(newBalance);
            recordTransaction(TransactionType.INTEREST_CREDIT, interest, newBalance,
                    String.format("Annual Interest Credit @ %.2f%% p.a.", interestRate));
            System.out.printf("[INTEREST] Interest Credited: Rs.%,.2f added to [%s] @ %.2f%%. New Balance: Rs.%,.2f%n",
                    interest, getAccountNumber(), interestRate, newBalance);
        }
        return interest;
    }

    @Override
    public void displayAccountSummary() {
        super.displayAccountSummary();
        System.out.printf("Interest Rate  : %.2f%%%n", interestRate);
        System.out.printf("Minimum Balance: Rs.%,.2f%n", minimumBalance);
    }
}
