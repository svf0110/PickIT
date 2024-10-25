///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package pekit2;
//
///**
// *
// * @author Gio Turtal and Jose Laserna
// */
//import java.io.*;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.Scanner;
//import javax.swing.JTextArea;
//import java.sql.Connection;
//
//
//public class TicketHandle
//{
//    private static final String TICKETS_FILE = "tickets.txt";
//    private TicketDAO ticketDAO;
//    
//     public TicketHandle(Connection conn) {
//        ticketDAO = new TicketDAO(conn);
//    }
//
//    
//    public void createHardwareTicket(String name, String email, String phone, String description, String hardware, String model) throws IOException {
//        String ticketNum = generateTicketNum("Hardware");
//        Ticket ticket = new HardwareTicket(ticketNum, name, description, email, phone, new Date(), hardware, model);
//        saveTicket(ticket);
//    }
//
//    public void createSoftwareTicket(String name, String email, String phone, String description, String software, String version) throws IOException {
//        String ticketNum = generateTicketNum("Software");
//        Ticket ticket = new SoftwareTicket(ticketNum, name, description, email, phone, new Date(), software, version);
//        saveTicket(ticket);
//    }
//
//    public void createNetworkTicket(String name, String email, String phone, String description, String device, String ipAddress) throws IOException {
//        String ticketNum = generateTicketNum("Network");
//        Ticket ticket = new NetworkTicket(ticketNum, name, description, email, phone, new Date(), device, ipAddress);
//        saveTicket(ticket);
//    }
//
//    private void saveTicket(Ticket ticket) throws IOException
//    {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TICKETS_FILE, true)))
//        {
//            writer.write(ticket.toFileString()); // Assuming Ticket has a toFileString method that formats its data for file writing
//            writer.newLine();
//        }
//    }
//
//    public ArrayList<Ticket> loadTickets() throws IOException
//    {
//        ArrayList<Ticket> tickets = new ArrayList<>();
//        File file = new File(TICKETS_FILE);
//        if (!file.exists())
//        {
//            return tickets;
//        }
//
//        try (BufferedReader reader = new BufferedReader(new FileReader(file)))
//        {
//            String line;
//            while ((line = reader.readLine()) != null)
//            {
//                String[] parts = line.split(",");
//                if (parts.length >= 6)
//                {
//                    String ticketNum = parts[0];
//                    String name = parts[1];
//                    String email = parts[2];
//                    String phone = parts[3];
//                    String description = parts[4];
//                    Date date = new Date(Long.parseLong(parts[5]));
//
//                    Ticket ticket = null;
//                    if (ticketNum.startsWith("H")) // HardwareTicket
//                    {
//                        String hardware = parts[6];
//                        String model = parts[7];
//                        ticket = new HardwareTicket(ticketNum, name, description, email, phone, date, hardware, model);
//                    }
//                    else if (ticketNum.startsWith("S")) // SoftwareTicket
//                    {
//                        String software = parts[6];
//                        String version = parts[7];
//                        ticket = new SoftwareTicket(ticketNum, name, description, email, phone, date, software, version);
//                    }
//                    else if (ticketNum.startsWith("N")) // NetworkTicket
//                    {
//                        String device = parts[6];
//                        String ipAddress = parts[7];
//                        ticket = new NetworkTicket(ticketNum, name, description, email, phone, date, device, ipAddress);
//                    }
//
//                    if (ticket != null)
//                    {
//                        tickets.add(ticket);
//                    }
//                }
//            }
//        }
//        return tickets;
//    }
//
//    
//    public void displayTickets(ArrayList<Ticket> tickets, JTextArea displayArea) {
//    displayArea.setText(""); // Clear the text area before displaying
//    for (Ticket ticket : tickets) {
//        // Append the ticket information to the JTextArea
//        displayArea.append(ticket.toString() + "\n");
//    }
//}
//
//    public void deleteTicket(ArrayList<Ticket> tickets) throws IOException
//    {
//        Scanner scan = new Scanner(System.in);
//        System.out.print("Enter ticket number to delete: \n");
//        System.out.print("H_ _ _, S_ _ _, N _ _ _");
//        String ticketNum = scan.nextLine();
//
//        tickets.removeIf(ticket -> ticket.getTicketNum().equals(ticketNum));
//        saveAllTickets(tickets);
//        System.out.println("\n          Ticket deleted successfully.            \n");
//    }
//
//    public void editTicket(ArrayList<Ticket> tickets) throws IOException
//    {
//        Scanner scan = new Scanner(System.in);
//        System.out.print("Enter ticket number to edit: ");
//        System.out.print("H_ _ _, S_ _ _, N _ _ _");
//        String ticketNum = scan.nextLine();
//
//        for (Ticket ticket : tickets)
//        {
//            if (ticket.getTicketNum().equals(ticketNum))
//            {
//                System.out.println("Edit status: Closed(1), Resolved(2), Open(3)");
//                int option = scan.nextInt();
//                scan.nextLine(); // Consume newline
//                switch (option)
//                {
//                    case 1:
//                        ticket.setStatus("Closed");
//                        break;
//                    case 2:
//                        ticket.setStatus("Resolved");
//                        break;
//                    case 3:
//                        ticket.setStatus("Open");
//                        break;
//                }
//                saveAllTickets(tickets);
//                System.out.println("\n          Ticket status updated successfully.            \n");
//                return;
//            }
//        }
//
//        System.out.println("\n          Ticket not found.           \n");
//    }
//
//    private void saveAllTickets(ArrayList<Ticket> tickets) throws IOException
//    {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TICKETS_FILE)))
//        {
//            for (Ticket ticket : tickets)
//            {
//                writer.write(ticket.toFileString());
//                writer.newLine();
//            }
//        }
//    }
//
//    private String generateTicketNum(String type) throws IOException
//    {
//        ArrayList<Ticket> tickets = loadTickets(); // Load all tickets
//        int nextTicketNumber = tickets.size() + 1; // Calculate the next ticket number based on the total count
//        char prefix = 'H'; // Default to Hardware, this will still prefix the ticket number with 'H', 'S', or 'N'
//
//        switch (type)
//        {
//            case "Hardware":
//                prefix = 'H';
//                break;
//            case "Software":
//                prefix = 'S';
//                break;
//            case "Network":
//                prefix = 'N';
//                break;
//        }
//
//        // Format the ticket number with the prefix and next sequential number
//        return String.format("%c%03d", prefix, nextTicketNumber); //prefix
//    }
//    
//    public ArrayList<Ticket> filterTicketsByType(ArrayList<Ticket> tickets, String type) {
//        ArrayList<Ticket> filteredTickets = new ArrayList<>();
//        for (Ticket ticket : tickets) {
//            if (ticket.getType().equalsIgnoreCase(type)) {
//                filteredTickets.add(ticket);
//            }
//        }
//        return filteredTickets;
//    }
//
//    private String capitalizeFirstLetter(String input)
//    {
//        if (input == null || input.isEmpty())
//        {
//            return input;
//        }
//        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
//    }
//}

