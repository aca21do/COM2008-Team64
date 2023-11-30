import java.sql.Connection;
import java.sql.SQLException;

public class Manager extends Staff {
    public Manager(String id, String email, char[] passHash, String salt) {
        super(id, email, passHash, salt);
        this.isManager = true;
    }

    public Staff promoteToStaff(User user, Connection con, DatabaseOperations dbOps) throws SQLException {
        char[] userSalt = user.getPasswordSalt(con, dbOps);
        String saltString = new String(userSalt);

        Staff staff = new Staff(user.getUserID(), user.getEmail(), user.getPasswordHash(con, dbOps), saltString);
        return staff;
    }

    public User demoteToUser(Staff staff, Connection con, DatabaseOperations dbOps) throws SQLException {
        char[] staffSalt = staff.getPasswordSalt(con, dbOps);
        String saltString = new String(staffSalt);

        User user = new User(staff.getUserID(), staff.getEmail(), staff.getPasswordHash(con, dbOps), saltString);
        return user;
    }
}