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

public class GuestInterface
{
    private TicketHandle ticketHandle = new TicketHandle();

    public void displayMenu() throws IOException, ClassNotFoundException
    {
        Scanner scan = new Scanner(System.in);
        while (true)
        {
            System.out.println("===============================================");
            System.out.println("        Welcome to the Guest Portal!           ");
            System.out.println("===============================================\n");  
            System.out.println("Select ticket type to create:");
            System.out.println("(1) Hardware");
            System.out.println("(2) Software");
            System.out.println("(3) Network");
            System.out.println("'x' to go back");

            String option = scan.nextLine();

            if (option.equals("x"))
            {
                break;
            }

            switch (option)
            {
                case "1":
                    ticketHandle.createTicket("Hardware");
                    break;
                case "2":
                    ticketHandle.createTicket("Software");
                    break;
                case "3":
                    ticketHandle.createTicket("Network");
                    break;
                default:
                    System.out.println("\n      Invalid option.         \n");
            }
        }
    }
}