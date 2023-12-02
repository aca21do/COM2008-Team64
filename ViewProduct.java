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
                dccCodeTextField.setEditable(false);
                dccCodeTextField.setText("");
                eraCodeTextField.setText(rollingStock.getEraCode());
            } else {
                eraCodeTextField.setEditable(false);
                dccCodeTextField.setEditable(false);
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
                String updateMessage = "";

                try {
                    InventoryItem updatedInventoryItem = inventory.getInventoryItem(productCode, connection);

                    if (!brandNameTextField.getText().isBlank()) {updatedInventoryItem.getProduct().
                            setBrandName(brandNameTextField.getText());}
                    if (!productNameTextField.getText().isBlank()) {updatedInventoryItem.getProduct().
                            setProductName(productNameTextField.getText());}

                    if (!priceTextField.getText().isBlank()) {
                        try {  // no need to handle null exception as it's not blank

                            updatedInventoryItem.getProduct().setRetailPrice(Double.parseDouble(priceTextField.getText()));
                        } catch (NumberFormatException numberFormatException) {
                            updatedInventoryItem.getProduct().setRetailPrice(InventoryManager.getInventory().
                                    getInventoryItem(productCode, connection).getProduct().getRetailPrice());
                        }
                    }

                    if (!quantityTextField.getText().isBlank()) {
                        try {
                            updatedInventoryItem.setQuantity(Integer.parseInt(quantityTextField.getText()));
                        } catch (NumberFormatException numberFormatException) {
                            updatedInventoryItem.setQuantity(InventoryManager.getInventory().
                                            getInventoryItem(productCode, connection).getQuantity());
                        }
                    }

                    if (!gaugeCodeTextField.getText().isBlank()) {
                        try {
                            updatedInventoryItem.getProduct().setGaugeCode(Gauge.valueOf(gaugeCodeTextField.getText()));
                        } catch (IllegalArgumentException illegalArgumentException) {
                            updatedInventoryItem.getProduct().setGaugeCode(InventoryManager.getInventory().
                                    getInventoryItem(productCode, connection).getProduct().getGaugeCode());
                        }
                    }

                    if (updatedInventoryItem.getProduct() instanceof Locomotive locomotive) {
                        if (!eraCodeTextField.getText().isBlank()) {locomotive.setEraCode(eraCodeTextField.getText());}
                        if (!dccCodeTextField.getText().isBlank()) {locomotive.setDCCCode(dccCodeTextField.getText());}
                    } else if (updatedInventoryItem.getProduct() instanceof RollingStock rollingStock) {
                        if (!eraCodeTextField.getText().isBlank()) {rollingStock.setEraCode(eraCodeTextField.getText());}
                    }

                    System.out.println(eraCodeTextField.getText());
                    if (updatedInventoryItem.getProduct() instanceof Locomotive locomotive) {
                        System.out.println(locomotive.getEraCode());
                    }
                    inventory.updateItem(updatedInventoryItem, connection);
                    updateMessage = "Product Updated";

                } catch (SQLException exception){
                    updateMessage = exception.getMessage();
                } finally {
                    updateMessageLabel.setText(updateMessage);
                    updateMessageLabel.updateUI();
                }
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
