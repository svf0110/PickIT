/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pickitup;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Comparator;
import java.util.Vector;

/**
 *
 * @author Gio Turtal and Jose Laserna
 */

public class ITInterfaceGUI extends JFrame {
    private TicketHandle ticketHandle;
    private TicketSorting ticketSorting;
    private JTable ticketTable;
    private DefaultTableModel tableModel;
    private Connection conn;
    private TableColumn[] columns;  // Store references to each column
    private int[] columnVisibility; // Fixed-size array for column visibility

    public ITInterfaceGUI() {
        ticketHandle = new TicketHandle();
        ticketSorting = new TicketSorting();

        // Set up JFrame
        setTitle("IT Staff Menu");
        setSize(1200, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Create a light green background panel for the form
        JPanel formPanel = new JPanel();
        formPanel.setBounds(200, 0, 1000, 650);
        formPanel.setLayout(null);
        formPanel.setBackground(new Color(174, 255, 173));
        add(formPanel);

        // Define columns for the ticket table
        String[] columnNames = {"Ticket Number", "Name", "Email", "Phone", "Description", 
                                "Creation Date", "Status", "Priority", "Type", "Extra Field 1", "Extra Field 2"};
        tableModel = new DefaultTableModel(columnNames, 0);
        ticketTable = new JTable(tableModel);

        // Initialize column visibility array and store column references
        columnVisibility = new int[columnNames.length];
        columns = new TableColumn[columnNames.length];
        for (int i = 0; i < columnNames.length; i++) {
            columns[i] = ticketTable.getColumnModel().getColumn(i);
            columnVisibility[i] = 1; // All columns visible initially
        }

        // Enable sorting for the table
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(ticketTable.getModel());
        ticketTable.setRowSorter(sorter);

        // Custom comparator for "Priority" column
        sorter.setComparator(7, new Comparator<String>() {
            @Override
            public int compare(String p1, String p2) {
                return Integer.compare(getPriorityLevel(p1), getPriorityLevel(p2));
            }

            private int getPriorityLevel(String priority) {
                switch (priority) {
                    case "Critical": return 4;
                    case "High": return 3;
                    case "Medium": return 2;
                    case "Low": return 1;
                    default: return 0;
                }
            }
        });

        // Scroll pane for the table
        JScrollPane scrollPane = new JScrollPane(ticketTable);
        scrollPane.setBounds(20, 150, 950, 400);
        formPanel.add(scrollPane);

        // Load data from the database and populate the table
        loadDataFromDatabase();

        // Column visibility checklist panel
        JPanel checklistPanel = new JPanel();
        checklistPanel.setBounds(0, 0, 200, 600);
        checklistPanel.setLayout(new GridLayout(columnNames.length, 1));
        add(checklistPanel);

        // Add checkboxes for each column
        for (int i = 0; i < columnNames.length; i++) {
            final int columnIndex = i;
            JCheckBox checkBox = new JCheckBox(columnNames[i], true);
            checkBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    toggleColumnVisibility(columnIndex, checkBox.isSelected());
                }
            });
            checklistPanel.add(checkBox);
        }

        // Buttons for IT staff actions (example only)
        JButton viewTicketsButton = new JButton("Filter Tickets");
        viewTicketsButton.setBounds(20, 20, 150, 40);
        formPanel.add(viewTicketsButton);

        viewTicketsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterTicketsByType();  // Reload the data in the table
            }
        });

        JButton editStatusButton = new JButton("Edit Status");
        editStatusButton.setBounds(180, 20, 150, 40);
        formPanel.add(editStatusButton);
        editStatusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openEditStatusDialog();
            }
        });

        JButton deleteTicketButton = new JButton("Delete Ticket");
        deleteTicketButton.setBounds(340, 20, 150, 40);
        formPanel.add(deleteTicketButton);
        deleteTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openDeleteTicketDialog();
            }
        });

        // Log Out button
        JButton logoutButton = new JButton("Log Out");
        logoutButton.setBounds(870, 10, 100, 30);
        formPanel.add(logoutButton);

        logoutButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Display confirmation dialog
            int confirm = JOptionPane.showConfirmDialog(
                    ITInterfaceGUI.this,
                    "Are you sure you want to log out?",
                    "Confirm Log Out",
                    JOptionPane.YES_NO_OPTION
            );

            // Check if user confirmed to log out
            if (confirm == JOptionPane.YES_OPTION) {
                // Go back to login screen
                dispose();
                new LoginGUI().setVisible(true);
            }
        }
    });        
    }
    
    // Method to toggle individual column visibility
    private void toggleColumnVisibility(int columnIndex, boolean visible) {
        columnVisibility[columnIndex] = visible ? 1 : 0;
        applyColumnVisibility();
    }

    // Method to apply column visibility based on the columnVisibility array
    private void applyColumnVisibility() {
        TableColumnModel columnModel = ticketTable.getColumnModel();

        // Remove all columns first
        for (int i = columnModel.getColumnCount() - 1; i >= 0; i--) {
            columnModel.removeColumn(columnModel.getColumn(i));
        }

        // Re-add columns based on visibility array
        for (int i = 0; i < columnVisibility.length; i++) {
            if (columnVisibility[i] == 1) {
                columnModel.addColumn(columns[i]);
            }
        }
    }

    // Method to load data from the database
    private void loadDataFromDatabase() {
        tableModel.setRowCount(0); // Clear existing data
        try {
            conn = DriverManager.getConnection("jdbc:derby:PickITUpDB;create=true");
            String sql = "SELECT * FROM tickets";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Vector<String> rowData = new Vector<>();
                rowData.add(rs.getString("ticketNum"));
                rowData.add(rs.getString("name"));
                rowData.add(rs.getString("email"));
                rowData.add(rs.getString("phone"));
                rowData.add(rs.getString("description"));
                rowData.add(rs.getString("creationDate"));
                rowData.add(rs.getString("status"));
                rowData.add(rs.getString("priority"));
                rowData.add(rs.getString("type"));
                rowData.add(rs.getString("extraField1"));
                rowData.add(rs.getString("extraField2"));
                tableModel.addRow(rowData);
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void filterTicketsByType() {
        // Define options for ticket types
        String[] ticketTypes = {"All", "HardwareTicket", "SoftwareTicket", "NetworkTicket"};

        // Show a dropdown dialog to select the ticket type
        String selectedType = (String) JOptionPane.showInputDialog(
                this,
                "Select ticket type to filter:",
                "Filter Tickets",
                JOptionPane.QUESTION_MESSAGE,
                null,
                ticketTypes,
                "All");  // Default selection is "All"

        if (selectedType != null) {  // Check if the user made a selection
            tableModel.setRowCount(0);  // Clear existing data in the table

            try {
                // Establish database connection
                conn = DriverManager.getConnection("jdbc:derby:PickITUpDB;create=true");

                // SQL query to select tickets by type
                String sql;
                if ("All".equals(selectedType)) {
                    sql = "SELECT * FROM tickets";  // No filtering if "All" is selected
                } else {
                    sql = "SELECT * FROM tickets WHERE type = ?";
                }

                // Prepare and execute the statement
                PreparedStatement pstmt = conn.prepareStatement(sql);
                if (!"All".equals(selectedType)) {
                    pstmt.setString(1, selectedType);  // Set the type parameter if filtering
                }
                ResultSet rs = pstmt.executeQuery();

                // Populate the table with filtered data
                while (rs.next()) {
                    Vector<String> rowData = new Vector<>();
                    rowData.add(rs.getString("ticketNum"));
                    rowData.add(rs.getString("name"));
                    rowData.add(rs.getString("email"));
                    rowData.add(rs.getString("phone"));
                    rowData.add(rs.getString("description"));
                    rowData.add(rs.getString("creationDate"));
                    rowData.add(rs.getString("status"));
                    rowData.add(rs.getString("priority"));
                    rowData.add(rs.getString("type"));
                    rowData.add(rs.getString("extraField1"));
                    rowData.add(rs.getString("extraField2"));
                    tableModel.addRow(rowData);  // Add row to the table model
                }

                // Close resources
                rs.close();
                pstmt.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
        private void openEditStatusDialog() {
        String ticketNum = JOptionPane.showInputDialog(this, "Enter Ticket Number (H, S, N):");

        // Validate ticket number
        if (ticketNum != null && !ticketNum.isEmpty()) {
            char firstChar = ticketNum.charAt(0);
            if (firstChar == 'H' || firstChar == 'S' || firstChar == 'N') {
                String[] statuses = {"Open", "Closed", "Resolved"};
                String newStatus = (String) JOptionPane.showInputDialog(this, "Select new status:",
                        "Edit Ticket Status", JOptionPane.QUESTION_MESSAGE, null, statuses, "Open");

                if (newStatus != null) {
                    try {
                        ticketHandle.updateTicketStatus(ticketNum, newStatus);
                        loadDataFromDatabase(); // Refresh table
                        JOptionPane.showMessageDialog(this, "Status updated successfully.");
                    } catch (SQLException e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Failed to update status.");
                    }
                }
            } else {
                // Show error message if the input does not start with the required letters
                JOptionPane.showMessageDialog(this, "Invalid Ticket Number. It must start with H, S, or N.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // Handle the case where the user cancels the dialog or enters nothing
            JOptionPane.showMessageDialog(this, "No ticket number entered.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
        
        private void openDeleteTicketDialog() {
        String ticketNum = JOptionPane.showInputDialog(this, "Enter Ticket Number to Delete (H, S, N): ");

        // Validate ticket number
        if (ticketNum != null && !ticketNum.isEmpty()) {
            char firstChar = ticketNum.charAt(0);
            if (firstChar == 'H' || firstChar == 'S' || firstChar == 'N') {
                int confirmation = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to delete ticket " + ticketNum + "?",
                        "Delete Ticket", JOptionPane.YES_NO_OPTION);

                if (confirmation == JOptionPane.YES_OPTION) {
                    try {
                        ticketHandle.deleteTicket(ticketNum);
                        loadDataFromDatabase(); // Refresh table
                        JOptionPane.showMessageDialog(this, "Ticket deleted successfully.");
                    } catch (SQLException e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Failed to delete ticket.");
                    }
                }
            } else {
                // Show error message if the input does not start with the required letters
                JOptionPane.showMessageDialog(this, "Invalid Ticket Number. It must start with H, S, or N.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // Handle the case where the user cancels the dialog or enters nothing
            JOptionPane.showMessageDialog(this, "No ticket number entered.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
