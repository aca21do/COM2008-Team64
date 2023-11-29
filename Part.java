import java.lang.constant.Constable;

public class Part extends Product{
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

    public Part(String brandName, String productCode, String gaugeCode, double retailPrice) {
        super(brandName, productCode, gaugeCode, retailPrice);
    }
}
