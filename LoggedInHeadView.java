import javax.swing.*;
import java.awt.*;

public class LoggedInHeadView extends JFrame {
    public JButton AccountButton() {
        JButton accountButton = new JButton("Account");

        // Create an ActionListener for the login button
//        loginButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String email = emailField.getText();
//                char[] passwordChars = passwordField.getPassword();
//                System.out.println(email);
//                System.out.println(new String(passwordChars));
//                DatabaseOperations databaseOperations = new DatabaseOperations();
//                System.out.println(databaseOperations.verifyLogin(connection, email, passwordChars));
//                // Secure disposal of the password
//                Arrays.fill(passwordChars, '\u0000');
//            }
//        });

        return accountButton;
    }

    public JButton LogoutButton() {
        JButton logoutButton = new JButton("Logout");

        // Create an ActionListener for the login button
//        loginButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String email = emailField.getText();
//                char[] passwordChars = passwordField.getPassword();
//                System.out.println(email);
//                System.out.println(new String(passwordChars));
//                DatabaseOperations databaseOperations = new DatabaseOperations();
//                System.out.println(databaseOperations.verifyLogin(connection, email, passwordChars));
//                // Secure disposal of the password
//                Arrays.fill(passwordChars, '\u0000');
//            }
//        });

        return logoutButton;
    }
}
