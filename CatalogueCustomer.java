import sheffield.DatabaseConnectionHandler;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class CatalogueCustomer extends JFrame {
    private JPanel catalogueCustomerPanel;
    private JComboBox categoryComboBox;
    private JTable catalogueTable;
    private JButton ordersButton;
    private JButton accountButton;
    private JButton staffViewButton;
    private JButton managerViewButton;

    public CatalogueCustomer () {
        DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();

        // panel setup
        setContentPane(catalogueCustomerPanel);
        setTitle("Catalogue");
        setSize(800, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        staffViewButton.setVisible(false);
        managerViewButton.setVisible(false);

        // TODO: if staff member
//        staffViewButton.setVisible(true);
//        staffViewButton.setEnabled(true);
//
//        managerViewButton.setVisible(true);
//        managerViewButton.setEnabled(true);
        categoryComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

            }
        });
    }

    public static void main(String[] args){
        CatalogueCustomer catalogueCustomer = new CatalogueCustomer();
    }
}
