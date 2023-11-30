import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class AccountDetails extends JFrame {
    private JTextField forenameTextField;
    private JTextField surnameTextField;
    private JTextField houseNumberTextField;
    private JTextField roadNameTextField;
    private JTextField cityNameTextField;
    private JTextField postcodeTextField;
    private JTextField emailTextField;
    private JButton browseButton;
    private JButton updateInfoButton;
    private JButton editPasswordButton;
    private JButton editBankDetailsButton;
    private JPanel accountPanel;
    private JButton logoutButton;
    private JLabel updateMessageLabel;

    public AccountDetails (Connection connection) {
        // panel setup
        setContentPane(accountPanel);
        setTitle("Account Details");
        setSize(400, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CatalogueCustomer(connection).setVisible(true);
                setVisible(false);
            }
        });
        updateInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserDatabaseOperations userDatabaseOperations = new UserDatabaseOperations();
                String errorMessage = "error updating user";

                try {
                    // get text area values and set a unique ID
                    User updatedUser = CurrentUser.getCurrentUser();
                    Address updatedAddress = updatedUser.getAddress();

                    if (!emailTextField.getText().isBlank()) {updatedUser.setEmail(emailTextField.getText()); }
                    if (!forenameTextField.getText().isBlank()) {updatedUser.setForename(forenameTextField.getText()); }
                    if (!surnameTextField.getText().isBlank()) {updatedUser.setSurname(surnameTextField.getText()); }

                    if (!houseNumberTextField.getText().isBlank()){
                        try {
                            updatedAddress.setHouseNumber(Integer.valueOf(houseNumberTextField.getText()));
                        } catch (NumberFormatException numberFormatException){
                            updatedAddress.setHouseNumber(-1);
                        }
                    }
                    else{
                        updatedAddress.setHouseNumber(-1);
                    }

                    if (!roadNameTextField.getText().isBlank()){
                        updatedAddress.setRoadName(roadNameTextField.getText());
                    }
                    if (!cityNameTextField.getText().isBlank()){
                        updatedAddress.setCityName(cityNameTextField.getText());
                    }
                    if (!postcodeTextField.getText().isBlank()){
                        updatedAddress.setPostcode(postcodeTextField.getText());
                    }

                    // insert user into database (including address)
                    userDatabaseOperations.updateUser(updatedUser, connection);

                    errorMessage = "details updated";

                }
                catch (SQLException error) {
                    errorMessage = error.getMessage();
                }
                finally {
                    // display error or success message
                    updateMessageLabel.setText(errorMessage);
                    updateMessageLabel.updateUI();
                }
            }
        });
        editPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EditPassword(connection).setVisible(true);
                setVisible(false);
            }
        });
        editBankDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EditBankDetails(connection).setVisible(true);
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
