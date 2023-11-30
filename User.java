import java.sql.Connection;
import java.sql.SQLException;

public class User {



    private static class PersonalRecord{
        private String forename;
        private String surname;
        PersonalRecord(String fName, String sName){
            this.forename = fName;
            this.surname = sName;
        }
        public String getForename(){
            return this.forename;
        }
        public String getSurname(){
            return this.surname;
        }
    }

    // ----------------- DECLARE ATTRIBUTES --------------
    private String userID;
    private String email;
    //private //password hash (list of chars?)
    private PersonalRecord personalRecord;
    private Address address;
    private boolean isStaff;
    private boolean isManager;
    private UserHasPayment hasPayment;
    private boolean isBlocked;

    private char[] passwordHash;
    private String passwordSalt;


    //-------------------- CONSTRUCTOR METHODS ----------------

    //constructor does not include isBlocked, passHash or salt, as this would be insecure
    //they each have get methods which interact with the database, so they are only fetched when needed
    public User(String id, String email, String forename, String surname){
        this.userID = id;
        this.email = email;
        this.isStaff= false;
        this.isManager = false;
        this.personalRecord = new PersonalRecord(forename, surname);
    }





    // ----------------------------- GETTER METHODS ---------------------
    public String getUserID() {
        return userID;
    }
    public String getEmail() {
        return email;
    }
    public char[] getPasswordHash(Connection con, UserDatabaseOperations dbOps) throws SQLException {// char array is used instead of string as it is more secure
        return dbOps.getUserPassHash(this, con);
    }
    public char[] getPasswordSalt(Connection con, UserDatabaseOperations dbOps) throws SQLException {// char array is used instead of string as it is more secure
        return dbOps.getUserPassSalt(this, con);
    }
    public PersonalRecord getPersonalRecord() {
        return personalRecord;
    }
    public Address getAddress(){return this.address; }
    public String getForename(){
        return this.personalRecord.forename;
    }
    public String getSurname(){return this.personalRecord.surname;}
    public boolean getIsStaff(){
        return this.isStaff;
    }
    public boolean getIsManager(){
        return this.isManager;
    }
    public UserHasPayment getHasPayment() {return this.hasPayment; }
    public boolean getIsBlocked(UserDatabaseOperations userDatabaseOperations, Connection con) throws SQLException{
        return userDatabaseOperations.userIsBlocked(this, con);
    }





    // --------------------- SETTER METHODS ----------------
    public void setForename(String foreName){
        this.personalRecord.forename = foreName;
    }
    public void setSurname(String surname){
        this.personalRecord.surname = surname;
    }

    public void setAddress(Address newAddress){
        this.address = newAddress;
    }

    public void setHasPayment(UserHasPayment newHasPayment){
        this.hasPayment = newHasPayment;
    }
    public void resetLoginAttempts(Connection con, UserDatabaseOperations dbOps) throws SQLException{

    }
    public void setPassword (char[] newPass, Connection con, UserDatabaseOperations dbOps) throws SQLException{
        dbOps.setPassword(this, newPass, con);
    }






}
