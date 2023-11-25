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

    public void setRetailPrice (float newRetailPrice) {
        this.retailPrice = newRetailPrice;
    }

    public void Product (String newBrandName, String newProductName,
                         String newProductCode, float newRetailPrice) {
        this.brandName = newBrandName;
        this.productName = newProductName;
        this.productCode = newProductCode;
        this.retailPrice = newRetailPrice;
    }
}
