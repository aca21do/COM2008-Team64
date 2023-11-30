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
        setSize(400, 250);
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

            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
