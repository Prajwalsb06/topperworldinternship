import java.util.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ATMSystem {
    private Map<String, User> users = new HashMap<>();

    public ATMSystem() {
        // Add some initial users
        users.put("123456", new User("123456", "1234", 1000.00));
        users.put("654321", new User("654321", "4321", 500.00));
    }

    public User authenticate(String accountNumber, String pin) {
        User user = users.get(accountNumber);
        if (user != null && user.getPin().equals(pin)) {
            return user;
        }
        return null;
    }

    public boolean withdraw(User user, double amount) {
        if (amount > 0 && user.getBalance() >= amount) {
            user.setBalance(user.getBalance() - amount);
            return true;
        }
        return false;
    }

    public void deposit(User user, double amount) {
        if (amount > 0) {
            user.setBalance(user.getBalance() + amount);
        }
    }

    public double checkBalance(User user) {
        return user.getBalance();
    }

}

class User {
    final private String accountNumber;
    final private String pin;
    private double balance;

    public User(String accountNumber, String pin, double balance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}

class GUI {
    final private ATMSystem atmSystem;
    private User currentUser;

    public GUI(ATMSystem atmSystem) {
        this.atmSystem = atmSystem;
        createLoginFrame();
    }

    private void createLoginFrame() {
        JFrame frame = new JFrame("ATM Login");
        frame.setSize(400,400);
        JLabel accountLabel = new JLabel("Account Number:");
        JTextField accountField = new JTextField(15);
        JLabel pinLabel = new JLabel("PIN:");
        JPasswordField pinField = new JPasswordField(15);
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountNumber = accountField.getText();
                String pin = new String(pinField.getPassword());
                currentUser = atmSystem.authenticate(accountNumber, pin);
                if (currentUser != null) {
                    frame.dispose();
                    createATMFrame();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid account number or PIN");
                }
            }
        });

        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.add(accountLabel);
        frame.add(accountField);
        frame.add(pinLabel);
        frame.add(pinField);
        frame.add(loginButton);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void createATMFrame() {
        JFrame frame = new JFrame("ATM");

        JButton balanceButton = new JButton("Check Balance");
        JButton withdrawButton = new JButton("Withdraw");
        JButton depositButton = new JButton("Deposit");

        balanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double balance = atmSystem.checkBalance(currentUser);
                JOptionPane.showMessageDialog(frame, "Current Balance: $" + balance);
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amountStr = JOptionPane.showInputDialog(frame, "Enter amount to withdraw:");
                double amount = Double.parseDouble(amountStr);
                if (atmSystem.withdraw(currentUser, amount)) {
                    JOptionPane.showMessageDialog(frame, "Withdrawal successful. New Balance: $" + currentUser.getBalance());
                } else {
                    JOptionPane.showMessageDialog(frame, "Insufficient funds.");
                }
            }
        });

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amountStr = JOptionPane.showInputDialog(frame, "Enter amount to deposit:");
                double amount = Double.parseDouble(amountStr);
                atmSystem.deposit(currentUser, amount);
                JOptionPane.showMessageDialog(frame, "Deposit successful. New Balance: $" + currentUser.getBalance());
            }
        });

        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.add(balanceButton);
        frame.add(withdrawButton);
        frame.add(depositButton);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}


public class ATMInterface {
    public static void main(String[] args) {
        ATMSystem atmSystem = new ATMSystem();
        new GUI(atmSystem);
    }
}