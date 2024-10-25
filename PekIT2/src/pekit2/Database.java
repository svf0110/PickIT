/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pekit2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author jmrla
 */
public class Database {
    private static final String USER_NAME = "sleepii2";
    private static final String PASSWORD = "jobiwan2212";
    private static final String URL = "jdbc:derby://localhost:1527/PIUDatabase";
    private Connection conn;

    public void initialize() {
        establishConnection();
        //initializeDAOs();
    }
    
    public Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER_NAME, PASSWORD);
    }

    private void establishConnection() {
        try {
            conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            System.out.println("Connected successfully.");
        } catch (SQLException ex) {
            System.err.println("Failed to establish connection: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    public Connection getConnection() {
        return conn;
    }

    public void closeConnection() {
        try {
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            System.err.println("Error closing connection: " + ex.getMessage());
        }
    }

//    private void initializeDAOs() {
//        AccountDAO accountDAO = new AccountDAO(conn);
//        TicketDAO ticketDAO = new TicketDAO(conn);
//    }
}

