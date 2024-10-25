///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package pekit2;
//
///**
// *
// * @author Gio Turtal and Jose Laserna
// */
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
//public class AccountHandle
//{
//    private static final String ACCOUNTS_FILE = "accounts.txt";
//
//    public Account login(String username, String password) throws IOException
//    {
//        List<Account> accounts = loadAccounts();
//        for (Account account : accounts)
//        {
//            if (account.getUsername().equals(username) && account.getPassword().equals(password))
//            {
//                return account;
//            }
//        }
//        return null;
//    }
//
//    public void createAccount() throws IOException
//    {
//        Scanner scan = new Scanner(System.in);
//
//        System.out.println("\n          Press 'x' at any time to cancel account creation.           \n");
//
//        System.out.print("Enter username: ");
//        String username = scan.nextLine();
//        if (username.equalsIgnoreCase("x"))
//        {
//            System.out.println("\nAccount creation canceled.\n");
//            return;
//        }
//
//        System.out.print("Enter password: ");
//        String password = scan.nextLine();
//        if (password.equalsIgnoreCase("x"))
//        {
//            System.out.println("\nAccount creation canceled.\n");
//            return;
//        }
//
//        System.out.print("Enter account type (1) IT Staff (2) Guest: ");
//        String option = scan.nextLine();
//        if (option.equalsIgnoreCase("x"))
//        {
//            System.out.println("\nAccount creation canceled.\n");
//            return;
//        }
//
//        int accountTypeChoice;
//        try
//        {
//            accountTypeChoice = Integer.parseInt(option);
//        }
//        catch (NumberFormatException e)
//        {
//            System.out.println("\nInvalid input. Account creation canceled.\n");
//            return;
//        }
//
//        String type = accountTypeChoice == 1 ? "IT" : "Guest";
//
//        Account newAccount = new Account(username, password, type);
//        saveAccount(newAccount);
//        System.out.println("\n          Account created successfully.           \n");
//    }
//
//    public void saveAccount(Account account) throws IOException
//    {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNTS_FILE, true)))
//        {
//            writer.write(account.getUsername() + "," + account.getPassword() + "," + account.getType());
//            writer.newLine();
//        }
//    }
//
//    private List<Account> loadAccounts() throws IOException
//    {
//        List<Account> accounts = new ArrayList<>();
//        File file = new File(ACCOUNTS_FILE);
//        if (!file.exists())
//        {
//            return accounts;
//        }
//
//        try (BufferedReader reader = new BufferedReader(new FileReader(file)))
//        {
//            String line;
//            while ((line = reader.readLine()) != null)
//            {
//                String[] parts = line.split(",");
//                if (parts.length == 3)
//                {
//                    Account account = new Account(parts[0], parts[1], parts[2]);
//                    accounts.add(account);
//                }
//            }
//        }
//        return accounts;
//    }
//}

package pekit2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.SQLException;


public class AccountHandle {
    private AccountDAO accountDAO;

    public AccountHandle(Connection conn) {
        accountDAO = new AccountDAO(conn);
    }

//    public Account login(String username, String password) {
//        return accountDAO.getAccount(username, password); // Retrieve account from the database
//    }
    
//    public void saveAccount(Account account) throws SQLException {
//        accountDAO.addAccount(account); // Assuming this method exists in AccountDAO
//    }
    
    // This method accepts parameters instead of an Account object
    public void saveAccount(String username, String password, String accountType) throws IOException {
        boolean success = accountDAO.addAccount(username, password, accountType);
        if (!success) {
            throw new IOException("Failed to create account; it may already exist.");
        }
    }
    
    public Account login(String username, String password) {
        try {
            return accountDAO.getAccount(username, password); // Retrieve account from the database
        } catch (SQLException e) {
            System.out.println("Error retrieving account: " + e.getMessage());
            return null; // Handle the error appropriately
        }
    }


    public void createAccount() {
        Scanner scan = new Scanner(System.in);

        System.out.println("\n          Press 'x' at any time to cancel account creation.           \n");

        System.out.print("Enter username: ");
        String username = scan.nextLine();
        if (username.equalsIgnoreCase("x")) {
            System.out.println("\nAccount creation canceled.\n");
            return;
        }

        System.out.print("Enter password: ");
        String password = scan.nextLine();
        if (password.equalsIgnoreCase("x")) {
            System.out.println("\nAccount creation canceled.\n");
            return;
        }

        System.out.print("Enter account type (1) IT Staff (2) Guest: ");
        String option = scan.nextLine();
        if (option.equalsIgnoreCase("x")) {
            System.out.println("\nAccount creation canceled.\n");
            return;
        }

        String type = option.equals("1") ? "IT" : "Guest";

        Account newAccount = new Account(username, password, type);
        accountDAO.addAccount(username, password, type); // Save to database
        System.out.println("\n          Account created successfully.           \n");
    }

//    public List<Account> loadAccounts() {
//        return accountDAO.getAllAccounts(); // Load accounts from database
//    }
    
    public List<Account> loadAccounts() {
    try {
        return accountDAO.getAllAccounts(); // Load accounts from database
    } catch (SQLException e) {
        System.out.println("Error loading accounts: " + e.getMessage());
        return new ArrayList<>(); // Return an empty list or handle as needed
    }
}

}
