public class InventoryItem {
    private String gaugeCode;

    public String getGaugeCode () {
        return this.gaugeCode;
    }

    public void setGaugeCode (String gaugeCode) {
        this.gaugeCode = gaugeCode;
    }

    public InventoryItem(String gaugeCode) {
        this.gaugeCode = gaugeCode;
    }
}
