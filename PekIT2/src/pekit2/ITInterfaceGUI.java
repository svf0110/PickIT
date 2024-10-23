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
import java.util.ArrayList;
import java.util.Scanner;

public class ITInterfaceGUI extends JFrame {
    private TicketHandle ticketHandle;
    private TicketSorting ticketSorting;
    private JTextArea ticketDisplayArea;

    public ITInterfaceGUI() {
        ticketHandle = new TicketHandle();
        ticketSorting = new TicketSorting();

        // Set up JFrame
        setTitle("IT Staff Menu");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Create a JTextArea for displaying tickets
        ticketDisplayArea = new JTextArea();
        ticketDisplayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(ticketDisplayArea);
        scrollPane.setBounds(20, 20, 350, 300); // Adjust size and position as needed
        add(scrollPane);

        // Buttons for IT staff actions
        JButton viewTicketsButton = new JButton("View Tickets");
        viewTicketsButton.setBounds(400, 50, 150, 30);
        add(viewTicketsButton);

        JButton createTicketButton = new JButton("Create Ticket");
        createTicketButton.setBounds(400, 100, 150, 30);
        add(createTicketButton);

        JButton sortTicketsButton = new JButton("Sort Tickets");
        sortTicketsButton.setBounds(400, 150, 150, 30);
        add(sortTicketsButton);

        JButton filterTicketsButton = new JButton("Filter Tickets");
        filterTicketsButton.setBounds(400, 200, 150, 30);
        add(filterTicketsButton);

        // Add button listeners
        viewTicketsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ArrayList<Ticket> tickets = ticketHandle.loadTickets();
                    ticketDisplayArea.setText(""); // Clear the text area
                    ticketHandle.displayTickets(tickets, ticketDisplayArea);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error loading tickets.");
                }
            }
        });

    createTicketButton.addActionListener(new ActionListener() { 
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String[] types = {"Hardware", "Software", "Network"};
            String type = (String) JOptionPane.showInputDialog(null, "Select ticket type:", "Ticket Type",
                    JOptionPane.QUESTION_MESSAGE, null, types, types[0]);

            if (type != null) {
                // Create a new Scanner object for user input
                Scanner scan = new Scanner(System.in);
                
                switch (type) {
                    case "Hardware":
                        createHardwareTicket(scan);
                        break;
                    case "Software":
                        createSoftwareTicket(scan);
                        break;
                    case "Network":
                        createNetworkTicket(scan);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Invalid ticket type selected.");
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error creating ticket.");
        }
    }

    private void createHardwareTicket(Scanner scan) throws IOException {
        // Gather hardware ticket details from user
        String name = JOptionPane.showInputDialog("Enter your Name:");
        String email = JOptionPane.showInputDialog("Enter your Email:");
        String phone = JOptionPane.showInputDialog("Enter your Phone:");
        String description = JOptionPane.showInputDialog("Enter Description:");
        String hardware = JOptionPane.showInputDialog("Enter the type of Hardware:");
        String model = JOptionPane.showInputDialog("Enter Model Number of Hardware:");

        ticketHandle.createHardwareTicket(name, email, phone, description, hardware, model);
        JOptionPane.showMessageDialog(null, "Hardware ticket created successfully!");
    }

    private void createSoftwareTicket(Scanner scan) throws IOException {
        // Gather software ticket details from user
        String name = JOptionPane.showInputDialog("Enter your Name:");
        String email = JOptionPane.showInputDialog("Enter your Email:");
        String phone = JOptionPane.showInputDialog("Enter your Phone:");
        String description = JOptionPane.showInputDialog("Enter Description:");
        String software = JOptionPane.showInputDialog("Enter name of Software:");
        String version = JOptionPane.showInputDialog("Enter the current Version of Software:");

        ticketHandle.createSoftwareTicket(name, email, phone, description, software, version);
        JOptionPane.showMessageDialog(null, "Software ticket created successfully!");
    }

    private void createNetworkTicket(Scanner scan) throws IOException {
        // Gather network ticket details from user
        String name = JOptionPane.showInputDialog("Enter your Name:");
        String email = JOptionPane.showInputDialog("Enter your Email:");
        String phone = JOptionPane.showInputDialog("Enter your Phone:");
        String description = JOptionPane.showInputDialog("Enter Description:");
        String device = JOptionPane.showInputDialog("Enter Network Issue:");
        String ipAddress = JOptionPane.showInputDialog("Enter IP address:");

        ticketHandle.createNetworkTicket(name, email, phone, description, device, ipAddress);
        JOptionPane.showMessageDialog(null, "Network ticket created successfully!");
    }
});


        sortTicketsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ArrayList<Ticket> tickets = ticketHandle.loadTickets();
                    // Assuming sortTickets takes (ArrayList<Ticket>, JTextArea) as parameters
                    ticketSorting.sortTickets(tickets, ticketDisplayArea);
                    ticketDisplayArea.setText("");
                    ticketHandle.displayTickets(tickets, ticketDisplayArea);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error sorting tickets.");
                }
            }
        });

        filterTicketsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ArrayList<Ticket> tickets = ticketHandle.loadTickets();
                    String status = JOptionPane.showInputDialog(null, "Enter status to filter (e.g., Open, Closed):");
                    if (status != null && !status.isEmpty()) {
                        // Assuming filterTickets takes (ArrayList<Ticket>, JTextArea, String) as parameters
                        ticketSorting.filterTickets(tickets, ticketDisplayArea);
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid status input.");
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error filtering tickets.");
                }
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
                            switch (type) {
                                case "Hardware":
                                    String hardware = JOptionPane.showInputDialog("Enter the type of Hardware:");
                                    String model = JOptionPane.showInputDialog("Enter Model Number of Hardware:");
                                    ticketHandle.createHardwareTicket(name, email, phone, description, hardware, model);
                                    break;
                                case "Software":
                                    String software = JOptionPane.showInputDialog("Enter name of Software:");
                                    String version = JOptionPane.showInputDialog("Enter the current Version of Software:");
                                    ticketHandle.createSoftwareTicket(name, email, phone, description, software, version);
                                    break;
                                case "Network":
                                    String device = JOptionPane.showInputDialog("Enter Network Issue:");
                                    String ipAddress = JOptionPane.showInputDialog("Enter IP address:");
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
    }

    public static void main(String[] args) {
        ITInterfaceGUI itInterface = new ITInterfaceGUI();
        itInterface.setVisible(true);
    }
}

