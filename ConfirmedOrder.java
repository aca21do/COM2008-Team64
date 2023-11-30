public class ConfirmedOrder extends Order {
    public ConfirmedOrder(String orderNumber, String orderDate, String orderStatus, OrderLine[] orderLines) {
        super(orderNumber, orderDate, orderStatus, orderLines);
    }
}
