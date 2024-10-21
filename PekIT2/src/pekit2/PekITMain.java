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
//import java.io.IOException;
//import java.util.Scanner;
//
//public class PekITMain
//{
//
//    public static void main(String[] args) throws IOException, ClassNotFoundException
//    {
//        Scanner scan = new Scanner(System.in);
//        AccountHandle accountHandle = new AccountHandle();
//
//        System.out.println("===============================================");
//        System.out.println(" Welcome to Pek IT Service Desk Customer Support!");
//        System.out.println("===============================================");
//        System.out.println("          Your Solution Starts Here!           ");
//        System.out.println("===============================================");
//
//        while (true)
//        {
//            System.out.println("                  Main Menu                   ");
//            System.out.println("1: Login");
//            System.out.println("2: Create a New Account");
//            System.out.println("x: Shut Down Program\n");
//            System.out.print("Choose an option: ");
//
//            String option = scan.nextLine();
//
//            switch (option)
//            {
//                case "1":
//                    handleLogin(scan, accountHandle);
//                    break;
//                case "2":
//                    accountHandle.createAccount();
//                    break;
//                case "x":
//                    System.out.println("\nThank you for using Pek IT Service Desk. Goodbye!\n");
//                    System.exit(0);  // Exit the program
//                default:
//                    System.out.println("\n          Invalid option! Please try again.           \n");
//                    break;
//            }
//        }
//    }
//
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
//}
