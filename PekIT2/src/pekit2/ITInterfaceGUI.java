/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pekit2;

/**
 *
 * @author jmrla
 */

import java.awt.Color;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class ITInterfaceGUI extends JFrame {
    private TicketHandle ticketHandle;
    private TicketSorting ticketSorting;
    private JTable ticketTable;
    private DefaultTableModel tableModel;

    public ITInterfaceGUI() {
        ticketHandle = new TicketHandle();
        ticketSorting = new TicketSorting();

        // Set up JFrame
        setTitle("IT Staff Menu");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Create a light green background panel for the form
        JPanel formPanel = new JPanel();
        formPanel.setBounds(0, 0, 1000, 600);
        formPanel.setLayout(null);
        formPanel.setBackground(new Color(174, 255, 173)); // Light green background
        add(formPanel);

        // Create a JTable to display tickets
        String[] columnNames = {"Name", "Email", "Phone", "Description", "Type", "Details"};
        tableModel = new DefaultTableModel(columnNames, 0);
        ticketTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(ticketTable);
        scrollPane.setBounds(20, 150, 950, 400); // Adjust size and position as needed
        formPanel.add(scrollPane);

        // Buttons for IT staff actions
        JButton viewTicketsButton = new JButton("View All Tickets");
        viewTicketsButton.setBounds(20, 20, 150, 40);
        formPanel.add(viewTicketsButton);

        JButton createTicketButton = new JButton("Create Guest Ticket");
        createTicketButton.setBounds(180, 20, 150, 40);
        formPanel.add(createTicketButton);

        JButton sortTicketsButton = new JButton("Sort Tickets");
        sortTicketsButton.setBounds(340, 20, 150, 40);
        formPanel.add(sortTicketsButton);

        JButton filterTicketsButton = new JButton("Filter Tickets Type");
        filterTicketsButton.setBounds(500, 20, 150, 40);
        formPanel.add(filterTicketsButton);
        
        JButton backButton = new JButton("Log Out");
        backButton.setBounds(660, 20, 150, 40); // Adjust size and position as needed
        formPanel.add(backButton);

        // Add button listeners
        viewTicketsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ArrayList<Ticket> tickets = ticketHandle.loadTickets();
                    displayTicketsInTable(tickets);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error loading tickets.");
                }
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

        createTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a new JFrame for creating a ticket
                JFrame createTicketFrame = new JFrame("Create Ticket");
                createTicketFrame.setSize(400, 300);
                createTicketFrame.setLayout(null);

                // Add components for creating a ticket (like text fields and labels)
                JLabel nameLabel = new JLabel("Enter your Name:");
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

                JLabel phoneLabel = new JLabel("Enter your Phone:");
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
                            addTicketToTable(name, email, phone, description, type, details);
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
        
        // Inside the ITInterfaceGUI constructor
sortTicketsButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            ArrayList<Ticket> tickets = ticketHandle.loadTickets();
            String criteria = JOptionPane.showInputDialog("Sort by: (name/date)");
            if ("name".equalsIgnoreCase(criteria)) {
                tickets = ticketSorting.sortByName(tickets);
            } else if ("date".equalsIgnoreCase(criteria)) {
                tickets = ticketSorting.sortByDate(tickets);
            }
            displayTicketsInTable(tickets);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error loading tickets.");
        }
    }
});

        filterTicketsButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String filterType = (String) JOptionPane.showInputDialog(null, "Select ticket type to filter:",
                "Filter Tickets", JOptionPane.QUESTION_MESSAGE, null, new String[]{"Hardware", "Software", "Network"}, "Hardware");
        try {
            ArrayList<Ticket> tickets = ticketHandle.loadTickets();
            ArrayList<Ticket> filteredTickets = ticketHandle.filterTicketsByType(tickets, filterType);
            displayTicketsInTable(filteredTickets);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error loading tickets.");
        }
    }
});


        
}

    private void displayTicketsInTable(ArrayList<Ticket> tickets) {
        // Clear existing rows
        tableModel.setRowCount(0);
        for (Ticket ticket : tickets) {
            // Assuming Ticket class has getName(), getEmail(), getPhone(), getDescription(), getType(), and getDetails() methods
            Object[] rowData = {
                ticket.getName(),
                ticket.getEmail(),
                ticket.getPhone(),
                ticket.getDescription(),
                ticket.getType(),
                ticket.getDetails()
            };
            tableModel.addRow(rowData);
        }
    }

    private void addTicketToTable(String name, String email, String phone, String description, String type, String details) {
        Object[] rowData = {name, email, phone, description, type, details};
        tableModel.addRow(rowData);
    }

    public static void main(String[] args) {
        ITInterfaceGUI itInterface = new ITInterfaceGUI();
        itInterface.setVisible(true);
    }
}
