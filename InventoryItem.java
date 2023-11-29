public class InventoryItem {

    private String gaugeCode;
    private Product product;


    public String getGaugeCode () {
        return this.gaugeCode;
    }

    public void setGaugeCode (String gaugeCode) {
        this.gaugeCode = gaugeCode;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public InventoryItem(String gaugeCode, Product product) {
        this.gaugeCode = gaugeCode;
        this.product = product;
    }
}
