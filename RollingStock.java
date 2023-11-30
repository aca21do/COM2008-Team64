public class RollingStock extends Part {
    private String eraCode;



    public String getEraCode() {
        return this.eraCode;
    }

    public void setEraCode(String eraCode) {
        this.eraCode = eraCode;
    }

    public RollingStock(String brandName, String productName, String productCode, double retailPrice, Gauge gaugeCode, String eraCode) {
        super(brandName, productName, productCode, retailPrice, gaugeCode);
        this.eraCode = eraCode;
    }
}
