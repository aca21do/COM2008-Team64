import java.sql.Connection;

public class CurrentUser {
    // stores a single user, which is logged in
    private static User currentUser;
    private static PendingOrder basket;

    // --------CURRENT USER-------
    // constructor
    private CurrentUser() {
        // can't be instantiated
    }

    public static User getCurrentUser() {
        return currentUser;
    }
    public static void setCurrentUser(User user) {
        currentUser = user;
    }
    public static void logout(){
        currentUser = null;
        basket = null;
    }
    public static boolean isLoggedIn(){
        return currentUser != null;
    }

    // -------basket------

    public static PendingOrder getBasket() {
        return basket;
    }
    public static void setBasket(PendingOrder newBasket) {
        basket = newBasket;
    }
    public static void resetBasket(Connection con){
        PendingOrder.getNewPendingOrder(CurrentUser.getCurrentUser(), con);
        CurrentUser.updateBasketFromDB(new UserDatabaseOperations(), con);
    }
    public static void updateBasketFromDB(UserDatabaseOperations userDBops, Connection con){
        CurrentUser.setBasket( userDBops.getUsersPendingOrder(CurrentUser.getCurrentUser(), con ) );
    }
}