public class OrderLine {
    /*
    Product may have to be changed to a different class
    lineCost can probably be derived by taking the price from the db and multiplying by quantity
    this removes the need for lineCost to be involved in constructor parameters
     */
    private double lineCost;
    private int quantity;
    private Product product;

    public double getLineCost() {
        return this.lineCost;
    }

    public void setLineCost(double lineCost) {
        this.lineCost = lineCost;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public OrderLine (double lineCost, int quantity, Product product) {
        this.lineCost = lineCost;
        this.quantity = quantity;
        this.product = product;
    }
}
