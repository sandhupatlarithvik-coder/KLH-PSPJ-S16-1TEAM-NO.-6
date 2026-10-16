import java.util.*;
import java.nio.file.*;
import java.io.IOException;

class ATMAccount {

    private String accountHolder;
    private int pin;
    private double balance;

    private ArrayList<String> transactionHistory = new ArrayList<>();

    // Parameterized constructor
    public ATMAccount(String accountHolder, int pin, double balance) {
        this.accountHolder = accountHolder;
        this.pin = pin;
        this.balance = balance;
    }

    // PIN verification
    public boolean verifyPin(int enteredPin) {
        return pin == enteredPin;
    }

    // Check balance
    public void checkBalance() {
        System.out.printf("Current Balance: Rs. %.2f%n", balance);
    }

    // Deposit money
    public void deposit(double amount) {

        if (amount > 0) {
            balance = balance + amount;

            String record = "Deposited: Rs. " + amount;
            transactionHistory.add(record);

            System.out.println("Deposit successful.");
            checkBalance();
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    // Withdraw money
    public void withdraw(double amount) {

        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount.");
        } else if (amount > balance) {
            System.out.println("Insufficient balance.");
        } else {
            balance = balance - amount;

            String record = "Withdrawn: Rs. " + amount;
            transactionHistory.add(record);

            System.out.println("Withdrawal successful.");
            checkBalance();
        }
    }

    // Display transaction history
    public void showTransactionHistory() {

        System.out.println("\n--- Transaction History ---");

        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions available.");
        } else {
            for (String transaction : transactionHistory) {
                System.out.println(transaction);
            }
        }
    }

    // Save transactions into a file
    public void saveTransactions() {

        try {
            Path file = Path.of("transactions.txt");

            Files.write(
                file,
                transactionHistory,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
            );

            System.out.println("Transactions saved successfully.");

        } catch (IOException e) {
            System.out.println("Error while saving transaction history.");
        }
    }

    public String getAccountHolder() {
        return accountHolder;
    }
}


public class ATMSimulator {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Creating an account object
        ATMAccount account =
            new ATMAccount("Rithvik", 1234, 10000.00);

        System.out.println("================================");
        System.out.println("      ATM TRANSACTION SIMULATOR");
        System.out.println("================================");

        System.out.print("Enter PIN: ");
        int enteredPin = sc.nextInt();

        // Authentication
        if (!account.verifyPin(enteredPin)) {
            System.out.println("Incorrect PIN. Access denied.");
            sc.close();
            return;
        }

        System.out.println("\nLogin successful!");
        System.out.println("Welcome, " + account.getAccountHolder());

        int choice;

        // ATM menu
        do {

            System.out.println("\n========== ATM MENU ==========");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Transaction History");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    account.checkBalance();
                    break;

                case 2:
                    System.out.print("Enter deposit amount: Rs. ");
                    double depositAmount = sc.nextDouble();

                    account.deposit(depositAmount);
                    break;

                case 3:
                    System.out.print("Enter withdrawal amount: Rs. ");
                    double withdrawAmount = sc.nextDouble();

                    account.withdraw(withdrawAmount);
                    break;

                case 4:
                    account.showTransactionHistory();
                    break;

                case 5:
                    account.saveTransactions();

                    System.out.println("Thank you for using the ATM.");
                    System.out.println("Please collect your card.");
                    break;

                default:
                    System.out.println(
                        "Invalid choice. Please try again."
                    );
            }

        } while (choice != 5);

        sc.close();
    }
}