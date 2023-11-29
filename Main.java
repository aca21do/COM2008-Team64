import sheffield.DatabaseConnectionHandler;
import sheffield.*;
import javax.swing.*;

public class Main {
    public static void main(String[] args){
        System.out.println("---------Hello---------");

        DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
        DatabaseOperations databaseOperations = new DatabaseOperations();

        try {
            System.out.println("connecting");
            databaseConnectionHandler.openConnection();
        }
        catch (Throwable e) {
            e.printStackTrace();
            System.out.println("connection failed");
        } finally {
            databaseConnectionHandler.closeConnection();
            System.out.println("connected terminated");
        }

        // from lab 5
        // Execute the Swing GUI application on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            LoginView loginView = null;
            try {
                // Open a database connection
                databaseConnectionHandler.openConnection();

                // Create and initial
                // ize the LoanTableDisplay view using the database connection
                loginView = new LoginView(databaseConnectionHandler.getConnection());
                loginView.setVisible(true);

            } catch (Throwable t) {
                // Close connection if database crashes.
                databaseConnectionHandler.closeConnection();
                throw new RuntimeException(t);
            }
        });
    }

}
