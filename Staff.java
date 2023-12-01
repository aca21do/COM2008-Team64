import java.sql.SQLException;
import java.sql.Connection;

public class Staff extends User {
    public Staff(String id, String email, String fname, String sname) {
        super(id, email, fname, sname);
        this.isStaff = true; 
    }

    public void updateStock(Product product, int quantity, Connection con, Inventory inventory) throws SQLException {
        inventory.updateStock(product, quantity, con);
    }

    public InventoryItem createItem(String brandName, String productName, 
                                    String productCode, double retailPrice, Gauge gaugeCode, int quantity) {
        Product newProduct = new Product(brandName, productName, productCode, retailPrice, gaugeCode);
        InventoryItem newItem = new InventoryItem(newProduct, quantity);

        return newItem;
    }

    public void insertItem(InventoryItem inventoryItem, Connection con, Inventory inventory) throws SQLException {
        inventory.insertItem(inventoryItem, con);
    }

    public void updateItem(InventoryItem inventoryItem, Connection con, Inventory inventory) throws SQLException {
        inventory.updateItem(inventoryItem, con);
    }
}
