public class Locomotive extends Part {
    private String eraCode;

    private String dccCode;

    public String getEraCode () {
        return this.eraCode;
    }

    public void setEraCode (String newEraCode) {
        this.eraCode = newEraCode;
    }

    public String getDCCCode () {
        return this.dccCode;
    }

    public void setDCCCode (String newDCCCode) {
        this.dccCode = newDCCCode;
    }

    public void Locomotive(String newProductCode, String newGaugeCode,
                                String newEraCode, String newDCCCode) {
        this.productCode = newProductCode;
        this.gaugeCode = newGaugeCode;
        this.eraCode = newEraCode;
        this.dccCode = newDCCCode;
    }
}
