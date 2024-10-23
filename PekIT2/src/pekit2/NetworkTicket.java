/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pekit2;

/**
 *
 * @author Gio Turtal and Jose Laserna
 */
import java.util.Date;

public class NetworkTicket extends Ticket
{
    private String device;
    private String ipAddress;

    public NetworkTicket(String ticketNum, String name, String description, String email, String phone, Date creationDate, String device, String ipAddress)
    {
        super(ticketNum, name, description, email, phone, creationDate);
        this.device = device;
        this.ipAddress = ipAddress;
    }

    @Override
    public String toFileString()
    {
        return super.toFileString() + String.format(",%s,%s", device, ipAddress);
    }

    @Override
    public String toString()
    {
        return String.format("NetworkTicket [TicketNumber: %s, Name: %s, Status: %s, Device: %s, IP Address: %s, Description: %s]",
                getTicketNum(), getName(), getStatus(), device, ipAddress, getDescription());
    }
}