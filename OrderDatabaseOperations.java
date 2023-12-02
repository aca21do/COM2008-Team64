import java.sql.*;
import java.util.ArrayList;

public class OrderDatabaseOperations {
    protected Order order;

    // constructor passes in order so it can be accessed by the class
    public OrderDatabaseOperations(Order order){
        this.order = order;
    }

    public Order insertOrder(User user, Connection con) throws SQLException{
        int maxOrderNo = 0;
        int updatedRows = 0;
        ResultSet maxOrderNoResult = con.prepareStatement("SELECT MAX(OrderNumber) FROM Orders").executeQuery();
        if (maxOrderNoResult.next()){
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
        if (maxOrderLineNoResult.next()){
            maxLineNo = maxOrderLineNoResult.getInt(1);
        }

        int lineNumber= maxLineNo + 1;
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
        }
    }
