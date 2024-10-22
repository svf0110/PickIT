/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pekit2;

/**
 *
 * @author jmrla
 */
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, createAccountButton;
    private AccountHandle accountHandle;

    public LoginGUI() {
        accountHandle = new AccountHandle();

        // Set up the JFrame
        setTitle("Pick IT Up Service Desk System");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Username field
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(20, 20, 80, 25);
        add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(100, 20, 165, 25);
        add(usernameField);

        // Password field
        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(20, 50, 80, 25);
        add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(100, 50, 165, 25);
        add(passwordField);

        // Login button
        loginButton = new JButton("Login");
        loginButton.setBounds(100, 80, 80, 25);
        add(loginButton);

        // Create Account button
        createAccountButton = new JButton("Create Account");
        createAccountButton.setBounds(200, 80, 150, 25);
        add(createAccountButton);

        // Set up the button listeners
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    login();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Login failed!");
                }
            }
        });

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    createAccount();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Account creation failed!");
                }
            }
        });
    }

    private void login() throws IOException {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        Account account = accountHandle.login(username, password);

        if (account != null) {
            JOptionPane.showMessageDialog(this, "Login Successful!");
            this.setVisible(false); // Close login window

            // Directly open the GUI based on account type
            if ("IT".equals(account.getType())) {
                new ITInterfaceGUI().setVisible(true);  // Open IT interface for IT staff
            } else if ("Guest".equals(account.getType())) {
                new GuestInterfaceGUI().setVisible(true);  // Open Guest interface for guests
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials. Try again.");
        }
    }

    private void createAccount() throws IOException {
        // Get username
        String username = JOptionPane.showInputDialog(this, "Enter username:");
        if (username == null || username.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Account creation canceled.");
            return;
        }

        // Get password
        String password = JOptionPane.showInputDialog(this, "Enter password:");
        if (password == null || password.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Account creation canceled.");
            return;
        }

        // Get account type
        String[] options = {"IT Staff", "Guest"};
        int accountTypeChoice = JOptionPane.showOptionDialog(this, "Select account type:", "Account Type",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        if (accountTypeChoice == -1) {
            JOptionPane.showMessageDialog(this, "Account creation canceled.");
            return;
        }

        String type = accountTypeChoice == 0 ? "IT" : "Guest";

        // Create new account and save it
        Account newAccount = new Account(username, password, type);
        accountHandle.saveAccount(newAccount);

        JOptionPane.showMessageDialog(this, "Account created successfully!");
    }

    public static void main(String[] args) {
        LoginGUI login = new LoginGUI();
        login.setVisible(true);
    }
}

