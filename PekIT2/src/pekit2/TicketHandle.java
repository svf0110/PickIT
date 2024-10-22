/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pekit2;

/**
 *
 * @author Gio Turtal and Jose Laserna
 */
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import javax.swing.JTextArea;

public class TicketHandle
{
    private static final String TICKETS_FILE = "tickets.txt";

    public void createTicket(String type) throws IOException
    {
        Scanner scan = new Scanner(System.in);
        System.out.print("            Please fill in Ticket Details.          \n\n");
        System.out.print("Enter your Name: ");
        String name = scan.nextLine();
        System.out.print("Enter your Email: ");
        String email = scan.nextLine();
        System.out.print("Enter your Phone: ");
        String phone = scan.nextLine();
        System.out.print("Enter Description: ");
        String description = scan.nextLine();
        
        Ticket ticket = null;
        String ticketNum = generateTicketNum(type); // Generate ticket number based on existing tickets
        name = capitalizeFirstLetter(name);
        
        switch (type)
        {
            case "Hardware":
                System.out.print("Enter the type of Hardware: ");
                String hardware = scan.nextLine();
                System.out.print("Enter Model Number of Hardware: ");
                String model = scan.nextLine();
                ticket = new HardwareTicket(ticketNum, name, description, email, phone, new Date(), hardware, model);
                break;
            case "Software":
                System.out.print("Enter name of Software: ");
                String software = scan.nextLine();
                System.out.print("Enter the current Version of Software: ");
                String version = scan.nextLine();
                ticket = new SoftwareTicket(ticketNum, name, description, email, phone, new Date(), software, version);
                break;
            case "Network":
                System.out.print("Enter Network Issue: ");
                String device = scan.nextLine();
                System.out.print("Enter IP address: ");
                String ipAddress = scan.nextLine();
                ticket = new NetworkTicket(ticketNum, name, description, email, phone, new Date(), device, ipAddress);
                break;
        }

        if (ticket != null)
        {
            saveTicket(ticket);
            System.out.println("-----------------------------------------------");
            System.out.println("      Ticket created successfully!             ");
            System.out.println("-----------------------------------------------\n");

            System.out.println("   Thank you for reaching out to us.        ");
            System.out.println(" Our team is already reviewing your request,");
            System.out.println("  and we will contact you shortly to assist. ");
            System.out.println("   Rest assured, we're here to help you.   \n ");
        }
    }

    private void saveTicket(Ticket ticket) throws IOException
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TICKETS_FILE, true)))
        {
            writer.write(ticket.toFileString()); // Assuming Ticket has a toFileString method that formats its data for file writing
            writer.newLine();
        }
    }

    public ArrayList<Ticket> loadTickets() throws IOException
    {
        ArrayList<Ticket> tickets = new ArrayList<>();
        File file = new File(TICKETS_FILE);
        if (!file.exists())
        {
            return tickets;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                String[] parts = line.split(",");
                if (parts.length >= 6)
                {
                    String ticketNum = parts[0];
                    String name = parts[1];
                    String email = parts[2];
                    String phone = parts[3];
                    String description = parts[4];
                    Date date = new Date(Long.parseLong(parts[5]));

                    Ticket ticket = null;
                    if (ticketNum.startsWith("H")) // HardwareTicket
                    {
                        String hardware = parts[6];
                        String model = parts[7];
                        ticket = new HardwareTicket(ticketNum, name, description, email, phone, date, hardware, model);
                    }
                    else if (ticketNum.startsWith("S")) // SoftwareTicket
                    {
                        String software = parts[6];
                        String version = parts[7];
                        ticket = new SoftwareTicket(ticketNum, name, description, email, phone, date, software, version);
                    }
                    else if (ticketNum.startsWith("N")) // NetworkTicket
                    {
                        String device = parts[6];
                        String ipAddress = parts[7];
                        ticket = new NetworkTicket(ticketNum, name, description, email, phone, date, device, ipAddress);
                    }

                    if (ticket != null)
                    {
                        tickets.add(ticket);
                    }
                }
            }
        }
        return tickets;
    }

//    public void displayTickets(ArrayList<Ticket> tickets)
//    {
//        for (Ticket ticket : tickets)
//        {
//            // This will call the correct toString() method for each specific Ticket subclass
//            System.out.println(ticket.toString());
//        }
//    }
    
    public void displayTickets(ArrayList<Ticket> tickets, JTextArea displayArea) {
    displayArea.setText(""); // Clear the text area before displaying
    for (Ticket ticket : tickets) {
        // Append the ticket information to the JTextArea
        displayArea.append(ticket.toString() + "\n");
    }
}

    public void deleteTicket(ArrayList<Ticket> tickets) throws IOException
    {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter ticket number to delete: \n");
        System.out.print("H_ _ _, S_ _ _, N _ _ _");
        String ticketNum = scan.nextLine();

        tickets.removeIf(ticket -> ticket.getTicketNum().equals(ticketNum));
        saveAllTickets(tickets);
        System.out.println("\n          Ticket deleted successfully.            \n");
    }

    public void editTicket(ArrayList<Ticket> tickets) throws IOException
    {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter ticket number to edit: ");
        System.out.print("H_ _ _, S_ _ _, N _ _ _");
        String ticketNum = scan.nextLine();

        for (Ticket ticket : tickets)
        {
            if (ticket.getTicketNum().equals(ticketNum))
            {
                System.out.println("Edit status: Closed(1), Resolved(2), Open(3)");
                int option = scan.nextInt();
                scan.nextLine(); // Consume newline
                switch (option)
                {
                    case 1:
                        ticket.setStatus("Closed");
                        break;
                    case 2:
                        ticket.setStatus("Resolved");
                        break;
                    case 3:
                        ticket.setStatus("Open");
                        break;
                }
                saveAllTickets(tickets);
                System.out.println("\n          Ticket status updated successfully.            \n");
                return;
            }
        }

        System.out.println("\n          Ticket not found.           \n");
    }

    private void saveAllTickets(ArrayList<Ticket> tickets) throws IOException
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TICKETS_FILE)))
        {
            for (Ticket ticket : tickets)
            {
                writer.write(ticket.toFileString());
                writer.newLine();
            }
        }
    }

    private String generateTicketNum(String type) throws IOException
    {
        ArrayList<Ticket> tickets = loadTickets(); // Load all tickets
        int nextTicketNumber = tickets.size() + 1; // Calculate the next ticket number based on the total count
        char prefix = 'H'; // Default to Hardware, this will still prefix the ticket number with 'H', 'S', or 'N'

        switch (type)
        {
            case "Hardware":
                prefix = 'H';
                break;
            case "Software":
                prefix = 'S';
                break;
            case "Network":
                prefix = 'N';
                break;
        }

        // Format the ticket number with the prefix and next sequential number
        return String.format("%c%03d", prefix, nextTicketNumber); //prefix
    }

    private String capitalizeFirstLetter(String input)
    {
        if (input == null || input.isEmpty())
        {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }
}

