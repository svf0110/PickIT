package pekit2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String DB_URL = "jdbc:derby:PickITUpDB;create=true";

    public static Connection connect() throws SQLException {
        // DriverManager automatically loads the driver if it's in the classpath
        return DriverManager.getConnection(DB_URL);
    }
}
