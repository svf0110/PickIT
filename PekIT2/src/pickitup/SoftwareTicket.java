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

public class SoftwareTicket extends Ticket 
{
    private String software;
    private String version;

    public SoftwareTicket(String ticketNum, String name, String description, String email, String phone, Date creationDate, String priority, String software, String version) 
    {
        super(ticketNum, name, description, email, phone, creationDate, priority);
        this.software = software;
        this.version = version;
    }

    @Override
    public String toFileString() {
        return super.toFileString() + String.format(",%s,%s", getSoftware(), getVersion());
    }

    @Override
    public String toString() {
        return String.format("SoftwareTicket [TicketNumber: %s, Name: %s, Status: %s, Priority: %s, Software: %s, Version: %s, Description: %s]",
                getTicketNum(), getName(), getStatus(), getPriority(), getSoftware(), getVersion(), getDescription());
    }

    public String getSoftware() {
        return software;
    }

    public void setSoftware(String software) {
        this.software = software;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}