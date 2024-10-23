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

public class HardwareTicket extends Ticket
{

    /**
     * @return the hardware
     */
    public String getHardware() {
        return hardware;
    }

    /**
     * @param hardware the hardware to set
     */
    public void setHardware(String hardware) {
        this.hardware = hardware;
    }

    /**
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(String model) {
        this.model = model;
    }
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
        return super.toFileString() + String.format(",%s,%s", getHardware(), getModel());
    }

    @Override
    public String toString()
    {
        return String.format("HardwareTicket [TicketNumber: %s, Name: %s, Status: %s, Hardware: %s, Model: %s, Description: %s]",
                getTicketNum(), getName(), getStatus(), getHardware(), getModel(), getDescription());
    }

}