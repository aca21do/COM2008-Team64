import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class ConfirmBankDetails extends JFrame {
    private JPanel confirmBankDetailsPanel;
    private JLabel cardNumberLabel;
    private JButton yesButton;
    private JButton noButton;

    public ConfirmBankDetails (Connection connection) {
        // panel setup
        setContentPane(confirmBankDetailsPanel);
        setTitle("Confirm Bank Details");
        setSize(400, 350);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CatalogueCustomer(connection).setVisible(true);
                setVisible(false);
            }
        });
        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BankDetails(connection).setVisible(true);
                setVisible(false);
            }
        });
    }
}
