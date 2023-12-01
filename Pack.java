import javax.management.monitor.GaugeMonitorMBean;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class Pack extends Product{

    DefaultTableModel componentProductCodes;

    public DefaultTableModel getComponentProductCodes() {
        return this.componentProductCodes;
    }

    public void setComponentProductCodes(DefaultTableModel componentProductCodes) {
        this.componentProductCodes = componentProductCodes;
    }

    public Pack(String brandName, String productName, String productCode, double retailPrice,
                Gauge gaugeCode, DefaultTableModel componentProductCodes) {
        super(brandName, productName, productCode, retailPrice, gaugeCode);
        this.componentProductCodes = componentProductCodes;
    }
}
