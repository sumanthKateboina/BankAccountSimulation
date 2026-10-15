/**
 * CurrentAccount represents a commercial/business checking bank account with overdraft privileges.
 * 
 * Core OOP Principles Demonstrated:
 * 1. Inheritance: Extends Account, inheriting identity, balance tracking, and transaction history.
 * 2. Method Overriding: Overrides withdraw() to permit negative balances within overdraft limit.
 * 3. Polymorphism: Treated interchangeably with SavingsAccount through Account references.
 */
public class CurrentAccount extends Account {
    private double overdraftLimit; // Maximum credit allowed beyond zero balance (e.g., Rs.10,000.0)

    public static final double DEFAULT_OVERDRAFT_LIMIT = 5000.0;

    /**
     * Parameterized Constructor with custom overdraft limit.
     */
    public CurrentAccount(String accountNumber, String accountHolderName, double initialBalance, double overdraftLimit) {
        super(accountNumber, accountHolderName, initialBalance);

        if (overdraftLimit < 0) {
            throw new IllegalArgumentException("Overdraft limit cannot be negative.");
        }
        this.overdraftLimit = overdraftLimit;
    }

    /**
     * Overloaded Constructor with default overdraft limit.
     */
    public CurrentAccount(String accountNumber, String accountHolderName, double initialBalance) {
        this(accountNumber, accountHolderName, initialBalance, DEFAULT_OVERDRAFT_LIMIT);
    }

    /**
     * Overloaded Constructor with zero initial balance.
     */
    public CurrentAccount(String accountNumber, String accountHolderName) {
        this(accountNumber, accountHolderName, 0.0, DEFAULT_OVERDRAFT_LIMIT);
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    public void setOverdraftLimit(double overdraftLimit) {
        if (overdraftLimit < 0) {
            throw new IllegalArgumentException("Overdraft limit cannot be negative.");
        }
        this.overdraftLimit = overdraftLimit;
    }

    public double getAvailableSpendingLimit() {
        return getBalance() + overdraftLimit;
    }

    @Override
    public String getAccountType() {
        return "Current Account";
    }

    /**
     * METHOD OVERRIDING:
     * Overrides Account.withdraw() to allow transactions up to (balance + overdraftLimit).
     */
    @Override
    public boolean withdraw(double amount, String description) {
        if (amount <= 0) {
            System.out.println("[ERROR] Withdrawal Error: Amount must be positive. Provided: Rs." + amount);
            return false;
        }

        double balance = getBalance();
        double availableFunds = balance + overdraftLimit;

        if (amount > availableFunds) {
            System.out.println("[ERROR] Withdrawal Rejected: Overdraft Limit Exceeded!");
            System.out.printf("   Current Balance       : Rs.%,.2f%n", balance);
            System.out.printf("   Overdraft Limit       : Rs.%,.2f%n", overdraftLimit);
            System.out.printf("   Total Available Funds : Rs.%,.2f%n", availableFunds);
            System.out.printf("   Requested Amount      : Rs.%,.2f%n", amount);
            System.out.printf("   Exceeded By           : Rs.%,.2f%n", (amount - availableFunds));
            return false;
        }

        double newBalance = balance - amount;
        setBalance(newBalance);

        String note = description;
        if (newBalance < 0) {
            note += String.format(" [Overdraft Used: Rs.%,.2f]", Math.abs(newBalance));
        }

        recordTransaction(TransactionType.WITHDRAWAL, amount, newBalance, note);

        System.out.printf("[SUCCESS] Withdrawal Successful: Rs.%,.2f deducted from Current Account [%s]. Updated Balance: Rs.%,.2f%n",
                amount, getAccountNumber(), newBalance);
        if (newBalance < 0) {
            System.out.printf("   [NOTE] Notice: Account is in Overdraft by Rs.%,.2f (Remaining Overdraft Cushion: Rs.%,.2f)%n",
                    Math.abs(newBalance), (overdraftLimit + newBalance));
        }
        return true;
    }

    @Override
    public void displayAccountSummary() {
        super.displayAccountSummary();
        System.out.printf("Overdraft Limit: Rs.%,.2f%n", overdraftLimit);
        System.out.printf("Total Spending : Rs.%,.2f%n", getAvailableSpendingLimit());
    }
}
