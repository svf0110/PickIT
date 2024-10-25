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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;


public class LoginGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, createAccountButton;
    private AccountHandle accountHandle;
    private Connection conn;


    public LoginGUI() {

        Database db = new Database();
        db.initialize();
        conn = db.getConnection(); // Save the connection for reuse in this class

        accountHandle = new AccountHandle(conn);
        TicketDAO ticketDAO = new TicketDAO(conn);
        AccountDAO accountDAO = new AccountDAO(conn);

        // Test creating the account table
        accountDAO.createAccountTable();
        
        // Set up the JFrame
        setTitle("Welcome to PIU Service Desk System");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create a panel for the login form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBackground(new Color(174, 255, 173)); // Light green background
        formPanel.setPreferredSize(new Dimension(400, 300)); // Set preferred size for the form panel

        // Username field
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(20, 20, 80, 25);
        formPanel.add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(100, 20, 165, 25);
        formPanel.add(usernameField);

        // Password field
        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(20, 60, 80, 25);
        formPanel.add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(100, 60, 165, 25);
        formPanel.add(passwordField);

        // Login button
        loginButton = new JButton("Login");
        loginButton.setBounds(100, 100, 80, 25);
        formPanel.add(loginButton);

        // Create Account button
        createAccountButton = new JButton("Create Account");
        createAccountButton.setBounds(200, 100, 150, 25);
        formPanel.add(createAccountButton);

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

        // Add form panel to the left side of the frame
        add(formPanel, BorderLayout.WEST);

        // Add image on the right side of the frame
        ImageIcon icon = new ImageIcon("plant.jpg"); // Change to your image path
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(500, 500, Image.SCALE_SMOOTH); // Scale the image
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImg));
        add(imageLabel, BorderLayout.CENTER);

        // Add logo to the bottom right corner
        ImageIcon logoIcon = new ImageIcon("leaflogo.png"); // Load your logo image
        if (logoIcon.getIconWidth() == -1 || logoIcon.getIconHeight() == -1) {
            System.err.println("Error: Logo image not found or unable to load.");
        } else {
            JLabel logoLabel = new JLabel(logoIcon);
            logoLabel.setBounds(100, 250, 200, 200); // Adjust size and position as needed
            formPanel.add(logoLabel); // Add logo label to form panel
        }

        // Make the frame visible
        setVisible(true);
    }
    
    private void login() throws IOException {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        Account account = accountHandle.login(username, password);

        if (account != null) {
            JOptionPane.showMessageDialog(this, "Login Successful!");
            this.setVisible(false); // Close login window

            // Directly open the GUI based on account type
            switch (account.getType()) {
                case "IT":
                    new ITInterfaceGUI().setVisible(true);  // Open IT interface for IT staff
                    break;
                case "Guest":
                    new GuestInterfaceGUI().setVisible(true);  // Open Guest interface for guests
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Unknown account type.");
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
        accountHandle.saveAccount(username, password, type);

        JOptionPane.showMessageDialog(this, "Account created successfully!");
    }

    public static void main(String[] args) {
        // Create and show the login GUI
        SwingUtilities.invokeLater(() -> new LoginGUI());
    }
}

