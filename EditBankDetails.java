import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class EditBankDetails extends JFrame {
    private JPanel editBankDetailsPanel;
    private JTextField cardNumberTextField;
    private JTextField bankCardNameTextField;
    private JTextField cardHolderNameTextFeild;
    private JTextField expiryDateTextField;
    private JTextField securityCodeTextField;
    private JButton updateBankDetailsButton;
    private JButton backButton;
    private JButton logoutButton;
    private JLabel updateMessageLabel;

    public EditBankDetails (Connection connection) {
        // panel setup
        setContentPane(editBankDetailsPanel);
        setTitle("Edit Bank Details");
        setSize(400, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setVisible(true);
        updateBankDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserDatabaseOperations userDatabaseOperations = new UserDatabaseOperations();
                String errorMessage = "error updating details";
                int updatedEntries = -1;

                try {
                    // get text area values and set a unique ID
                    User updatedUser = CurrentUser.getCurrentUser();

                    // check all fields not empty
                    if (! ( cardNumberTextField.getText().isBlank() &&
                            bankCardNameTextField.getText().isBlank() &&
                            cardHolderNameTextFeild.getText().isBlank() &&
                            expiryDateTextField.getText().isBlank() &&
                            securityCodeTextField.getText().isBlank() )){

                        // create new paymentMethod object
                        User user = CurrentUser.getCurrentUser();
                        PaymentMethod newPayment = new PaymentMethod(
                                bankCardNameTextField.getText(),
                                cardHolderNameTextFeild.getText(),
                                Integer.valueOf(cardNumberTextField.getText()),
                                expiryDateTextField.getText().toCharArray(),
                                Integer.valueOf(securityCodeTextField.getText()));

                        // delete old payment method
                        userDatabaseOperations.deletePaymentMethod(CurrentUser.getCurrentUser(), connection);
                        // create + insert new payment method
                        UserHasPayment newHasPayment = new UserHasPayment(user, newPayment);
                        updatedEntries = userDatabaseOperations.insertPaymentMethod(newHasPayment, connection);
                    }
                    else{
                        errorMessage = "fill out all fields";
                    }

                }
                catch (SQLException error) {
                    error.getMessage();
                    errorMessage = error.getMessage();
                }
                catch (NumberFormatException numberFormatException){
                    errorMessage = "string in number field";
                }
                finally {
                    // display error or success message
                    System.out.println(updatedEntries);
                    if (updatedEntries > 0){
                        errorMessage = "successfully updated bank details";
                    }
                    updateMessageLabel.setText(errorMessage);
                    updateMessageLabel.updateUI();
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
