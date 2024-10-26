/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pekit2;

/**
 *
 * @author Gio Turtal and Jose Laserna
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class AccountHandle {

    public void createAccountsTable() throws SQLException {
        String sql = "CREATE TABLE accounts ("
                + "username VARCHAR(255) PRIMARY KEY, "
                + "password VARCHAR(255), "
                + "type VARCHAR(50))";

        try (Connection conn = DBConnection.connect();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);
            System.out.println("ACCOUNTS table created successfully.");

        } catch (SQLException e) {
            if (e.getSQLState().equals("X0Y32")) {
                System.out.println("ACCOUNTS table already exists, skipping creation.");
            } else {
                throw e;
            }
        }
    }

    public Account login(String username, String password) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE username = ? AND password = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Account(rs.getString("username"), rs.getString("password"), rs.getString("type"));
            }
        }
        return null; // If no matching account is found
    }

    public void createAccount(String username, String password, String type) throws SQLException {

        Account newAccount = new Account(username, password, type);
        saveAccount(newAccount);
        System.out.println("\n          Account created successfully.           \n");
    }

    private void saveAccount(Account account) throws SQLException {
        String sql = "INSERT INTO accounts (username, password, type) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, account.getUsername());
            pstmt.setString(2, account.getPassword());
            pstmt.setString(3, account.getType());
            pstmt.executeUpdate();
        }
    }

    public List<Account> loadAccounts() throws SQLException {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts";

        try (Connection conn = DBConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Account account = new Account(rs.getString("username"), rs.getString("password"), rs.getString("type"));
                accounts.add(account);
            }
        }
        return accounts;
    }
}
