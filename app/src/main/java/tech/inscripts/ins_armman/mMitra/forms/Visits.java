package tech.inscripts.ins_armman.mMitra.forms;

/**
 * Created by Amol on 25/10/16.
 */
public class Visits
{
    String id, name, village,images;

    String anmUsername;
    String anmPassword;
    String anmID;
    String subcenterId;
    String showPopUp;
    double rangemin;
    double rangemax;
    String rangeLang;
    String ec_app_id;
    String createdOn;
    int ec_edit_status;
    int formAutoId;
    String womenID;
    String formID;
    String ecID;
    String womenEcAppId;
    String ashaID;
    String childId;
    String womenId;
    String womenVillageId;
    String womenAshaId;
    String uniqueId;

    public Visits(double rangemin, double rangemax, String rangeLang, String showPopUp) {
        this.rangemin = rangemin;
        this.rangemax = rangemax;
        this.rangeLang = rangeLang;
        this.showPopUp = showPopUp;
    }

    public Visits(int formAutoId, String womenID, String formID, String anmUsername, String anmPassword, String anmID, String subcenterId, String createdOn, String ashaID) {
        this.formAutoId = formAutoId;
        this.womenID = womenID;
        this.formID = formID;
        this.anmUsername = anmUsername;
        this.anmPassword = anmPassword;
        this.anmID = anmID;
        this.subcenterId = subcenterId;
        this.createdOn = createdOn;
        this.ashaID = ashaID;
    }

    public Visits(int formAutoId, String childId, String womenID, String formID, String anmUsername, String anmPassword, String anmID, String subcenterId, String createdOn, String ec_app_id, String ashaID) {
        this.formAutoId = formAutoId;
        this.childId = childId;
        this.womenID = womenID;
        this.formID = formID;
        this.anmUsername = anmUsername;
        this.anmPassword = anmPassword;
        this.anmID = anmID;
        this.subcenterId = subcenterId;
        this.createdOn = createdOn;
        this.ec_app_id = ec_app_id;
        this.ashaID = ashaID;
    }

    public Visits(int formAutoId, String childId, String ecID, String formID, String anmUsername, String anmPassword, String anmID, String subcenterId, String ashaID, String createdOn, int ec_edit_status) {
        this.formAutoId = formAutoId;
        this.childId = childId;
        this.ecID = ecID;
        this.formID = formID;
        this.anmUsername = anmUsername;
        this.anmPassword = anmPassword;
        this.anmID = anmID;
        this.subcenterId = subcenterId;
        this.ashaID = ashaID;
        this.createdOn = createdOn;
        this.ec_edit_status = ec_edit_status;
    }

    public Visits(String womenId, String womenVillageId, String womenAshaId, String womenEcAppId) {
        this.womenId = womenId;
        this.womenVillageId = womenVillageId;
        this.womenAshaId = womenAshaId;
        this.womenEcAppId = womenEcAppId;
    }

    public String getAnmUsername() {
        return anmUsername;
    }

    // Bitmap images;

//    public Visits(String id, String images, String firstName, String village)
//    {
//
//        this.id = id;
//        this.firstName = firstName;
//        this.village = village;
//        this.images = images;
//
//    }

//    public String getFormAutoId() {
//        return formAutoId;
//    }
//
//    public void setFormAutoId(String formAutoId) {
//        this.formAutoId = formAutoId;
//    }

    public void setAnmUsername(String anmUsername) {
        this.anmUsername = anmUsername;
    }

    public String getAnmPassword() {
        return anmPassword;
    }

    public void setAnmPassword(String anmPassword) {
        this.anmPassword = anmPassword;
    }

    public String getAnmID() {
        return anmID;
    }

    public void setAnmID(String anmID) {
        this.anmID = anmID;
    }

    public String getSubcenterId() {
        return subcenterId;
    }

    public void setSubcenterId(String subcenterId) {
        this.subcenterId = subcenterId;
    }

    public String getShowPopUp() {
                return showPopUp;
            }

                public void setShowPopUp(String showPopUp) {
            this.showPopUp = showPopUp;
        }

    public double getRangemin() {
        return rangemin;
    }

    public void setRangemin(double rangemin) {
        this.rangemin = rangemin;
    }

    public double getRangemax() {
        return rangemax;
    }

    public void setRangemax(double rangemax) {
        this.rangemax = rangemax;
    }

    public String getRangeLang() {
        return rangeLang;
    }

    public void setRangeLang(String rangeLang) {
        this.rangeLang = rangeLang;
    }

    public String getEc_app_id() {
        return ec_app_id;
    }

    public void setEc_app_id(String ec_app_id) {
        this.ec_app_id = ec_app_id;
    }

    public String getWomenID() {
        return womenID;
    }

    public void setWomenID(String womenID) {
        this.womenID = womenID;
    }

    public String getFormID() {
        return formID;
    }

    public void setFormID(String formID) {
        this.formID = formID;
    }

    public int getEc_edit_status() {
        return ec_edit_status;
    }

    public void setEc_edit_status(int ec_edit_status) {
        this.ec_edit_status = ec_edit_status;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getWomenEcAppId() {
        return womenEcAppId;
    }

    public void setWomenEcAppId(String womenEcAppId) {
        this.womenEcAppId = womenEcAppId;
    }

    public String getAshaID() {
        return ashaID;
    }

    public void setAshaID(String ashaID) {
        this.ashaID = ashaID;
    }

    public String getEcID() {
        return ecID;
    }

    public void setEcID(String ecID) {
        this.ecID = ecID;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public String getWomenId() {
        return womenId;
    }

    public void setWomenId(String womenId) {
        this.womenId = womenId;
    }

    public String getWomenVillageId() {
        return womenVillageId;
    }

    public void setWomenVillageId(String womenVillageId) {
        this.womenVillageId = womenVillageId;
    }

    public String getWomenAshaId() {
        return womenAshaId;
    }

    public void setWomenAshaId(String womenAshaId) {
        this.womenAshaId = womenAshaId;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public int getFormAutoId() {
        return formAutoId;
    }

    public void setFormAutoId(int formAutoId) {
        this.formAutoId = formAutoId;
    }

    @Override
    public String toString() {
        return "Visits{" +
                "id='" + id + '\'' +
                ", firstName='" + name + '\'' +
                ", village='" + village + '\'' +
                ", images='" + images + '\'' +
                ", anmUsername='" + anmUsername + '\'' +
                ", anmPassword='" + anmPassword + '\'' +
                ", anmID='" + anmID + '\'' +
                ", subcenterId='" + subcenterId + '\'' +
                ", rangemin=" + rangemin +
                ", rangemax=" + rangemax +
                ", rangeLang='" + rangeLang + '\'' +
                ", formAutoId=" + formAutoId +
                ", womenID='" + womenID + '\'' +
                ", formID='" + formID + '\'' +
                ", ecID='" + ecID + '\'' +
                ", womenEcAppId=" + womenEcAppId +
                ", ashaID='" + ashaID + '\'' +
                ", childId='" + childId + '\'' +
                ", womenId='" + womenId + '\'' +
                ", womenVillageId='" + womenVillageId + '\'' +
                ", womenAshaId='" + womenAshaId + '\'' +
                '}';
    }
}