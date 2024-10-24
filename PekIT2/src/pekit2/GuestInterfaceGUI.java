/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pekit2;

/**
 *
 * @author jmrla
 */
//

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GuestInterfaceGUI extends JFrame {
    private TicketHandle ticketHandle;

    public GuestInterfaceGUI() {
        ticketHandle = new TicketHandle(); // Initialize ticket handle
        
        // JFrame setup for Guest Menu
        setTitle("Guest Menu");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout()); // Use BorderLayout for better layout management

        // Create a panel for the guest menu
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(null);
        menuPanel.setBackground(new Color(174, 255, 173)); // Light green background
        menuPanel.setPreferredSize(new Dimension(400, 600)); // Set preferred size for the menu panel

        // Create Guest Ticket Button
        JButton createTicketButton = new JButton("Create Guest Ticket");
        createTicketButton.setBounds(100, 100, 200, 40);
        menuPanel.add(createTicketButton);
        
        JButton backButton = new JButton("Log Out");
        backButton.setBounds(100, 200, 200, 40); // Adjust size and position as needed
        menuPanel.add(backButton);

        createTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a new JFrame for creating a ticket
                JFrame createTicketFrame = new JFrame("Create Ticket");
                createTicketFrame.setSize(400, 400);
                createTicketFrame.setLayout(null);
                createTicketFrame.getContentPane().setBackground(new Color(174, 255, 173)); // Light green background


                // Add components for creating a ticket (like text fields and labels)
                JLabel nameLabel = new JLabel("Enter your Full Name:");
                nameLabel.setBounds(20, 20, 150, 25);
                createTicketFrame.add(nameLabel);

                JTextField nameField = new JTextField();
                nameField.setBounds(180, 20, 180, 25);
                createTicketFrame.add(nameField);

                JLabel emailLabel = new JLabel("Enter your Email:");
                emailLabel.setBounds(20, 60, 150, 25);
                createTicketFrame.add(emailLabel);

                JTextField emailField = new JTextField();
                emailField.setBounds(180, 60, 180, 25);
                createTicketFrame.add(emailField);

                JLabel phoneLabel = new JLabel("Enter your Phone Number:");
                phoneLabel.setBounds(20, 100, 150, 25);
                createTicketFrame.add(phoneLabel);

                JTextField phoneField = new JTextField();
                phoneField.setBounds(180, 100, 180, 25);
                createTicketFrame.add(phoneField);

                JLabel descLabel = new JLabel("Enter Description:");
                descLabel.setBounds(20, 140, 150, 25);
                createTicketFrame.add(descLabel);

                JTextField descField = new JTextField();
                descField.setBounds(180, 140, 180, 25);
                createTicketFrame.add(descField);

                JLabel typeLabel = new JLabel("Select Ticket Type:");
                typeLabel.setBounds(20, 180, 150, 25);
                createTicketFrame.add(typeLabel);

                String[] types = {"Hardware", "Software", "Network"};
                JComboBox<String> typeComboBox = new JComboBox<>(types);
                typeComboBox.setBounds(180, 180, 180, 25);
                createTicketFrame.add(typeComboBox);

                JButton submitButton = new JButton("Submit");
                submitButton.setBounds(150, 220, 100, 30);
                createTicketFrame.add(submitButton);

                submitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Logic to create and save the ticket using the TicketHandle class
                        String name = nameField.getText();
                        String email = emailField.getText();
                        String phone = phoneField.getText();
                        String description = descField.getText();
                        String type = (String) typeComboBox.getSelectedItem();

                        try {
                            String details = "";
                            switch (type) {
                                case "Hardware":
                                    String hardware = JOptionPane.showInputDialog("Enter the type of Hardware:");
                                    String model = JOptionPane.showInputDialog("Enter Model Number of Hardware:");
                                    details = "Hardware: " + hardware + ", Model: " + model;
                                    ticketHandle.createHardwareTicket(name, email, phone, description, hardware, model);
                                    break;
                                case "Software":
                                    String software = JOptionPane.showInputDialog("Enter name of Software:");
                                    String version = JOptionPane.showInputDialog("Enter the current Version of Software:");
                                    details = "Software: " + software + ", Version: " + version;
                                    ticketHandle.createSoftwareTicket(name, email, phone, description, software, version);
                                    break;
                                case "Network":
                                    String device = JOptionPane.showInputDialog("Enter Network Issue:");
                                    String ipAddress = JOptionPane.showInputDialog("Enter IP address:");
                                    details = "Device: " + device + ", IP: " + ipAddress;
                                    ticketHandle.createNetworkTicket(name, email, phone, description, device, ipAddress);
                                    break;
                            }
                            JOptionPane.showMessageDialog(createTicketFrame, "Ticket Created Successfully!");
                            createTicketFrame.dispose(); // Close the frame after submission
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(createTicketFrame, "Error creating ticket: " + ex.getMessage());
                        }
                    }
                });

                createTicketFrame.setVisible(true);
            }
        });
        
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Go back to the login screen
                LoginGUI loginGUI = new LoginGUI();
                loginGUI.setVisible(true);
                dispose(); // Close the current window
            }
        });

        // Add the menu panel to the left side of the main frame
        add(menuPanel, BorderLayout.WEST);

        // Add the plant image on the right side of the main frame
        ImageIcon icon = new ImageIcon("plant.jpg"); // Change to your image path
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(500, 500, Image.SCALE_SMOOTH); // Scale the image
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImg));
        add(imageLabel, BorderLayout.CENTER);

        // Make the frame visible
        setVisible(true);
    }

    public static void main(String[] args) {
        GuestInterfaceGUI guestInterface = new GuestInterfaceGUI();
        guestInterface.setVisible(true);
    }
}