package pekit2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import javax.swing.JTextArea;
import java.sql.Connection;
import java.sql.SQLException;

public class TicketHandle {
    private TicketDAO ticketDAO;

    public TicketHandle(Connection conn) {
        ticketDAO = new TicketDAO(conn);
    }

    public void createHardwareTicket(String name, String email, String phone, String description, String hardware, String model) throws IOException, SQLException {
        String ticketNum = generateTicketNum("Hardware");
        Ticket ticket = new HardwareTicket(ticketNum, name, description, email, phone, new Date(), hardware, model);
        ticketDAO.addTicket(ticket); // Save to database
    }

    public void createSoftwareTicket(String name, String email, String phone, String description, String software, String version) throws IOException, SQLException {
        String ticketNum = generateTicketNum("Software");
        Ticket ticket = new SoftwareTicket(ticketNum, name, description, email, phone, new Date(), software, version);
        ticketDAO.addTicket(ticket); // Save to database
    }

    public void createNetworkTicket(String name, String email, String phone, String description, String device, String ipAddress) throws IOException, SQLException {
        String ticketNum = generateTicketNum("Network");
        Ticket ticket = new NetworkTicket(ticketNum, name, description, email, phone, new Date(), device, ipAddress);
        ticketDAO.addTicket(ticket); // Save to database
    }
    
    public ArrayList<Ticket> loadTickets() throws IOException {
        try {
            return ticketDAO.getAllTickets(); // Load tickets from database
        } catch (SQLException e) {
            // Handle SQLException (log it, rethrow it, etc.)
            e.printStackTrace(); // Print the stack trace for debugging
            throw new IOException("Failed to load tickets from the database.", e); // Wrap and rethrow as IOException
        }
    }
    
    public void displayTickets(ArrayList<Ticket> tickets, JTextArea displayArea) {
        displayArea.setText(""); // Clear the text area before displaying
        for (Ticket ticket : tickets) {
            displayArea.append(ticket.toString() + "\n");
        }
    }

//    public void deleteTicket(String ticketNum) throws IOException {
//        ticketDAO.deleteTicket(ticketNum); // Delete ticket from the database
//        System.out.println("\n          Ticket deleted successfully.            \n");
//    }
    
    public void deleteTicket(String ticketNum) throws IOException {
        try {
            ticketDAO.deleteTicket(ticketNum); // Delete ticket from the database
            System.out.println("\n          Ticket deleted successfully.            \n");
        } catch (SQLException e) {
            e.printStackTrace(); // Print the stack trace for debugging
            throw new IOException("Failed to delete the ticket from the database.", e); // Wrap and rethrow
        }
    }
    
    public void editTicket(String ticketNum, String status) throws IOException, SQLException {
        ticketDAO.updateTicketStatus(ticketNum, status); // Update ticket status in the database
        System.out.println("\n          Ticket status updated successfully.            \n");
    }
    
    private String generateTicketNum(String type) throws IOException, SQLException {
    // Logic for generating the ticket number
    int nextTicketNumber = ticketDAO.getNextTicketNumber(type);
    char prefix = type.charAt(0); // 'H', 'S', or 'N'
    return String.format("%c%03d", prefix, nextTicketNumber);
}


    public ArrayList<Ticket> filterTicketsByType(ArrayList<Ticket> tickets, String type) {
        ArrayList<Ticket> filteredTickets = new ArrayList<>();
        for (Ticket ticket : tickets) {
            if (ticket.getType().equalsIgnoreCase(type)) {
                filteredTickets.add(ticket);
            }
        }
        return filteredTickets;
    }
}