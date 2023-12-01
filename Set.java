import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class Set extends Pack{
    public Set(String brandName, String productName, String productCode, double retailPrice,
                    Gauge gaugeCode, DefaultTableModel components) {
        super(brandName, productName, productCode, retailPrice, gaugeCode, components);
    }
}
