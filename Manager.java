import java.sql.Connection;
import java.sql.SQLException;

public class Manager extends Staff {
    public Manager(String id, String email, String fname, String sname) {
        super(id, email, fname, sname);
        this.isManager = true;
    }

    public Staff promoteToStaff(User user, Connection con, UserDatabaseOperations dbOps) throws SQLException {
        char[] userSalt = user.getPasswordSalt(con, dbOps);
        String saltString = new String(userSalt);

        Staff staff = new Staff(user.getUserID(), user.getEmail(), user.getForename(), user.getSurname());
        return staff;
    }

    public User demoteToUser(Staff staff, Connection con, UserDatabaseOperations dbOps) throws SQLException {
        char[] staffSalt = staff.getPasswordSalt(con, dbOps);
        String saltString = new String(staffSalt);

        User user = new User(staff.getUserID(), staff.getEmail(), staff.getForename(), staff.getSurname());
        return user;
    }
}