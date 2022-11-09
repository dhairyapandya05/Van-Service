package dhairyapandya.com.vanservice2.driver;

import java.util.List;

public class commingmodle {
    String BoardingPoint,City,MailID,MobileNumber,Name;
    List<String> tags;
    private String documentId;

    commingmodle(){

    }


    public commingmodle(String boardingPoint, String city, String mailID, String mobileNumber, String name, List<String> tags) {
        BoardingPoint = boardingPoint;
        City = city;
        MailID = mailID;
        MobileNumber = mobileNumber;
        Name = name;
        this.tags = tags;
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

}
