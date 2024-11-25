package dhairyapandya.com.vanservice2.customer.availabledrivers;

import java.util.List;

public class modledriversdetails {
    String Name,Cost,Vehical,MobileNumber,MailID,uid,Modleofvehical,Platenumberofvehical,ColorofVehical,imageUrl;
    List<String> Commingstudents;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getuid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public modledriversdetails(String name, String cost, String vehical, String mobileNumber, String mailID, String uid, String modleofvehical, String platenumberofvehical, String colorofVehical, List<String> commingstudents,String imageUrl) {
        Name = name;
        Cost = cost;
        Vehical = vehical;
        MobileNumber = mobileNumber;
        MailID = mailID;
        this.uid = uid;
        this.imageUrl=imageUrl;
        Modleofvehical = modleofvehical;
        Platenumberofvehical = platenumberofvehical;
        ColorofVehical = colorofVehical;
        Commingstudents = commingstudents;
    }

    public String getModleofvehical() {
        return Modleofvehical;
    }

    public void setModleofvehical(String modleofvehical) {
        Modleofvehical = modleofvehical;
    }

    public String getPlatenumberofvehical() {
        return Platenumberofvehical;
    }

    public void setPlatenumberofvehical(String platenumberofvehical) {
        Platenumberofvehical = platenumberofvehical;
    }

    public String getColorofVehical() {
        return ColorofVehical;
    }

    public void setColorofVehical(String colorofVehical) {
        ColorofVehical = colorofVehical;
    }

    public List<String> getCommingstudents() {
        return Commingstudents;
    }

    public void setCommingstudents(List<String> commingstudents) {
        Commingstudents = commingstudents;
    }




    public String getMailID() {
        return MailID;
    }

    public void setMailID(String mailID) {
        MailID = mailID;
    }

    modledriversdetails()
    {

    }

    public String getVehical() {
        return Vehical;
    }

    public void setVehical(String vehical) {
        Vehical = vehical;
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

    public String getCost() {
        return Cost;
    }

    public void setCost(String cost) {
        Cost = cost;
    }
}
