import java.sql.Connection;
import java.sql.SQLException;

public class User {



    public static class PersonalRecord{
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

    //constructor does not include isBlocked, passHash or salt, as this would be insecure
    //they each have get methods which interact with the database, so they are only fetched when needed
    public User(String id, String email){
        this.userID = id;
        this.email = email;
        this.isStaff= false;
        this.isManager = false;
    }
    public User(String id, String email, char[] passHash, String salt){
        this.userID = id;
        this.email = email;
        this.isStaff= false;
        this.isManager = false;
        this.passwordHash = passHash;
        this.passwordSalt = salt;
    }

    public String getUserID() {
        return userID;
    }
    public String getEmail() {
        return email;
    }
    public char[] getPasswordHash(Connection con, DatabaseOperations dbOps) throws SQLException {// char array is used instead of string as it is more secure
        return dbOps.getUserPassHash(this, con);
    }
    public char[] getPasswordSalt(Connection con, DatabaseOperations dbOps) throws SQLException {// char array is used instead of string as it is more secure
        return dbOps.getUserPassSalt(this, con);
    }
    public PersonalRecord getPersonalRecord() {
        return personalRecord;
    }


    public boolean getIsStaff(){
        return this.isStaff;
    }
    public boolean getIsManager(){
        return this.isManager;
    }
    public boolean getIsBlocked(DatabaseOperations databaseOperations, Connection con) throws SQLException{
        return databaseOperations.userIsBlocked(this, con);
    }
    public UserHasPayment getHasPayment() {return this.hasPayment; }
    public void setHasPayment(UserHasPayment newHasPayment){
        this.hasPayment = newHasPayment;
    }
    public void resetLoginAttempts(Connection con, DatabaseOperations dbOps) throws SQLException{

    }
    public void setPassword (char[] newPass, Connection con, DatabaseOperations dbOps) throws SQLException{
        dbOps.setPassword(this, newPass, con);
    }





}
