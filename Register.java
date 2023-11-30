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
                new Login(connection).setVisible(true);
                setVisible(false);
            }
        });
        registerDBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserDatabaseOperations userDatabaseOperations = new UserDatabaseOperations();
                String errorMessage = "error registering";

                try {
                    // get text area values and set a unique ID
                    String uniqueID = UUID.randomUUID().toString();
                    String email = emailTextField.getText();
                    String forename = forenameTextField.getText();
                    String surname = surnameTextField.getText();
                    int houseNo = Integer.valueOf(houseNumberTextField.getText());
                    String roadName = roadNameTextField.getText();
                    String cityName = cityNameTextField.getText();
                    String postcode = postcodeTextField.getText();

                    User newUser;

                    if (!(email.isBlank() && forename.isBlank() && surname.isBlank())){
                        if (!(postcode.isBlank() && roadName.isBlank() && cityName.isBlank())){
                            newUser = new User(uniqueID, email, forename, surname);

                            // create and add address to user
                            Address usersAddress = new Address(houseNo, roadName, cityName, postcode);
                            newUser.setAddress(usersAddress);

                            // insert user into database (including address)
                            userDatabaseOperations.insertUser(newUser, connection);

                            // hash and store password+salt
                            char[] passwordChars = passwordField.getPassword();
                            userDatabaseOperations.setPassword(newUser, passwordField.getPassword(), connection);
                            Arrays.fill(passwordChars, '\u0000');// clear the password

                            errorMessage = "register success";
                        }
                        else{
                            errorMessage = "Please complete all fields";
                        }
                    }
                    else{
                        errorMessage = "Please complete all fields";
                    }
                }
                catch (SQLException error) {
                    errorMessage = error.getMessage();
                }
                catch (NumberFormatException error){
                    errorMessage = "House Number must be a number";
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
