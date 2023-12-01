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
        return getBasket();
    }
    public static void setBasket(PendingOrder newBasket) {
        basket = newBasket;
    }
}