import sheffield.DatabaseConnectionHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;

public class CatalogueCustomer extends JFrame {
    private JPanel catalogueCustomerPanel;
    private JComboBox categoryComboBox;
    private JTable catalogueTable;
    private JButton ordersButton;
    private JButton accountButton;
    private JButton staffViewButton;
    private JButton managerViewButton;

    public CatalogueCustomer (Connection connection) {
        // panel setup
        setContentPane(catalogueCustomerPanel);
        setTitle("Catalogue");
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
        managerViewButton.setVisible(true);
        managerViewButton.setEnabled(true);
        categoryComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

            }
        });
        ordersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OrdersCustomer(connection).setVisible(true);
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
                new ViewUsers(connection).setVisible(true);
                setVisible(false);
            }
        });
    }
}
