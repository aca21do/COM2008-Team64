import java.util.ArrayList;
import java.util.Date;

public class FulfilledOrder extends Order{
    public FulfilledOrder (int orderNumber, Date orderDate, String orderStatus, ArrayList<OrderLine> orderLines) {
        super(orderNumber, orderDate, orderStatus, orderLines);
    }
}
