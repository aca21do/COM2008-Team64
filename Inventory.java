import sheffield.DatabaseConnectionHandler;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

public class Inventory {
    public void insertItem(InventoryItem inventoryItem, Connection connection) throws SQLException {
        try {
            //Processes an SQL theory to check if a product already exists
            String sql = "SELECT * FROM Products WHERE ProductCode = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, inventoryItem.getProduct().getProductCode());
            ResultSet resultSet = preparedStatement.executeQuery();

            //if it doesn't already exist add it to the db tables
            if (!resultSet.next()) {
                //Adds all products to products table no matter what type it is
                sql = "INSERT INTO Products (ProductCode, GaugeCode) VALUES (?, ?)";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, inventoryItem.getProduct().getProductCode());
                preparedStatement.setString(2, String.valueOf(inventoryItem.getProduct().getGaugeCode()));
                int rowsAffected = preparedStatement.executeUpdate();
                System.out.println(rowsAffected + " row(s) inserted successfully.");

                //checks which type of product it is and changes the relevant tables as necessary
                if (inventoryItem.getProduct().getProductCode().charAt(0) == 'S') {
                    RollingStock product = (RollingStock) inventoryItem.getProduct();
                    sql = "INSERT INTO Eras (ProductCode, EraCode) VALUES (?,?)";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, product.getProductCode());
                    preparedStatement.setString(2, product.getEraCode());
                    rowsAffected = preparedStatement.executeUpdate();
                    System.out.println(rowsAffected + " row(s) inserted successfully.");
                }
                else if (inventoryItem.getProduct().getProductCode().charAt(0) == 'L') {
                    Locomotive product = (Locomotive) inventoryItem.getProduct();
                    sql = "INSERT INTO Eras (ProductCode, EraCode) VALUES (?,?)";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, product.getProductCode());
                    preparedStatement.setString(2, product.getEraCode());
                    rowsAffected = preparedStatement.executeUpdate();
                    System.out.println(rowsAffected + " row(s) inserted successfully.");

                    sql = "INSERT INTO DCCCodes (ProductCode, DCCCode) VALUES (?,?)";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, product.getProductCode());
                    preparedStatement.setString(2, product.getDCCCode());
                    rowsAffected = preparedStatement.executeUpdate();
                    System.out.println(rowsAffected + " row(s) inserted successfully.");
                }
                else if (inventoryItem.getProduct().getProductCode().charAt(0) == 'M') {
                    sql = "INSERT INTO SetsAndPacks (ProductCode) VALUES (?)";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, inventoryItem.getProduct().getProductCode());
                    rowsAffected = preparedStatement.executeUpdate();
                    System.out.println(rowsAffected + " row(s) inserted successfully.");

                    Set product = (Set) inventoryItem.getProduct();

                    sql = "INSERT INTO SetAndPackComponents (ProductCode, ComponentProductCode, Quantity) VALUES (?,?,?)";
                    for (int i = 0; i < product.getComponentProductCodes().getRowCount(); i++) {
                        preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setString(1, inventoryItem.getProduct().getProductCode());
                        preparedStatement.setString(2, (String) product.getComponentProductCodes()
                                                                                            .getValueAt(i, 0));
                        preparedStatement.setInt(3, (Integer) product.getComponentProductCodes()
                                                                                            .getValueAt(i, 1));
                        rowsAffected = preparedStatement.executeUpdate();
                        System.out.println(rowsAffected + " row(s) inserted successfully.");
                    }
                }
                else if (inventoryItem.getProduct().getProductCode().charAt(0) == 'P') {
                    sql = "INSERT INTO SetsAndPacks (ProductCode) VALUES (?)";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, inventoryItem.getProduct().getProductCode());
                    rowsAffected = preparedStatement.executeUpdate();
                    System.out.println(rowsAffected + " row(s) inserted successfully.");

                    Pack product = (Pack) inventoryItem.getProduct();

                    sql = "INSERT INTO SetAndPackComponents (ProductCode, ComponentProductCode, Quantity) VALUES (?,?,?)";
                    for (int i = 0; i < product.getComponentProductCodes().getRowCount(); i++) {
                        preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setString(1, inventoryItem.getProduct().getProductCode());
                        preparedStatement.setString(2, (String) product.getComponentProductCodes()
                                .getValueAt(i, 0));
                        preparedStatement.setInt(3, (Integer) product.getComponentProductCodes()
                                .getValueAt(i, 1));
                        rowsAffected = preparedStatement.executeUpdate();
                        System.out.println(rowsAffected + " row(s) inserted successfully.");
                    }
                }
            }

            //checks if the item being inserted is in inventory or not
            sql = "SELECT * FROM Inventory WHERE ProductCode = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, inventoryItem.getProduct().getProductCode());
            resultSet = preparedStatement.executeQuery();

            //if not inserts a new record into the inventory otherwise updates the value
            if (!resultSet.next()) {
                sql = "INSERT INTO Inventory (ProductCode, BrandName, ProductName, Price, Quantity) VALUES (?, ?, ?, ?, ?)";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, inventoryItem.getProduct().getProductCode());
                preparedStatement.setString(2, inventoryItem.getProduct().getBrandName());
                preparedStatement.setString(3, inventoryItem.getProduct().getProductName());
                preparedStatement.setDouble(4, inventoryItem.getProduct().getRetailPrice());
                preparedStatement.setInt(5, inventoryItem.getQuantity());
                int rowsAffected = preparedStatement.executeUpdate();
                System.out.println(rowsAffected + " row(s) inserted successfully.");
            }
            else {
                int quantity = resultSet.getInt("quantity") + inventoryItem.getQuantity();
                sql = "UPDATE Inventory SET quantity=? WHERE ProductCode=?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, quantity);
                preparedStatement.setString(2, inventoryItem.getProduct().getProductCode());

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println(rowsAffected + " row(s) updated successfully");
                }
                else {
                    System.out.println("No rows were updated for ProductCode: " + inventoryItem.getProduct().getProductCode());
                }
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    //function to change the quantity of stock of a product in the database
    public void updateStock(Product product, int quantity, Connection connection) throws SQLException{
        try {
            String sql = "UPDATE Inventory SET quantity=? WHERE ProductCode=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, quantity);
            preparedStatement.setString(2, product.getProductCode());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println(rowsAffected + " row(s) updated successfully");
            }
            else {
                System.out.println("No rows were updated for ProductCode: " + product.getProductCode());
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }


    //function to return the correct class for the different products to get access to methods and attributes
    public Product returnProductSubClass(String productCode, Connection connection) throws SQLException {
        try {
            //gets the first letter of the product code to identify which type it is
            Character firstLetter = productCode.toUpperCase().charAt(0);

            //gets the records from inventory to insert the attributes into the objects
            String sql = "SELECT * FROM Inventory WHERE ProductCode = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, productCode);
            ResultSet resultSet = preparedStatement.executeQuery();

            //gets the records from products to insert gaugeCodes
            sql = "SELECT * FROM Products WHERE ProductCode=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, productCode);
            ResultSet resultSet1 = preparedStatement.executeQuery();

            resultSet.next();
            resultSet1.next();

            //returns the correct classes with the correct attributes based on the productCode first letter
            if (firstLetter == 'L') {

                sql = "SELECT * FROM Eras WHERE ProductCode = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, productCode);
                ResultSet extraResults = preparedStatement.executeQuery();
                extraResults.next();
                String eraCode = extraResults.getString("EraCode");

                sql = "SELECT * FROM DCCCodes WHERE ProductCode = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, productCode);
                extraResults = preparedStatement.executeQuery();
                extraResults.next();
                String dccCode = extraResults.getString("DCCCode");

                Gauge gaugeType = Gauge.valueOf(resultSet1.getString("GaugeCode"));

                Locomotive locomotive = new Locomotive(
                        resultSet.getString("BrandName"),
                        resultSet.getString("ProductName"),
                        resultSet.getString("ProductCode"),
                        resultSet.getDouble("Price"),
                        gaugeType,
                        eraCode,
                        dccCode);
                return locomotive;

            }
            else if (firstLetter == 'S') {
                sql = "SELECT * FROM Eras WHERE ProductCode = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, productCode);
                ResultSet extraResults = preparedStatement.executeQuery();
                extraResults.next();
                String eraCode = extraResults.getString("EraCode");

                Gauge gaugeType = Gauge.valueOf(resultSet1.getString("GaugeCode"));

                RollingStock rollingStock = new RollingStock(
                        resultSet.getString("BrandName"),
                        resultSet.getString("ProductName"),
                        resultSet.getString("ProductCode"),
                        resultSet.getDouble("Price"),
                        gaugeType,
                        eraCode);
                return rollingStock;
            }

            else if (firstLetter == 'R') {
                Gauge gaugeType = Gauge.valueOf(resultSet1.getString("GaugeCode"));
                
                TrackPiece trackPiece = new TrackPiece(
                        resultSet.getString("BrandName"),
                        resultSet.getString("ProductName"),
                        resultSet.getString("ProductCode"),
                        resultSet.getDouble("Price"),
                        gaugeType);
                return trackPiece;
            }

            else if (firstLetter == 'C') {
                Gauge gaugeType = Gauge.valueOf(resultSet1.getString("GaugeCode"));

                Controller controller= new Controller(
                        resultSet.getString("BrandName"),
                        resultSet.getString("ProductName"),
                        resultSet.getString("ProductCode"),
                        resultSet.getDouble("Price"),
                        gaugeType);
                return controller;
            }
            else if (firstLetter == 'M') {
                Gauge gaugeType = Gauge.valueOf(resultSet1.getString("GaugeCode"));

                DefaultTableModel components = new DefaultTableModel();
                components.addColumn("ProductCode");
                components.addColumn("Quantity");

                sql = "SELECT * FROM SetAndPackComponents WHERE ProductCode=?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1,resultSet.getString("ProductCode"));
                ResultSet setComponents = preparedStatement.executeQuery();

                while (setComponents.next()) {
                    components.addRow(new Object[]{setComponents.getString("ComponentProductCode"),
                            setComponents.getInt("Quantity")});
                }

                Set set = new Set(
                        resultSet.getString("BrandName"),
                        resultSet.getString("ProductName"),
                        resultSet.getString("ProductCode"),
                        resultSet.getDouble("Price"),
                        gaugeType,
                        components);
                return set;
            }
            else if (firstLetter == 'P') {
                Gauge gaugeType = Gauge.valueOf(resultSet1.getString("GaugeCode"));
                DefaultTableModel components = new DefaultTableModel();
                components.addColumn("ProductCode");
                components.addColumn("Quantity");

                sql = "SELECT * FROM SetAndPackComponents WHERE ProductCode=?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1,resultSet.getString("ProductCode"));
                ResultSet setComponents = preparedStatement.executeQuery();

                while (setComponents.next()) {
                    components.addRow(new Object[]{setComponents.getString("ComponentProductCode"),
                                                    setComponents.getInt("Quantity")});
                }

                Pack pack = new Pack(
                        resultSet.getString("BrandName"),
                        resultSet.getString("ProductName"),
                        resultSet.getString("ProductCode"),
                        resultSet.getDouble("Price"),
                        gaugeType,
                        components);
                return pack;
            }
            else {
                return null;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    //function to get an InventoryItem class object based on the productCode
    public InventoryItem getInventoryItem(String productCode, Connection connection) throws SQLException {
        try {
            //gets that product data from the database
            String sql = "SELECT * FROM Inventory WHERE ProductCode = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, productCode);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();

            //gets the correct type of product using the previous function
            Product product = returnProductSubClass(productCode, connection);
            if (product == null) {
                System.out.println("Invalid Product Code");
                return null;
            }

            //creates the InventoryItem object with the product and the quantity
            InventoryItem inventoryItem = new InventoryItem(product, resultSet.getInt("Quantity"));
            return inventoryItem;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    //function to update the database for a specific item
    public void updateItem (InventoryItem inventoryItem, Connection connection) throws SQLException {
        try {
            //Creates an SQL statement based on the items being updated and changes the values in the database
            String sql = "UPDATE Inventory SET BrandName=?, ProductName=?, Price=?, Quantity=? WHERE ProductCode=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, inventoryItem.getProduct().getBrandName());
            preparedStatement.setString(2, inventoryItem.getProduct().getProductName());
            preparedStatement.setDouble(3, inventoryItem.getProduct().getRetailPrice());
            preparedStatement.setInt(4, inventoryItem.getQuantity());
            preparedStatement.setString(5, inventoryItem.getProduct().getProductCode());
            insertItem(inventoryItem, connection);

            //updates extra values based on the type of item the product is
            Character firstletter = inventoryItem.getProduct().getProductCode().toUpperCase().charAt(0);
            if (firstletter == 'S') {
                sql = "UPDATE Eras SET EraCode=? WHERE ProductCode=?";
                preparedStatement = connection.prepareStatement(sql);
                RollingStock product = (RollingStock) returnProductSubClass(inventoryItem.getProduct().getProductCode(),
                                                                                                            connection);
                preparedStatement.setString(1, product.getEraCode());
                preparedStatement.setString(2, inventoryItem.getProduct().getProductCode());
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println(rowsAffected + " row(s) updated successfully");
                }
                else {
                    System.out.println("No rows were updated for ProductCode: " + product.getProductCode());
                }
            }
            else if (firstletter == 'L') {
                preparedStatement = connection.prepareStatement(sql);
                Locomotive product = (Locomotive) returnProductSubClass(inventoryItem.getProduct().getProductCode(),
                                                                                                            connection);
                preparedStatement.setString(1, product.getEraCode());
                preparedStatement.setString(2, inventoryItem.getProduct().getProductCode());
                int rowsAffected = preparedStatement.executeUpdate();

                sql = "UPDATE DCCCodes SET DCCCode=? WHERE ProductCode=?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, product.getDCCCode());
                preparedStatement.setString(2, product.getProductCode());
                rowsAffected += preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println(rowsAffected + " row(s) updated successfully");
                }
                else {
                    System.out.println("No rows were updated for ProductCode: " + product.getProductCode());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
