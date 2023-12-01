import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class OrdersStaff extends JFrame {
    private JPanel ordersStaffPanel;
    private JButton browseButton;
    private JButton accountButton;
    private JButton customerViewButton;
    private JButton managerViewButton;
    private JLabel tableLabel;
    private JButton currentOrdersButton;
    private JButton ordersArchiveButton;
    private JTable ordersTable;
    private JButton fulfillNextOrderButton;
    private JButton deleteNextOrderButton;

    public OrdersStaff (Connection connection) {
        // panel setup
        setContentPane(ordersStaffPanel);
        setTitle("Orders (Staff)");
        setSize(800, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        // make manager button invisible by default
        managerViewButton.setVisible(false);

        if (CurrentUser.getCurrentUser().getIsManager()) {
            managerViewButton.setVisible(true);
            managerViewButton.setEnabled(true);
        }
        browseButton.addActionListener(new ActionListener() {
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
        currentOrdersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentOrdersButton.setEnabled(false);
                ordersArchiveButton.setEnabled(true);
                fulfillNextOrderButton.setEnabled(true);
                deleteNextOrderButton.setEnabled(true);
                tableLabel.setText("Current Orders");
            }
        });
        ordersArchiveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ordersArchiveButton.setEnabled(false);
                currentOrdersButton.setEnabled(true);
                fulfillNextOrderButton.setEnabled(false);
                deleteNextOrderButton.setEnabled(false);
                tableLabel.setText("Orders Archive");
            }
        });
        fulfillNextOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        deleteNextOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
