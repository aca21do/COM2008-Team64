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

public class CatalogueStaff extends JFrame {
    private JComboBox categoryComboBox;
    private JButton ordersButton;
    private JButton accountButton;
    private JButton customerViewButton;
    private JButton managerViewButton;
    private JTable catalogueTable;
    private JButton viewProductButton;
    private JPanel catalogueStaffPanel;
    private JButton createNewProductButton;

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

    public CatalogueStaff (Connection connection) {
        // panel setup
        setContentPane(catalogueStaffPanel);
        setTitle("Catalogue (Staff)");
        setSize(800, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        // make manager button invisible by default
        managerViewButton.setVisible(false);

        if (CurrentUser.getCurrentUser().getIsManager()) {
            managerViewButton.setVisible(true);
            managerViewButton.setEnabled(true);
        }

        // placeholder data
        String[] columnNames = {"First Name",
                "Last Name"};

        Object[][] data = {
                {"Kathy", "Smith"},
                {"John", "Doe"},
                {"Sue", "Black"},
                {"Jane", "White"},
                {"Joe", "Brown"}
        };

        DefaultTableModel dataModel = new DefaultTableModel(data, columnNames){
            // make table not able to be selected
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
        };
        catalogueTable.setModel(dataModel);

        // select first row automatically
        catalogueTable.setRowSelectionInterval(0, 0);

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
                new OrdersStaff(connection).setVisible(true);
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
        viewProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get data in first column of selected row
                int column = 0;
                int row = catalogueTable.getSelectedRow();
                String productCode = catalogueTable.getModel().getValueAt(row, column).toString();

                new ViewProduct(connection, productCode).setVisible(true);
                setVisible(false);
            }
        });
        createNewProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateProduct(connection).setVisible(true);
                setVisible(false);
            }
        });
    }
}
