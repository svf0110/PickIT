/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pekit2;

import java.util.Date;
/**
 *
 * @author Gio Turtal and Jose Laserna
 */

public class HardwareTicket extends Ticket
{
    private String hardware;
    private String model;

    public HardwareTicket(String ticketNum, String name, String description, String email, String phone, Date creationDate, String hardware, String model)
    {
        super(ticketNum, name, description, email, phone, creationDate);
        this.hardware = hardware;
        this.model = model;
    }

    @Override
    public String toFileString()
    {
        return super.toFileString() + String.format(",%s,%s", hardware, model);
    }

    @Override
    public String toString()
    {
        return String.format("HardwareTicket [TicketNumber: %s, Name: %s, Status: %s, Hardware: %s, Model: %s, Description: %s, Email: %s, Phone: %s, Date: %s]",
                getTicketNum(), getName(), getStatus(), hardware, model, getDescription(), getEmail(), getPhone(), getCreationDate());
    }
}
