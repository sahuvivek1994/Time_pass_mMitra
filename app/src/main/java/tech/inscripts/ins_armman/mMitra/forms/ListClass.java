package tech.inscripts.ins_armman.mMitra.forms;

import java.io.Serializable;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by lenovo on 14/11/2017.
 */

public class ListClass implements Serializable {

    String ListText;

    String value;

    String Color;
    String locationId;

    String Recipeid;
    String ModifiedOn;
    String tagID=null;
    String id=null;
    String SystemNo=null;
    String villageId;
    List<ListClass> overDueList;
    TreeMap<Integer, Integer> casePositionInList;
    String lastVisit;
    int statusMigrant, statusMAMTACard;
    String womanID, womanServerID, womanName, womanMobileNo, villageID, villageName, ashaID, ashaMobileNo, dateLMP, lastSkippedVisitID,
            dateEDD, dateDelivery, dateClosure, subcenterID, regWomanAutoID;
    int highRiskStatus;
    String houseHoldId, husbandName;
    int migrantWoman;
    String womenID;
    String womenname;
    String womenMobNo;
    String ashaName;
    String womenLmp;
    String womenvisit;
    String ashaMobNo;
    String fatherName;
    String houseHoldNo;
    String ecAppId;
    String womenClosureDate;
    String womenDeliveryDate;
    String womenMotherId;
    String Gest_age;
    String createdOn;
    int lastFilledFormId;

    public ListClass(String regWomanAutoID, String womanID, String womanName, String womanMobileNo, String villageID
            , String villageName, String ashaID, String ashaName, String ashaMobileNo, String dateLMP, String lastSkippedVisitID
            , String dateEDD, String dateDelivery, String dateClosure, String subcenterID, int statusMigrant, int statusMAMTACard){

        this.regWomanAutoID=regWomanAutoID;
        this.womanID=womanID;
//        this.womanServerID=womanServerID;
        this.womanName=womanName;
        this.womanMobileNo=womanMobileNo;
        this.villageID=villageID;
        this.villageName=villageName;
        this.ashaID=ashaID;
        this.ashaName=ashaName;
        this.ashaMobileNo=ashaMobileNo;
        this.dateLMP=dateLMP;
        this.lastSkippedVisitID=lastSkippedVisitID;
        this.dateEDD=dateEDD;
        this.dateDelivery=dateDelivery;
        this.dateClosure=dateClosure;
        this.subcenterID=subcenterID;
        this.statusMigrant=statusMigrant;
        this.statusMAMTACard=statusMAMTACard;

    }

    public ListClass(String women_id, String name, String mob_no, String women_village, String expec_delivery_date, String string, String ancVisit, String visit_id, String motherID, String lastVisit, int highRiskStatus, String houseHoldId, String husbandName) {
        this.ListText = women_id; // womanID
        this.value = name;       // firstName
        this.tagID = mob_no;
        this.locationId = women_village; // village firstName
        this.Color = expec_delivery_date;
        this.id = string;                 // lmp||delivery-date
        this.Recipeid = ancVisit;     // Visit Name
        this.SystemNo = visit_id;     // Visit ID
        this.womenMotherId = motherID; // mcts mother ID
        this.lastVisit = lastVisit; // mcts mother ID
        this.highRiskStatus = highRiskStatus;
        this.houseHoldId = houseHoldId;
        this.husbandName = husbandName;
    }

    public ListClass(List<ListClass> overDueList, TreeMap<Integer, Integer> casePositionInList) {
        this.overDueList = overDueList;
        this.casePositionInList = casePositionInList;
    }

    public ListClass(String womenID, String womenname, String womenMobNo, String villageId, String womenLmp, String womenvisitno, String ashaMobNo) {
        this.womenID = womenID;
        this.womenname = womenname;
        this.womenMobNo = womenMobNo;
        this.villageId = villageId;
        this.womenLmp = womenLmp;
        this.womenvisit = womenvisitno;
        this.ashaMobNo = ashaMobNo;
    }

    public ListClass(String listtext) {
        this.ListText = listtext;

    }

