import com.mysql.cj.x.protobuf.MysqlxCrud;
import com.mysql.cj.x.protobuf.MysqlxPrepare;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    private JButton placeOrdersButton;

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

        if (CurrentUser.getCurrentUser().getIsStaff()) {
            staffViewButton.setVisible(true);
            staffViewButton.setEnabled(true);
        }

        if (CurrentUser.getCurrentUser().getIsManager()) {
            managerViewButton.setVisible(true);
            managerViewButton.setEnabled(true);
        }
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
        ordersHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ordersHistoryButton.setEnabled(false);
                myOrdersButton.setEnabled(true);
                placeOrdersButton.setEnabled(false);
                tableLabel.setText("Order History Items");

                String[] columnNames = {"OrderID", "Date", "TotalCost", "Status", "ProductNo",
                        "ProductCode", "Quantity", "LineCost"};

                DefaultTableModel dataModel = new DefaultTableModel(columnNames, 0);

                try {
                    String sql = "SELECT * FROM Orders WHERE UserID=? AND OrderStatus != \"pending\"";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, CurrentUser.getCurrentUser().getUserID());
                    ResultSet ordersResults = preparedStatement.executeQuery();
                    boolean displayOrder = true;
                    Object[] data;

                    while (ordersResults.next()) {
                        sql = "SELECT * FROM OrderLines WHERE OrderNumber=?";
                        preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setInt(1, ordersResults.getInt("OrderNumber"));
                        ResultSet orderLinesResults = preparedStatement.executeQuery();
                        while(orderLinesResults.next()) {
                            if (displayOrder) {
                                data = new Object[]{ordersResults.getInt("OrderNumber"),
                                        ordersResults.getDate("OrderDate"),
                                        ordersResults.getDouble("TotalCost"),
                                        ordersResults.getString("OrderStatus"),
                                        orderLinesResults.getString("OrderLineNumber"),
                                        orderLinesResults.getString("ProductCode"),
                                        orderLinesResults.getInt("Quantity"),
                                        orderLinesResults.getDouble("LineCost")};

                                displayOrder = false;
                            }
                            else {
                                data = new Object[]{"", "", "", "",
                                        orderLinesResults.getString("OrderLineNumber"),
                                        orderLinesResults.getString("ProductCode"),
                                        orderLinesResults.getInt("Quantity"),
                                        orderLinesResults.getDouble("LineCost")};

                            }
                            dataModel.addRow(data);
                        }
                        displayOrder = true;
                    }

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                ordersTable.setModel(dataModel);
            }
        });
        myOrdersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myOrdersButton.setEnabled(false);
                ordersHistoryButton.setEnabled(true);
                placeOrdersButton.setEnabled(true);
                tableLabel.setText("Order Items");

                String[] columnNames = {"OrderID", "Date", "TotalCost", "Status", "ProductNo",
                                            "ProductCode", "Quantity", "LineCost"};

                DefaultTableModel dataModel = new DefaultTableModel(columnNames, 0);

                try {
                    String sql = "SELECT * FROM Orders WHERE UserID=? AND OrderStatus = \"pending\"";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, CurrentUser.getCurrentUser().getUserID());
                    ResultSet ordersResults = preparedStatement.executeQuery();
                    boolean displayOrder = true;
                    Object[] data;

                    while (ordersResults.next()) {
                        sql = "SELECT * FROM OrderLines WHERE OrderNumber=?";
                        preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setInt(1, ordersResults.getInt("OrderNumber"));
                        ResultSet orderLinesResults = preparedStatement.executeQuery();
                        while(orderLinesResults.next()) {
                            if (displayOrder) {
                                data = new Object[]{ordersResults.getInt("OrderNumber"),
                                        ordersResults.getDate("OrderDate"),
                                        ordersResults.getDouble("TotalCost"),
                                        ordersResults.getString("OrderStatus"),
                                        orderLinesResults.getString("OrderLineNumber"),
                                        orderLinesResults.getString("ProductCode"),
                                        orderLinesResults.getInt("Quantity"),
                                        orderLinesResults.getDouble("LineCost")};

                                displayOrder = false;
                            }
                            else {
                                data = new Object[]{"", "", "", "",
                                        orderLinesResults.getString("OrderLineNumber"),
                                        orderLinesResults.getString("ProductCode"),
                                        orderLinesResults.getInt("Quantity"),
                                        orderLinesResults.getDouble("LineCost")};

                            }
                            dataModel.addRow(data);
                        }
                        displayOrder = true;
                    }

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                ordersTable.setModel(dataModel);
                
            }
        });
        placeOrdersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserDatabaseOperations userDBOps = new UserDatabaseOperations();
                try {
                    // if valid bank details exist, take user to confirm bank details
                    if (userDBOps.getPaymentMethod(CurrentUser.getCurrentUser(), connection)) {
                        new ConfirmBankDetails(connection).setVisible(true);
                        setVisible(false);
                    } else {
                        // if no valid bank details, take user to edit tem
                        new EditBankDetails(connection).setVisible(true);
                        setVisible(false);
                    }
                }
                catch (SQLException error){
                    new EditBankDetails(connection).setVisible(true);
                    setVisible(false);
                }
            }
        });
    }
}
