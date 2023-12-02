import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class CreateProductSet extends JFrame {
    private JButton backButton;
    private JButton accountButton;
    private JButton customerViewButton;
    private JButton managerViewButton;
    private JTable productTable;
    private JPanel createProductSetPanel;
    private JTextField setCodeTextField;
    private JComboBox addQuantityComboBox;
    private JButton addButton;
    private JButton removeButton;
    private JTextField productToRemoveTextField;
    private JTextField productToAddTextField;
    private JComboBox removeQuantityComboBox;
    private JButton createSetButton;
    private JLabel errorLabel;
    private JButton confirmButton;

    public CreateProductSet (Connection connection) {
        // panel setup
        setContentPane(createProductSetPanel);
        setTitle("Create Product Set");
        setSize(800, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        // make manager button invisible by default
        managerViewButton.setVisible(false);

        if (CurrentUser.getCurrentUser().getIsManager()) {
            managerViewButton.setVisible(true);
            managerViewButton.setEnabled(true);
        }

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
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String setProductCode = setCodeTextField.getText();
                InventoryItem inventoryItem;
                try {
                    inventoryItem = InventoryManager.getInventory().getInventoryItem(setProductCode,
                            connection);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                if (setProductCode.charAt(0) == 'M') {
                    Set product = (Set) inventoryItem.getProduct();
                    Object[] component = new Object[] {productToAddTextField.getText(), addQuantityComboBox.getSelectedItem()};
                    product.getComponentProductCodes().addRow(component);
                }
                else if (setProductCode.charAt(0) == 'P') {
                    Pack product = (Pack) inventoryItem.getProduct();
                    Object[] component = new Object[] {productToAddTextField.getText(), addQuantityComboBox.getSelectedItem()};
                    product.getComponentProductCodes().addRow(component);
                }
            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        createSetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
