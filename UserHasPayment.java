public class UserHasPayment {

    private String userID;
    private int cardNo;

    private User user;
    private PaymentMethod paymentMethod;

    public UserHasPayment(User user, PaymentMethod method){
        this.userID = user.getUserID();
        this.cardNo = method.getCardNumber();
        this.user = user;
        this.paymentMethod = method;
        user.setHasPayment(this);
    }

    public PaymentMethod getPaymentMethod(){
        return this.paymentMethod;
    }
    public User getUser(){
        return user;
    }
}

