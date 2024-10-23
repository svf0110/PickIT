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
        return super.toFileString() + String.format(",%s,%s", getDevice(), getIpAddress());
    }

    @Override
    public String toString()
    {
        return String.format("NetworkTicket [TicketNumber: %s, Name: %s, Status: %s, Device: %s, IP Address: %s, Description: %s]",
                getTicketNum(), getName(), getStatus(), getDevice(), getIpAddress(), getDescription());
    }

    /**
     * @return the device
     */
    public String getDevice() {
        return device;
    }

    /**
     * @param device the device to set
     */
    public void setDevice(String device) {
        this.device = device;
    }

    /**
     * @return the ipAddress
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * @param ipAddress the ipAddress to set
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}