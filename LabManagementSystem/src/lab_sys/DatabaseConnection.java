package lab_sys;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {


    private static final String URL = "jdbc:mysql://localhost:3307/labmanagement"; // Use the correct port
    private static final String USER = "root"; // Change as needed
    private static final String PASSWORD = "root"; // Change as needed

    public static Connection getConnection() throws SQLException {
        // Load MySQL JDBC Driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // For newer versions of MySQL Connector/J
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found.", e);
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}


