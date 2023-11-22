import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.Stream;

public class DatabaseOperations {
    // add operations to select, insert edit ect tables in the database
    // see lab 3 solutions for inspiration
    public void operation(){
        System.out.println("database ;operations class");
    }

    // creates a user object from its id
    public User getUserFromID(int id, Connection con) throws SQLException{
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
            if (resultSet.next()) {
                email = resultSet.getString("Email");
                passHash = resultSet.getString("PasswordHash").toCharArray();
                forename = resultSet.getString("Forename");
                surname = resultSet.getString("Surname");
                User user = new User(id, email, passHash, forename, surname);
                return user;
            } else{
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;// Re-throw the exception to signal an error.
        }
    }


    // insert a new user into the database from a user object
    // sets isStaff and is isManager to 0 by default
    public void insertUser(User user, Connection con) throws SQLException{
        try {
            String sqlString = "INSERT INTO Users (UserID, Email, PasswordHash, Forename, Surname) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(sqlString);

            // format attributes into correct data types
            char[] passwordHashChars = user.getPasswordHash();//convert password has from char list to string
            String passwordHash = "";
            for (char c:passwordHashChars) {
                passwordHash = passwordHash + Character.toString(c);
            }

            String forename = user.getPersonalRecord().getForename();
            String surname = user.getPersonalRecord().getSurname();

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

    public void getPaymentMethod(User user, Connection con) throws SQLException{
        try {
            // execute query
            System.out.println("database get payment method");
            String hasPaymentSqlString = "SELECT UserID, CardNumber FROM HasPayment WHERE UserID = ?";
            PreparedStatement statement = con.prepareStatement(hasPaymentSqlString);
            statement.setInt(1, user.getUserID());
            ResultSet resultSet = statement.executeQuery();

            // initialise variables for constructor
            int cardNo = 0;

            // if a payment method linker is found, create instances of the card and linker class
            if (resultSet.next()) {
                cardNo = resultSet.getInt("CardNumber");

                // get the users payment method from database
                String getCardSqlString = "SELECT * FROM BankCards WHERE CardNumber = ?";
                PreparedStatement getCardStatement = con.prepareStatement(getCardSqlString);
                getCardStatement.setInt(1, cardNo);
                ResultSet usersPaymentMethod = getCardStatement.executeQuery();

                //initialise payment method object
                PaymentMethod paymentMethod;
                UserHasPayment userHasPayment;

                if (usersPaymentMethod.next()){
                    // get payment method variables
                    String cardName = usersPaymentMethod.getString("BankCardName");
                    String holderName = usersPaymentMethod.getString("CardHolderName");
                    Reader expiryReader = usersPaymentMethod.getCharacterStream("ExpiryDate");
                    char[] expiryDate = new char[5];
                    expiryDate = expiryReader.toString().toCharArray();
                    int securityCode = usersPaymentMethod.getInt("SecurityCode");// this needs changing, not secure, this is just a placeholder to get the method working

                    // create classes
                    paymentMethod = new PaymentMethod(cardName, holderName, cardNo, expiryDate, securityCode);
                    userHasPayment = new UserHasPayment(user, paymentMethod);
                    user.setHasPayment(userHasPayment);
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
            throw e;// Re-throw the exception to signal an error.
        }
    }

    public void insertPaymentMethod(UserHasPayment hasPayment, Connection con) throws SQLException {
        String insPayMeth = "INSERT INTO BankCards (CardNumber, BankCardName, CardHolderName, ExpiryDate, SecurityCode) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement cardStatement = con.prepareStatement(insPayMeth);


        PaymentMethod card = hasPayment.getPaymentMethod();
        CharArrayReader expiryReader = new CharArrayReader(card.getExpiryDate());

        cardStatement.setInt(1, card.getCardNumber());
        cardStatement.setString(2, card.getCardName());
        cardStatement.setString(3, card.getCardholderName());
        cardStatement.setCharacterStream(4, expiryReader);
        cardStatement.setInt(5, card.getSecurityCode());

        String insHasPayment = "INSERT INTO HasPayment (CardNumber, UserID) VALUES (?, ?)";
        PreparedStatement hasPStatement = con.prepareStatement(insHasPayment);

        hasPStatement.setInt(1, card.getCardNumber());
        hasPStatement.setInt(2, hasPayment.getUser().getUserID());

        int cardRowsUpdated = cardStatement.executeUpdate();
        int hasPayMethRowsUpdated = hasPStatement.executeUpdate();
        System.out.println("HasPaymentMethod inserted rows: " + String.valueOf(hasPayMethRowsUpdated));
        System.out.println("PaymentCard inserted rows : " + cardRowsUpdated);
    }

}
