import sheffield.DatabaseConnectionHandler;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//mainframe.java
public class MainFrame extends JFrame {
    private JButton loginButton;
    private JButton registerButton;
    private JPanel mainPanel;

    public MainFrame () {
        DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();

        InventoryManager.setInventory(new Inventory());

        // panel setup
        setContentPane(mainPanel);
        setTitle("Welcome");
        setSize(400, 250);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    Login login;
                    try {
                        // Open a database connection
                        databaseConnectionHandler.openConnection();

                        // Create and initial
                        // ize the LoanTableDisplay view using the database connection
                        login = new Login(databaseConnectionHandler.getConnection());
                        login.setVisible(true);
                        setVisible(false);

                    } catch (Throwable t) {
                        // Close connection if database crashes.
                        databaseConnectionHandler.closeConnection();
                        throw new RuntimeException(t);
                    }
                });
            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    Register register;
                    try {
                        // Open a database connection
                        databaseConnectionHandler.openConnection();

                        // Create and initial
                        // ize the LoanTableDisplay view using the database connection
                        register = new Register(databaseConnectionHandler.getConnection());
                        register.setVisible(true);
                        setVisible(false);

                    } catch (Throwable t) {
                        // Close connection if database crashes.
                        databaseConnectionHandler.closeConnection();
                        throw new RuntimeException(t);
                    }
                });
            }
        });
    }
}
