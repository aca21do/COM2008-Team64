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
        DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();

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
        loginDBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        });
    }
}
