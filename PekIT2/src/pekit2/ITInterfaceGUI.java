///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package pekit2;
//
///**
// *
// * @author jmrla
// */
//import javax.swing.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.IOException;
//import java.util.ArrayList;
//
//public class ITInterfaceGUI extends JFrame {
//    private TicketHandle ticketHandle;
//    private TicketSorting ticketSorting;
//
//    public ITInterfaceGUI() {
//        ticketHandle = new TicketHandle();
//        ticketSorting = new TicketSorting();
//
//        // Set up JFrame
//        setTitle("IT Staff Menu");
//        setSize(400, 300);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLayout(null);
//
//        // Buttons for IT staff actions
//        JButton viewTicketsButton = new JButton("View Tickets");
//        viewTicketsButton.setBounds(100, 50, 200, 30);
//        add(viewTicketsButton);
//
//        JButton createTicketButton = new JButton("Create Ticket");
//        createTicketButton.setBounds(100, 100, 200, 30);
//        add(createTicketButton);
//
//        JButton sortTicketsButton = new JButton("Sort Tickets");
//        sortTicketsButton.setBounds(100, 150, 200, 30);
//        add(sortTicketsButton);
//
//        JButton filterTicketsButton = new JButton("Filter Tickets");
//        filterTicketsButton.setBounds(100, 200, 200, 30);
//        add(filterTicketsButton);
//
//        // Add button listeners
//        viewTicketsButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    ArrayList<Ticket> tickets = ticketHandle.loadTickets();
//                    ticketHandle.displayTickets(tickets);
//                } catch (IOException ex) {
//                    JOptionPane.showMessageDialog(null, "Error loading tickets.");
//                }
//            }
//        });
//
//        createTicketButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    String[] types = {"Hardware", "Software", "Network"};
//                    String type = (String) JOptionPane.showInputDialog(null, "Select ticket type:", "Ticket Type",
//                            JOptionPane.QUESTION_MESSAGE, null, types, types[0]);
//
//                    if (type != null) {
//                        ticketHandle.createTicket(type);
//                    }
//                } catch (IOException ex) {
//                    JOptionPane.showMessageDialog(null, "Error creating ticket.");
//                }
//            }
//        });
//
//        sortTicketsButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    ArrayList<Ticket> tickets = ticketHandle.loadTickets();
//                    ticketSorting.sortTickets(tickets);
//                } catch (IOException ex) {
//                    JOptionPane.showMessageDialog(null, "Error sorting tickets.");
//                }
//            }
//        });
//
//        filterTicketsButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    ArrayList<Ticket> tickets = ticketHandle.loadTickets();
//                    ticketSorting.filterTickets(tickets);
//                } catch (IOException ex) {
//                    JOptionPane.showMessageDialog(null, "Error filtering tickets.");
//                }
//            }
//        });
//    }
//
//    public static void main(String[] args) {
//        ITInterfaceGUI itInterface = new ITInterfaceGUI();
//        itInterface.setVisible(true);
//    }
//}
//

package pekit2;

import javax.swing.*;
import java.awt.*;
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
        setLayout(new BorderLayout());

        // Create the ticket display area
        ticketDisplayArea = new JTextArea();
        ticketDisplayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(ticketDisplayArea);
        add(scrollPane, BorderLayout.CENTER);

        // Create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1));

        JButton viewTicketsButton = new JButton("View Tickets");
        JButton createTicketButton = new JButton("Create Ticket");
        JButton sortTicketsButton = new JButton("Sort Tickets");
        JButton filterTicketsButton = new JButton("Filter Tickets");

        buttonPanel.add(viewTicketsButton);
        buttonPanel.add(createTicketButton);
        buttonPanel.add(sortTicketsButton);
        buttonPanel.add(filterTicketsButton);

        add(buttonPanel, BorderLayout.EAST);

        // Add button listeners
        viewTicketsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ArrayList<Ticket> tickets = ticketHandle.loadTickets();
                    ticketDisplayArea.setText("");
                    ticketHandle.displayTickets(tickets, ticketDisplayArea);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error loading tickets.");
                }
            }
        });

        createTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createTicket();
            }
        });

//        sortTicketsButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    ArrayList<Ticket> tickets = ticketHandle.loadTickets();
//                    ticketSorting.sortTickets(tickets);
//                    ticketDisplayArea.setText("");
//                    ticketHandle.displayTickets(tickets, ticketDisplayArea);
//                } catch (IOException ex) {
//                    JOptionPane.showMessageDialog(null, "Error sorting tickets.");
//                }
//            }
//        });

        sortTicketsButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            ArrayList<Ticket> tickets = ticketHandle.loadTickets();
            ticketSorting.sortTickets(tickets, ticketDisplayArea); // This line needs to pass ticketDisplayArea
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
                    ticketSorting.filterTickets(tickets, ticketDisplayArea);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error filtering tickets.");
                }
            }
        });
    }

    private void createTicket() {
        String[] types = {"Hardware", "Software", "Network"};
        String type = (String) JOptionPane.showInputDialog(null, "Select ticket type:", "Ticket Type",
                JOptionPane.QUESTION_MESSAGE, null, types, types[0]);

        if (type != null) {
            try {
                ticketHandle.createTicket(type);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error creating ticket.");
            }
        }
    }

    public static void main(String[] args) {
        ITInterfaceGUI itInterface = new ITInterfaceGUI();
        itInterface.setVisible(true);
    }
}
