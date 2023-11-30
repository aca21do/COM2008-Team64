import javax.management.monitor.GaugeMonitorMBean;

public class Pack extends Product{

    public Pack(String brandName, String productName, String productCode, double retailPrice, Gauge gaugeCode) {
        super(brandName, productName, productCode, retailPrice, gaugeCode);
    }
}
