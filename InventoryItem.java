public class InventoryItem {
    private Product product;
    private int quantity;

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public InventoryItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
}
