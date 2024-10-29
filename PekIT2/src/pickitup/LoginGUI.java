/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pickitup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 *
 * @author Gio Turtal and Jose Laserna
 */

public class LoginGUI extends JFrame {
    public JTextField usernameField;
    public JPasswordField passwordField;
    public JButton loginButton, createAccountButton;
    public AccountHandle accountHandle;

    public LoginGUI() {
        accountHandle = new AccountHandle();

        // Set up the JFrame
        setTitle("Welcome to PIU Service Desk System");
        setSize(1100, 600);
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
                handleLogin();
            }
        });

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAccountCreation();
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
            logoLabel.setBounds(820, 250, 200, 200); // Adjust size and position as needed
            formPanel.add(logoLabel); // Add logo label to form panel
        }

        // Make the frame visible
        setVisible(true);
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        
        try {
            Account account = accountHandle.login(username, password);
            if (account != null) {
                JOptionPane.showMessageDialog(this, "Login successful! Welcome, " + account.getUsername());
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
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password!");
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Login failed due to a database error.");
            ex.printStackTrace();
        }
    }

    private void handleAccountCreation() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Prompt user to select account type
        String[] accountTypes = {"IT", "Guest"};
        String type = (String) JOptionPane.showInputDialog(this, 
                "Select account type:", 
                "Account Type Selection", 
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                accountTypes, 
                accountTypes[0]);

        if (type != null && !type.isEmpty()) {
            try {
                accountHandle.createAccount(username, password, type);
                JOptionPane.showMessageDialog(this, "Account created successfully as " + type);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Account creation failed due to a database error.");
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Account creation canceled.");
        }
    }
    
    public static void main(String[] args) {
        // Create and show the login GUI
        SwingUtilities.invokeLater(() -> new LoginGUI());
    }
}

