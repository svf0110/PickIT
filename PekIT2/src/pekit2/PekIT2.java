/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pekit2;

/**
 *
 * @author Gio Turtal and Jose Laserna
 */
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.SwingUtilities;

public class PekIT2
{

    public static void main(String[] args) throws ClassNotFoundException, SQLException
    {
        TicketHandle ticketHandle = new TicketHandle();
        AccountHandle accountHandle = new AccountHandle();
        
        ticketHandle.checkTicketsTable();
        accountHandle.createAccountsTable();
        
        SwingUtilities.invokeLater(() -> new LoginGUI());
        
//        SwingUtilities.invokeLater(() -> {
//            new TicketTableGUI();  // This will call the GUI and display the ticket table
//        });
        
        try (Connection conn = DBConnection.connect()) {
            if (conn != null) 
            {
                System.out.println("Connected to the database successfully!");
            }
        } catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }
}
