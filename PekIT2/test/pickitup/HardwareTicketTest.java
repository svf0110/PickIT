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
public class HardwareTicketTest {
    
    public HardwareTicketTest() {
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
     * Test of toFileString method, of class HardwareTicket.
     */
    @Test
    public void testToFileString() {
        System.out.println("toFileString");
        HardwareTicket instance = null;
        String expResult = "";
        String result = instance.toFileString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class HardwareTicket.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        HardwareTicket instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHardware method, of class HardwareTicket.
     */
    @Test
    public void testGetHardware() {
        System.out.println("getHardware");
        HardwareTicket instance = null;
        String expResult = "";
        String result = instance.getHardware();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setHardware method, of class HardwareTicket.
     */
    @Test
    public void testSetHardware() {
        System.out.println("setHardware");
        String hardware = "";
        HardwareTicket instance = null;
        instance.setHardware(hardware);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getModel method, of class HardwareTicket.
     */
    @Test
    public void testGetModel() {
        System.out.println("getModel");
        HardwareTicket instance = null;
        String expResult = "";
        String result = instance.getModel();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setModel method, of class HardwareTicket.
     */
    @Test
    public void testSetModel() {
        System.out.println("setModel");
        String model = "";
        HardwareTicket instance = null;
        instance.setModel(model);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
