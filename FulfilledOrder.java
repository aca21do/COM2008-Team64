public class FulfilledOrder extends Order{
    public FulfilledOrder (String orderNumber, String orderDate, String orderStatus, OrderLine[] orderLines) {
        super(orderNumber, orderDate, orderStatus, orderLines);
    }
}
