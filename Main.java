import sheffield.DatabaseConnectionHandler;

import java.sql.PreparedStatement;

public class Main {
    public static void main(String[] args){
        System.out.println("---------Hello---------");

        DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
        DatabaseOperations databaseOperations = new DatabaseOperations();
        try {
            System.out.println("connecting");
            databaseConnectionHandler.openConnection();


        } catch (Throwable e) {
            e.printStackTrace();
            System.out.println("connection failed");
        } finally {
            databaseConnectionHandler.closeConnection();
            System.out.println("connected terminated");
        }
    }

}
