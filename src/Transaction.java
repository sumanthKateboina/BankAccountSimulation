import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Transaction class represents an individual financial transaction on a bank account.
 * Demonstrates:
 * - Encapsulation (private final fields with public getters)
 * - Immutability of transaction records
 */
public class Transaction {
    private final String transactionId;
    private final LocalDateTime timestamp;
    private final TransactionType type;
    private final double amount;
    private final double balanceAfter;
    private final String description;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Parameterized Constructor
     */
    public Transaction(String transactionId, TransactionType type, double amount, double balanceAfter, String description) {
        this.transactionId = transactionId;
        this.timestamp = LocalDateTime.now();
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.description = description;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getFormattedTimestamp() {
        return timestamp.format(FORMATTER);
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return String.format("%-12s | %-19s | %-15s | Rs.%-10.2f | Rs.%-12.2f | %s",
                transactionId,
                getFormattedTimestamp(),
                type.getDisplayName(),
                amount,
                balanceAfter,
                description);
    }
}
