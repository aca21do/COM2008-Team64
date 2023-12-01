import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class PendingOrder extends Order{
    private static PendingOrder currentBasket;

    public PendingOrder(int orderNumber, Date orderDate, String orderStatus, ArrayList<OrderLine> orderLines) {
        super(orderNumber, orderDate, orderStatus, orderLines);
    }

    public PendingOrder(Order order){
        super(order.getOrderNumber(), order.getOrderDate(), "pending", order.getOrderLines());
    }

    public void addOrderLine(OrderLine orderLine, Connection connection) throws SQLException {
        try {
            orderLines.add(orderLine);
            String sql = "INSERT INTO OrderLines (OrderNumber, OrderLineNumber, " +
                    "ProductCode, Quantity, LineCost) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, orderNumber);
            preparedStatement.setInt(2, orderLines.size());
            preparedStatement.setString(3, orderLine.getProduct().getProductCode());
            preparedStatement.setInt(4, orderLine.getQuantity());
            preparedStatement.setDouble(5, orderLine.getLineCost());
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted successfully.");
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void removeOrderLine(OrderLine orderLine, Connection connection) throws SQLException{
        try{
            orderLines.remove(orderLine);
            String sql = "DELETE FROM OrderLines WHERE OrderNumber=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, orderNumber);
            preparedStatement.executeUpdate();

            sql = "INSERT INTO OrderLines (OrderNumber, OrderLineNumber, " +
                    "ProductCode, Quantity, LineCost) VALUES (?, ?, ?, ?, ?)";
            for (int i = 0; i < orderLines.size(); i++) {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, orderNumber);
                preparedStatement.setInt(2, i+1);
                preparedStatement.setString(3, orderLines.get(i).getProduct().getProductCode());
                preparedStatement.setInt(4, orderLines.get(i).getQuantity());
                preparedStatement.setDouble(5, orderLines.get(i).getLineCost());
                preparedStatement.executeUpdate();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static PendingOrder getNewPendingOrder(User user, Connection con){
        PendingOrder emptyPendingOrder = new PendingOrder(-1, Date.from(Instant.now()),
                "pending", new ArrayList<OrderLine>());

        PendingOrder newPendingOrder;
        try {
            newPendingOrder = new PendingOrder(emptyPendingOrder.dbOperations.insertOrder(user, con));
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
            newPendingOrder = emptyPendingOrder;
        }
        return emptyPendingOrder;
    }

}
