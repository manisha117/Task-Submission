import java.util.*;
import java.io.*;

class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    private String accountNumber;
    private String holderName;
    private double balance;

    public Account(String accountNumber, String holderName, double initialDeposit) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = initialDeposit;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: " + amount);
        } else {
            System.out.println("Invalid amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            System.out.println("Withdrew: " + amount);
        } else {
            System.out.println("Insufficient balance or invalid amount.");
        }
    }

    @Override
    public String toString() {
        return "Account: " + accountNumber + ", Holder: " + holderName + ", Balance: " + balance;
    }
}

public class Banky {
    private static Map<String, Account> accounts = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);
    private static final String FILE_NAME = "accounts.dat";

    public static void main(String[] args) {
        loadAccounts();
        int choice;
        do {
            System.out.println("\n--- BankY Menu ---");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer Funds");
            System.out.println("5. View Accounts");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    deposit();
                    break;
                case 3:
                    withdraw();
                    break;
                case 4:
                    transfer();
                    break;
                case 5:
                    viewAccounts();
                    break;
                case 6:
                    saveAccounts();
                    System.out.println("Exiting... Data saved.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 6);
    }

    private static void createAccount() {
        System.out.print("Enter account number: ");
        String accNo = scanner.nextLine();
        System.out.print("Enter holder name: ");
        String name = scanner.nextLine();
        System.out.print("Enter initial deposit: ");
        double deposit = scanner.nextDouble();
        scanner.nextLine();

        if (accounts.containsKey(accNo)) {
            System.out.println("Account already exists.");
        } else {
            Account acc = new Account(accNo, name, deposit);
            accounts.put(accNo, acc);
            System.out.println("Account created successfully.");
        }
    }

    private static void deposit() {
        System.out.print("Enter account number: ");
        String accNo = scanner.nextLine();
        Account acc = accounts.get(accNo);
        if (acc != null) {
            System.out.print("Enter amount to deposit: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            acc.deposit(amount);
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void withdraw() {
        System.out.print("Enter account number: ");
        String accNo = scanner.nextLine();
        Account acc = accounts.get(accNo);
        if (acc != null) {
            System.out.print("Enter amount to withdraw: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            acc.withdraw(amount);
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void transfer() {
        System.out.print("Enter source account number: ");
        String srcAccNo = scanner.nextLine();
        System.out.print("Enter destination account number: ");
        String destAccNo = scanner.nextLine();

        Account src = accounts.get(srcAccNo);
        Account dest = accounts.get(destAccNo);

        if (src != null && dest != null) {
            System.out.print("Enter amount to transfer: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            if (amount > 0 && src.getBalance() >= amount) {
                src.withdraw(amount);
                dest.deposit(amount);
                System.out.println("Transfer successful.");
            } else {
                System.out.println("Insufficient funds or invalid amount.");
            }
        } else {
            System.out.println("One or both accounts not found.");
        }
    }

    private static void viewAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts available.");
        } else {
            for (Account acc : accounts.values()) {
                System.out.println(acc);
            }
        }
    }

    // --- Data persistence ---
    @SuppressWarnings("unchecked")
    private static void loadAccounts() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            accounts = (HashMap<String, Account>) ois.readObject();
            System.out.println("Accounts loaded successfully.");
        } catch (Exception e) {
            System.out.println("No existing account data found. Starting fresh.");
        }
    }

    private static void saveAccounts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(accounts);
            System.out.println("Accounts saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving accounts.");
        }
    }
}

