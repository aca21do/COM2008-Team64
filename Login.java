import sheffield.DatabaseConnectionHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.Arrays;

public class Login extends JFrame {
    private JButton homeButton;
    private JTextField emailTextField;
    private JPasswordField passwordField;
    private JButton loginDBButton;
    private JButton registerButton;
    private JPanel loginPanel;
    private JLabel errorLabel;

    public Login (Connection connection) {
        // panel setup
        setContentPane(loginPanel);
        setTitle("Login");
        setSize(400, 250);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainFrame().setVisible(true);
                setVisible(false);
            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Register(connection).setVisible(true);
                setVisible(false);
            }
        });
        loginDBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: check login
                String loginMessage = "";

                try {
                    String email = emailTextField.getText();
                    char[] passwordChars = passwordField.getPassword();
                    UserDatabaseOperations userDatabaseOperations = new UserDatabaseOperations();
                    loginMessage = userDatabaseOperations.verifyLogin(connection, email, passwordChars);
                    // Secure disposal of the password
                    Arrays.fill(passwordChars, '\u0000');
                }
                finally {
                    // display error or success message
                    errorLabel.setText(loginMessage);
                    errorLabel.updateUI();
                }

                new CatalogueCustomer(connection).setVisible(true);
                setVisible(false);
            }
        });
    }
}
