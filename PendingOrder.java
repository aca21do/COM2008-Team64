public class PendingOrder extends Order{
    public PendingOrder(String orderNumber, String orderDate, String orderStatus, OrderLine[] orderLines) {
        super(orderNumber, orderDate, orderStatus, orderLines);
    }

    public void addOrderLine(OrderLine orderLine) {

    }
}
