public class Staff extends User {
    public Staff(String id, String email, char[] passHash, String salt) {
        super(id, email, passHash, salt);
        this.isStaff = true; 
    }
}
