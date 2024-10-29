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

public abstract class Ticket {
    protected String ticketNum;
    protected String name;
    protected String description;
    protected String email;
    protected String phone;
    protected Date creationDate;
    protected String status;
    protected String priority; // New field for priority

    public Ticket(String ticketNum, String name, String description, String email, String phone, Date creationDate, String priority) {
        this.ticketNum = ticketNum;
        this.name = name;
        this.description = description;
        this.email = email;
        this.phone = phone;
        this.creationDate = creationDate;
        this.status = "Open"; // Default status
        this.priority = priority; // Set priority
    }

    public String getTicketNum() {
        return ticketNum;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() { // Getter for priority
        return priority;
    }

    public void setPriority(String priority) { // Setter for priority
        this.priority = priority;
    }

    public String toFileString() {
        return String.format("%s,%s,%s,%s,%s,%d,%s", getTicketNum(), getName(), getEmail(), getPhone(), getDescription(), getCreationDate().getTime(), getPriority());
    }
}
