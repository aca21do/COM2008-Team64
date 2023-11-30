import sheffield.DatabaseConnectionHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class AccountDetails extends JFrame {
    private JTextField forenameTextField;
    private JTextField surnameTextField;
    private JTextField houseNumberTextField;
    private JTextField roadNameTextField;
    private JTextField cityNameTextField;
    private JTextField postcodeTextField;
    private JTextField emailTextField;
    private JButton browseButton;
    private JButton updateInfoButton;
    private JButton editPasswordButton;
    private JButton editBankDetailsButton;
    private JPanel accountPanel;
    private JButton logoutButton;

    public AccountDetails (Connection connection) {
        DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();

        // panel setup
        setContentPane(accountPanel);
        setTitle("Account Details");
        setSize(400, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    CatalogueCustomer catalogueCustomer;
                    try {
                        // Open a database connection
                        databaseConnectionHandler.openConnection();

                        // Create and initial
                        // ize the LoanTableDisplay view using the database connection
                        catalogueCustomer = new CatalogueCustomer(databaseConnectionHandler.getConnection());
                        catalogueCustomer.setVisible(true);
                        setVisible(false);

                    } catch (Throwable t) {
                        // Close connection if database crashes.
                        databaseConnectionHandler.closeConnection();
                        throw new RuntimeException(t);
                    }
                });
            }
        });
        updateInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        editPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    EditPassword editPassword;
                    try {
                        // Open a database connection
                        databaseConnectionHandler.openConnection();

                        // Create and initial
                        // ize the LoanTableDisplay view using the database connection
                        editPassword = new EditPassword(databaseConnectionHandler.getConnection());
                        editPassword.setVisible(true);
                        setVisible(false);

                    } catch (Throwable t) {
                        // Close connection if database crashes.
                        databaseConnectionHandler.closeConnection();
                        throw new RuntimeException(t);
                    }
                });
            }
        });
        editBankDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    EditBankDetails editBankDetails;
                    try {
                        // Open a database connection
                        databaseConnectionHandler.openConnection();

                        // Create and initial
                        // ize the LoanTableDisplay view using the database connection
                        editBankDetails = new EditBankDetails(databaseConnectionHandler.getConnection());
                        editBankDetails.setVisible(true);
                        setVisible(false);

                    } catch (Throwable t) {
                        // Close connection if database crashes.
                        databaseConnectionHandler.closeConnection();
                        throw new RuntimeException(t);
                    }
                });
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainFrame().setVisible(true);
                setVisible(false);
            }
        });
    }
}
