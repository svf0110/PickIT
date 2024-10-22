///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package pekit2;
//
///**
// *
// * @author jmrla
// */
//import javax.swing.*;
//
//public class MainMenuGUI extends JFrame {
//
//    public MainMenuGUI(Account account) {
//        // Setup JFrame
//        setTitle("Main Menu");
//        setSize(400, 300);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLayout(null);
//
//        // Customize GUI based on the account type (e.g., IT or Guest)
//        if ("IT".equals(account.getType())) {
//            JButton itButton = new JButton("IT Interface");
//            itButton.setBounds(100, 50, 200, 30);
//            add(itButton);
//
//            // Add action listener for IT Interface
//            itButton.addActionListener(e -> {
//                new ITInterfaceGUI().setVisible(true);
//                this.dispose();  // Close Main Menu
//            });
//        } else {
//            JButton guestButton = new JButton("Guest Interface");
//            guestButton.setBounds(100, 50, 200, 30);
//            add(guestButton);
//
//            // Add action listener for Guest Interface
//            guestButton.addActionListener(e -> {
//                new GuestInterfaceGUI().setVisible(true);
//                this.dispose();  // Close Main Menu
//            });
//        }
//    }
//
//    public static void main(String[] args) {
//        // Testing main menu without login
//        Account dummyAccount = new Account("guest", "password", "Guest");
//        new MainMenuGUI(dummyAccount).setVisible(true);
//    }
//}

