///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
package pekit2;
//
///**
// *
// * @author Gio Turtal and Jose Laserna
// */
//import java.io.IOException;
//import java.util.Scanner;
//import java.util.ArrayList;
//
//public class ITInterface
//{
//    public  TicketHandle ticketHandle = new TicketHandle();
//    public TicketSorting ticketSorting = new TicketSorting();
//
//    public void displayMenu() throws IOException, ClassNotFoundException
//    {
//        ArrayList<Ticket> tickets = ticketHandle.loadTickets();
//
//        Scanner scan = new Scanner(System.in);
//        while (true)
//        {
//            System.out.println("\n===============================================");
//            System.out.println("             Welcome to IT Portal              ");
//            System.out.println("===============================================\n");
//            System.out.println("All tickets(1), Filter(2), Sort(3), Edit(4) or Delete(5)? 'x' to go back\n");
//            String option = scan.nextLine();
//
//            if (option.equals("x"))
//            {
//                break;
//            }
//
//            switch (option)
//            {
//                case "1":
//                    ticketHandle.displayTickets(tickets);
//                    break;
//                case "2":
//                    ticketSorting.filterTickets(tickets);
//                    break;
//                case "3":
//                    ticketSorting.sortTickets(tickets);
//                    break;
//                case "4":
//                    ticketHandle.editTicket(tickets);
//                    break;
//                case "5":
//                    ticketHandle.deleteTicket(tickets);
//                    break;
//                default:
//                    System.out.println("\n          Invalid. Try Again.         \n");
//            }
//        }
//    }
//}

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ITInterface {
    public TicketHandle ticketHandle = new TicketHandle();
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
                    ticketSorting.filterTickets(tickets, ticketDisplayArea); // Pass the JTextArea
                    break;
                case "3":
                    ticketSorting.sortTickets(tickets, ticketDisplayArea); // Update this if necessary
                    break;
                case "4":
                    ticketHandle.editTicket(tickets);
                    break;
                case "5":
                    ticketHandle.deleteTicket(tickets);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid option. Try Again.");
            }
        }
    }
}
