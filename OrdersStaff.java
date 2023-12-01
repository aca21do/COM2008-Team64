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
    private JButton viewOrderButton;
    private JTable ordersTable;

    public OrdersStaff (Connection connection) {
        // panel setup
        setContentPane(ordersStaffPanel);
        setTitle("Orders");
        setSize(800, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        // make staff buttons invisible by default
        customerViewButton.setVisible(false);
        managerViewButton.setVisible(false);

        // TODO: if staff member
        customerViewButton.setVisible(true);
        customerViewButton.setEnabled(true);

        // TODO: if manager
        managerViewButton.setVisible(true);
        managerViewButton.setEnabled(true);
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
                viewOrderButton.setEnabled(true);
                tableLabel.setText("Current Orders");
            }
        });
        ordersArchiveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ordersArchiveButton.setEnabled(false);
                currentOrdersButton.setEnabled(true);
                viewOrderButton.setEnabled(false);
                tableLabel.setText("Orders Archive");
            }
        });
        viewOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
