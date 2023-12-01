import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

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
                InventoryItem inventoryItem = null;
                try {
                    inventoryItem = InventoryManager.getInventory().getInventoryItem("L11111", connection);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                if (productCodeTextField.getText().charAt(0) == 'L') {
                    Locomotive product = new Locomotive(brandNameTextField.getText(),
                                                        productNameTextField.getText(),
                                                        productCodeTextField.getText(),
                                                        Double.parseDouble(priceTextField.getText()),
                                                        Gauge.valueOf(gaugeCodeTextField.getText()),
                                                        eraCodeTextField.getText(),
                                                        dccCodeTextField.getText());
                    inventoryItem = new InventoryItem(product, Integer.parseInt(quantityTextField.getText()));
                }
                else if (productCodeTextField.getText().charAt(0) == 'S') {
                    RollingStock product = new RollingStock(brandNameTextField.getText(),
                            productNameTextField.getText(),
                            productCodeTextField.getText(),
                            Double.parseDouble(priceTextField.getText()),
                            Gauge.valueOf(gaugeCodeTextField.getText()),
                            eraCodeTextField.getText());
                    inventoryItem = new InventoryItem(product, Integer.parseInt(quantityTextField.getText()));
                }
                else if (productCodeTextField.getText().charAt(0) == 'C') {
                    Controller product = new Controller(brandNameTextField.getText(),
                            productNameTextField.getText(),
                            productCodeTextField.getText(),
                            Double.parseDouble(priceTextField.getText()),
                            Gauge.valueOf(gaugeCodeTextField.getText()));
                    inventoryItem = new InventoryItem(product, Integer.parseInt(quantityTextField.getText()));
                }
                else if (productCodeTextField.getText().charAt(0) == 'R') {
                    TrackPiece product = new TrackPiece(brandNameTextField.getText(),
                            productNameTextField.getText(),
                            productCodeTextField.getText(),
                            Double.parseDouble(priceTextField.getText()),
                            Gauge.valueOf(gaugeCodeTextField.getText()));
                    inventoryItem = new InventoryItem(product, Integer.parseInt(quantityTextField.getText()));
                }
                else if (productCodeTextField.getText().charAt(0) == 'P') {
                    DefaultTableModel defaultTableModel = new DefaultTableModel();
                    Pack product = new Pack(brandNameTextField.getText(),
                            productNameTextField.getText(),
                            productCodeTextField.getText(),
                            Double.parseDouble(priceTextField.getText()),
                            Gauge.valueOf(gaugeCodeTextField.getText()),
                            defaultTableModel);
                    inventoryItem = new InventoryItem(product, Integer.parseInt(quantityTextField.getText()));
                }
                else if (productCodeTextField.getText().charAt(0) == 'M') {
                    DefaultTableModel defaultTableModel = new DefaultTableModel();
                    Set product = new Set(brandNameTextField.getText(),
                            productNameTextField.getText(),
                            productCodeTextField.getText(),
                            Double.parseDouble(priceTextField.getText()),
                            Gauge.valueOf(gaugeCodeTextField.getText()),
                            defaultTableModel);
                    inventoryItem = new InventoryItem(product, Integer.parseInt(quantityTextField.getText()));
                }
                try {
                    InventoryManager.getInventory().insertItem(inventoryItem, connection);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
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
