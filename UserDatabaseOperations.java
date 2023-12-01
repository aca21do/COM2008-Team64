import com.mysql.cj.util.StringUtils;

import java.io.CharArrayReader;
import java.io.Reader;
import java.sql.*;
import java.util.Random;

public class UserDatabaseOperations {
    // add operations to select, insert edit ect tables in the database
    // see lab 3 solutions for inspiration
    public void operation(){
        System.out.println("database ;operations class");
    }

    //------------------------------------------Users-------------------------------------
    // TODO better exception handling
    // insert a new user into the database from a user object
    // sets isStaff and is isManager to 0 by default

    /**
     * takes a user object and adds it to the database. Also adds the user's address to the database,
     * and updates the HasAddress table.
     * @param user the user to add
     * @param con the database connection
     * @throws SQLException
     */
    public void insertUser(User user, Connection con) throws SQLException{
        try {
            String sqlString = "INSERT INTO Users (UserID, Email, Forename, Surname) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(sqlString);

            // format attributes into correct data types
            char[] passwordHashChars = user.getPasswordHash(con, this);//convert password has from char list to string
            String passwordHash = new String(passwordHashChars);

            // insert attributes into statement
            statement.setString(1, user.getUserID());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getForename());
            statement.setString(4, user.getSurname());

            statement.executeUpdate();
            System.out.println("insert user");
            insertAddress(user, con);
        }
        catch(SQLException exception){
            if (exception.getMessage().substring(0,9).equals("Duplicate")){
                throw new SQLException("Email already taken");
            } else {
                throw new SQLException("error saving user");
            }
        }
    }

    public void insertAddress(User user, Connection con) throws SQLException{
        Address usersAddress = user.getAddress();
        String addressSqlString = "INSERT INTO Addresses (HouseNumber, RoadName, CityName, PostCode) VALUES (?, ?, ?, ?)";
        PreparedStatement addressStatement = con.prepareStatement(addressSqlString);

        // insert attributes into statement
        addressStatement.setInt(1, usersAddress.getHouseNumber());
        addressStatement.setString(2, usersAddress.getRoadName());
        addressStatement.setString(3, usersAddress.getCityName());
        addressStatement.setString(4, usersAddress.getPostcode());
        System.out.println("insert address");

        addressStatement.executeUpdate();

        // insert has address
        String hasAddressString = "INSERT INTO HasAddress (UserID, HouseNumber, Postcode) VALUES (?, ?, ?)";
        PreparedStatement hasAddressStatement = con.prepareStatement(hasAddressString);
        hasAddressStatement.setString(1, user.getUserID());
        hasAddressStatement.setInt(2, usersAddress.getHouseNumber());
        hasAddressStatement.setString(3, usersAddress.getPostcode());
        hasAddressStatement.executeUpdate();
    }


    // creates a user object from its id
    /**
     * Finds the user with the parameter userID, and returns a user object.
     * Not null attributes: UserID, Email, isStaff & isManager are always set.
     * nullable attributes: forename & surname are set if they are not null in the database.
     * @param id the user's ID (primary key)
     * @param con the connection to the database
     * @return the user from the database, with the matching ID primary key
     * @throws SQLException
     */
    public User getUserFromID(String id, Connection con) throws SQLException{
        try {
            // execute query
            String sqlString = "SELECT * FROM Users WHERE UserID = ?";
            PreparedStatement statement = con.prepareStatement(sqlString);
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            System.out.println("get user query executed");

            // get values for constructor
            if (resultSet.next()) {
                // get not nullable fields and create user object
                String email = resultSet.getString("Email");
                boolean isStaff = resultSet.getBoolean("isStaff");
                boolean isManager = resultSet.getBoolean("isManager");
                String forename = resultSet.getString("Forename");
                String surname = resultSet.getString("Surname");
                User user = new User(id, email, forename, surname);

                loadAddress(user, con);
                return user;
            } else{
                throw new SQLException("User not found in database");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("User not found in database");// Re-throw the exception to signal an error.
        }
    }

    public void loadAddress(User user, Connection con) throws SQLException{
        // find HasAddress with matching userID
        String findHasAddressSql = "SELECT HouseNumber, PostCode FROM HasAddress WHERE UserID = ?";
        PreparedStatement hasAddressStatement = con.prepareStatement(findHasAddressSql);
        hasAddressStatement.setString(1, user.getUserID());
        ResultSet resultSet = hasAddressStatement.executeQuery();

        if (resultSet.next()) {
            int houseNo = resultSet.getInt("HouseNumber");
            String postCode = resultSet.getString("PostCode");

            // get address with matching primary key
            String getAddressSql = "SELECT * FROM Addresses WHERE HouseNumber = ? AND PostCode = ?";
            PreparedStatement getAddressStatement = con.prepareStatement(getAddressSql);
            getAddressStatement.setInt(1, houseNo);
            getAddressStatement.setString(2, postCode);
            ResultSet resultSet1 = getAddressStatement.executeQuery();
            if (resultSet1.next()){
                String roadName = resultSet1.getString("RoadName");
                String cityName = resultSet1.getString("CityName");

                // create and set user address
                Address loadedAddress = new Address(houseNo, roadName, cityName, postCode);
                user.setAddress(loadedAddress);
            }
        }
    }


    /**
     * Finds the user with the matching email parameter, and returns a user object
     * (by finding the user and calling getUserFromID).
     * Not null attributes: UserID, Email, isStaff & isManager are always set.
     * nullable attributes: forename & surname are set if they are not null in the database.
     * @param email
     * @param con
     * @return the user from the database, with the matching unique email.
     * @throws SQLException
     */
    public User getUserFromEmail(String email, Connection con) throws SQLException{
        try {
            // execute query to find the id of the user with that email
            User user = null;
            String sqlString = "SELECT UserID FROM Users WHERE Email = ?";
            PreparedStatement statement = con.prepareStatement(sqlString);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            // if user found, create a user object from its id
            if (resultSet.next()) {
                String id = resultSet.getString("UserID");
                user = this.getUserFromID(id, con);
            }

            return user;// will return null if no user exists

        } catch (SQLException e) {
            throw new SQLException("User not found in database");// Re-throw the exception to signal an error.
        }
    }

    /**
     * takes a local user and checks if any attributes are different to those stored in the database.
     * Does NOT update PasswordHash, PasswordSalt, isBlocked or login attempts. For security reasons,
     * these can only be updated by the login or register systems.
     * @param updatedUser
     * @param con the database connection
     * @throws SQLException
     */
    public int updateUser(User updatedUser, Connection con) throws SQLException{
        int attributesUpdated = 0;
        int addressUpdated = 0;
        try {
            User storedUser = getUserFromID(updatedUser.getUserID(), con);
            String id = storedUser.getUserID();

            // update user attributes if different to what is stored in database
            if (! storedUser.getEmail().equals(updatedUser.getEmail()) ) {
                attributesUpdated += updateUserAttribute("Email", updatedUser.getEmail(), id, con);
            }
            if (! storedUser.getForename().equals(updatedUser.getForename()) ) {
                attributesUpdated += updateUserAttribute("Forename", updatedUser.getForename(), id, con);
            }
            if (! storedUser.getSurname().equals(updatedUser.getSurname()) ){
                attributesUpdated += updateUserAttribute("Surname", updatedUser.getSurname(), id, con);
            }
            if (! storedUser.getIsStaff() == updatedUser.getIsStaff()){
                attributesUpdated += updateUserAttribute("isStaff", updatedUser.getIsStaff(), id, con);
            }
            if (! storedUser.getIsManager() == updatedUser.getIsManager()) {
                attributesUpdated += updateUserAttribute("isManager", updatedUser.getIsManager(), id, con);
            }

            // -------------update address---------------
            try {
                Address updatedAddress = updatedUser.getAddress();
                Address storedAddress = storedUser.getAddress();

                // if address values not entered, set them to the stored values
                if (updatedAddress.getHouseNumber() == -1) {
                    updatedAddress.setHouseNumber(storedAddress.getHouseNumber());
                    System.out.println("house no. null");
                }
                if (updatedAddress.getPostcode() == null) {
                    updatedAddress.setPostcode(storedAddress.getPostcode());
                    System.out.println("postcode null");
                }
                if (updatedAddress.getRoadName() == null) {
                    updatedAddress.setRoadName(storedAddress.getRoadName());
                    System.out.println("address null");
                }
                if (updatedAddress.getCityName() == null) {
                    updatedAddress.setCityName(storedAddress.getCityName());
                    System.out.println("city null");
                }

                // if the address has been updated
                if (! (storedAddress.toString().equals(updatedAddress.toString()))) {
                    String checkAddressSql = "SELECT * FROM Addresses WHERE HouseNumber = ? AND Postcode = ?";
                    PreparedStatement checkAddressStatement = con.prepareStatement(checkAddressSql);
                    checkAddressStatement.setInt(1, updatedAddress.getHouseNumber());
                    checkAddressStatement.setString(2, updatedAddress.getPostcode());
                    ResultSet checkAddressResults = checkAddressStatement.executeQuery();

                    // if the new address already exists, update just the linker table
                    if (checkAddressResults.next()) {
                        // insert new address linker
                        String hasAddressString = "INSERT INTO HasAddress (UserID, HouseNumber, Postcode) VALUES (?, ?, ?)";
                        PreparedStatement hasAddressStatement = con.prepareStatement(hasAddressString);
                        hasAddressStatement.setString(1, id);
                        hasAddressStatement.setInt(2, updatedAddress.getHouseNumber());
                        hasAddressStatement.setString(3, updatedAddress.getPostcode());
                        addressUpdated += hasAddressStatement.executeUpdate();
                    }
                    // if the address does not already exist, insert address and add to linker
                    else {
                        insertAddress(updatedUser, con);
                        addressUpdated += 1;
                    }
                    // delete old address linker
                    String removedHasAddressSQL =
                            "DELETE FROM HasAddress WHERE UserID = ? AND HouseNumber = ? AND Postcode = ?";
                    PreparedStatement removeHasAddress = con.prepareStatement(removedHasAddressSQL);
                    removeHasAddress.setString(1, id);
                    removeHasAddress.setInt(2, storedAddress.getHouseNumber());
                    removeHasAddress.setString(3, storedAddress.getPostcode());
                    attributesUpdated += removeHasAddress.executeUpdate();
                }
            }
            catch (SQLException addressException){
                if (addressException.getMessage().substring(0,9).equals("Duplicate")){
                    if (attributesUpdated == 0) {
                        throw new SQLException("Address already updated");
                    }
                } else {
                    throw new SQLException("error updating Address");
                }
            }
        }
        catch(SQLException exception){
            // if exception created manually, throw it, else set generic error message
            if (exception.getMessage().equals("error updating Address") ||
                    exception.getMessage().equals("Address already updated")){
                throw exception;
            } else {
                throw new SQLException("error updating user");
            }
        }
        finally {
            System.out.println("Updated " +attributesUpdated +" attributes and " +addressUpdated +" address records");
            return attributesUpdated+addressUpdated;
        }
    }



    private int updateUserAttribute(String attribute, String value, String userID, Connection con)
    throws SQLException{
        try {
            String updateUserAttributeString = "UPDATE Users SET " + attribute + "=? WHERE UserID = ?";
            PreparedStatement statement = con.prepareStatement(updateUserAttributeString);
            statement.setString(1, value);
            statement.setString(2, userID);
            System.out.println(String.valueOf(statement));
            System.out.println(attribute + " updated to " + value);
            return statement.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new SQLException(("problem updating "+ attribute));
        }
    }
    private int updateUserAttribute(String attribute, boolean value, String userID, Connection con)
            throws SQLException{
        try {
            String updateUserAttributeString = "UPDATE Users SET " + attribute + " = ? WHERE UserID = ?";
            PreparedStatement statement = con.prepareStatement(updateUserAttributeString);
            statement.setBoolean(1, value);
            statement.setString(2, userID);
            System.out.println(attribute + " updated to " + String.valueOf(value));
            return statement.executeUpdate();
        }
        catch(SQLException e){
            throw new SQLException(("problem updating "+ attribute));
        }
    }


    // ------------------- login/security------------
    public boolean userIsBlocked(User user, Connection con) throws SQLException {
        try {
            String isBlockedString = "SELECT isBlocked FROM Users WHERE UserID = ?";
            PreparedStatement isBlockedStatement = con.prepareStatement(isBlockedString);
            isBlockedStatement.setString(1, user.getUserID());
            ResultSet isBlockedResultSet = isBlockedStatement.executeQuery();

            boolean isBlocked = true;
            if (isBlockedResultSet.next()) {
                isBlocked = isBlockedResultSet.getBoolean("isBlocked");
            }

            return isBlocked;
        }
        catch (SQLException e){
            throw new SQLException("couldn't verify user");
        }
    }


    public char[] getUserPassHash(User user, Connection con) throws SQLException {
        try {
            String getHashString = "SELECT PasswordHash FROM Users WHERE UserID = ?";
            PreparedStatement getHashStatement = con.prepareStatement(getHashString);
            getHashStatement.setString(1, user.getUserID());
            ResultSet hashResultSet = getHashStatement.executeQuery();

            char[] hash;
            if (hashResultSet.next()) {
                hash = hashResultSet.getString("PasswordHash").toCharArray();
            } else {
                hash = new char[0];
            }
            return hash;
        }
        catch (SQLException e){
            throw new SQLException("couldn't check password");
        }

    }

    public char[] getUserPassSalt(User user, Connection con) throws SQLException {
        try {
            String getSaltString = "SELECT PasswordSalt FROM Users WHERE UserID = ?";
            PreparedStatement getSaltStatement = con.prepareStatement(getSaltString);
            getSaltStatement.setString(1, user.getUserID());
            ResultSet saltResultSet = getSaltStatement.executeQuery();

            char[] salt;
            if (saltResultSet.next()) {
                salt = saltResultSet.getString("PasswordSalt").toCharArray();
            } else {
                salt = new char[0];
            }
            return salt;
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new SQLException("couldn't check password");
        }
    }

    public void resetLoginAttempts(User user, Connection con) throws SQLException{
        try {
            String resetAttemptsStr = "UPDATE Users Set loginAttempts = ? WHERE UserID = ?";
            PreparedStatement resetAttemptsStatement = con.prepareStatement(resetAttemptsStr);
            resetAttemptsStatement.setInt(1, 0);
            resetAttemptsStatement.setString(2, user.getUserID());
            resetAttemptsStatement.execute();
        }
        catch (SQLException e){
            throw new SQLException("couldn't reset login attempts");
        }
    }

    public int incrementLoginAttempts(User user, Connection con) throws SQLException{
        try {
            int attempts;
            String getAttemptsStr = "Select loginAttempts FROM Users WHERE UserID = ?";
            PreparedStatement getAttemptsStatement = con.prepareStatement(getAttemptsStr);
            getAttemptsStatement.setString(1, user.getUserID());
            ResultSet getAttemptsResult = getAttemptsStatement.executeQuery();

            if (getAttemptsResult.next()) {
                attempts = getAttemptsResult.getInt(1);
                attempts += 1;
            } else {
                throw new SQLException("couldn't find user");
            }

            //update login attempts
            String resetAttemptsStr = "UPDATE Users Set loginAttempts = ? WHERE UserID = ?";
            PreparedStatement resetAttemptsStatement = con.prepareStatement(resetAttemptsStr);
            resetAttemptsStatement.setInt(1, attempts);
            resetAttemptsStatement.setString(2, user.getUserID());
            resetAttemptsStatement.execute();

            //update isblocked
            if (attempts >= 3){
                String setBlockedStr = "UPDATE Users Set isBlocked = 1 WHERE UserID = ?";
                PreparedStatement setBlockedState = con.prepareStatement(setBlockedStr);
                setBlockedState.setString(1, user.getUserID());
                setBlockedState.execute();
            }

            return attempts;
        }
        catch (SQLException e){
           throw new SQLException("couldn't increment login attempts");
        }
    }

    // set password take a char array and produces a random
    /**
     * Set Password hashes a char array and sets the salt and hash value in the database.
     * @param user the user whose password is being set
     * @param newPass the password to be set as a char array
     */
    public void setPassword(User user, char[] newPass, Connection con) throws SQLException{
        //set random salt
        Random random = new Random();
        random.nextInt();
        char[] salt = new char[16];
        for (int i=0; i<16 ; i++){
            int randInt = random.nextInt(32,126);
            salt[i] = (char) randInt;
        }
        //hash password
        HashedPasswordGenerator hashGen = new HashedPasswordGenerator(salt);
        String hashedPassword = hashGen.hashPassword(newPass);

        // store in db
        try {
            String setPassStr = "UPDATE Users SET PasswordHash = ?, PasswordSalt = ? WHERE UserID = ?";
            PreparedStatement setPassStatement = con.prepareStatement(setPassStr);
            setPassStatement.setString(1, hashedPassword);
            setPassStatement.setString(2, String.valueOf(salt));
            setPassStatement.setString(3, user.getUserID());
            setPassStatement.execute();
        }
        catch (SQLException e){
            throw new SQLException("couldn't set password");
        }

    }

    //-----------------------------payment methods/bank cards---------------------------
    public void getPaymentMethod(User user, Connection con) throws SQLException{
        try {
            // execute query
            System.out.println("database get payment method");
            String hasPaymentSqlString = "SELECT UserID, CardNumber FROM HasPayment WHERE UserID = ?";
            PreparedStatement statement = con.prepareStatement(hasPaymentSqlString);
            statement.setString(1, user.getUserID());
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

    public int insertPaymentMethod(UserHasPayment hasPayment, Connection con) throws SQLException {
        try {
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
            hasPStatement.setString(2, hasPayment.getUser().getUserID());

            int cardRowsUpdated = cardStatement.executeUpdate();
            int hasPayMethRowsUpdated = hasPStatement.executeUpdate();
            System.out.println("HasPaymentMethod inserted rows: " + String.valueOf(hasPayMethRowsUpdated));
            System.out.println("PaymentCard inserted rows : " + cardRowsUpdated);
            return cardRowsUpdated + hasPayMethRowsUpdated;
        }
        catch(SQLException e){
            throw new SQLException("Error adding payment method");
        }
    }

    /**
     * Deletes a user's payment method (Ass
     * @param user
     * @param con
     * @throws SQLException
     */
    public int deletePaymentMethod(User user, Connection con) throws SQLException{
        // find the current payment method
        getPaymentMethod(user, con);

        // delete the current payment method
        String delHasPaySQL = "DELETE FROM HasPayment WHERE UserID = ?";
        PreparedStatement delHasPayState = con.prepareStatement(delHasPaySQL);
        delHasPayState.setString(1, user.getUserID());
        delHasPayState.executeUpdate();

        //delete current payment card from linker table
        String delCardSQL = "DELETE FROM BankCards WHERE CardNumber = ?";
        PreparedStatement delCardState = con.prepareStatement(delCardSQL);
        if (user.getHasPayment() != null) {
            delCardState.setString(1, user.getHasPayment().getPaymentMethod().getCardName());
            return delCardState.executeUpdate();
        } else {
            return 0;
        }
    }

    //------------------------------login------------------------
    // from lab 5
    public String verifyLogin(Connection con, String email, char[] enteredPassword) {
        // TODO : Implement this method.
        String errorMessage = "unable to verify user";
        try{
            User user = this.getUserFromEmail(email, con);
            if (user != null) {
                if (!user.getIsBlocked(this, con)){
                    if (this.verifyPassword(con, enteredPassword, user)){
                        errorMessage = "success";
                    }
                    else{
                        errorMessage = "wrong password";
                    }
                }
                else{
                    errorMessage = "User is blocked";
                }
            }
            else{
                errorMessage = "couldn't find user";
            }
            System.out.println(errorMessage);
        }

        catch (SQLException e){
            errorMessage = e.getMessage();
            System.out.println(errorMessage);
        }

        return errorMessage;
    }

    public boolean verifyPassword(Connection con, char[] enteredPassword, User user){
        try {
            System.out.println("verifying password");
            // get entered and stored passwords/hashes as char arrays
            char[] storedPasswordHashChars = user.getPasswordHash(con, this);
            char[] salt = user.getPasswordSalt(con, this);

            //hash entered password
            HashedPasswordGenerator hashedPasswordGenerator = new HashedPasswordGenerator(salt);

            // convert char arrays to strings
            String enteredPasswordHash = hashedPasswordGenerator.hashPassword(enteredPassword);
            String storedPasswordHash = String.valueOf(storedPasswordHashChars);

            boolean passwordsMatch = storedPasswordHash.equals(enteredPasswordHash);
            if (passwordsMatch){
                resetLoginAttempts(user, con);
            }
            else{
                incrementLoginAttempts(user, con);
            }

            return passwordsMatch;
        }
        catch (SQLException e){
            return false;
        }
    }

}