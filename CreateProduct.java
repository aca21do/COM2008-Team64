import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class CreateProduct extends JFrame {
    private JTextField productCodeTextField;
    private JTextField brandNameTextField;
    private JTextField productNameTextField;
    private JTextField priceTextField;
    private JTextField quantityTextField;
    private JTextField gaugeCodeTextField;
    private JTextField eraCodeTextField;
    private JButton createButton;
    private JTextField dccCodeTextField;
    private JButton backButton;
    private JLabel updateMessageLabel;
    private JPanel createProductPanel;
    private JButton accountButton;
    private JButton customerViewButton;
    private JButton managerViewButton;

    public CreateProduct (Connection connection) {
        // panel setup
        setContentPane(createProductPanel);
        setTitle("Create Product");
        setSize(400, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        // make manager button invisible by default
        managerViewButton.setVisible(false);

        if (CurrentUser.getCurrentUser().getIsManager()) {
            managerViewButton.setVisible(true);
            managerViewButton.setEnabled(true);
        }
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CatalogueStaff(connection).setVisible(true);
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
        customerViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CatalogueCustomer(connection).setVisible(true);
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
