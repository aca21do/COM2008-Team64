import java.util.ArrayList;

public class ConfirmedOrder extends Order {
    public ConfirmedOrder(int orderNumber, String orderDate, String orderStatus, ArrayList<OrderLine> orderLines) {
        super(orderNumber, orderDate, orderStatus, orderLines);
    }
}
