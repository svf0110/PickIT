/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pekit2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author jmrla
 */
public class TicketDAO {
    private Connection conn;

    public TicketDAO(Connection conn) {
        this.conn = conn;
    }

    public void createTicketTable() {
        String sql = "CREATE TABLE tickets ("
                + "ticketID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
                + "username VARCHAR(255), "
                + "description VARCHAR(1000), "
                + "status VARCHAR(50), "
                + "category VARCHAR(50), "
                + "FOREIGN KEY (username) REFERENCES accounts(username))";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
            System.out.println("Ticket table created successfully.");
        } catch (SQLException ex) {
            System.err.println("Table creation failed: " + ex.getMessage());
        }
    }

    public boolean addTicket(String username, String description, String status, String category) {
        String sql = "INSERT INTO tickets (username, description, status, category) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, description);
            stmt.setString(3, status);
            stmt.setString(4, category);
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error adding ticket: " + ex.getMessage());
            return false;
        }
    }
}

