import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    private JButton confirmButton;

    public void populateTable(Connection connection) {
        String productCode = setCodeTextField.getText();
        try {
            Pack product = (Pack) InventoryManager.getInventory().getInventoryItem(productCode, connection)
                    .getProduct();
            productTable.setModel(product.getComponentProductCodes());
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

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
                String sql;
                try {
                    inventoryItem = InventoryManager.getInventory().getInventoryItem(setProductCode,
                            connection);
                    boolean present = false;
                    int quantityAdd = 0;
                    if (setProductCode.charAt(0) == 'M') {
                        Set product = (Set) inventoryItem.getProduct();

                        sql = "SELECT * FROM SetAndPackComponents WHERE ProductCode=?";
                        PreparedStatement preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setString(1, setProductCode);
                        ResultSet resultSet = preparedStatement.executeQuery();

                        while (resultSet.next()) {
                            if (resultSet.getString("ComponentProductCode") == productToAddTextField.getText()) {
                                present = true;
                                quantityAdd = resultSet.getInt("Quantity");
                            }
                        }
                        if (present) {
                            sql = "UPDATE SetAndPackComponents SET Quantity=? WHERE ComponentProductCode=?";
                            preparedStatement = connection.prepareStatement(sql);
                            preparedStatement.setInt(1, quantityAdd + Integer.valueOf(
                                                                addQuantityComboBox.getSelectedItem().toString()));
                            preparedStatement.setString(2, productToAddTextField.getText());
                        }
                        else {
                            sql = "INSERT INTO SetAndPackComponents (ProductCode, ComponentProductCode, Quantity) VALUES (?, ?, ?)";
                            preparedStatement = connection.prepareStatement(sql);
                            preparedStatement.setString(1, product.getProductCode());
                            preparedStatement.setString(2, productToAddTextField.getText());
                            preparedStatement.setInt(3, Integer.valueOf(
                                                                    addQuantityComboBox.getSelectedItem().toString()));
                            int rowsAffected = preparedStatement.executeUpdate();
                            System.out.println(rowsAffected + " row(s) inserted successfully.");
                        }
                    }
                    else if (setProductCode.charAt(0) == 'P') {
                        Pack product = (Pack) inventoryItem.getProduct();

                        sql = "SELECT * FROM SetAndPackComponents WHERE ProductCode=?";
                        PreparedStatement preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setString(1, setProductCode);
                        ResultSet resultSet = preparedStatement.executeQuery();

                        while (resultSet.next()) {
                            if (resultSet.getString("ComponentProductCode") == productToAddTextField.getText()) {
                                present = true;
                                quantityAdd = resultSet.getInt("Quantity");
                            }
                        }
                        if (present) {
                            sql = "UPDATE SetAndPackComponents SET Quantity=? WHERE ComponentProductCode=?";
                            preparedStatement = connection.prepareStatement(sql);
                            preparedStatement.setInt(1, quantityAdd + Integer.valueOf(
                                    addQuantityComboBox.getSelectedItem().toString()));
                            preparedStatement.setString(2, productToAddTextField.getText());
                        }
                        else {
                            sql = "INSERT INTO SetAndPackComponents (ProductCode, ComponentProductCode, Quantity) VALUES (?, ?, ?)";
                            preparedStatement = connection.prepareStatement(sql);
                            preparedStatement.setString(1, product.getProductCode());
                            preparedStatement.setString(2, productToAddTextField.getText());
                            preparedStatement.setInt(3, Integer.valueOf(
                                    addQuantityComboBox.getSelectedItem().toString()));
                            int rowsAffected = preparedStatement.executeUpdate();
                            System.out.println(rowsAffected + " row(s) inserted successfully.");
                        }
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                populateTable(connection);
            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                populateTable(connection);
            }
        });
    }
}
