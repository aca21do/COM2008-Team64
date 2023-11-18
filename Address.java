public class Address{
    private int houseNumber;
    private String roadName;
    private String cityName;
    private String postcode;

    Address(int hNo, String rName, String cName, String pCode){
        this.houseNumber = hNo;
        this.roadName = rName;
        this.cityName = cName;
        this.postcode = pCode;
    }
}