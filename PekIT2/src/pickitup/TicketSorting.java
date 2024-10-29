/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package pickitup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

/**
 *
 * @author Gio Turtal and Jose Laserna
 */

public class TicketSorting {

    public void filterTickets(ArrayList<Ticket> tickets) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Filter by status: (1) Open, (2) Closed, (3) Resolved");
        int option = scan.nextInt();

        String status = getStatusFromOption(option);
        if (status.isEmpty()) {
            System.out.println("Invalid option.");
            return;
        }

        displayFilteredTickets(tickets, status);
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

    private void displayFilteredTickets(ArrayList<Ticket> tickets, String status) {
        for (Ticket ticket : tickets) {
            if (ticket.getStatus().equalsIgnoreCase(status)) {
                System.out.println(ticket.getTicketNum() + ": " + ticket.getName() + " - " + ticket.getStatus());
            }
        }
    }

    public void sortTickets(ArrayList<Ticket> tickets) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Sort by: (1) Name Ascending, (2) Name Descending, (3) Date Ascending, (4) Date Descending");
        int option = scan.nextInt();

        if (!applySortingOption(tickets, option)) {
            System.out.println("\n          Invalid option.         \n");
            return;
        }

        displaySortedTickets(tickets);
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
