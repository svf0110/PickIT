/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pickitup;

import java.util.List;
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
public class AccountHandleTest {
    
    public AccountHandleTest() {
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
     * Test of createAccountsTable method, of class AccountHandle.
     */
    @Test
    public void testCreateAccountsTable() throws Exception {
        System.out.println("createAccountsTable");
        AccountHandle instance = new AccountHandle();
        instance.createAccountsTable();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of login method, of class AccountHandle.
     */
    @Test
    public void testLogin() throws Exception {
        System.out.println("login");
        String username = "";
        String password = "";
        AccountHandle instance = new AccountHandle();
        Account expResult = null;
        Account result = instance.login(username, password);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createAccount method, of class AccountHandle.
     */
    @Test
    public void testCreateAccount() throws Exception {
        System.out.println("createAccount");
        String username = "";
        String password = "";
        String type = "";
        AccountHandle instance = new AccountHandle();
        instance.createAccount(username, password, type);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadAccounts method, of class AccountHandle.
     */
    @Test
    public void testLoadAccounts() throws Exception {
        System.out.println("loadAccounts");
        AccountHandle instance = new AccountHandle();
        List<Account> expResult = null;
        List<Account> result = instance.loadAccounts();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
