import sheffield.DatabaseConnectionHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
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


                    if (loginMessage.equals("success")){
                        User userToLogin = userDatabaseOperations.getUserFromEmail(email, connection);
                        CurrentUser.setCurrentUser(userToLogin);
                        PendingOrder basket =
                                userDatabaseOperations.getUsersPendingOrder(CurrentUser.getCurrentUser(), connection);
                        if (basket == null){
                            basket = PendingOrder.getNewPendingOrder(CurrentUser.getCurrentUser(), connection);
                        }
                        CurrentUser.setBasket(basket);
                    }
                }
                catch (SQLException exception){
                    errorLabel.setText(exception.getMessage());
                }
                finally {
                    // login if password correct, else error if not
                    if (CurrentUser.isLoggedIn()){
                        new CatalogueCustomer(connection).setVisible(true);
                        setVisible(false);
                    }
                    else {
                        errorLabel.setText(loginMessage);
                        errorLabel.updateUI();
                    }
                }
            }
        });
    }
}
