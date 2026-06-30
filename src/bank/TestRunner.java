package bank;

import java.util.List;

public class TestRunner {
    public static void main(String[] args) {
        System.out.println("Starting Automated Verification...");
        BankService service = new BankService();
        int accNum = 9999;
        
        try {
            // Clean up any existing test account
            try (java.sql.Connection conn = DatabaseConnection.getConnection();
                 java.sql.PreparedStatement pstmt1 = conn.prepareStatement("DELETE FROM transactions WHERE account_number = ?");
                 java.sql.PreparedStatement pstmt2 = conn.prepareStatement("DELETE FROM accounts WHERE account_number = ?")) {
                pstmt1.setInt(1, accNum);
                pstmt1.executeUpdate();
                pstmt2.setInt(1, accNum);
                pstmt2.executeUpdate();
            }

            // 1. Create Account
            System.out.print("Testing Create Account... ");
            service.createAccount(new BankAccount(accNum, "Test User", 500.0));
            System.out.println("PASSED");

            // 2. Deposit
            System.out.print("Testing Deposit... ");
            service.deposit(accNum, 200.0);
            System.out.println("PASSED");

            // 3. Withdraw
            System.out.print("Testing Withdraw... ");
            service.withdraw(accNum, 100.0);
            System.out.println("PASSED");

            // 4. Check Balance
            System.out.print("Testing Check Balance... ");
            double balance = service.checkBalance(accNum);
            if (balance == 600.0) { // 500 + 200 - 100
                System.out.println("PASSED (Balance: " + balance + ")");
            } else {
                System.out.println("FAILED (Expected 600.0, got " + balance + ")");
                throw new Exception("Balance verification failed.");
            }

            // 5. Transaction History
            System.out.print("Testing Transaction History... ");
            List<Transaction> history = service.getTransactionHistory(accNum);
            if (history.size() == 2) { // 1 deposit, 1 withdraw
                System.out.println("PASSED (" + history.size() + " transactions found)");
            } else {
                System.out.println("FAILED (Expected 2 transactions, got " + history.size() + ")");
                throw new Exception("History verification failed.");
            }
            
            System.out.println("\nAll Verification Tests Passed Successfully!");

        } catch (Exception e) {
            System.err.println("\nVERIFICATION FAILED: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
