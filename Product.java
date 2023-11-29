public class Product {
    protected String brandName;
    protected String productName;
    protected String productCode;
    protected double retailPrice;
    private String gaugeCode;

    public String getBrandName() {
        return this.brandName;
    }

    public void setBrandName(String newBrandName) {
        this.brandName = newBrandName;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String newProductName) {
        this.productName = newProductName;
    }

    public String getProductCode () {
        return this.productCode;
    }

    public void setProductCode (String newProductCode) {
        this.productCode = newProductCode;
    }

    public double getRetailPrice () {
        return this.retailPrice;
    }

    public void setRetailPrice (double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getGaugeCode() {
        return this.gaugeCode;
    }

    public void setGaugeCode(String gaugeCode) {
        this.gaugeCode = gaugeCode;
    }

    public Product (String brandName, String productName,
                    String productCode, double retailPrice, String gaugeCode) {
        this.brandName = brandName;
        this.productName = productName;
        this.productCode = productCode;
        this.retailPrice = retailPrice;
        this.gaugeCode = gaugeCode;
    }
}
