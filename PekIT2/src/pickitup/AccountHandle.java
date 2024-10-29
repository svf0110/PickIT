/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pickitup;

/**
 *
 * @author Gio Turtal and Jose Laserna
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AccountHandle 
{

    public void createAccountsTable() throws SQLException 
    {
        String sql = "CREATE TABLE accounts ("
                + "username VARCHAR(255) PRIMARY KEY, "
                + "password VARCHAR(255), "
                + "type VARCHAR(50))";

        try (Connection conn = DBConnection.connect();
             Statement stmnt = conn.createStatement()) 
        {
            stmnt.executeUpdate(sql);
            System.out.println("ACCOUNTS table created successfully.");

        } 
        catch (SQLException e) 
        {
            if (e.getSQLState().equals("X0Y32")) 
            {
                System.out.println("ACCOUNTS table already exists, skipping creation.");
            } 
            else 
            {
                throw e;
            }
        }
    }

    public Account login(String username, String password) throws SQLException 
    {
        String sql = "SELECT * FROM accounts WHERE username = ? AND password = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement pstmnt = conn.prepareStatement(sql)) 
        {
            pstmnt.setString(1, username);
            pstmnt.setString(2, password);
            ResultSet resultset = pstmnt.executeQuery();

            if (resultset.next()) 
            {
                return new Account(resultset.getString("username"), resultset.getString("password"), resultset.getString("type"));
            }
        }
        return null; // If no matching account is found
    }

    public void createAccount(String username, String password, String type) throws SQLException 
    {
        Account newAccount = new Account(username, password, type);
        saveAccount(newAccount);
    }

    private void saveAccount(Account account) throws SQLException 
    {
        String sql = "INSERT INTO accounts (username, password, type) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.connect();
             PreparedStatement pstmnt = conn.prepareStatement(sql)) 
        {

            pstmnt.setString(1, account.getUsername());
            pstmnt.setString(2, account.getPassword());
            pstmnt.setString(3, account.getType());
            pstmnt.executeUpdate();
        }
    }

    public List<Account> loadAccounts() throws SQLException 
    {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts";

        try (Connection conn = DBConnection.connect();
             Statement stmnt = conn.createStatement();
             ResultSet resultset = stmnt.executeQuery(sql)) 
        {

            while (resultset.next()) 
            {
                Account account = new Account(resultset.getString("username"), resultset.getString("password"), resultset.getString("type"));
                accounts.add(account);
            }
        }
        return accounts;
    }
}
