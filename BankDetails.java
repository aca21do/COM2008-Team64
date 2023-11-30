import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class BankDetails extends JFrame {
    private JTextField cardNumberTextField;
    private JTextField bankCardNameTextField;
    private JTextField cardHolderName;
    private JTextField expiryDateTextField;
    private JTextField securityCodeTextField;
    private JButton updateBankDetailsButton;
    private JButton backButton;
    private JButton logoutButton;
    private JPanel bankDetailsPanel;

    public BankDetails (Connection connection) {
        // panel setup
        setContentPane(bankDetailsPanel);
        setTitle("Set Bank Details");
        setSize(400, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        updateBankDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OrdersCustomer(connection).setVisible(true);
                setVisible(false);
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainFrame().setVisible(true);
                setVisible(false);
            }
        });
    }
}
