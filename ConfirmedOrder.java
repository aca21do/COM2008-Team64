import java.util.ArrayList;
import java.util.Date;

public class ConfirmedOrder extends Order {
    public ConfirmedOrder(int orderNumber, Date orderDate, String orderStatus, ArrayList<OrderLine> orderLines) {
        super(orderNumber, orderDate, orderStatus, orderLines);
    }

    public ConfirmedOrder(Order order){
        super(order.getOrderNumber(), order.getOrderDate(), "confirmed", order.getOrderLines());
    }
}
