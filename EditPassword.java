import sheffield.DatabaseConnectionHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class EditPassword extends JFrame {
    private JPasswordField currentPasswordField;
    private JPasswordField newPasswordField;
    private JButton updatePasswordButton;
    private JButton backButton;
    private JPanel editPasswordPanel;
    private JButton logoutButton;

    public EditPassword (Connection connection) {
        DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();

        // panel setup
        setContentPane(editPasswordPanel);
        setTitle("Edit Password");
        setSize(400, 250);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        updatePasswordButton.addActionListener(new ActionListener() {
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
