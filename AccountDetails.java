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
        setSize(400, 250);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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

            }
        });
        editBankDetailsButton.addActionListener(new ActionListener() {
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
