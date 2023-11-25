public class RollingStock extends Part {
    private String eraCode;

    public String getEraCode() {
        return this.eraCode;
    }

    public void setEraCode(String eraCode) {
        this.eraCode = eraCode;
    }

    public void RollingStock(String productCode, String gaugeCode, String eraCode) {
        this.productCode = productCode;
        this.gaugeCode = gaugeCode;
        this.eraCode = eraCode;
    }
}
