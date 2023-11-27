public class FulfilledOrder extends Order{
    public void FulfilledOrder (String orderNumber, String orderDate, String orderStatus) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
    }
}
