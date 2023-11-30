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
        // panel setup
        setContentPane(accountPanel);
        setTitle("Account Details");
        setSize(400, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CatalogueCustomer(connection).setVisible(true);
                setVisible(false);
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
                new EditPassword(connection).setVisible(true);
                setVisible(false);
            }
        });
        editBankDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EditBankDetails(connection).setVisible(true);
                setVisible(false);
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
