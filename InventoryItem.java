public class InventoryItem {
    private String gaugeCode;

    public String getGaugeCode () {
        return this.gaugeCode;
    }

    public void setGaugeCode (String newGaugeCode) {
        this.gaugeCode = newGaugeCode;
    }

    public void InventoryItem(String newGaugeCode) {
        this.gaugeCode = newGaugeCode;
    }
}
