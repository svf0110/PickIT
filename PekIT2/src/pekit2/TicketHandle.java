/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pekit2;

/**
 *
 * @author Gio Turtal and Jose Laserna
 */
import java.sql.Statement;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TicketHandle
{
    private static final String TICKETS_FILE = "tickets.txt";

    public void checkTicketsTable() throws SQLException {
        String sql = "CREATE TABLE tickets ("
                + "ticketNum VARCHAR(10) PRIMARY KEY, "
                + "name VARCHAR(255), description VARCHAR(1000), email VARCHAR(255), "
                + "phone VARCHAR(20), creationDate TIMESTAMP, status VARCHAR(20), "
                + "type VARCHAR(20), extraField1 VARCHAR(255), extraField2 VARCHAR(255))";

        try (Connection conn = DBConnection.connect(); Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);
            System.out.println("TICKETS table created successfully.");

        } catch (SQLException e) {
            // Check if the error is because the table already exists
            if (e.getSQLState().equals("X0Y32")) {
                System.out.println("TICKETS table already exists, skipping creation.");
            } else {
                // Re-throw the exception if it's some other issue
                throw e;
            }
        }
    }


    public void createTicket(String type) throws IOException, SQLException
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

    private void saveTicket(Ticket ticket) throws SQLException 
    {
        String sql = "INSERT INTO tickets (ticketNum, name, description, email, phone, creationDate, status, type, extraField1, extraField2) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) 
        {
            pstmt.setString(1, ticket.getTicketNum());
            pstmt.setString(2, ticket.getName());
            pstmt.setString(3, ticket.getDescription());
            pstmt.setString(4, ticket.getEmail());
            pstmt.setString(5, ticket.getPhone());
            pstmt.setTimestamp(6, new java.sql.Timestamp(ticket.getCreationDate().getTime()));
            pstmt.setString(7, ticket.getStatus());
            pstmt.setString(8, ticket.getClass().getSimpleName());
            if (ticket instanceof SoftwareTicket) {
                SoftwareTicket st = (SoftwareTicket) ticket;
                pstmt.setString(9, st.getSoftware());
                pstmt.setString(10, st.getVersion());
            } else if (ticket instanceof HardwareTicket) {
                HardwareTicket ht = (HardwareTicket) ticket;
                pstmt.setString(9, ht.getHardware());
                pstmt.setString(10, ht.getModel());
            } else if (ticket instanceof NetworkTicket) {
                NetworkTicket nt = (NetworkTicket) ticket;
                pstmt.setString(9, nt.getDevice());
                pstmt.setString(10, nt.getIpAddress());
            } else {
                pstmt.setString(9, null);
                pstmt.setString(10, null);
            }
            pstmt.executeUpdate();
        }
    }

    //THIS MIGHT NOT EVEN BE USED!!!!!!!
    public ArrayList<Ticket> loadTickets() throws SQLException 
    {
        ArrayList<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM tickets";
        try (Connection conn = DBConnection.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) 
        {
            while (rs.next()) 
            {
                String ticketNum = rs.getString("ticketNum");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String description = rs.getString("description");
                Date creationDate = new Date(rs.getTimestamp("creationDate").getTime());
                String status = rs.getString("status");
                String type = rs.getString("type");

                Ticket ticket = null;
                if ("SoftwareTicket".equals(type)) 
                {
                    String software = rs.getString("extraField1");
                    String version = rs.getString("extraField2");
                    ticket = new SoftwareTicket(ticketNum, name, description, email, phone, creationDate, software, version);
                } 
                else if ("HardwareTicket".equals(type)) 
                {
                    String hardware = rs.getString("extraField1");
                    String model = rs.getString("extraField2");
                    ticket = new HardwareTicket(ticketNum, name, description, email, phone, creationDate, hardware, model);
                } 
                else if ("NetworkTicket".equals(type)) 
                {
                    String device = rs.getString("extraField1");
                    String ipAddress = rs.getString("extraField2");
                    ticket = new NetworkTicket(ticketNum, name, description, email, phone, creationDate, device, ipAddress);
                }
                if (ticket != null) 
                {
                    tickets.add(ticket);
                }
            }
        }
        return tickets;
    }


    public void displayTickets() throws SQLException 
    {
        String sql = "SELECT * FROM tickets";

        try (Connection conn = DBConnection.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) 
        {
            // Iterate through the result set
            while (rs.next()) {
                String ticketNum = rs.getString("ticketNum");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String description = rs.getString("description");
                Date creationDate = new Date(rs.getTimestamp("creationDate").getTime());
                String status = rs.getString("status");
                String type = rs.getString("type");

                Ticket ticket = null;

                // Instantiate the correct ticket subclass based on the type
                if ("SoftwareTicket".equals(type)) {
                    String software = rs.getString("extraField1");
                    String version = rs.getString("extraField2");
                    ticket = new SoftwareTicket(ticketNum, name, description, email, phone, creationDate, software, version);
                } 
                else if ("HardwareTicket".equals(type)) 
                {
                    String hardware = rs.getString("extraField1");
                    String model = rs.getString("extraField2");
                    ticket = new HardwareTicket(ticketNum, name, description, email, phone, creationDate, hardware, model);
                } 
                else if ("NetworkTicket".equals(type)) 
                {
                    String device = rs.getString("extraField1");
                    String ipAddress = rs.getString("extraField2");
                    ticket = new NetworkTicket(ticketNum, name, description, email, phone, creationDate, device, ipAddress);
                }

                // Print the ticket details
                if (ticket != null) 
                {
                    System.out.println(ticket.toString());
                }
            }
        }
    }

    public void deleteTicket() throws SQLException 
    {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter ticket number to delete: ");
        System.out.print("H_ _ _, S_ _ _, N _ _ _");
        String ticketNum = scan.nextLine();

        // SQL query to delete the ticket with the specified ticketNum
        String sql = "DELETE FROM tickets WHERE ticketNum = ?";

        try (Connection conn = DBConnection.connect(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) 
        {
            // Set the ticketNum in the query
            pstmt.setString(1, ticketNum);
            
            int rowsAffected = pstmt.executeUpdate();
            
            // Check if a ticket was actually deleted
            if (rowsAffected > 0) 
            {
                System.out.println("\nTicket deleted successfully.\n");
            } 
            else 
            {
                System.out.println("\nTicket not found.\n");
            }
        }
    }

    public void editTicket() throws SQLException {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter ticket number to edit: ");
        System.out.print("H_ _ _, S_ _ _, N _ _ _");
        String ticketNum = scan.nextLine();

        System.out.println("Edit status: Closed(1), Resolved(2), Open(3)");
        int option = scan.nextInt();
        scan.nextLine(); // Consume newline

        // Determine the new status based on the user input
        String newStatus = null;
        switch (option) {
            case 1:
                newStatus = "Closed";
                break;
            case 2:
                newStatus = "Resolved";
                break;
            case 3:
                newStatus = "Open";
                break;
            default:
                System.out.println("Invalid option.");
                return;
        }

        // SQL query to update the status of the ticket
        String sql = "UPDATE tickets SET status = ? WHERE ticketNum = ?";

        try (Connection conn = DBConnection.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) 
        {
            // Set the new status and ticketNum in the query
            pstmt.setString(1, newStatus);
            pstmt.setString(2, ticketNum);

            int rowsAffected = pstmt.executeUpdate();

            // Check if the ticket was found and updated
            if (rowsAffected > 0) 
            {
                System.out.println("\nTicket status updated successfully.\n");
            } else 
            {
                System.out.println("\nTicket not found.\n");
            }
        }
    }

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

    private String generateTicketNum(String type) throws SQLException 
    {
        String prefix = null;
        switch (type) 
        {
            case "Hardware":
                prefix = "H";
                break;
            case "Software":
                prefix = "S";
                break;
            case "Network":
                prefix = "N";
                break;
        }

        String sql = "SELECT COUNT(*) FROM tickets WHERE ticketNum LIKE ?";
        try (Connection conn = DBConnection.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) 
        {
            pstmt.setString(1, prefix + "%");
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) 
            {
                int count = rs.getInt(1) + 1; // Next ticket number
                return String.format("%s%03d", prefix, count);
            }
        }
        return null; // Fallback, in case of failure
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
