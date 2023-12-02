import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

public class OrderDatabaseOperations {
    protected Order order;

    // constructor passes in order so it can be accessed by the class
    public OrderDatabaseOperations(Order order) {
        this.order = order;
    }

    public Order insertOrder(User user, Connection con) throws SQLException {
        int maxOrderNo = 0;
        int updatedRows = 0;

        // if adding a new pending order, remove the user's old pending order
        if (order.getOrderStatus().equals("pending")) {
            removeExistingPending(con);
        }

        ResultSet maxOrderNoResult = con.prepareStatement("SELECT MAX(OrderNumber) FROM Orders").executeQuery();
        if (maxOrderNoResult.next()) {
            maxOrderNo = maxOrderNoResult.getInt(1);
        }
        order.setOrderNumber(maxOrderNo + 1);
        String insertOrderSQL =
                "INSERT INTO Orders (OrderNumber, OrderDate, TotalCost, OrderStatus, UserID)" +
                        " VALUES (?, ?, ?, ?, ?)";
        PreparedStatement insertOrderState = con.prepareStatement(insertOrderSQL);
        Date sqlDate = new Date(
                order.getOrderDate().getYear(), order.getOrderDate().getMonth(), order.getOrderDate().getDay());

        // insert attributes into statement
        insertOrderState.setInt(1, order.getOrderNumber());
        insertOrderState.setDate(2, sqlDate);
        insertOrderState.setDouble(3, order.getTotalCost());
        insertOrderState.setString(4, order.getOrderStatus());
        insertOrderState.setString(5, user.getUserID());

        updatedRows += insertOrderState.executeUpdate();
        updatedRows += order.dbOperations.insertOrderLines(con);

        return order;
    }

    /**
     * Automatically inserts order lines for a order which has not been added to the database yet.
     *
     * @param con
     * @return
     * @throws SQLException
     */
    private int insertOrderLines(Connection con) throws SQLException {
        int linesAdded = 0;
        ArrayList<OrderLine> lines = order.getOrderLines();

        // for every order line
        for (OrderLine line : lines) {
            String insertOrderLineSQL =
                    "INSERT INTO OrderLines (OrderNumber,OrderLineNumber, ProductCode, Quantity, LineCost)" +
                            " VALUES (?, ?, ?, ?, ?)";
            PreparedStatement insertLineState = con.prepareStatement(insertOrderLineSQL);

            // insert attributes into statement
            insertLineState.setInt(1, order.getOrderNumber());
            insertLineState.setInt(2, line.getLineNumber());
            insertLineState.setString(3, line.getProduct().getProductCode());
            insertLineState.setInt(4, line.getQuantity());
            insertLineState.setDouble(5, line.getLineCost());

            linesAdded += insertLineState.executeUpdate();
        }
        return linesAdded;
    }

    public void insertOrderLine(Product product, int quantity, Connection con) throws SQLException {
        int maxLineNo = 0;
        int linesAdded = 0;

        String insertOrderLineSQL =
                "INSERT INTO OrderLines (OrderNumber,OrderLineNumber, ProductCode, Quantity, LineCost)" +
                        " VALUES (?, ?, ?, ?, ?)";
        PreparedStatement insertLineState = con.prepareStatement(insertOrderLineSQL);

        // set line number and cost
        String maxLineNoSQL = "SELECT MAX(OrderLineNumber) FROM OrderLines";
        ResultSet maxOrderLineNoResult = con.prepareStatement(maxLineNoSQL).executeQuery();
        if (maxOrderLineNoResult.next()) {
            maxLineNo = maxOrderLineNoResult.getInt(1);
        }

        int lineNumber = maxLineNo + 1;
        double lineCost = product.getRetailPrice() * quantity;

        // insert attributes into statement
        insertLineState.setInt(1, order.getOrderNumber());
        insertLineState.setInt(2, lineNumber);
        insertLineState.setString(3, product.getProductCode());
        insertLineState.setInt(4, quantity);
        insertLineState.setDouble(5, lineCost);

        linesAdded += insertLineState.executeUpdate();

        OrderLine newOrderLine = new OrderLine(lineNumber, lineCost, quantity, product);
        order.orderLines.add(newOrderLine);

        InventoryItem inventoryItem = InventoryManager.getInventory().getInventoryItem(product.getProductCode(), con);
        int newQuantityInStock = inventoryItem.getQuantity() - quantity;

        String setQuantitySQL = "UPDATE Inventory SET Quantity = ? WHERE ProductCode = ?";
        PreparedStatement setQuantityStatement = con.prepareStatement(setQuantitySQL);
        setQuantityStatement.setInt(1, newQuantityInStock);
        setQuantityStatement.setString(2, product.getProductCode());
        setQuantityStatement.executeUpdate();
    }

    public void removeExistingPending(Connection con) throws SQLException{
    UserDatabaseOperations userDBops = new UserDatabaseOperations();

    PendingOrder pendingOrder = userDBops.getUsersPendingOrder(CurrentUser.getCurrentUser(), con);
    int i=0;
    while (pendingOrder != null && i<100){
        // remove lines of pending order
        String removePendingLines = "DELETE FROM OrderLines WHERE OrderNumber = ?";
        PreparedStatement removeLinePrep = con.prepareStatement(removePendingLines);
        removeLinePrep.setInt(1, pendingOrder.getOrderNumber());
        removeLinePrep.executeUpdate();

        // remove pending order
        String removePendingSQL = "DELETE FROM Orders WHERE OrderStatus = 'pending' AND UserID = ?";
        PreparedStatement removePendingStatement = con.prepareStatement(removePendingSQL);
        removePendingStatement.setString(1, CurrentUser.getCurrentUser().getUserID());
        removePendingStatement.executeUpdate();

        // cleanup any possible other pending orders
        pendingOrder = userDBops.getUsersPendingOrder(CurrentUser.getCurrentUser(), con);
        i++;
        }
    }

    public ConfirmedOrder confirmOrder(Connection con) throws SQLException{
        String confirmSQL = "UPDATE Orders SET OrderStatus = 'confirmed' WHERE OrderNumber = ?";
        PreparedStatement confirmState = con.prepareStatement(confirmSQL);
        confirmState.setInt(1, order.orderNumber);
        confirmState.executeUpdate();

        ConfirmedOrder confirmedOrder = new ConfirmedOrder(order);
        return confirmedOrder;
    }
}
