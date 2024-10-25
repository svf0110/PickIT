/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pekit2;

/**
 *
 * @author Gio Turtal and Jose Laserna
 */
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JTextArea;
import java.sql.Connection;
import java.sql.SQLException;


public class PekIT2
{

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner scan = new Scanner(System.in);

        // Obtain a database connection
        Database database = new Database();
        Connection conn = null;
        try {
            conn = database.connect(); // Assuming you have a method to get a connection
            AccountHandle accountHandle = new AccountHandle(conn); // Pass the connection to the AccountHandle constructor
        
        System.out.println("===============================================");
        System.out.println(" Welcome to Pek IT Service Desk Customer Support!");
        System.out.println("===============================================");
        System.out.println("          Your Solution Starts Here!           ");
        System.out.println("===============================================");

        while (true)
        {
            System.out.println("                  Main Menu                   ");
            System.out.println("1: Login");
            System.out.println("2: Create a New Account");
            System.out.println("x: Shut Down Program\n");
            System.out.print("Choose an option: ");

            String option = scan.nextLine();

            switch (option)
            {
                case "1":
                    handleLogin(scan, accountHandle);
                    break;
                case "2":
                    accountHandle.createAccount();
                    break;
                case "x":
                    System.out.println("\nThank you for using Pek IT Service Desk. Goodbye!\n");
                    System.exit(0);  // Exit the program
                default:
                    System.out.println("\n          Invalid option! Please try again.           \n");
                    break;
            }
        }
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database: " + e.getMessage());
            return; // Exit the program or handle as needed
        } finally {
            if (conn != null) {
                try {
                    conn.close(); // Ensure the connection is closed
                } catch (SQLException e) {
                    System.err.println("Failed to close the database connection: " + e.getMessage());
                }
        }
    }
}

//    private static void handleLogin(Scanner scan, AccountHandle accountHandle) throws IOException, ClassNotFoundException
//    {
//        System.out.println("\n                 Login                     \n");
//        System.out.print("Enter username: ");
//        String username = scan.nextLine();
//        System.out.print("Enter password: ");
//        String password = scan.nextLine();
//
//        Account account = accountHandle.login(username, password);
//        if (account != null)
//        {
//            System.out.println("\n            Login successful!            \n");
//            if (account.getType().equals("IT"))
//            {
//                ITInterface itInterface = new ITInterface();
//                itInterface.displayMenu();
//            }
//            else
//            {
//                GuestInterface guestInterface = new GuestInterface();
//                guestInterface.displayMenu();
//            }
//        }
//        else
//        {
//            System.out.println("\n        Invalid login. Please try again.        \n");
//        }
//    }
    
    private static void handleLogin(Scanner scan, AccountHandle accountHandle) throws IOException, ClassNotFoundException {
    System.out.println("\n                 Login                     \n");
    System.out.print("Enter username: ");
    String username = scan.nextLine();
    System.out.print("Enter password: ");
    String password = scan.nextLine();

    Account account = accountHandle.login(username, password);
    if (account != null) {
        System.out.println("\n            Login successful!            \n");
        
        // Create a JTextArea to pass to ITInterface
        JTextArea ticketDisplayArea = new JTextArea();
        
        if (account.getType().equals("IT")) {
            ITInterface itInterface = new ITInterface(ticketDisplayArea);
            itInterface.displayMenu();
        } else {
            GuestInterface guestInterface = new GuestInterface();
            guestInterface.displayMenu();
        }
    } else {
        System.out.println("\n        Invalid login. Please try again.        \n");
    }
}

}
