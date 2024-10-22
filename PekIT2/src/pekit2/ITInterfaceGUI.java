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
                        ticketHandle.createTicket(type);
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error creating ticket.");
                }
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
    }

    public static void main(String[] args) {
        ITInterfaceGUI itInterface = new ITInterfaceGUI();
        itInterface.setVisible(true);
    }
}

