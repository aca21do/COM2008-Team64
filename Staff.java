public class Staff extends User {
    public Staff(String id, String email, String fname, String sname) {
        super(id, email, fname, sname);
        this.isStaff = true; 
    }
}
