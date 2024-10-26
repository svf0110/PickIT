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
import java.util.Date;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TicketHandle {

    public void checkTicketsTable() throws SQLException {
        String sql = "CREATE TABLE tickets ("
                + "ticketNum VARCHAR(10) PRIMARY KEY, "
                + "name VARCHAR(255), description VARCHAR(1000), email VARCHAR(255), "
                + "phone VARCHAR(20), creationDate TIMESTAMP, status VARCHAR(20), "
                + "priority VARCHAR(20), " // Added priority column
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
    
    public void createTicket(String type, String name, String description, String email, String phone, String priority, String field1, String field2) throws SQLException {
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


    public void saveTicket(Ticket ticket) throws SQLException {
        String sql = "INSERT INTO tickets (ticketNum, name, description, email, phone, creationDate, status, priority, type, extraField1, extraField2) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, ticket.getTicketNum());
            pstmt.setString(2, ticket.getName());
            pstmt.setString(3, ticket.getDescription());
            pstmt.setString(4, ticket.getEmail());
            pstmt.setString(5, ticket.getPhone());
            pstmt.setTimestamp(6, new java.sql.Timestamp(ticket.getCreationDate().getTime()));
            pstmt.setString(7, ticket.getStatus());
            pstmt.setString(8, ticket.getPriority()); // Save priority
            pstmt.setString(9, ticket.getClass().getSimpleName());
            if (ticket instanceof SoftwareTicket) {
                SoftwareTicket st = (SoftwareTicket) ticket;
                pstmt.setString(10, st.getSoftware());
                pstmt.setString(11, st.getVersion());
            } else if (ticket instanceof HardwareTicket) {
                HardwareTicket ht = (HardwareTicket) ticket;
                pstmt.setString(10, ht.getHardware());
                pstmt.setString(11, ht.getModel());
            } else if (ticket instanceof NetworkTicket) {
                NetworkTicket nt = (NetworkTicket) ticket;
                pstmt.setString(10, nt.getDevice());
                pstmt.setString(11, nt.getIpAddress());
            } else {
                pstmt.setString(10, null);
                pstmt.setString(11, null);
            }
            pstmt.executeUpdate();
        }
    }

    public void displayTickets() throws SQLException {
        String sql = "SELECT * FROM tickets";

        try (Connection conn = DBConnection.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
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
                String priority = rs.getString("priority");  // Fetch the priority field

                Ticket ticket = null;

                // Instantiate the correct ticket subclass based on the type
                if ("SoftwareTicket".equals(type)) {
                    String software = rs.getString("extraField1");
                    String version = rs.getString("extraField2");
                    ticket = new SoftwareTicket(ticketNum, name, description, email, phone, creationDate, priority, software, version);
                } else if ("HardwareTicket".equals(type)) {
                    String hardware = rs.getString("extraField1");
                    String model = rs.getString("extraField2");
                    ticket = new HardwareTicket(ticketNum, name, description, email, phone, creationDate, priority, hardware, model);
                } else if ("NetworkTicket".equals(type)) {
                    String device = rs.getString("extraField1");
                    String ipAddress = rs.getString("extraField2");
                    ticket = new NetworkTicket(ticketNum, name, description, email, phone, creationDate, priority, device, ipAddress);
                }

                // Print the ticket details
                if (ticket != null) {
                    System.out.println(ticket.toString());
                }
            }
        }
    }

    public void updateTicketStatus(String ticketNum, String newStatus) throws SQLException {
        String sql = "UPDATE tickets SET status = ? WHERE ticketNum = ?";
        try (Connection conn = DBConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newStatus);
            pstmt.setString(2, ticketNum);
            pstmt.executeUpdate();
        }
    }

    public void deleteTicket(String ticketNum) throws SQLException {
        String sql = "DELETE FROM tickets WHERE ticketNum = ?";
        try (Connection conn = DBConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, ticketNum);
            pstmt.executeUpdate();
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
}
