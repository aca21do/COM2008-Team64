public class UserHasPayment {

    private int userID;
    private int cardNo;

    private User user;
    private PaymentMethod paymentMethod;

    public UserHasPayment(User user, PaymentMethod method){
        this.userID = user.getUserID();
        this.cardNo = method.getCardNumber();
        this.user = user;
        this.paymentMethod = method;
        user.setHasPayment(this);
        System.out.println("Created UserHasPayment");
    }

    public PaymentMethod getPaymentMethod(){
        return this.paymentMethod;
    }
    public User getUser(){
        return user;
    }
}

