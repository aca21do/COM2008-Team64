import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import sheffield.DatabaseConnectionHandler;

public class DatabaseOperations {
    // add operations to select, insert edit ect tables in the database
    // see lab 3 solutions for inspiration
    public void operation(){
        System.out.println("database ;operations class");
    }

    // creates a user object from its id
    public static User getUser(int id, Connection con) throws SQLException{
        try {
            // execute query
            String sqlString = "SELECT Email, PasswordHash, Forename, Surname FROM Users WHERE UserID = ?";
            PreparedStatement statement = con.prepareStatement(sqlString);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            System.out.println("get user query executed");

            // initialise variables for constucter
            String email = "";
            char[] passHash = new char[0];
            String forename = "";
            String surname = "";

            // get values for constructor
            while (resultSet.next()) {
                email = resultSet.getString("Email");
                passHash = resultSet.getString("PasswordHash").toCharArray();
                forename = resultSet.getString("Forename");
                surname = resultSet.getString("Surname");
            }

            User user = new User(id, email, passHash, forename, surname);
            return user;

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;// Re-throw the exception to signal an error.
        }
    }


    // insert a new user into the database from a user object
    // sets isStaff and is isManager to 0 by default
    public static void insertUser(User user, Connection con) throws SQLException{
        try {
            String sqlString = "INSERT INTO Users (UserID, Email, PasswordHash, Forename, Surname) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(sqlString);

            // format attributes into correct data types
            char[] passwordHashChars = user.getPasswordHash();//convert password has from char list to string
            String passwordHash = "";
            for (char c:passwordHashChars) {
                passwordHash = passwordHash + Character.toString(c);
            }

            String forename = user.getPersonalRecord().getforname();
            String surname = user.getPersonalRecord().getsurname();

            // insert attributes into statement
            statement.setInt(1, user.getUserID());
            statement.setString(2, user.getEmail());
            statement.setString(3, passwordHash);
            statement.setString(4, forename);
            statement.setString(5, surname);

            int rowsUpdated = statement.executeUpdate();
            System.out.println("insert user");

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;// Re-throw the exception to signal an error.
        }
    }

    // from lab 5
    public String verifyLogin(Connection connection, String username, char[] enteredPassword) {
        // TODO : Implement this method.
        return null;
    }

}
