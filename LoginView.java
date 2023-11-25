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

public class LoginView extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    public LoginView (Connection connection) throws SQLException {
        // Create the JFrame in the constructor
        this.setTitle("Login Application");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 150);

        // Create a JPanel to hold the components
        JPanel panel = new JPanel();
        this.add(panel);

        // Set a layout manager for the panel (e.g., GridLayout)
        panel.setLayout(new GridLayout(3, 2));

        // Create JLabels for email and password
        JLabel emailLabel = new JLabel("Email:");
        JLabel passwordLabel = new JLabel("Password:");

        // Create JTextFields for entering email and password
        emailField = new JTextField(20);
        passwordField = new JPasswordField(20);

        // Create a JButton for the login action
        JButton loginButton = new JButton("Login");

        // Add components to the panel
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel());  // Empty label for spacing
        panel.add(loginButton);

        // Create an ActionListener for the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                char[] passwordChars = passwordField.getPassword();
                System.out.println(email);
                System.out.println(new String(passwordChars));
                DatabaseOperations databaseOperations = new DatabaseOperations();
                System.out.println(databaseOperations.verifyLogin(connection, email, passwordChars));
                // Secure disposal of the password
                Arrays.fill(passwordChars, '\u0000');
            }
        });
    }
}
