/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pickitup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Gio Turtal and Jose Laserna
 */

public class DBConnection {
    private static final String DB_URL = "jdbc:derby:PickITUpDB;create=true";

    public static Connection connect() throws SQLException {
        // DriverManager automatically loads the driver if it's in the classpath
        return DriverManager.getConnection(DB_URL);
    }
}
