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

public class SoftwareTicket extends Ticket
{
    private String software;
    private String version;

    public SoftwareTicket(String ticketNum, String name, String description, String email, String phone, Date creationDate, String software, String version)
    {
        super(ticketNum, name, description, email, phone, creationDate);
        this.software = software;
        this.version = version;
    }

    @Override
    public String toFileString()
    {
        return super.toFileString() + String.format(",%s,%s", software, version);
    }

    @Override
    public String toString()
    {
        return String.format("SoftwareTicket [TicketNumber: %s, Name: %s, Status: %s, Software: %s, Version: %s, Description: %s]",
                getTicketNum(), getName(), getStatus(), software, version, getDescription());
    }
}
