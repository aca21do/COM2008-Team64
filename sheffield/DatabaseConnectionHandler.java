/*
This is part of the com.sheffield package used in lab 3
 */


package sheffield;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionHandler {
    //private static final String DB_URL = "jdbc:mysql://stusql.dcs.shef.ac.uk:3306/<DB_NAME>";
    private static final String DB_URL = "jdbc:mysql://stusql.dcs.shef.ac.uk:3306/team064";
    private static final String DB_USER = "team064";
    private static final String DB_PASSWORD = "thohNg6un";

    // Define the connection as a class member to share it across the application.
    private Connection connection = null;

    public void openConnection() throws SQLException {
        // Load the JDBC driver and open the connection
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("connection successful");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("connection failed");
        }
    }

    public void closeConnection() {
        // Close the connection in a separate method to ensure proper resource
        // management.
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void closeConnection (Connection c) {
        // Close the connection in a separate method to ensure proper resource
        // management.
        if (c != null) {
            try {
                c.close();
                System.out.println("connection terminated");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

}