import java.util.ArrayList;

public class ConfirmedOrder extends Order {
    public ConfirmedOrder(String orderNumber, String orderDate, String orderStatus, ArrayList<OrderLine> orderLines) {
        super(orderNumber, orderDate, orderStatus, orderLines);
    }
}
