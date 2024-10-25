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

public abstract class Ticket
{
    protected String ticketNum;
    protected String name;
    protected String description;
    protected String email;
    protected String phone;
    protected Date creationDate;
    protected String status;
    protected String details;
    
    public Ticket(String ticketNum, String name, String description, String email, String phone, Date creationDate)
    {
        this.ticketNum = ticketNum;
        this.name = name;
        this.description = description;
        this.email = email;
        this.phone = phone;
        this.creationDate = creationDate;
        this.status = "Open"; // Default status
    }
    
    public abstract String getType();
    
    
    public String getSpecificDetails() {
        return details;
    }

    // Add this method to provide details when needed
    public void setDetails(String details) {
        this.details = details;
    }
    
    public String getTicketNum()
    {
        return ticketNum;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPhone()
    {
        return phone;
    }

    public Date getCreationDate()
    {
        return creationDate;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }
      
    public String toFileString()
    {
        return String.format("%s,%s,%s,%s,%s,%d",
                getTicketNum(), getName(), getEmail(), getPhone(), getDescription(), getCreationDate().getTime());
    }
}
