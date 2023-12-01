import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CatalogueCustomer extends JFrame {
    private JPanel catalogueCustomerPanel;
    private JComboBox categoryComboBox;
    private JTable catalogueTable;
    private JButton ordersButton;
    private JButton accountButton;
    private JButton staffViewButton;
    private JButton managerViewButton;
    private JButton addToOrderButton;
    private JComboBox quantityComboBox;
    private JTextField quantityTextField;

    public DefaultTableModel returnSetOrPackDataModel(Character selectedCategory,Connection connection) throws SQLException {
        try {
            String[] columnNames = {"SetProductCode", "ProductCode", "BrandName", "ProductName", "Price", "Quantity",
                    "GaugeCode", "EraCode", "DCCCode"};
            Object[] data;
            String era;
            String dccCode;
            boolean displaySet;
            DefaultTableModel dataModel = new DefaultTableModel(columnNames, 0);

            String sql = "SELECT * FROM SetsAndPacks";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String productCode = resultSet.getString("ProductCode");
                if (productCode.toUpperCase().charAt(0) == selectedCategory) {
                    InventoryItem set = InventoryManager.getInventory().getInventoryItem(productCode, connection);
                    data = new Object[] {productCode,
                                            "-",
                                            set.getProduct().getBrandName(),
                                            set.getProduct().getProductName(),
                                            set.getProduct().getRetailPrice(),
                                            set.getQuantity(),
                                            set.getProduct().getGaugeCode(),
                                            "",
                                            ""};
                    dataModel.addRow(data);

                    sql = "SELECT * FROM SetAndPackComponents WHERE ProductCode = ?";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, productCode);
                    ResultSet componentsProductCodes = preparedStatement.executeQuery();

                    while (componentsProductCodes.next()) {
                        productCode = componentsProductCodes.getString("ComponentProductCode");

                        InventoryItem component = InventoryManager.getInventory().getInventoryItem(productCode, connection);


                        sql = "SELECT * FROM Products WHERE ProductCode = ?";
                        preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setString(1, productCode);
                        ResultSet gaugeCodes = preparedStatement.executeQuery();
                        gaugeCodes.next();

                        sql = "SELECT * FROM Eras WHERE ProductCode = ?";
                        preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setString(1, productCode);
                        ResultSet eraCodes = preparedStatement.executeQuery();

                        sql = "SELECT * FROM DCCCodes WHERE ProductCode = ?";
                        preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setString(1, productCode);
                        ResultSet dccCodes = preparedStatement.executeQuery();

                        if (eraCodes.next()) {
                            era = eraCodes.getString("EraCode");
                        } else {
                            era = "";
                        }
                        if (dccCodes.next()) {
                            dccCode = dccCodes.getString("DCCCode");
                        } else {
                            dccCode = "";
                        }

                        data = new Object[]{"",
                                productCode,
                                component.getProduct().getBrandName(),
                                component.getProduct().getProductName(),
                                "-",
                                componentsProductCodes.getInt("Quantity"),
                                component.getProduct().getGaugeCode(),
                                era,
                                dccCode};

                        dataModel.addRow(data);
                    }

                }
            }
            return dataModel;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
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

        try {
            DefaultTableModel defaultTableModel = returnSetOrPackDataModel('M', connection);
            catalogueTable.setModel(defaultTableModel);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        categoryComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String[] columnNames = {"ProductCode", "BrandName", "ProductName", "Price", "Quantity",
                                            "GaugeCode", "EraCode", "DCCCode"};
                Object[] data;
                String era;
                String dccCode;

                DefaultTableModel dataModel = new DefaultTableModel(columnNames, 0){
                    @Override
                    public boolean isCellEditable(int i, int i1) {
                        return false;
                    }
                };


                Character selectedCategory = null;
                if (categoryComboBox.getSelectedItem() == "Train Sets") {
                    selectedCategory = 'M';
                }
                else if (categoryComboBox.getSelectedItem() == "Train Packs") {
                    selectedCategory = 'P';
                }
                else if (categoryComboBox.getSelectedItem() == "Locomotives") {
                    selectedCategory = 'L';
                }
                else if (categoryComboBox.getSelectedItem() == "Rolling Stocks") {
                    selectedCategory = 'S';
                }
                else if (categoryComboBox.getSelectedItem() == "Tracks") {
                    selectedCategory = 'R';
                }
                else if (categoryComboBox.getSelectedItem() == "Controllers") {
                    selectedCategory = 'C';
                }

                if (selectedCategory == 'P' || selectedCategory == 'M') {
                    try {
                        dataModel = returnSetOrPackDataModel(selectedCategory, connection);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else {
                    try {
                        String sql = "SELECT * FROM Inventory";
                        PreparedStatement preparedStatement = connection.prepareStatement(sql);
                        ResultSet resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()) {
                            String productCode = resultSet.getString("ProductCode");

                            if (productCode.toUpperCase().charAt(0) == selectedCategory) {
                                sql = "SELECT * FROM Products WHERE ProductCode = ?";
                                preparedStatement = connection.prepareStatement(sql);
                                preparedStatement.setString(1, productCode);
                                ResultSet gaugeCodes = preparedStatement.executeQuery();
                                gaugeCodes.next();

                                sql = "SELECT * FROM Eras WHERE ProductCode = ?";
                                preparedStatement = connection.prepareStatement(sql);
                                preparedStatement.setString(1, productCode);
                                ResultSet eraCodes = preparedStatement.executeQuery();

                                sql = "SELECT * FROM DCCCodes WHERE ProductCode = ?";
                                preparedStatement = connection.prepareStatement(sql);
                                preparedStatement.setString(1, productCode);
                                ResultSet dccCodes = preparedStatement.executeQuery();

                                if (eraCodes.next()) {
                                    era = eraCodes.getString("EraCode");
                                } else {
                                    era = "";
                                }
                                if (dccCodes.next()) {
                                    dccCode = dccCodes.getString("DCCCode");
                                } else {
                                    dccCode = "";
                                }

                                data = new Object[]{productCode,
                                        resultSet.getString("BrandName"),
                                        resultSet.getString("ProductName"),
                                        resultSet.getDouble("Price"),
                                        resultSet.getInt("Quantity"),
                                        gaugeCodes.getString("GaugeCode"),
                                        era,
                                        dccCode};

                                dataModel.addRow(data);
                            }
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                catalogueTable.setModel(dataModel);
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
                new CatalogueStaff(connection).setVisible(true);
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

        // add to order
        addToOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    Inventory inventory = new Inventory();
                    // get data in first column of selected row
                    int column = 0;
                    int row = catalogueTable.getSelectedRow();
                    String productCode = catalogueTable.getModel().getValueAt(row, column).toString();
                    System.out.println("product code is " + productCode);
                    InventoryItem inventoryItem = inventory.getInventoryItem(productCode, connection);
                    int quantityInStock = inventoryItem.getQuantity();

                    // get the quantity to add to order
                    int quantityToAdd = quantityComboBox.getSelectedIndex();
                    if (quantityToAdd <= quantityInStock) {
                        UserDatabaseOperations userDBOps = new UserDatabaseOperations();
                        ArrayList<Order> usersOrders = userDBOps.getUsersOrders(CurrentUser.getCurrentUser(), connection);
                        System.out.println(usersOrders.toString());
                    }
                }
                catch(Exception exception){
                    exception.printStackTrace();
                }
            }
        });
    }
}
