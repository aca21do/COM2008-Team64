public class RollingStock extends Part {
    private String eraCode;

    public String getEraCode() {
        return this.eraCode;
    }

    public void setEraCode(String eraCode) {
        this.eraCode = eraCode;
    }

    public RollingStock(String brandName, String productCode, String gaugeCode, double retailPrice, String eraCode) {
        super(brandName, productCode, gaugeCode, retailPrice);
        this.eraCode = eraCode;
    }
}
