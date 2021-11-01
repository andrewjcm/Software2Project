package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import utils.auth.Password;

/**
 * Database connection object.
 * @author Andrew Cesar-Metzgus
 */
public class DBConnection {

    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//localhost:3306/";
    private static final String dbName = "client_schedule";
    private static final String tzConn = "?connectionTimeZone=SERVER";
    private static final String jdbcURL = protocol + vendorName + ipAddress + dbName + tzConn;
    private static final String MYSQLJBCDriver = "com.mysql.cj.jdbc.Driver";
    private static final String username = "sqlUser";
    private static Connection conn = null;

    /**
     * Starts the database connection.
     * @return db connection.
     */
    public static Connection startConnection() {
        try {
            Class.forName(MYSQLJBCDriver);
            conn = DriverManager.getConnection(jdbcURL, username, Password.getPassword());

            System.out.println("Connection successful.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Gets the opening connection.
     * @return db connection.
     */
    public static Connection getConnection(){
        return conn;
    }

    /**
     * Closes current open connection.
     */
    public static void closeConnection() {
        try {
            conn.close();
        } catch (Exception e) {
            // Ignore any exception.
        }
    }
}
