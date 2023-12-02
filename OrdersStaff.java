import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrdersStaff extends JFrame {
    private JPanel ordersStaffPanel;
    private JButton browseButton;
    private JButton accountButton;
    private JButton customerViewButton;
    private JButton managerViewButton;
    private JLabel tableLabel;
    private JButton currentOrdersButton;
    private JButton ordersArchiveButton;
    private JTable ordersTable;
    private JButton fulfillNextOrderButton;
    private JButton deleteNextOrderButton;

    public OrdersStaff (Connection connection) {
        // panel setup
        setContentPane(ordersStaffPanel);
        setTitle("Orders (Staff)");
        setSize(1800, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        currentOrdersButton.setEnabled(false);

        // make manager button invisible by default
        managerViewButton.setVisible(false);


        if (CurrentUser.getCurrentUser().getIsManager()) {
            managerViewButton.setVisible(true);
            managerViewButton.setEnabled(true);
        }


        UserDatabaseOperations userDBOps = new UserDatabaseOperations();
        CurrentUser.updateBasketFromDB(userDBOps, connection);

        //---------------------------------- generate table (start) -----------------------------
        // set column names for table and create initial object
        String[] columnNames = {"OrderNumber", "Date", "TotalCost", "Status",
                "Name", "Email", "Address", "payment valid",
                "Order Line No.", "ProductCode", "Quantity", "LineCost"};
        DefaultTableModel dataModel = new DefaultTableModel(columnNames, 0);

        // add data to model
        try {
            // get confirmed orders
            String sql = "SELECT * FROM Orders WHERE OrderStatus = \"confirmed\"";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet ordersResults = preparedStatement.executeQuery();
            boolean displayOrder = true;// true represents an order, false an order line
            Object[] data;

            // for every confirmed order
            while (ordersResults.next()) {
                // get the order lines
                sql = "SELECT * FROM OrderLines WHERE OrderNumber=?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, ordersResults.getInt("OrderNumber"));
                ResultSet orderLinesResults = preparedStatement.executeQuery();

                // get the user
                String userID = ordersResults.getString("UserID");
                User ordersUser = userDBOps.getUserFromID(userID, connection);
                String ordersUserPayment = "no";
                if (ordersUser.getHasPayment() != null){ ordersUserPayment = "yes";}

                // for each order line, add to table
                while (orderLinesResults.next()) {
                    // if first line in order, show order details and order line
                    if (displayOrder) {
                        data = new Object[]{
                                // Order info
                                ordersResults.getInt("OrderNumber"),
                                ordersResults.getDate("OrderDate"),
                                ordersResults.getDouble("TotalCost"),
                                ordersResults.getString("OrderStatus"),
                                // order's user info
                                ordersUser.getCombinedName(),
                                ordersUser.getEmail(),
                                ordersUser.getAddress().toString(),
                                ordersUserPayment,
                                // order line info
                                orderLinesResults.getString("OrderLineNumber"),
                                orderLinesResults.getString("ProductCode"),
                                orderLinesResults.getInt("Quantity"),
                                orderLinesResults.getDouble("LineCost")};

                        displayOrder = false;
                    }
                    // if not first line in order, just show order line
                    else {
                        data = new Object[]{"", "", "", "", "", "", "", "",
                                orderLinesResults.getString("OrderLineNumber"),
                                orderLinesResults.getString("ProductCode"),
                                orderLinesResults.getInt("Quantity"),
                                orderLinesResults.getDouble("LineCost")};

                    }
                    dataModel.addRow(data); // add to model
                }
                displayOrder = true;
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        ordersTable.setModel(dataModel);
        //---------------------------------- generate table (finish) -----------------------------

        browseButton.addActionListener(new ActionListener() {
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
        currentOrdersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentOrdersButton.setEnabled(false);
                ordersArchiveButton.setEnabled(true);
                fulfillNextOrderButton.setEnabled(true);
                deleteNextOrderButton.setEnabled(true);
                tableLabel.setText("Current Orders");

                //---------------------------------- generate table (start) -----------------------------
                // set column names for table and create initial object
                String[] columnNames = {"OrderNumber", "Date", "TotalCost", "Status",
                        "Name", "Email", "Address", "payment valid",
                        "Order Line No.", "ProductCode", "Quantity", "LineCost"};
                DefaultTableModel dataModel = new DefaultTableModel(columnNames, 0);

                // add data to model
                try {
                    // get confirmed orders
                    String sql = "SELECT * FROM Orders WHERE OrderStatus = \"confirmed\"";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    ResultSet ordersResults = preparedStatement.executeQuery();
                    boolean displayOrder = true;// true represents an order, false an order line
                    Object[] data;

                    // for every confirmed order
                    while (ordersResults.next()) {
                        // get the order lines
                        sql = "SELECT * FROM OrderLines WHERE OrderNumber=?";
                        preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setInt(1, ordersResults.getInt("OrderNumber"));
                        ResultSet orderLinesResults = preparedStatement.executeQuery();

                        // get the user
                        String userID = ordersResults.getString("UserID");
                        User ordersUser = userDBOps.getUserFromID(userID, connection);
                        String ordersUserPayment = "no";
                        if (ordersUser.getHasPayment() != null){ ordersUserPayment = "yes";}

                        // for each order line, add to table
                        while (orderLinesResults.next()) {
                            // if first line in order, show order details and order line
                            if (displayOrder) {
                                data = new Object[]{
                                        // Order info
                                        ordersResults.getInt("OrderNumber"),
                                        ordersResults.getDate("OrderDate"),
                                        ordersResults.getDouble("TotalCost"),
                                        ordersResults.getString("OrderStatus"),
                                        // order's user info
                                        ordersUser.getCombinedName(),
                                        ordersUser.getEmail(),
                                        ordersUser.getAddress().toString(),
                                        ordersUserPayment,
                                        // order line info
                                        orderLinesResults.getString("OrderLineNumber"),
                                        orderLinesResults.getString("ProductCode"),
                                        orderLinesResults.getInt("Quantity"),
                                        orderLinesResults.getDouble("LineCost")};

                                displayOrder = false;
                            }
                            // if not first line in order, just show order line
                            else {
                                data = new Object[]{"", "", "", "", "", "", "", "",
                                        orderLinesResults.getString("OrderLineNumber"),
                                        orderLinesResults.getString("ProductCode"),
                                        orderLinesResults.getInt("Quantity"),
                                        orderLinesResults.getDouble("LineCost")};

                            }
                            dataModel.addRow(data); // add to model
                        }
                        displayOrder = true;
                    }

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                ordersTable.setModel(dataModel);
                //---------------------------------- generate table (finish) -----------------------------
            }
        });
        ordersArchiveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ordersArchiveButton.setEnabled(false);
                currentOrdersButton.setEnabled(true);
                fulfillNextOrderButton.setEnabled(false);
                deleteNextOrderButton.setEnabled(false);
                tableLabel.setText("Orders Archive");


                //---------------------------------- generate table (start) -----------------------------
                // set column names for table and create initial object
                String[] columnNames = {"OrderNumber", "Date", "TotalCost", "Status",
                        "Name", "Email", "Address", "payment valid",
                        "Order Line No.", "ProductCode", "Quantity", "LineCost"};
                DefaultTableModel dataModel = new DefaultTableModel(columnNames, 0);

                // add data to model
                try {
                    // get confirmed orders
                    String sql = "SELECT * FROM Orders WHERE OrderStatus = \"fulfilled\"";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    ResultSet ordersResults = preparedStatement.executeQuery();
                    boolean displayOrder = true;// true represents an order, false an order line
                    Object[] data;

                    // for every confirmed order
                    while (ordersResults.next()) {
                        // get the order lines
                        sql = "SELECT * FROM OrderLines WHERE OrderNumber=?";
                        preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setInt(1, ordersResults.getInt("OrderNumber"));
                        ResultSet orderLinesResults = preparedStatement.executeQuery();

                        // get the user
                        String userID = ordersResults.getString("UserID");
                        User ordersUser = userDBOps.getUserFromID(userID, connection);
                        String ordersUserPayment = "no";
                        if (ordersUser.getHasPayment() != null){ ordersUserPayment = "yes";}

                        // for each order line, add to table
                        while (orderLinesResults.next()) {
                            // if first line in order, show order details and order line
                            if (displayOrder) {
                                data = new Object[]{
                                        // Order info
                                        ordersResults.getInt("OrderNumber"),
                                        ordersResults.getDate("OrderDate"),
                                        ordersResults.getDouble("TotalCost"),
                                        ordersResults.getString("OrderStatus"),
                                        // order's user info
                                        ordersUser.getCombinedName(),
                                        ordersUser.getEmail(),
                                        ordersUser.getAddress().toString(),
                                        ordersUserPayment,
                                        // order line info
                                        orderLinesResults.getString("OrderLineNumber"),
                                        orderLinesResults.getString("ProductCode"),
                                        orderLinesResults.getInt("Quantity"),
                                        orderLinesResults.getDouble("LineCost")};

                                displayOrder = false;
                            }
                            // if not first line in order, just show order line
                            else {
                                data = new Object[]{"", "", "", "", "", "", "", "",
                                        orderLinesResults.getString("OrderLineNumber"),
                                        orderLinesResults.getString("ProductCode"),
                                        orderLinesResults.getInt("Quantity"),
                                        orderLinesResults.getDouble("LineCost")};

                            }
                            dataModel.addRow(data); // add to model
                        }
                        displayOrder = true;
                    }

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                ordersTable.setModel(dataModel);
                //---------------------------------- generate table (finish) -----------------------------
            }
        });




        fulfillNextOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = "error fulfilling order";
                String orderNo = dataModel.getValueAt(0,0).toString();
                System.out.println(orderNo);
                try {
                    OrderDatabaseOperations.fulfillOrderFromOrderNumber(Integer.valueOf(orderNo), connection);
                    message = "order fulfilled";
                }
                catch(SQLException exception) {
                    message = "error fulfilling order";
                }
                finally {
                    // display message
                    System.out.println(message);
                    currentOrdersButton.setEnabled(true);
                    currentOrdersButton.doClick();
                }
            }
        });
        deleteNextOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = "error deleting order";
                String orderNo = dataModel.getValueAt(0,0).toString();
                System.out.println(orderNo);
                try {
                    OrderDatabaseOperations.deleteOrderFromOrderNumber(Integer.valueOf(orderNo), connection);
                    message = "order deleted";
                }
                catch(SQLException exception) {
                    message = "error deleting order";
                }
                finally {
                    // display message
                    System.out.println(message);
                    currentOrdersButton.setEnabled(true);
                    currentOrdersButton.doClick();
                }
            }
        });
    }
}
