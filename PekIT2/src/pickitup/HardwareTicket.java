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

public class HardwareTicket extends Ticket 
{
    private String hardware;
    private String model;

    public HardwareTicket(String ticketNum, String name, String description, String email, String phone, Date creationDate, String priority, String hardware, String model) {
        super(ticketNum, name, description, email, phone, creationDate, priority);
        this.hardware = hardware;
        this.model = model;
    }

    @Override
    public String toFileString() {
        return super.toFileString() + String.format(",%s,%s", getHardware(), getModel());
    }

    @Override
    public String toString() {
        return String.format("HardwareTicket [TicketNumber: %s, Name: %s, Status: %s, Priority: %s, Hardware: %s, Model: %s, Description: %s]",
                getTicketNum(), getName(), getStatus(), getPriority(), getHardware(), getModel(), getDescription());
    }

    public String getHardware() {
        return hardware;
    }

    public void setHardware(String hardware) {
        this.hardware = hardware;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}