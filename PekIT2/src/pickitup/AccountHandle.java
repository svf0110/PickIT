/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pickitup;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gio Turtal and Jose Laserna
 */

public class AccountHandle 
{

    // Creates the account table in the database
    public void createAccountsTable() throws SQLException 
    {
        String sql = "CREATE TABLE accounts ("
                + "username VARCHAR(255) PRIMARY KEY, "
                + "password VARCHAR(255), "
                + "type VARCHAR(50))";

        try (Connection conn = DBConnection.connect(); // Establishes connection to the database
             Statement stmnt = conn.createStatement()) 
        {

            stmnt.executeUpdate(sql); // Execute the SQL statement
            System.out.println("ACCOUNTS table created successfully.");

        } catch (SQLException e) {
            // Check if the table already exists
            if (e.getSQLState().equals("X0Y32")) 
            {
                System.out.println("ACCOUNTS table already exists, skipping creation.");
            } 
            else 
            {
                throw e; // Rethrow the exception for other SQL erroresultSet
            }
        }
    }

    // Logs in a user by checking the provided username and password against the database.
    public Account login(String username, String password) throws SQLException 
    {
        String sql = "SELECT * FROM accounts WHERE username = ? AND password = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement pstmnt = conn.prepareStatement(sql)) {

            // Set parameters for the prepared statement
            pstmnt.setString(1, username);
            pstmnt.setString(2, password);
            ResultSet resultSet = pstmnt.executeQuery();

            // If a matching account is found then return it
            if (resultSet.next()) 
            {
                return new Account(resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("type"));
            }
        }
        return null; // If no matching account is found
    }

    // Creates a new account with the provided username, password and type
    public void createAccount(String username, String password, String type) throws SQLException 
    {

        Account newAccount = new Account(username, password, type);
        saveAccount(newAccount);
        System.out.println("\n          Account created successfully.           \n");
    }

    // Saves an account to the database.
    private void saveAccount(Account account) throws SQLException 
    {
        String sql = "INSERT INTO accounts (username, password, type) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.connect();
             PreparedStatement pstmnt = conn.prepareStatement(sql)) 
        {
            // Set parameters for the prepared statement
            pstmnt.setString(1, account.getUsername());
            pstmnt.setString(2, account.getPassword());
            pstmnt.setString(3, account.getType());
            pstmnt.executeUpdate(); // Execute the update
        }
    }

    // Loads all accounts from the database
    public List<Account> loadAccounts() throws SQLException 
    {
        List<Account> accounts = new ArrayList<>(); // List to hold all accounts
        String sql = "SELECT * FROM accounts"; // SQL to select all accounts

        try (Connection conn = DBConnection.connect();
             Statement stmnt = conn.createStatement();
             ResultSet resultSet = stmnt.executeQuery(sql)) 
        {

            // Iterate through the result set and create Account objects
            while (resultSet.next()) 
            {
                Account account = new Account(resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("type"));
                accounts.add(account);
            }
        }
        return accounts;
    }
}
