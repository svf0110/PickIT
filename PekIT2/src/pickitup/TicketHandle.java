/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pickitup;

import java.sql.Statement;
import java.util.Date;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Gio Turtal and Jose Laserna
 */

public class TicketHandle 
{

    public void checkTicketsTable() throws SQLException 
    {
        String sql = "CREATE TABLE tickets ("
                + "ticketNum VARCHAR(10) PRIMARY KEY, "
                + "name VARCHAR(255), description VARCHAR(1000), email VARCHAR(255), "
                + "phone VARCHAR(20), creationDate TIMESTAMP, status VARCHAR(20), "
                + "priority VARCHAR(20), " // Added priority column
                + "type VARCHAR(20), extraField1 VARCHAR(255), extraField2 VARCHAR(255))";

        try (Connection conn = DBConnection.connect(); Statement stmnt = conn.createStatement()) 
        {
            stmnt.executeUpdate(sql);
            System.out.println("TICKETS table created successfully.");

        } catch (SQLException e) 
        {
            // Check if the error is because the table already exists
            if (e.getSQLState().equals("X0Y32")) 
            {
                System.out.println("TICKETS table already exists, skipping creation.");
            } 
            else 
            {
                // Re-throw the exception if it's some other issue
                throw e;
            }
        }
    }
    
    public void createTicket(String type, String name, String description, String email, String phone, String priority, String field1, String field2) throws SQLException 
    {
        Ticket ticket = null;  // Declare ticket here
        String ticketnum = generateTicketNum(type);  // Declare ticketnum here

        switch (type) 
        {
            case "Hardware":
                ticket = new HardwareTicket(ticketnum, name, description, email, phone, new Date(), priority, field1, field2);
                saveTicket(ticket);
                return;
            case "Software":
                ticket = new SoftwareTicket(ticketnum, name, description, email, phone, new Date(), priority, field1, field2);
                saveTicket(ticket);
                return;
            case "Network":
                ticket = new NetworkTicket(ticketnum, name, description, email, phone, new Date(), priority, field1, field2);
                saveTicket(ticket);
                return;
            default:
                throw new IllegalArgumentException("Unknown ticket type: " + type);
        }
    }


    public void saveTicket(Ticket ticket) throws SQLException 
    {
        String sql = "INSERT INTO tickets (ticketNum, name, description, email, phone, creationDate, status, priority, type, extraField1, extraField2) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.connect(); PreparedStatement pstmnt = conn.prepareStatement(sql)) 
        {
            pstmnt.setString(1, ticket.getTicketNum());
            pstmnt.setString(2, ticket.getName());
            pstmnt.setString(3, ticket.getDescription());
            pstmnt.setString(4, ticket.getEmail());
            pstmnt.setString(5, ticket.getPhone());
            pstmnt.setTimestamp(6, new java.sql.Timestamp(ticket.getCreationDate().getTime()));
            pstmnt.setString(7, ticket.getStatus());
            pstmnt.setString(8, ticket.getPriority()); // Save priority
            pstmnt.setString(9, ticket.getClass().getSimpleName());
            if (ticket instanceof SoftwareTicket) 
            {
                SoftwareTicket st = (SoftwareTicket) ticket;
                pstmnt.setString(10, st.getSoftware());
                pstmnt.setString(11, st.getVersion());
            } 
            else if (ticket instanceof HardwareTicket) 
            {
                HardwareTicket ht = (HardwareTicket) ticket;
                pstmnt.setString(10, ht.getHardware());
                pstmnt.setString(11, ht.getModel());
            } 
            else if (ticket instanceof NetworkTicket) 
            {
                NetworkTicket nt = (NetworkTicket) ticket;
                pstmnt.setString(10, nt.getDevice());
                pstmnt.setString(11, nt.getIpAddress());
            } 
            else 
            {
                pstmnt.setString(10, null);
                pstmnt.setString(11, null);
            }
            pstmnt.executeUpdate();
        }
    }

    public void displayTickets() throws SQLException {
        String sql = "SELECT * FROM tickets";

        try (Connection conn = DBConnection.connect(); Statement stmnt = conn.createStatement(); ResultSet resultSet = stmnt.executeQuery(sql)) {
            // Iterate through the result set
            while (resultSet.next()) 
            {
                String ticketNum = resultSet.getString("ticketNum");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String description = resultSet.getString("description");
                Date creationDate = new Date(resultSet.getTimestamp("creationDate").getTime());
                String status = resultSet.getString("status");
                String type = resultSet.getString("type");
                String priority = resultSet.getString("priority");  // Fetch the priority field

                Ticket ticket = null;

                // Instantiate the correct ticket subclass based on the type
                if ("SoftwareTicket".equals(type)) 
                {
                    String software = resultSet.getString("extraField1");
                    String version = resultSet.getString("extraField2");
                    ticket = new SoftwareTicket(ticketNum, name, description, email, phone, creationDate, priority, software, version);
                } 
                else if ("HardwareTicket".equals(type)) 
                {
                    String hardware = resultSet.getString("extraField1");
                    String model = resultSet.getString("extraField2");
                    ticket = new HardwareTicket(ticketNum, name, description, email, phone, creationDate, priority, hardware, model);
                } 
                else if ("NetworkTicket".equals(type)) 
                {
                    String device = resultSet.getString("extraField1");
                    String ipAddress = resultSet.getString("extraField2");
                    ticket = new NetworkTicket(ticketNum, name, description, email, phone, creationDate, priority, device, ipAddress);
                }

                // Print the ticket details
                if (ticket != null) 
                {
                    System.out.println(ticket.toString());
                }
            }
        }
    }

    public void updateTicketStatus(String ticketNum, String newStatus) throws SQLException 
    {
        String sql = "UPDATE tickets SET status = ? WHERE ticketNum = ?";
        try (Connection conn = DBConnection.connect();
             PreparedStatement pstmnt = conn.prepareStatement(sql)) {
            pstmnt.setString(1, newStatus);
            pstmnt.setString(2, ticketNum);
            pstmnt.executeUpdate();
        }
    }

    public void deleteTicket(String ticketNum) throws SQLException 
    {
        String sql = "DELETE FROM tickets WHERE ticketNum = ?";
        try (Connection conn = DBConnection.connect();
             PreparedStatement pstmnt = conn.prepareStatement(sql)) 
        {
            pstmnt.setString(1, ticketNum);
            pstmnt.executeUpdate();
        }
    }
    
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
        try (Connection conn = DBConnection.connect(); PreparedStatement pstmnt = conn.prepareStatement(sql)) 
        {
            pstmnt.setString(1, prefix + "%");
            ResultSet resultSet = pstmnt.executeQuery();
            if (resultSet.next()) 
            {
                int count = resultSet.getInt(1) + 1; // Next ticket number
                return String.format("%s%03d", prefix, count);
            }
        }
        return null; // Fallback, in case of failure
    }
}
