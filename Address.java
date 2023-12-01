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

    // -----GETTERS-----
    public int getHouseNumber() {
        return houseNumber;
    }
    public String getRoadName() {return roadName; }
    public String getCityName() {return cityName;}
    public String getPostcode() {return postcode;}

    // ----SETTERS-----

    public void setHouseNumber(int newHouseNo) {this.houseNumber = newHouseNo;}
    public void setRoadName(String newRName) {this.roadName = newRName; }
    public void setCityName(String newCName) {this.cityName = newCName;}
    public void setPostcode(String newPostcode) {this.postcode = newPostcode;}

    // ----- METHODS -----
    public String toString(){
        return (String.valueOf(this.houseNumber) + " " +
                this.roadName + " " +
                this.cityName + " " +
                this.postcode);
    }
}