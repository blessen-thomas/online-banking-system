package bank;

import java.sql.SQLException;
import java.util.List;

public interface BankOperations {
    void createAccount(BankAccount account) throws SQLException, Exception;
    void deposit(int accountNumber, double amount) throws SQLException, Exception;
    void withdraw(int accountNumber, double amount) throws SQLException, Exception;
    double checkBalance(int accountNumber) throws SQLException, Exception;
    List<Transaction> getTransactionHistory(int accountNumber) throws SQLException, Exception;
}
