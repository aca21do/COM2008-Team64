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

    public void Locomotive(String productCode, String gaugeCode,
                                String eraCode, String dccCode) {
        this.productCode = productCode;
        this.gaugeCode = gaugeCode;
        this.eraCode = eraCode;
        this.dccCode = dccCode;
    }
}