    public ListClass(String listtext, String value) {
        this.ListText = listtext;
        this.value = value;
    }

    public ListClass(String listtext, String value, String color) {
        this.ListText = listtext;
        this.value = value;
        this.Color = color;
    }

    public ListClass(String listtext, String value, String color, String locationId) {
        this.ListText = listtext;
        this.value = value;
        this.Color = color;
        this.locationId = locationId;
    }

    public ListClass(String womenname, String fatherName, String houseHoldNo, String ecAppId, String womenMobNo) {
        this.womenname = womenname;
        this.fatherName = fatherName;
        this.houseHoldNo = houseHoldNo;
        this.ecAppId = ecAppId;
        this.womenMobNo = womenMobNo;
    }

    public ListClass(String listtext, String value, String tagID, String locationId, String color, String createdOn) {
        this.ListText = listtext;
        this.value = value;
        this.tagID = tagID;
        this.locationId = locationId;
        this.Color = color;
        this.createdOn = createdOn;
    }

    public ListClass(String listtext, String value, String tagID, String locationId, String color, String id, String Recipeid, String SystemNo, String womenMotherId) {
        this.ListText = listtext; // womanID
        this.value = value;       // firstName
        this.tagID = tagID;
        this.locationId = locationId; // village firstName
        this.Color = color;
        this.id = id;                 // lmp||delivery-date
        this.Recipeid = Recipeid;     // Visit Name
        this.SystemNo = SystemNo;     // Visit ID
        this.womenMotherId = womenMotherId; // mcts mother ID
    }

    public ListClass(String listtext, String value, String tagID, String locationId, String color, String id, String Recipeid, String SystemNo, String Gest_age, String womenDeliveryDate, String womenClosureDate, String womenMotherId, String villageId, int migrantWoman, String lastVisit, int highRiskStatus, String houseHoldId, String husbandName, int statusMAMTACard, int lastFilledFormId) {
        this.ListText = listtext;
        this.value = value;
        this.tagID = tagID;
        this.locationId = locationId;
        this.Color = color;
        this.id = id;
        this.Recipeid = Recipeid;
        this.SystemNo = SystemNo;
        this.Gest_age = Gest_age;
        this.womenDeliveryDate = womenDeliveryDate;
        this.womenClosureDate = womenClosureDate;
        this.womenMotherId = womenMotherId;
        this.villageId = villageId;
        this.migrantWoman = migrantWoman;
        this.lastVisit = lastVisit;
        this.highRiskStatus = highRiskStatus;
        this.houseHoldId = houseHoldId;
        this.husbandName = husbandName;
        this.statusMAMTACard = statusMAMTACard;
        this.lastFilledFormId = lastFilledFormId;
    }

    public String getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(String lastVisit) {
        this.lastVisit = lastVisit;
    }

    public String getHouseHoldId() {
        return houseHoldId;
    }

    public void setHouseHoldId(String houseHoldId) {
        this.houseHoldId = houseHoldId;
    }

    public String getHusbandName() {
        return husbandName;
    }

    public void setHusbandName(String husbandName) {
        this.husbandName = husbandName;
    }

    public int getHighRiskStatus() {
        return highRiskStatus;
    }

    public void setHighRiskStatus(int highRiskStatus) {
        this.highRiskStatus = highRiskStatus;
    }

    public String getWomanServerID() {
        return womanServerID;
    }

    public void setWomanServerID(String womanServerID) {
        this.womanServerID = womanServerID;
    }

    public String getWomanName() {
        return womanName;
    }

    public void setWomanName(String womanName) {
        this.womanName = womanName;
    }

    public String getWomanMobileNo() {
        return womanMobileNo;
    }

    public void setWomanMobileNo(String womanMobileNo) {
        this.womanMobileNo = womanMobileNo;
    }

    public String getWomanID() {
        return womanID;
    }

