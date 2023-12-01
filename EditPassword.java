import sheffield.DatabaseConnectionHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

public class EditPassword extends JFrame {
    private JPasswordField currentPasswordField;
    private JPasswordField newPasswordField;
    private JButton updatePasswordButton;
    private JButton backButton;
    private JPanel editPasswordPanel;
    private JButton logoutButton;
    private JLabel errorLabel;

    public EditPassword (Connection connection) {
        // panel setup
        setContentPane(editPasswordPanel);
        setTitle("Edit Password");
        setSize(400, 250);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        updatePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    UserDatabaseOperations userDBOps = new UserDatabaseOperations();
                    char[] oldPass = currentPasswordField.getPassword();
                    char[] newPass = newPasswordField.getPassword();

                    // if old password matches hash, hash and set new password
                    if (userDBOps.verifyPassword(connection, oldPass, CurrentUser.getCurrentUser())) {
                        userDBOps.setPassword(CurrentUser.getCurrentUser(), newPass, connection);
                        errorLabel.setText("success");
                    }
                    else{
                        errorLabel.setText("Current password is wrong");
                    }

                    // overwrite passwords
                    Arrays.fill(oldPass, '\u0000');
                    Arrays.fill(newPass, '\u0000');

                }
                catch (SQLException exception){
                    errorLabel.setText(exception.getMessage());
                }
                finally {
                    errorLabel.updateUI();
                }


            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AccountDetails(connection).setVisible(true);
                setVisible(false);
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CurrentUser.logout();
                new MainFrame().setVisible(true);
                setVisible(false);
            }
        });
    }
}
