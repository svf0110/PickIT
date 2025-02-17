/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pickitup;

import java.util.Date;

/**
 *
 * @author Gio Turtal and Jose Laserna
 */

public class NetworkTicket extends Ticket 
{
    private String device;
    private String ipAddress;

    public NetworkTicket(String ticketNum, String name, String description, String email, String phone, Date creationDate, String priority, String device, String ipAddress) 
    {
        super(ticketNum, name, description, email, phone, creationDate, priority);
        this.device = device;
        this.ipAddress = ipAddress;
    }

    @Override
    public String toFileString() {
        return super.toFileString() + String.format(",%s,%s", getDevice(), getIpAddress());
    }

    @Override
    public String toString() {
        return String.format("NetworkTicket [TicketNumber: %s, Name: %s, Status: %s, Priority: %s, Device: %s, IP Address: %s, Description: %s]",
                getTicketNum(), getName(), getStatus(), getPriority(), getDevice(), getIpAddress(), getDescription());
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}