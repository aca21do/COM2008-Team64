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
    private int userID;

    private String email;
    //private //password hash (list of chars?)
    private PersonalRecord personalRecord;
    private Address address;
    private boolean isStaff;
    private boolean isManager;
    private UserHasPayment hasPayment;

    private char[] passwordHash;

    public User(int id, String email, char[] passHash, String forename, String surname){
        this.userID = id;
        this.email = email;
        this.passwordHash = passHash;
        this.personalRecord = new PersonalRecord(forename, surname);
        this.isStaff= false;
        this.isManager = false;
    }

    public int getUserID() {
        return userID;
    }
    public String getEmail() {
        return email;
    }
    public char[] getPasswordHash() {// char array is used instead of string as it is more secure
        return passwordHash;
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
    public UserHasPayment getHasPayment() {return this.hasPayment; }
    public void setHasPayment(UserHasPayment newHasPayment){
        this.hasPayment = newHasPayment;
    }





}
