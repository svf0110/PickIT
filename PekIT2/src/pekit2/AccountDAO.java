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
public class AccountDAO {
    private Connection conn;

    public AccountDAO(Connection conn) {
        this.conn = conn;
    }
    
    

    public void createAccountTable() {
        String sql = "CREATE TABLE accounts ("
                + "username VARCHAR(255) NOT NULL PRIMARY KEY, "
                + "password VARCHAR(255) NOT NULL, "
                + "accountType VARCHAR(50) NOT NULL)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
            System.out.println("Account table created successfully.");
        } catch (SQLException ex) {
            System.err.println("Table creation failed: " + ex.getMessage());
        }
    }

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
}

