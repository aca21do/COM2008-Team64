/*
This is part of the com.sheffield package used in lab 5
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

public class LoginView extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    public LoginView (Connection connection) throws SQLException {
        // Create the JFrame in the constructor
        this.setTitle("Login Application");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 200);

        // Create a JPanel to hold the components
        JPanel panel = new JPanel();
        this.add(panel);

        // Set a layout manager for the panel (e.g., GridLayout)
        panel.setLayout(new GridLayout(6, 2));

        // Create JButtons for the head using LoggedInHeadView
        LoggedInHeadView loggedInHeadView = new LoggedInHeadView();
        JButton accountButton = loggedInHeadView.AccountButton();
        JButton logoutButton = loggedInHeadView.LogoutButton();

        // Create JLabels for email and password
        JLabel emailLabel = new JLabel("Email:");
        JLabel passwordLabel = new JLabel("Password:");

        // Create JTextFields for entering email and password
        emailField = new JTextField(20);
        passwordField = new JPasswordField(20);

        // Create a JButton for the login action
        JButton loginButton = new JButton("Login");

        // Create a JButton to Register
        JButton registerButton = new JButton("Register");

        // Add head components to the panel
        panel.add(accountButton);
        panel.add(logoutButton);

        // add label as text area for messages/errors
        JLabel errorLabel = new JLabel("");

        // Add components to the panel
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(registerButton);  // Empty label for spacing
        panel.add(loginButton);
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(errorLabel);
        panel.add(new JLabel());

        // Create an ActionListener for the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                char[] passwordChars = passwordField.getPassword();
                DatabaseOperations databaseOperations = new DatabaseOperations();
                String loginMessage = databaseOperations.verifyLogin(connection, email, passwordChars);
                // Secure disposal of the password
                Arrays.fill(passwordChars, '\u0000');

                // display error or success message
                errorLabel.setText(loginMessage);
                errorLabel.updateUI();
            }
        });

        // Create an ActionListener for the register button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseOperations databaseOperations = new DatabaseOperations();
                String errorMessage = "error registering";

                try {
                    // create user with unique ID
                    String uniqueID = UUID.randomUUID().toString();
                    String email = emailField.getText();
                    User newUser = new User(uniqueID, email);
                    databaseOperations.insertUser(newUser, connection);

                    char[] passwordChars = passwordField.getPassword();
                    databaseOperations.setPassword(newUser, passwordField.getPassword(), connection);// hash + store password
                    Arrays.fill(passwordChars, '\u0000');// clear the password
                    errorMessage = "register success";
                }
                catch(SQLException error){
                    errorMessage = error.getMessage();
                }
                finally {
                    // display error or success message
                    errorLabel.setText(errorMessage);
                    errorLabel.updateUI();
                }
            }
        });
    }
}
