/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pickitup;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author User
 */
public class TicketHandleTest {
    
    public TicketHandleTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of checkTicketsTable method, of class TicketHandle.
     */
    @Test
    public void testCheckTicketsTable() throws Exception {
        System.out.println("checkTicketsTable");
        TicketHandle instance = new TicketHandle();
        instance.checkTicketsTable();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createTicket method, of class TicketHandle.
     */
    @Test
    public void testCreateTicket() throws Exception {
        System.out.println("createTicket");
        String type = "";
        String name = "";
        String description = "";
        String email = "";
        String phone = "";
        String priority = "";
        String field1 = "";
        String field2 = "";
        TicketHandle instance = new TicketHandle();
        instance.createTicket(type, name, description, email, phone, priority, field1, field2);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveTicket method, of class TicketHandle.
     */
    @Test
    public void testSaveTicket() throws Exception {
        System.out.println("saveTicket");
        Ticket ticket = null;
        TicketHandle instance = new TicketHandle();
        instance.saveTicket(ticket);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of displayTickets method, of class TicketHandle.
     */
    @Test
    public void testDisplayTickets() throws Exception {
        System.out.println("displayTickets");
        TicketHandle instance = new TicketHandle();
        instance.displayTickets();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateTicketStatus method, of class TicketHandle.
     */
    @Test
    public void testUpdateTicketStatus() throws Exception {
        System.out.println("updateTicketStatus");
        String ticketNum = "";
        String newStatus = "";
        TicketHandle instance = new TicketHandle();
        instance.updateTicketStatus(ticketNum, newStatus);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteTicket method, of class TicketHandle.
     */
    @Test
    public void testDeleteTicket() throws Exception {
        System.out.println("deleteTicket");
        String ticketNum = "";
        TicketHandle instance = new TicketHandle();
        instance.deleteTicket(ticketNum);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
