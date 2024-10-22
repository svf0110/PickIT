package pekit2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class TicketSorting {
    
//    public void filterTickets(ArrayList<Ticket> tickets, JTextArea displayArea) {
//    Scanner scan = new Scanner(System.in);
//    displayArea.setText(""); // Clear the text area before displaying
//
//    System.out.println("Filter by status: (1) Open, (2) Closed, (3) Resolved");
//    int option = scan.nextInt();
//
//    String status = getStatusFromOption(option);
//    if (status.isEmpty()) {
//        System.out.println("Invalid option.");
//        return;
//    }
//
//    // Call the method to display the filtered tickets
//    displayFilteredTickets(tickets, status, displayArea);
//}
    
    public void filterTickets(ArrayList<Ticket> tickets, JTextArea displayArea) {
    displayArea.setText(""); // Clear the text area before displaying

    // Prompt the user to select a filter option
    String[] options = {"Open", "Closed", "Resolved"};
    String selectedOption = (String) JOptionPane.showInputDialog(
        null,
        "Filter by status:",
        "Filter Tickets",
        JOptionPane.QUESTION_MESSAGE,
        null,
        options,
        options[0]
    );

    if (selectedOption == null) {
        // User canceled or closed the dialog
        return;
    }

    // Call the method to display the filtered tickets
    displayFilteredTickets(tickets, selectedOption, displayArea);
}


    
    private void displayFilteredTickets(ArrayList<Ticket> tickets, String status, JTextArea displayArea) {
    for (Ticket ticket : tickets) {
        if (ticket.getStatus().equalsIgnoreCase(status)) {
            // Append the filtered ticket information to the JTextArea
            displayArea.append(ticket.getTicketNum() + ": " + ticket.getName() + " - " + ticket.getStatus() + "\n");
        }
    }
}

    private String getStatusFromOption(int option) {
        switch (option) {
            case 1:
                return "Open";
            case 2:
                return "Closed";
            case 3:
                return "Resolved";
            default:
                return "";
        }
    }
    
    // Method to sort tickets
    public void sortTickets(ArrayList<Ticket> tickets, JTextArea displayArea) {
        // Example sorting logic - you can customize it based on your requirements
        Collections.sort(tickets, new Comparator<Ticket>() {
            @Override
            public int compare(Ticket t1, Ticket t2) {
                // Assuming Ticket has a 'getDate' method to sort by date
                return t1.getCreationDate().compareTo(t2.getCreationDate()); // Sort by date
            }
        });

        // Display sorted tickets in the JTextArea
        displayArea.setText(""); // Clear existing text
        for (Ticket ticket : tickets) {
            displayArea.append(ticket.toString() + "\n"); // Display each ticket
        }
    }

    private boolean applySortingOption(ArrayList<Ticket> tickets, int option) {
        switch (option) {
            case 1:
                Collections.sort(tickets, Comparator.comparing(Ticket::getName));
                return true;
            case 2:
                Collections.sort(tickets, Comparator.comparing(Ticket::getName).reversed());
                return true;
            case 3:
                Collections.sort(tickets, Comparator.comparing(Ticket::getCreationDate));
                return true;
            case 4:
                Collections.sort(tickets, Comparator.comparing(Ticket::getCreationDate).reversed());
                return true;
            default:
                return false;
        }
    }

    private void displaySortedTickets(ArrayList<Ticket> tickets) {
        System.out.println("\n          Tickets sorted:         \n");
        for (Ticket ticket : tickets) {
            System.out.println(ticket.getTicketNum() + ": " + ticket.getName() + " - " + ticket.getCreationDate());
        }
    }
}