    public void setWomanID(String womanID) {
        this.womanID = womanID;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public String getVillageID() {
        return villageID;
    }

    public void setVillageID(String villageID) {
        this.villageID = villageID;
    }

    public String getSubcenterID() {
        return subcenterID;
    }

    public void setSubcenterID(String subcenterID) {
        this.subcenterID = subcenterID;
    }

    public int getStatusMigrant() {
        return statusMigrant;
    }

    public void setStatusMigrant(int statusMigrant) {
        this.statusMigrant = statusMigrant;
    }

    public int getStatusMAMTACard() {
        return statusMAMTACard;
    }

    public void setStatusMAMTACard(int statusMAMTACard) {
        this.statusMAMTACard = statusMAMTACard;
    }

    public String getRegWomanAutoID() {
        return regWomanAutoID;
    }

    public void setRegWomanAutoID(String regWomanAutoID) {
        this.regWomanAutoID = regWomanAutoID;
    }

    public String getLastSkippedVisitID() {
        return lastSkippedVisitID;
    }

    public void setLastSkippedVisitID(String lastSkippedVisitID) {
        this.lastSkippedVisitID = lastSkippedVisitID;
    }

    public String getDateLMP() {
        return dateLMP;
    }

    public void setDateLMP(String dateLMP) {
        this.dateLMP = dateLMP;
    }

    public String getDateEDD() {
        return dateEDD;
    }

    public void setDateEDD(String dateEDD) {
        this.dateEDD = dateEDD;
    }

    public String getDateDelivery() {
        return dateDelivery;
    }

    public void setDateDelivery(String dateDelivery) {
        this.dateDelivery = dateDelivery;
    }

    public String getDateClosure() {
        return dateClosure;
    }

    public void setDateClosure(String dateClosure) {
        this.dateClosure = dateClosure;
    }

    public String getAshaMobileNo() {
        return ashaMobileNo;
    }

    public void setAshaMobileNo(String ashaMobileNo) {
        this.ashaMobileNo = ashaMobileNo;
    }

    public String getAshaID() {
        return ashaID;
    }

    public void setAshaID(String ashaID) {
        this.ashaID = ashaID;
    }

    public TreeMap<Integer, Integer> getCasePositionInList() {
        return casePositionInList;
    }

    public void setCasePositionInList(TreeMap<Integer, Integer> casePositionInList) {
        this.casePositionInList = casePositionInList;
    }

    public List<ListClass> getOverDueList() {
        return overDueList;
    }

    public void setOverDueList(List<ListClass> overDueList) {
        this.overDueList = overDueList;
    }

    public String getWomenname() {
        return womenname;
    }

    public void setWomenname(String womenname) {
        this.womenname = womenname;
    }

    public String getWomenID() {
        return womenID;
    }

    public void setWomenID(String womenID) {
        this.womenID = womenID;
    }

    public String getWomenMobNo() {
        return womenMobNo;
    }

    public void setWomenMobNo(String womenMobNo) {
        this.womenMobNo = womenMobNo;
    }

    public String getAshaName() {
        return ashaName;
    }

    public void setAshaName(String ashaName) {
        this.ashaName = ashaName;
    }

    public String getWomenLmp() {
        return womenLmp;
    }

    public void setWomenLmp(String womenLmp) {
        this.womenLmp = womenLmp;
    }

    public String getWomenvisit() {
        return womenvisit;
    }

    public void setWomenvisit(String womenvisit) {
        this.womenvisit = womenvisit;
    }

    public String getAshaMobNo() {
        return ashaMobNo;
    }

    public void setAshaMobNo(String ashaMobNo) {
        this.ashaMobNo = ashaMobNo;
    }

    public int getMigrantWoman() {
        return migrantWoman;
    }

    public void setMigrantWoman(int migrantWoman) {
        this.migrantWoman = migrantWoman;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getHouseHoldNo() {
        return houseHoldNo;
    }

    public void setHouseHoldNo(String houseHoldNo) {
        this.houseHoldNo = houseHoldNo;
    }

    public String getEcAppId() {
        return ecAppId;
    }

    public void setEcAppId(String ecAppId) {
        this.ecAppId = ecAppId;
    }

    public String getWomenClosureDate() {
        return womenClosureDate;
    }

    public void setWomenClosureDate(String womenClosureDate) {
        this.womenClosureDate = womenClosureDate;
    }

    public String getWomenDeliveryDate() {
        return womenDeliveryDate;
    }

    public void setWomenDeliveryDate(String womenDeliveryDate) {
        this.womenDeliveryDate = womenDeliveryDate;
    }

    public String getWomenMotherId() {
        return womenMotherId;
    }

    public void setWomenMotherId(String womenMotherId) {
        this.womenMotherId = womenMotherId;
    }

    public String getGest_age() {
        return Gest_age;
    }

    public void setGest_age(String gest_age) {
        Gest_age = gest_age;
    }

    public String getSystemNo() {
        return SystemNo;
    }

    public void setSystemNo(String systemNo) {
        SystemNo = systemNo;
    }

    public String getRecipeid() {
        return Recipeid;
    }

    public void setRecipeid(String recipeid) {
        Recipeid = recipeid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTagID() {
        return tagID;
    }

    public void setTagID(String tagID) {
        this.tagID = tagID;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getColor(){
        return Color;

    }

    public String getValue() {
        return value;
    }

    public String getListText() {
        return ListText;
    }

    public String ModifiedOn() {
        return ModifiedOn;
    }

    public void ModifiedOn(String modifiedOn) {
        this.ModifiedOn = modifiedOn;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getVillageId() {
        return villageId;
    }

    public void setVillageId(String villageId) {
        this.villageId = villageId;
    }

    public int getLastFilledFormId() {
        return lastFilledFormId;
    }

    public void setLastFilledFormId(int lastFilledFormId) {
        this.lastFilledFormId = lastFilledFormId;
    }

    @Override
    public String toString() {
        return "ListClass{" +
                "ashaID='" + ashaID + '\'' +
                ", ListText='" + ListText + '\'' +
                ", value='" + value + '\'' +
                ", Color='" + Color + '\'' +
                ", locationId='" + locationId + '\'' +
                ", Recipeid='" + Recipeid + '\'' +
                ", ModifiedOn='" + ModifiedOn + '\'' +
                ", tagID='" + tagID + '\'' +
                ", id='" + id + '\'' +
                ", SystemNo='" + SystemNo + '\'' +
                ", villageId='" + villageId + '\'' +
                ", overDueList=" + overDueList +
                ", casePositionInList=" + casePositionInList +
                ", regWomanAutoID=" + regWomanAutoID +
                ", statusMigrant=" + statusMigrant +
                ", statusMAMTACard=" + statusMAMTACard +
                ", womanID='" + womanID + '\'' +
                ", womanServerID='" + womanServerID + '\'' +
                ", womanName='" + womanName + '\'' +
                ", womanMobileNo='" + womanMobileNo + '\'' +
                ", villageID='" + villageID + '\'' +
                ", villageName='" + villageName + '\'' +
                ", ashaMobileNo='" + ashaMobileNo + '\'' +
                ", dateLMP='" + dateLMP + '\'' +
                ", lastSkippedVisitID='" + lastSkippedVisitID + '\'' +
                ", dateEDD='" + dateEDD + '\'' +
                ", dateDelivery='" + dateDelivery + '\'' +
                ", dateClosure='" + dateClosure + '\'' +
                ", subcenterID='" + subcenterID + '\'' +
                ", migrantWoman=" + migrantWoman +
                ", womenID='" + womenID + '\'' +
                ", womenname='" + womenname + '\'' +
                ", womenMobNo='" + womenMobNo + '\'' +
                ", ashaName='" + ashaName + '\'' +
                ", womenLmp='" + womenLmp + '\'' +
                ", womenvisit='" + womenvisit + '\'' +
                ", ashaMobNo='" + ashaMobNo + '\'' +
                ", womenClosureDate='" + womenClosureDate + '\'' +
                ", womenDeliveryDate='" + womenDeliveryDate + '\'' +
                ", womenMotherId='" + womenMotherId + '\'' +
                ", Gest_age='" + Gest_age + '\'' +
                '}';
    }
}
