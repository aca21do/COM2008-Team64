public class PaymentMethod{
    private String cardName;
    private String cardholderName;
    private int cardNumber;
    private char[] expiryDate;
    private int securityCode;

    PaymentMethod(String cardName, String holderName, int cardNo, char[] exDate, int secCode){
        this.cardName = cardName;
        this.cardholderName = holderName;
        this.cardNumber = cardNo;
        this.expiryDate = exDate;
        this.securityCode = secCode;
        System.out.println("created pay meth");
    }

    public int getCardNumber(){
        return this.cardNumber;
    }
    public String getCardName() {return this.cardName;}
    public String getCardholderName() {return this.cardholderName;}
    public char[] getExpiryDate() {return this.expiryDate;}

    public int getSecurityCode() {return securityCode;}
}