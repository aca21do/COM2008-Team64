public class Locomotive extends Part {
    private String eraCode;

    private String dccCode;



    public String getEraCode () {
        return this.eraCode;
    }

    public void setEraCode (String eraCode) {
        this.eraCode = eraCode;
    }

    public String getDCCCode () {
        return this.dccCode;
    }

    public void setDCCCode (String dccCode) {
        this.dccCode = dccCode;
    }

    public Locomotive(String brandName, String productName, String productCode, double retailPrice,
                      Gauge gaugeCode, String eraCode, String dccCode) {
        super(brandName, productName, productCode, retailPrice, gaugeCode);
        this.eraCode = eraCode;
        this.dccCode = dccCode;
    }
}
