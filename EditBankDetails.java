import sheffield.DatabaseConnectionHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class EditBankDetails extends JFrame {
    private JPanel editBankDetailsPanel;
    private JTextField cardNumberTextField;
    private JTextField bankCardNameTextField;
    private JTextField cardHolderName;
    private JTextField expiryDateTextField;
    private JTextField securityCodeTextField;
    private JButton updateBankDetailsButton;
    private JButton backButton;
    private JButton logoutButton;

    public EditBankDetails (Connection connection) {
        DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();

        // panel setup
        setContentPane(editBankDetailsPanel);
        setTitle("Edit Bank Details");
        setSize(400, 350);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        updateBankDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    AccountDetails accountDetails;
                    try {
                        // Open a database connection
                        databaseConnectionHandler.openConnection();

                        // Create and initial
                        // ize the LoanTableDisplay view using the database connection
                        accountDetails = new AccountDetails(databaseConnectionHandler.getConnection());
                        accountDetails.setVisible(true);
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
