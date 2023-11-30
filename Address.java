public class Address {
    private int houseNumber;
    private String roadName;
    private String cityName;
    private String postcode;

    Address(int hNo, String rName, String cName, String pCode) {
        this.houseNumber = hNo;
        this.roadName = rName;
        this.cityName = cName;
        this.postcode = pCode;
    }

    public int getHouseNumber() {
        return houseNumber;
    }


    public String getRoadName() {
        return roadName;
    }

    public String getCityName() {
        return cityName;
    }

    public String getPostcode() {
        return postcode;
    }
}