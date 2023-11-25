public class Product {
    protected String brandName;
    protected String productName;
    protected String productCode;
    protected float retailPrice;

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

    public float getRetailPrice () {
        return this.retailPrice;
    }

    public void setRetailPrice (float retailPrice) {
        this.retailPrice = retailPrice;
    }

    public void Product (String brandName, String productName,
                         String productCode, float retailPrice) {
        this.brandName = brandName;
        this.productName = productName;
        this.productCode = productCode;
        this.retailPrice = retailPrice;
    }
}
