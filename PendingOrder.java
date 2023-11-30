import java.util.ArrayList;

public class PendingOrder extends Order{
    public PendingOrder(String orderNumber, String orderDate, String orderStatus, ArrayList<OrderLine> orderLines) {
        super(orderNumber, orderDate, orderStatus, orderLines);
    }

    public void addOrderLine(OrderLine orderLine) {
        orderLines.add(orderLine);
    }

    public void removeOrderLine(OrderLine orderLine) {
        orderLines.remove(orderLine);
    }
}
