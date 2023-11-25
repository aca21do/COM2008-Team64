import java.lang.constant.Constable;

public class Part {
    protected String productCode;
    protected String gaugeCode;

    public String getProductCode() {
        return this.productCode;
    }

    public void setProductCode(String newProductCode) {
        this.productCode = newProductCode;
    }

    public String getGaugeCode() {
        return this.gaugeCode;
    }

    public void setGaugeCode (String newGaugeCode) {
        this.gaugeCode = newGaugeCode;
    }

    public void Part(String newProductCode, String newGaugeCode) {
        this.productCode = newProductCode;
        this.gaugeCode = newGaugeCode;
    }
}
