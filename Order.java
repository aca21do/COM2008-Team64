import java.util.ArrayList;
import java.util.Date;

public class Order {
    protected int orderNumber;
    protected Date orderDate;
    protected String orderStatus;
    protected ArrayList<OrderLine> orderLines;
    protected OrderDatabaseOperations dbOperations;

    public int getOrderNumber() {
        return this.orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getOrderDate() {
        return this.orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public ArrayList<OrderLine> getOrderLines() {
        return this.orderLines;
    }

    public void setOrderLines(ArrayList<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public Order(int orderNumber, Date orderDate, String orderStatus, ArrayList<OrderLine> orderLines) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.orderLines = orderLines;
        this.dbOperations = new OrderDatabaseOperations(this);
    }

    public static Order getSubclassInstance(Order order) {
        switch (order.getOrderStatus()) {
            case "pending":
                return new PendingOrder(order);
            case "confirmed":
                return new ConfirmedOrder(order);
            case "fulfilled":
                return new FulfilledOrder(order);
            default:
                return order;
        }
    }
}
