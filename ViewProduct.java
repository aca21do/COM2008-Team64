import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class ViewProduct extends JFrame {
    private JTextField productCodeTextField;
    private JTextField brandNameTextField;
    private JTextField productNameTextField;
    private JTextField priceTextField;
    private JTextField quantityTextField;
    private JTextField gaugeCodeTextField;
    private JTextField eraCodeTextField;
    private JButton updateInfoButton;
    private JLabel updateMessageLabel;
    private JPanel viewProductPanel;
    private JTextField dccCodeTextField;
    private JButton backButton;
    private JButton deleteProductButton;
    private JButton accountButton;
    private JButton customerViewButton;
    private JButton managerViewButton;

    public ViewProduct (Connection connection, String productCode) throws SQLException {
        // panel setup
        setContentPane(viewProductPanel);
        setTitle("View Product");
        setSize(400, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        // make manager button invisible by default
        managerViewButton.setVisible(false);

        if (CurrentUser.getCurrentUser().getIsManager()) {
            managerViewButton.setVisible(true);
            managerViewButton.setEnabled(true);
        }

        Inventory inventory = InventoryManager.getInventory();
        productCodeTextField.setText(productCode);
        try {
            Product product = inventory.returnProductSubClass(productCode, connection);

            brandNameTextField.setText(product.getBrandName());
            productNameTextField.setText(product.getProductName());
            priceTextField.setText(String.valueOf(product.getRetailPrice()));
            quantityTextField.setText(String.valueOf(inventory.getInventoryItem(productCode, connection).getQuantity()));
            gaugeCodeTextField.setText(product.getGaugeCode().toString());

            if (product instanceof Locomotive locomotive) {
                eraCodeTextField.setText(locomotive.getEraCode());
                dccCodeTextField.setText(locomotive.getDCCCode());
            } else if (product instanceof RollingStock rollingStock) {
                eraCodeTextField.setText(rollingStock.getEraCode());
            } else {
                eraCodeTextField.setText("");
                dccCodeTextField.setText("");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        // TODO: setText in the rest of the Fields using productCode
        updateInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        deleteProductButton.addActionListener(new ActionListener() {
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
