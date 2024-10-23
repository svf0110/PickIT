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
import java.io.IOException;
import java.util.Scanner;

public class GuestInterface extends JFrame {
    private TicketHandle ticketHandle = new TicketHandle();

    public GuestInterface() {
        // Set up JFrame for Guest Interface
        setTitle("Guest Ticket Creation");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        
        // Add components for creating a ticket
        JLabel nameLabel = new JLabel("Enter your Name:");
        nameLabel.setBounds(20, 20, 150, 25);
        add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(180, 20, 180, 25);
        add(nameField);

        JLabel emailLabel = new JLabel("Enter your Email:");
        emailLabel.setBounds(20, 60, 150, 25);
        add(emailLabel);

        JTextField emailField = new JTextField();
        emailField.setBounds(180, 60, 180, 25);
        add(emailField);

        JLabel phoneLabel = new JLabel("Enter your Phone:");
        phoneLabel.setBounds(20, 100, 150, 25);
        add(phoneLabel);

        JTextField phoneField = new JTextField();
        phoneField.setBounds(180, 100, 180, 25);
        add(phoneField);

        JLabel descLabel = new JLabel("Enter Description:");
        descLabel.setBounds(20, 140, 150, 25);
        add(descLabel);

        JTextField descField = new JTextField();
        descField.setBounds(180, 140, 180, 25);
        add(descField);

        JLabel typeLabel = new JLabel("Select Ticket Type:");
        typeLabel.setBounds(20, 180, 150, 25);
        add(typeLabel);

        String[] types = {"Hardware", "Software", "Network"};
        JComboBox<String> typeComboBox = new JComboBox<>(types);
        typeComboBox.setBounds(180, 180, 180, 25);
        add(typeComboBox);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(150, 220, 100, 30);
        add(submitButton);

        submitButton.addActionListener(e -> {
            // Logic to create and save the ticket using the TicketHandle class
            String name = nameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String description = descField.getText();
            String type = (String) typeComboBox.getSelectedItem();

            try {
                switch (type) {
                    case "Hardware":
                        String hardware = JOptionPane.showInputDialog("Enter the type of Hardware:");
                        String model = JOptionPane.showInputDialog("Enter Model Number of Hardware:");
                        ticketHandle.createHardwareTicket(name, email, phone, description, hardware, model);
                        break;
                    case "Software":
                        String software = JOptionPane.showInputDialog("Enter name of Software:");
                        String version = JOptionPane.showInputDialog("Enter the current Version of Software:");
                        ticketHandle.createSoftwareTicket(name, email, phone, description, software, version);
                        break;
                    case "Network":
                        String device = JOptionPane.showInputDialog("Enter Network Issue:");
                        String ipAddress = JOptionPane.showInputDialog("Enter IP address:");
                        ticketHandle.createNetworkTicket(name, email, phone, description, device, ipAddress);
                        break;
                }
                JOptionPane.showMessageDialog(this, "Ticket Created Successfully!");
                this.dispose(); // Close the frame after submission
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error creating ticket: " + ex.getMessage());
            }
        });
    }
    
    public void displayMenu() throws IOException, ClassNotFoundException {
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("===============================================");
            System.out.println("        Welcome to the Guest Portal!           ");
            System.out.println("===============================================\n");  
            System.out.println("Select ticket type to create:");
            System.out.println("(1) Hardware");
            System.out.println("(2) Software");
            System.out.println("(3) Network");
            System.out.println("'x' to go back");

            String option = scan.nextLine();

            if (option.equals("x")) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GuestInterface guestInterface = new GuestInterface();
            guestInterface.setVisible(true);
        });
    }
}

