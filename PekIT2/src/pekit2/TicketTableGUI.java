/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pekit2;

/**
 *
 * @author Gio Turtal and Jose Laserna
 */

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;
import java.util.Comparator;
import java.util.Vector;

public class TicketTableGUI extends JFrame {

    private JTable ticketTable;
    private DefaultTableModel tableModel;
    
    // Database connection setup
    private Connection conn;

    public TicketTableGUI() {
        // Set up the frame
        setTitle("Tickets Table");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);

        // Create a panel to hold the table
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Define the column headers for the table
        String[] columnNames = {"Ticket Number", "Name", "Email", "Phone", "Description", 
                                "Creation Date", "Status", "Priority", "Type", "Extra Field 1", "Extra Field 2"};

        // Create the table model with columns and rows
        tableModel = new DefaultTableModel(columnNames, 0);

        // Create the JTable with the table model
        ticketTable = new JTable(tableModel);

        // Enable sorting for the table
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(ticketTable.getModel());
        ticketTable.setRowSorter(sorter);

        // Custom comparator for the "Priority" column (index 7)
        Comparator<String> priorityComparator = new Comparator<String>() {
            @Override
            public int compare(String p1, String p2) {
                return Integer.compare(getPriorityLevel(p1), getPriorityLevel(p2));
            }

            // Method to convert priority levels to integers for sorting
            private int getPriorityLevel(String priority) {
                switch (priority) {
                    case "Critical":
                        return 4;
                    case "High":
                        return 3;
                    case "Medium":
                        return 2;
                    case "Low":
                        return 1;
                    default:
                        return 0; // Default if priority is missing or unknown
                }
            }
        };

        // Apply the custom comparator to the "Priority" column
        sorter.setComparator(7, priorityComparator);  // 7 is the column index for "Priority"

        // Add the table to a scroll pane (to make the table scrollable)
        JScrollPane scrollPane = new JScrollPane(ticketTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Add the panel to the frame
        add(panel);

        // Load data from the database and populate the table
        loadDataFromDatabase();

        // Display the frame
        setVisible(true);
    }

    // Method to load ticket data from the database and populate the table
    private void loadDataFromDatabase() {
        try {
            // Establish a database connection (adjust URL, username, password as needed)
            conn = DriverManager.getConnection("jdbc:derby:PickITUpDB;create=true");

            // SQL query to retrieve all tickets
            String sql = "SELECT * FROM tickets";

            // Create a statement
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // Iterate over the ResultSet and add rows to the table model
            while (rs.next()) {
                // Fetch data for each ticket
                Vector<String> rowData = new Vector<>();
                rowData.add(rs.getString("ticketNum"));
                rowData.add(rs.getString("name"));
                rowData.add(rs.getString("email"));
                rowData.add(rs.getString("phone"));
                rowData.add(rs.getString("description"));
                rowData.add(rs.getString("creationDate"));
                rowData.add(rs.getString("status"));
                rowData.add(rs.getString("priority")); // This will be sorted with the custom comparator
                rowData.add(rs.getString("type"));
                rowData.add(rs.getString("extraField1"));
                rowData.add(rs.getString("extraField2"));

                // Add the row data to the table model
                tableModel.addRow(rowData);
            }

            // Close the ResultSet and Statement
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Launch the GUI
        SwingUtilities.invokeLater(() -> new TicketTableGUI());
    }
}
