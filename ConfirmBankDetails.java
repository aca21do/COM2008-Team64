import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class ConfirmBankDetails extends JFrame {
    private JPanel confirmBankDetailsPanel;
    private JLabel cardNumberLabel;
    private JButton yesButton;
    private JButton noButton;
    private JButton accountButton;
    private JButton staffViewButton;
    private JButton managerViewButton;
    private JLabel errorLabel;

    public ConfirmBankDetails (Connection connection) {
        // panel setup
        setContentPane(confirmBankDetailsPanel);
        setTitle("Confirm Bank Details");
        setSize(400, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        // make staff buttons invisible by default
        staffViewButton.setVisible(false);
        managerViewButton.setVisible(false);

        String cardNo = String.valueOf(CurrentUser.getCurrentUser().hasPayment.getPaymentMethod().getCardNumber());
        String stars = "**** **** **** ";
        int length = cardNo.length();
        if (length < 4) {
            for (int i = 0; i < 4-length; i++) {
                stars += "*";
            }
            stars += cardNo;
        }
        else {
            stars += cardNo.substring(cardNo.length() - 4);
        }
        cardNumberLabel.setText(stars);


        if (CurrentUser.getCurrentUser().getIsStaff()) {
            staffViewButton.setVisible(true);
            staffViewButton.setEnabled(true);
        }

        if (CurrentUser.getCurrentUser().getIsManager()) {
            managerViewButton.setVisible(true);
            managerViewButton.setEnabled(true);
        }
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String errorMessage = "error confirming order";

                // try to confirm order, if error then display error message
                try {
                    CurrentUser.getBasket().confirmOrder(connection);
                    CurrentUser.resetBasket(connection);

                    new OrdersCustomer(connection).setVisible(true);
                    setVisible(false);
                }
                catch (SQLException sqlException){
                    errorLabel.setText(errorMessage);
                    errorLabel.updateUI();
                }
                new CatalogueCustomer(connection).setVisible(true);
                setVisible(false);
            }
        });
        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EditBankDetails(connection).setVisible(true);
                setVisible(false);
            }
        });
        accountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AccountDetails(connection).setVisible(true);
                setVisible(false);
            }
        });
        staffViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CatalogueStaff(connection).setVisible(true);
                setVisible(false);
            }
        });
        managerViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewUsers(connection).setVisible(true);
                setVisible(false);
            }
        });
    }
}
