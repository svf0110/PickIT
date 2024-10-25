/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pekit2;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.sql.SQLException;

/**
 *
 * @author Gio Turtal and Jose Laserna
 */

public class ITInterface {
    public TicketHandle ticketHandle;
    public TicketSorting ticketSorting = new TicketSorting();
    private JTextArea ticketDisplayArea; // Create JTextArea for displaying tickets

    public ITInterface(JTextArea displayArea) {
        this.ticketDisplayArea = displayArea; // Initialize with JTextArea
    }

    public void displayMenu() throws IOException {
        ArrayList<Ticket> tickets = ticketHandle.loadTickets();

        while (true) {
            String option = JOptionPane.showInputDialog(
                null,
                "Welcome to IT Portal\n" +
                "1. All tickets\n" +
                "2. Filter\n" +
                "3. Sort\n" +
                "4. Edit\n" +
                "5. Delete\n" +
                "Enter 'x' to go back."
            );

            if (option == null || option.equals("x")) {
                break;
            }

            switch (option) {
                case "1":
                    ticketHandle.displayTickets(tickets, ticketDisplayArea); // Pass the JTextArea
                    break;
                    case "2":
                    // Prompt the user for filter type
                    String[] filterOptions = {"All", "Hardware", "Software", "Network"};
                    String filterType = (String) JOptionPane.showInputDialog(
                        null,
                        "Select filter type:",
                        "Filter Tickets",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        filterOptions,
                        filterOptions[0] // Default selection
                    );

                    if (filterType != null) { // Check if the user didn't cancel the dialog
                        ticketSorting.filterTickets(tickets, ticketDisplayArea, filterType); // Pass the JTextArea and filter type
                    }
                    break;
                case "3":
                    ticketSorting.sortTickets(tickets, ticketDisplayArea); // Update this if necessary
                    break;
                case "4":
                // Prompt the user for the ticket number
                String ticketNum = JOptionPane.showInputDialog("Enter the ticket number you want to edit:");
                if (ticketNum == null || ticketNum.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Ticket editing canceled.");
                    break; // Exit the case if user cancels
                }

                // Prompt for the new status
                String[] statusOptions = {"Open", "In Progress", "Resolved", "Closed"};
                String status = (String) JOptionPane.showInputDialog(
                    null,
                    "Select new status:",
                    "Edit Ticket Status",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    statusOptions,
                    statusOptions[0] // Default selection
                );

                if (status == null) {
                    JOptionPane.showMessageDialog(null, "Ticket editing canceled.");
                    break; // Exit if user cancels
                }

                // Call the editTicket method with the gathered inputs
                try {
                    ticketHandle.editTicket(ticketNum, status);
                } catch (IOException | SQLException e) {
                    JOptionPane.showMessageDialog(null, "Failed to update ticket status: " + e.getMessage());
                }
                break;
                case "5":
                // Prompt the user for the ticket number to delete
                String ticketNumToDelete = JOptionPane.showInputDialog("Enter the ticket number you want to delete:");
                if (ticketNumToDelete == null || ticketNumToDelete.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Ticket deletion canceled.");
                    break; // Exit the case if user cancels
                }

                // Call the deleteTicket method with the gathered input
                try {
                    ticketHandle.deleteTicket(ticketNumToDelete);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Failed to delete ticket: " + e.getMessage());
                }
                break;

                default:
                    JOptionPane.showMessageDialog(null, "Invalid option. Try Again.");
            }
        }
    }
}


