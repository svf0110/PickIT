/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pekit2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author jmrla
 */

public class AccountDAO {
    private static final String CREATE_TABLE_QUERY = "CREATE TABLE accounts (" +
            "username VARCHAR(255) NOT NULL PRIMARY KEY, " +
            "password VARCHAR(255) NOT NULL, " +
            "accountType VARCHAR(50) NOT NULL)";

    public Connection conn;

    // Constructor that accepts an existing database connection
    public AccountDAO(Connection conn) {
        this.conn = conn;
        createAccountTable(); // Create the accounts table if it doesn't exist
    }

    // Method to create the accounts table if it does not exist
    public void createAccountTable() {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(CREATE_TABLE_QUERY);
            System.out.println("Account table created successfully.");
        } catch (SQLException e) {
            if (!e.getSQLState().equals("X0Y32")) { // Ignore if the table already exists (SQLState X0Y32)
                System.err.println("Table creation failed: " + e.getMessage());
            }
        }
    }

    // Method to add an account to the database
    public boolean addAccount(String username, String password, String accountType) {
        if (doesAccountExist(username)) return false;

        String sql = "INSERT INTO accounts (username, password, accountType) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, accountType);
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error adding account: " + ex.getMessage());
            return false;
        }
    }

    // Method to check if an account already exists
    public boolean doesAccountExist(String username) {
        String sql = "SELECT COUNT(*) FROM accounts WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException ex) {
            System.err.println("Error checking account existence: " + ex.getMessage());
            return false;
        }
    }

    // Method to delete an account
    public boolean deleteAccount(String username) {
        String sql = "DELETE FROM accounts WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error deleting account: " + ex.getMessage());
            return false;
        }
    }

    // Method to update an account's password or account type
    public boolean updateAccount(String username, String password, String accountType) {
        String sql = "UPDATE accounts SET password = ?, accountType = ? WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, password);
            stmt.setString(2, accountType);
            stmt.setString(3, username);
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error updating account: " + ex.getMessage());
            return false;
        }
    }
    
    // Method to print all accounts for debugging
    private void printAllAccounts() {
        String sql = "SELECT * FROM accounts";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
             
            System.out.println("Accounts in the database:");
            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password"); // Avoid printing sensitive info in production
                String accountType = rs.getString("accountType");
                System.out.printf("Username: %s, Password: %s, Account Type: %s%n", username, password, accountType);
            }
        } catch (SQLException ex) {
            System.err.println("Error retrieving accounts: " + ex.getMessage());
        }
    }
}