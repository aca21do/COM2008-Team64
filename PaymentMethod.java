public class PaymentMethod{
    private String cardName;
    private String cardholderName;
    private int cardNumber;
    private String expiryDate;
    private int securityCode;

    PaymentMethod(String cardName, String holderName, int cardNo, String exDate, int secCode){
        this.cardName = cardName;
        this.cardholderName = holderName;
        this.cardNumber = cardNo;
        this.expiryDate = exDate;
        this.securityCode = secCode;
    }
}