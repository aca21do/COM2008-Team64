import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class OrdersCustomer extends JFrame {
    private JButton browseButton;
    private JButton accountButton;
    private JButton staffViewButton;
    private JButton managerViewButton;
    private JTable ordersTable;
    private JPanel ordersCustomerPanel;
    private JLabel tableLabel;
    private JButton ordersHistoryButton;
    private JButton myOrdersButton;

    public OrdersCustomer (Connection connection) {
        // panel setup
        setContentPane(ordersCustomerPanel);
        setTitle("Orders");
        setSize(800, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        // make staff buttons invisible by default
        staffViewButton.setVisible(false);
        managerViewButton.setVisible(false);

        // TODO: if staff member
        staffViewButton.setVisible(true);
        staffViewButton.setEnabled(true);

        // TODO: if manager
//        managerViewButton.setVisible(true);
//        managerViewButton.setEnabled(true);
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CatalogueCustomer(connection).setVisible(true);
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

            }
        });
        managerViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        ordersHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: change table contents

                ordersHistoryButton.setEnabled(false);
                myOrdersButton.setEnabled(true);
                tableLabel.setText("Order History Items");
            }
        });
        myOrdersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: change table contents

                myOrdersButton.setEnabled(false);
                ordersHistoryButton.setEnabled(true);
                tableLabel.setText("Order Items");
            }
        });
    }
}
