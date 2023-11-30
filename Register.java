import sheffield.DatabaseConnectionHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.UUID;

public class Register extends JFrame {
    private JTextField forenameTextField;
    private JTextField surnameTextField;
    private JTextField houseNumberTextField;
    private JTextField roadNameTextField;
    private JTextField cityNameTextField;
    private JTextField postcodeTextField;
    private JTextField emailTextField;
    private JPasswordField passwordField;
    private JButton registerDBButton;
    private JButton homeButton;
    private JButton loginButton;
    private JPanel registerPanel;
    private JLabel errorLabel;

    public Register (Connection connection) {
        DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();

        // panel setup
        setContentPane(registerPanel);
        setTitle("Register");
        setSize(400, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainFrame().setVisible(true);
                setVisible(false);
            }
        });
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
        registerDBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserDatabaseOperations userDatabaseOperations = new UserDatabaseOperations();
                String errorMessage = "error registering";

                try {
                    // create user with unique ID
                    String uniqueID = UUID.randomUUID().toString();
                    String email = emailTextField.getText();
                    String forename = forenameTextField.getText();
                    String surname = surnameTextField.getText();
                    User newUser = new User(uniqueID, email, forename, surname);

                    // create and add address to user
                    int houseNo = Integer.valueOf(houseNumberTextField.getText());
                    String roadName = roadNameTextField.getText();
                    String cityName = cityNameTextField.getText();
                    String postcode = postcodeTextField.getText();
                    Address usersAddress = new Address(houseNo, roadName, cityName, postcode);
                    newUser.setAddress(usersAddress);

                    // insert user into database (including address)
                    userDatabaseOperations.insertUser(newUser, connection);

                    // hash and store password+salt
                    char[] passwordChars = passwordField.getPassword();
                    userDatabaseOperations.setPassword(newUser, passwordField.getPassword(), connection);// hash + store password
                    Arrays.fill(passwordChars, '\u0000');// clear the password



                    errorMessage = "register success";
                }
                catch (SQLException error) {
                    error.printStackTrace();
                    errorMessage = error.getMessage();
                }
                finally {
                    // display error or success message
                    errorLabel.setText(errorMessage);
                    errorLabel.updateUI();
                }
            }

            public void addOptionalDetails(User newUser){
                if (!forenameTextField.getText().isEmpty()){
                    //user.set
                }

            }
        });
    }
}
