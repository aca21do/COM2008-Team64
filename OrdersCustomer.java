import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrdersCustomer extends JFrame {
    private JButton browseButton;
    private JButton accountButton;
    private JButton staffViewButton;
    private JButton managerViewButton;
    private JTable ordersTable;
    private JPanel ordersCustomerPanel;
    private JLabel tableLabel;
    private JButton ordersHistoryButton;
    private JButton pendingOrderButton;
    private JButton placeOrdersButton;
    private JComboBox deleteLineComboBox;
    private JLabel removeOrderLineLabel;
    private JButton deleteLineButton;
    private JButton clearPendingButton;

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
        removeOrderLineLabel.setVisible(false);
        deleteLineButton.setVisible(false);
        deleteLineComboBox.setVisible(false);
        clearPendingButton.setVisible(false);

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
                pendingOrderButton.setEnabled(true);
                placeOrdersButton.setEnabled(false);
                removeOrderLineLabel.setVisible(false);
                deleteLineButton.setVisible(false);
                deleteLineComboBox.setVisible(false);
                clearPendingButton.setVisible(false);
                tableLabel.setText("Order History Items");

                String[] columnNames = {"OrderID", "Date", "TotalCost", "Status", "Order Line No.",
                        "Product Code", "Quantity", "Line Cost"};

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
                        while (orderLinesResults.next()) {
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
                            } else {
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
        pendingOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pendingOrderButton.setEnabled(false);
                ordersHistoryButton.setEnabled(true);
                placeOrdersButton.setEnabled(true);
                removeOrderLineLabel.setVisible(true);
                deleteLineButton.setVisible(true);
                deleteLineComboBox.setVisible(true);
                clearPendingButton.setVisible(true);
                tableLabel.setText("Order Items");

                UserDatabaseOperations userDBOps = new UserDatabaseOperations();
                CurrentUser.updateBasketFromDB(userDBOps, connection);

                String[] columnNames = {"OrderID", "Date", "TotalCost", "Status", "Order Line No.",
                        "Product Code", "Quantity", "Line Cost"};

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
                        while (orderLinesResults.next()) {
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
                            } else {
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
                populateDeleteLineComboBox();
                deleteLineComboBox.setVisible(true);
            }
        });

        deleteLineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String lineNoStr = String.valueOf(deleteLineComboBox.getSelectedItem());
                int lineNo = Integer.valueOf(lineNoStr);
                ArrayList<OrderLine> usersOrderLines = CurrentUser.getBasket().getOrderLines();

                OrderLine lineToRemove = null;
                System.out.println("finding order line");
                try {
                    for (OrderLine line : usersOrderLines) {
                        if (line.getLineNumber() == lineNo) {
                            System.out.println(lineNo);
                            lineToRemove = line;
                        }
                    }
                    if (lineToRemove != null) {
                        CurrentUser.getBasket().removeOrderLine(lineToRemove, connection);
                        System.out.println("removed");
                    } else {
                        System.out.println("error deleting - could not find order line " + lineNo);
                    }
                } catch (SQLException exception) {
                    exception.printStackTrace();
                    System.out.println("Couldn't remove order line");
                } finally {
                    System.out.println("refresh");
                    pendingOrderButton.setEnabled(true);
                    pendingOrderButton.doClick();
                }
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
                } catch (SQLException error) {
                    new EditBankDetails(connection).setVisible(true);
                    setVisible(false);
                }
            }
        });


        clearPendingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CurrentUser.resetBasket(connection);
                pendingOrderButton.setEnabled(true);
                pendingOrderButton.doClick();
            }
        });
    }

    private void populateDeleteLineComboBox() {
        TableModel dataModel = ordersTable.getModel();
        int noOfRows = dataModel.getRowCount();
        System.out.println(noOfRows);
        for (int i = 0; i< noOfRows; i++) {
            deleteLineComboBox.addItem(dataModel.getValueAt(i, 4));
        }
    }
}
