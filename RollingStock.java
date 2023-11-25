public class RollingStock extends Part {
    private String eraCode;

    public String getEraCode() {
        return this.eraCode;
    }

    public void setEraCode(String newEraCode) {
        this.eraCode = newEraCode;
    }

    public void RollingStock(String newProductCode, String newGaugeCode, String newEraCode) {
        this.productCode = newProductCode;
        this.gaugeCode = newGaugeCode;
        this.eraCode = newEraCode;
    }
}
