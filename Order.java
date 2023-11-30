public class Order {
    protected String orderNumber;
    protected String orderDate;
    protected String orderStatus;
    protected OrderLine[] orderLines;

    public String getOrderNumber() {
        return this.orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderDate() {
        return this.orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public OrderLine[] getOrderLines() {
        return this.orderLines;
    }

    public void setOrderLines(OrderLine[] orderLines) {
        this.orderLines = orderLines;
    }

    public Order(String orderNumber, String orderDate, String orderStatus, OrderLine[] orderLines) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.orderLines = orderLines;
    }
}
