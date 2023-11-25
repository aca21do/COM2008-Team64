public class Pack extends Product{
    public void Pack(String newBrandName, String newProductName,
                     String newProductCode, float newRetailPrice) {
        this.brandName = newBrandName;
        this.productName = newProductName;
        this.productCode = newProductCode;
        this.retailPrice = newRetailPrice;
    }
}
