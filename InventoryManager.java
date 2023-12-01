public class InventoryManager {
    // stores a single user, which is logged in
    private static Inventory inventory;

    // constructor
    private InventoryManager() {
        // can't be instantiated
    }

    public static Inventory getInventory() {
        return inventory;
    }
    public static void setInventory(Inventory newInventory) {
        inventory = newInventory;
    }
}