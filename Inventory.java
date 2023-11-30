import sheffield.DatabaseConnectionHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

public class Inventory {
    public void insertItem(InventoryItem inventoryItem, Connection connection) throws SQLException {
        try {
            String sql = "SELECT * FROM Inventory WHERE ProductCode = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, inventoryItem.getProduct().getProductCode());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                sql = "INSERT INTO Inventory (ProductKey, BrandName, ProductName, Price, Quantity) VALUES (?, ?, ?, ?, ?)";
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

    public InventoryItem getInventoryItem(String productCode, Connection connection) throws SQLException {
        try {
            String sql = "SELECT * FROM Inventory WHERE ProductCode = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, productCode);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();

            Product product = returnProductSubClass(productCode, connection);
            if (product == null) {
                System.out.println("Invalid Product Code");
                return null;
            }
            InventoryItem inventoryItem = new InventoryItem(product, resultSet.getInt("Quantity"));
            return inventoryItem;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Product returnProductSubClass(String productCode, Connection connection) throws SQLException {
        try {
            Character firstLetter = productCode.charAt(0);

            String sql = "SELECT * FROM Inventory WHERE ProductCode = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, productCode);
            ResultSet resultSet = preparedStatement.executeQuery();

            sql = "SELECT * FROM Products WHERE ProductCode=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, productCode);
            ResultSet resultSet1 = preparedStatement.executeQuery();

            resultSet.next();
            resultSet1.next();

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

                Locomotive locomotive = new Locomotive(
                        resultSet.getString("BrandName"),
                        resultSet.getString("ProductName"),
                        resultSet.getString("ProductCode"),
                        resultSet.getDouble("Price"),
                        resultSet1.getString("GaugeCode"),
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

                RollingStock rollingStock = new RollingStock(
                        resultSet.getString("BrandName"),
                        resultSet.getString("ProductName"),
                        resultSet.getString("ProductCode"),
                        resultSet.getDouble("Price"),
                        resultSet1.getString("GaugeCode"),
                        eraCode);
                return rollingStock;
            }
            else if (firstLetter == 'R') {
                TrackPiece trackPiece = new TrackPiece(
                        resultSet.getString("BrandName"),
                        resultSet.getString("ProductName"),
                        resultSet.getString("ProductCode"),
                        resultSet.getDouble("Price"),
                        resultSet1.getString("GaugeCode"));
                return trackPiece;
            }
            else if (firstLetter == 'C') {
                Controller controller= new Controller(
                        resultSet.getString("BrandName"),
                        resultSet.getString("ProductName"),
                        resultSet.getString("ProductCode"),
                        resultSet.getDouble("Price"),
                        resultSet1.getString("GaugeCode"));
                return controller;
            }
            else if (firstLetter == 'S') {
                Set set = new Set(
                        resultSet.getString("BrandName"),
                        resultSet.getString("ProductName"),
                        resultSet.getString("ProductCode"),
                        resultSet.getDouble("Price"),
                        resultSet1.getString("GaugeCode"));
                return set;
            }
            else if (firstLetter == 'P') {
                Pack pack = new Pack(
                        resultSet.getString("BrandName"),
                        resultSet.getString("ProductName"),
                        resultSet.getString("ProductCode"),
                        resultSet.getDouble("Price"),
                        resultSet1.getString("GaugeCode"));
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
}
