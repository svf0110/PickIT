/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pekit2;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author jmrla
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class TicketDAO {
    private Connection conn;

    public TicketDAO(Connection conn) {
        this.conn = conn;
        createTableIfNotExists();
    }
    
    public void addTicket(Ticket ticket) throws IOException {
        String sql = "INSERT INTO tickets (ticketNum, name, description, email, phone, date, hardware, model) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ticket.getTicketNum());
            stmt.setString(2, ticket.getName());
            stmt.setString(3, ticket.getDescription());
            stmt.setString(4, ticket.getEmail());
            stmt.setString(5, ticket.getPhone());
            stmt.setDate(6, new java.sql.Date(ticket.getDate().getTime()));

            // Add specific fields based on ticket type
            if (ticket instanceof HardwareTicket) {
                stmt.setString(7, ((HardwareTicket) ticket).getHardware());
                stmt.setString(8, ((HardwareTicket) ticket).getModel());
            } else if (ticket instanceof SoftwareTicket) {
                // Set software and version instead
            } else if (ticket instanceof NetworkTicket) {
                // Set device and IP address instead
            }

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new IOException("Failed to add ticket to the database", e);
        }
    }
    
    // Method to get all tickets
    public ArrayList<Ticket> getAllTickets() throws SQLException {
        ArrayList<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM tickets"; // Adjust SQL based on your table name
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                // Assuming you have a 'Ticket' table with appropriate columns
                String ticketNum = rs.getString("ticketNum");
                String name = rs.getString("name");
                String description = rs.getString("description");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                Date creationDate = new Date(rs.getLong("creationDate")); // Assuming it's stored as a timestamp
                String type = rs.getString("type"); // Add type if needed for subclassing

                // Create the ticket based on its type
                Ticket ticket = null;
                if (type.equals("Hardware")) {
                    String hardware = rs.getString("hardware");
                    String model = rs.getString("model");
                    ticket = new HardwareTicket(ticketNum, name, description, email, phone, creationDate, hardware, model);
                } else if (type.equals("Software")) {
                    String software = rs.getString("software");
                    String version = rs.getString("version");
                    ticket = new SoftwareTicket(ticketNum, name, description, email, phone, creationDate, software, version);
                } else if (type.equals("Network")) {
                    String device = rs.getString("device");
                    String ipAddress = rs.getString("ipAddress");
                    ticket = new NetworkTicket(ticketNum, name, description, email, phone, creationDate, device, ipAddress);
                }

                if (ticket != null) {
                    tickets.add(ticket);
                }
            }
        }
        return tickets;
    }
    
    // Method to update ticket status
    public void updateTicketStatus(String ticketNum, String status) throws SQLException {
        String sql = "UPDATE tickets SET status = ? WHERE ticketNum = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setString(2, ticketNum);
            pstmt.executeUpdate();
        }
    }
    
    public int getNextTicketNumber(String type) throws SQLException {
        // Example logic to get the next ticket number from the database.
        String query = "SELECT COUNT(*) AS count FROM tickets WHERE type = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, type);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("count") + 1; // Assuming ticket numbers are sequential
                }
            }
        }
        return 1; // Start with 1 if there are no existing tickets of that type
    }
    
    private void createTableIfNotExists() {
        String CREATE_TABLE_QUERY = "CREATE TABLE Tickets (" +
                "ticketNum VARCHAR(20) PRIMARY KEY, " +
                "name VARCHAR(100), " +
                "email VARCHAR(100), " +
                "phone VARCHAR(15), " +
                "description VARCHAR(255), " +
                "creationDate BIGINT, " +
                "type VARCHAR(20), " +
                "details VARCHAR(255))";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(CREATE_TABLE_QUERY);
        } catch (SQLException e) {
            if (!e.getSQLState().equals("X0Y32")) { // Ignore if the table already exists
                e.printStackTrace();
            }
        }
    }

    public void saveTicket(Ticket ticket) throws SQLException {
        String sql = "INSERT INTO Tickets (ticketNum, name, email, phone, description, creationDate, type, details) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, ticket.getTicketNum());
            pstmt.setString(2, ticket.getName());
            pstmt.setString(3, ticket.getEmail());
            pstmt.setString(4, ticket.getPhone());
            pstmt.setString(5, ticket.getDescription());
            pstmt.setLong(6, ticket.getCreationDate().getTime());
            pstmt.setString(7, ticket.getType());
            pstmt.setString(8, ticket.getSpecificDetails());

            pstmt.executeUpdate();
        }
    }

    public ArrayList<Ticket> loadTickets() throws SQLException {
        ArrayList<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM Tickets";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String ticketNum = rs.getString("ticketNum");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String description = rs.getString("description");
                Date creationDate = new Date(rs.getLong("creationDate"));
                String type = rs.getString("type");
                String details = rs.getString("details");

                Ticket ticket;
                switch (type) {
                    case "Hardware":
                        String[] hwParts = details.split(", Model: ");
                        ticket = new HardwareTicket(ticketNum, name, description, email, phone, creationDate, hwParts[0], hwParts[1]);
                        break;
                    case "Software":
                        String[] swParts = details.split(", Version: ");
                        ticket = new SoftwareTicket(ticketNum, name, description, email, phone, creationDate, swParts[0], swParts[1]);
                        break;
                    case "Network":
                        String[] nwParts = details.split(", IP: ");
                        ticket = new NetworkTicket(ticketNum, name, description, email, phone, creationDate, nwParts[0], nwParts[1]);
                        break;
                    default:
                        continue; // Skip if type doesn't match
                }
                tickets.add(ticket);
            }
        }
        return tickets;
    }

    // Method to delete ticket based on ticket number
    public void deleteTicket(String ticketNum) throws SQLException {
        String sql = "DELETE FROM Tickets WHERE ticketNum = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, ticketNum);
            pstmt.executeUpdate();
        }
    }

    // Update method for changing ticket status or other details
    public void updateTicket(Ticket ticket) throws SQLException {
        String sql = "UPDATE Tickets SET description = ?, type = ?, details = ? WHERE ticketNum = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, ticket.getDescription());
            pstmt.setString(2, ticket.getType());
            pstmt.setString(3, ticket.getSpecificDetails());
            pstmt.setString(4, ticket.getTicketNum());
            pstmt.executeUpdate();
        }
    }
}


