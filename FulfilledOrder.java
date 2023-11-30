import java.util.ArrayList;

public class FulfilledOrder extends Order{
    public FulfilledOrder (String orderNumber, String orderDate, String orderStatus, ArrayList<OrderLine> orderLines) {
        super(orderNumber, orderDate, orderStatus, orderLines);
    }
}
