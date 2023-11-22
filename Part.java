import java.lang.constant.Constable;

public class Part {
    private String productCode;
    private String gaugeCode;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String newProductCode) {
        productCode = newProductCode;
    }

    public String getGaugeCode() {
        return gaugeCode;
    }

    public void setGaugeCode (String newGaugeCode) {
        gaugeCode = newGaugeCode;
    }

    public void Part(String newProductCode, String newGaugeCode) {
        productCode = newProductCode;
        gaugeCode = newGaugeCode;
    }
}
