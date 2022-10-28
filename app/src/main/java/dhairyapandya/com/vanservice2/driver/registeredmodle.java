package dhairyapandya.com.vanservice2.driver;

public class registeredmodle {
    String BoardingPoint,City,MailID,MobileNumber,Name;

    public registeredmodle(String boardingPoint, String city, String mailID, String mobileNumber, String name) {
        BoardingPoint = boardingPoint;
        City = city;
        MailID = mailID;
        MobileNumber = mobileNumber;
        Name = name;
    }

    public String getBoardingPoint() {
        return BoardingPoint;
    }

    public void setBoardingPoint(String boardingPoint) {
        BoardingPoint = boardingPoint;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getMailID() {
        return MailID;
    }

    public void setMailID(String mailID) {
        MailID = mailID;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }



}
