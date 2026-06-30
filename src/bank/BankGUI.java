package bank;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BankGUI extends JFrame {
    private JTextField txtAccountNumber;
    private JTextField txtCustomerName;
    private JTextField txtAmount;
    private JTextArea txtDisplayArea;
    private JLabel lblStatusTime;
    
    private BankOperations bankService;

    public BankGUI() {
        bankService = new BankService();
        setTitle("ONLINE BANKING SYSTEM");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        
        Font mainFont = new Font("Segoe UI", Font.PLAIN, 15);
        Font titleFont = new Font("Segoe UI", Font.BOLD, 15);
        Font btnFont = new Font("Segoe UI", Font.BOLD, 14);
        
        setLayout(new BorderLayout(10, 10));
        
        // --- Top Container (Holds Customer Info & Banking Operations) ---
        JPanel topContainer = new JPanel(new BorderLayout(15, 15));
        topContainer.setBorder(new EmptyBorder(15, 20, 0, 20));

        // 1. Customer Information Panel (GridBagLayout)
        JPanel customerPanel = new JPanel(new GridBagLayout());
        customerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                    BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), 
                    "Customer Information", TitledBorder.LEFT, TitledBorder.TOP, titleFont),
                new EmptyBorder(10, 15, 10, 15)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 15, 10, 15);
        
        Dimension fieldSize = new Dimension(200, 32);
        
        JLabel lblAcc = new JLabel("Account Number:");
        lblAcc.setFont(mainFont);
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.1;
        customerPanel.add(lblAcc, gbc);
        
        txtAccountNumber = new JTextField();
        txtAccountNumber.setFont(mainFont);
        txtAccountNumber.setPreferredSize(fieldSize);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.4;
        customerPanel.add(txtAccountNumber, gbc);
        
        JLabel lblName = new JLabel("Customer Name:");
        lblName.setFont(mainFont);
        gbc.gridx = 2; gbc.gridy = 0; gbc.weightx = 0.1;
        customerPanel.add(lblName, gbc);
        
        txtCustomerName = new JTextField();
        txtCustomerName.setFont(mainFont);
        txtCustomerName.setPreferredSize(fieldSize);
        gbc.gridx = 3; gbc.gridy = 0; gbc.weightx = 0.4;
        customerPanel.add(txtCustomerName, gbc);
        
        JLabel lblAmount = new JLabel("Amount:");
        lblAmount.setFont(mainFont);
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.1;
        customerPanel.add(lblAmount, gbc);
        
        txtAmount = new JTextField();
        txtAmount.setFont(mainFont);
        txtAmount.setPreferredSize(fieldSize);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.4;
        customerPanel.add(txtAmount, gbc);
        
        topContainer.add(customerPanel, BorderLayout.NORTH);

        // 2. Banking Operations Panel (GridLayout)
        JPanel opsPanel = new JPanel(new GridLayout(2, 3, 15, 15));
        opsPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                    BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), 
                    "Banking Operations", TitledBorder.LEFT, TitledBorder.TOP, titleFont),
                new EmptyBorder(10, 15, 10, 15)
        ));
        
        JButton btnCreateAccount = createStyledButton("Create Account", "\u2795", new Color(46, 204, 113), btnFont);
        JButton btnDeposit = createStyledButton("Deposit", "\uD83D\uDCB5", new Color(52, 152, 219), btnFont);
        JButton btnWithdraw = createStyledButton("Withdraw", "\uD83D\uDCB8", new Color(231, 76, 60), btnFont);
        JButton btnBalance = createStyledButton("Balance Enquiry", "\uD83D\uDD0D", new Color(241, 196, 15), btnFont);
        btnBalance.setForeground(Color.BLACK);
        JButton btnHistory = createStyledButton("Transaction History", "\uD83D\uDCDC", new Color(155, 89, 182), btnFont);
        JButton btnClear = createStyledButton("Clear", "\u274C", new Color(149, 165, 166), btnFont);

        opsPanel.add(btnCreateAccount);
        opsPanel.add(btnDeposit);
        opsPanel.add(btnWithdraw);
        opsPanel.add(btnBalance);
        opsPanel.add(btnHistory);
        opsPanel.add(btnClear);
        
        topContainer.add(opsPanel, BorderLayout.CENTER);
        
        add(topContainer, BorderLayout.NORTH);

        // 3. Transaction Log Panel (BorderLayout)
        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.setBorder(new EmptyBorder(0, 20, 10, 20));
        
        txtDisplayArea = new JTextArea();
        txtDisplayArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        txtDisplayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtDisplayArea);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                    BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), 
                    "Transaction Log", TitledBorder.LEFT, TitledBorder.TOP, titleFont),
                new EmptyBorder(5, 5, 5, 5)
        ));
        logPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(logPanel, BorderLayout.CENTER);

        // 4. Footer Status Bar
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBorder(new EmptyBorder(5, 20, 10, 20));
        
        JLabel lblStatusDb = new JLabel("\uD83D\uDDF4 Database: Connected (bankdb)");
        lblStatusDb.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblStatusDb.setForeground(new Color(39, 174, 96));
        
        lblStatusTime = new JLabel();
        lblStatusTime.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        footerPanel.add(lblStatusDb, BorderLayout.WEST);
        footerPanel.add(lblStatusTime, BorderLayout.EAST);
        
        add(footerPanel, BorderLayout.SOUTH);

        // Setup Timer for Live Date & Time
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy | hh:mm:ss a");
                lblStatusTime.setText(sdf.format(new Date()));
            }
        });
        timer.start();

        // Action Listeners
        btnCreateAccount.addActionListener(e -> createAccount());
        btnDeposit.addActionListener(e -> deposit());
        btnWithdraw.addActionListener(e -> withdraw());
        btnBalance.addActionListener(e -> checkBalance());
        btnHistory.addActionListener(e -> showHistory());
        btnClear.addActionListener(e -> clearFields());
    }

    private JButton createStyledButton(String text, String unicodeIcon, Color bgColor, Font font) {
        JButton btn = new JButton(unicodeIcon + "  " + text);
        btn.setFont(font);
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.putClientProperty("JButton.buttonType", "roundRect");
        return btn;
    }

    private void createAccount() {
        try {
            int accNum = Integer.parseInt(txtAccountNumber.getText().trim());
            String name = txtCustomerName.getText().trim();
            
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Customer name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            double balance = 0;
            String amountText = txtAmount.getText().trim();
            if (!amountText.isEmpty()) {
                balance = Double.parseDouble(amountText);
            }
            if (balance < 0) {
                 JOptionPane.showMessageDialog(this, "Initial balance cannot be negative.", "Error", JOptionPane.ERROR_MESSAGE);
                 return;
            }

            BankAccount account = new BankAccount(accNum, name, balance);
            bankService.createAccount(account);
            txtDisplayArea.setText("Account created successfully for " + name + ".\n");
            JOptionPane.showMessageDialog(this, "Account Created successfully.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for Account Number and Amount.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deposit() {
        try {
            int accNum = Integer.parseInt(txtAccountNumber.getText().trim());
            double amount = Double.parseDouble(txtAmount.getText().trim());
            
            bankService.deposit(accNum, amount);
            txtDisplayArea.setText("Deposited $" + amount + " to account " + accNum + " successfully.\n");
            JOptionPane.showMessageDialog(this, "Deposit successful.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void withdraw() {
        try {
            int accNum = Integer.parseInt(txtAccountNumber.getText().trim());
            double amount = Double.parseDouble(txtAmount.getText().trim());
            
            bankService.withdraw(accNum, amount);
            txtDisplayArea.setText("Withdrew $" + amount + " from account " + accNum + " successfully.\n");
            JOptionPane.showMessageDialog(this, "Withdrawal successful.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void checkBalance() {
        try {
            int accNum = Integer.parseInt(txtAccountNumber.getText().trim());
            double balance = bankService.checkBalance(accNum);
            txtDisplayArea.setText("Account Number: " + accNum + "\nCurrent Balance: $" + balance + "\n");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid Account Number.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showHistory() {
        try {
            int accNum = Integer.parseInt(txtAccountNumber.getText().trim());
            List<Transaction> history = bankService.getTransactionHistory(accNum);
            
            StringBuilder sb = new StringBuilder("Transaction History for Account: " + accNum + "\n");
            sb.append("--------------------------------------------------\n");
            for (Transaction t : history) {
                sb.append(t.getTransactionDate()).append(" | ")
                  .append(t.getTransactionType()).append(" | $")
                  .append(t.getAmount()).append("\n");
            }
            txtDisplayArea.setText(sb.toString());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid Account Number.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void clearFields() {
        txtAccountNumber.setText("");
        txtCustomerName.setText("");
        txtAmount.setText("");
        txtDisplayArea.setText("");
    }
}
