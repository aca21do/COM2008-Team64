import java.sql.Connection;
import java.sql.SQLException;

public class Manager extends Staff {
    public Manager(String id, String email, String fname, String sname) {
        super(id, email, fname, sname);
        this.isManager = true;
    }

    // Creates a new Staff object with the same parameters as the user being promoted
    // Calls a function to update the info in the database
    public Staff promoteToStaff(User user, Connection con, UserDatabaseOperations dbOps) throws SQLException {
        Staff staff = new Staff(user.getUserID(), user.getEmail(), user.getForename(), user.getSurname());
        dbOps.updateUser(staff, con);
        return staff;
    }

    // Creates a new User object with the same parameters as the staff being demoted
    // Also calls a function to update the info in the database
    public User demoteToUser(Staff staff, Connection con, UserDatabaseOperations dbOps) throws SQLException {
        User user = new User(staff.getUserID(), staff.getEmail(), staff.getForename(), staff.getSurname());
        dbOps.updateUser(user, con);
        return user;
    }
}