public class CurrentUser {
    // stores a single user, which is logged in
    private static User currentUser;

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
    public static void logout(){ currentUser = null;}
    public static boolean isLoggedIn(){
        return currentUser != null;
    }
}