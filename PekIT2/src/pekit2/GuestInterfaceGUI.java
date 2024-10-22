/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pekit2;

/**
 *
 * @author jmrla
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuestInterfaceGUI extends JFrame {

    public GuestInterfaceGUI() {
        // JFrame setup for Guest Menu
        setTitle("Guest Menu");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Buttons for guest actions
        JButton viewTicketsButton = new JButton("View Tickets");
        viewTicketsButton.setBounds(100, 50, 200, 30);
        add(viewTicketsButton);

        // Add button listeners
        viewTicketsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logic for viewing tickets as a guest
                JOptionPane.showMessageDialog(null, "Feature: Viewing tickets as a guest.");
            }
        });

        // You can add more guest-specific features here
    }

    public static void main(String[] args) {
        GuestInterfaceGUI guestInterface = new GuestInterfaceGUI();
        guestInterface.setVisible(true);
    }
}

