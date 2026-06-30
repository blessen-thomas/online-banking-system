package bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BankService implements BankOperations {
    
    @Override
    public void createAccount(BankAccount account) throws SQLException, Exception {
        if (accountExists(account.getAccountNumber())) {
            throw new Exception("Invalid Account: Account number already exists.");
        }
        
        String sql = "INSERT INTO accounts (account_number, customer_name, balance) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, account.getAccountNumber());
            pstmt.setString(2, account.getCustomerName());
            pstmt.setDouble(3, account.getBalance());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void deposit(int accountNumber, double amount) throws SQLException, Exception {
        if (amount <= 0) {
            throw new Exception("Invalid Amount: Deposit amount must be positive.");
        }
        if (!accountExists(accountNumber)) {
            throw new Exception("Invalid Account: Account does not exist.");
        }
        
        String updateSql = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
            pstmt.setDouble(1, amount);
            pstmt.setInt(2, accountNumber);
            pstmt.executeUpdate();
            
            recordTransaction(accountNumber, "DEPOSIT", amount, conn);
        }
    }

    @Override
    public void withdraw(int accountNumber, double amount) throws SQLException, Exception {
        if (amount <= 0) {
            throw new Exception("Invalid Amount: Withdraw amount must be positive.");
        }
        if (!accountExists(accountNumber)) {
            throw new Exception("Invalid Account: Account does not exist.");
        }
        
        double currentBalance = checkBalance(accountNumber);
        if (amount > currentBalance) {
            throw new Exception("Insufficient Balance.");
        }
        
        String updateSql = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
            pstmt.setDouble(1, amount);
            pstmt.setInt(2, accountNumber);
            pstmt.executeUpdate();
            
            recordTransaction(accountNumber, "WITHDRAW", amount, conn);
        }
    }

    @Override
    public double checkBalance(int accountNumber) throws SQLException, Exception {
        if (!accountExists(accountNumber)) {
            throw new Exception("Invalid Account: Account does not exist.");
        }
        
        String sql = "SELECT balance FROM accounts WHERE account_number = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, accountNumber);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("balance");
                }
            }
        }
        return 0;
    }

    @Override
    public List<Transaction> getTransactionHistory(int accountNumber) throws SQLException, Exception {
        if (!accountExists(accountNumber)) {
            throw new Exception("Invalid Account: Account does not exist.");
        }
        
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE account_number = ? ORDER BY transaction_date DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, accountNumber);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String type = rs.getString("transaction_type");
                    double txnAmount = rs.getDouble("amount");
                    Timestamp date = rs.getTimestamp("transaction_date");
                    transactions.add(new Transaction(id, accountNumber, type, txnAmount, date));
                }
            }
        }
        return transactions;
    }
    
    private boolean accountExists(int accountNumber) throws SQLException {
        String sql = "SELECT 1 FROM accounts WHERE account_number = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, accountNumber);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }
    
    private void recordTransaction(int accountNumber, String type, double amount, Connection conn) throws SQLException {
        String sql = "INSERT INTO transactions (account_number, transaction_type, amount) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, accountNumber);
            pstmt.setString(2, type);
            pstmt.setDouble(3, amount);
            pstmt.executeUpdate();
        }
    }
}
