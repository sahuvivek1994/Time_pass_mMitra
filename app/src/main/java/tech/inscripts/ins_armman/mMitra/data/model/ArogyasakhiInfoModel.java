package tech.inscripts.ins_armman.mMitra.data.model;

public class ArogyasakhiInfoModel {

    private int womenCount = 0;
    private int childrenCount = 0;
    private int totalVisitCount = 0;
    private int incompleteVisitCount = 0;
    private int dueVisitCount = 0;
    private int overdueVisitCount = 0;
    private int totalReferralCount = 0;
    private int notReferredCount = 0;
    private int totalANCCount=0;
    private int totalPNCCount =0;

    private int totalANCCount1=0,totalPNCCount1=0,registrationCount1=0;
    public int getTotalANCCount1() {
        return totalANCCount1;
    }
    public void setTotalANCCount1(int totalANCCount1) {
        this.totalANCCount1 = totalANCCount1;
    }
    public int getTotalPNCCount1() {
        return totalPNCCount1;
    }
    public void setTotalPNCCount1(int totalPNCCount1) {
        this.totalPNCCount1 = totalPNCCount1;
    }
    public int getRegistrationCount1() {
        return registrationCount1;
    }
    public void setRegistrationCount1(int registrationCount1) {
        this.registrationCount1 = registrationCount1;
    }
    public int getTotalANCCount() {
        return totalANCCount;
    }

    public void setTotalANCCount(int totalANCCount) {
        this.totalANCCount = totalANCCount;
    }

    public int getTotalPNCCount() {
        return totalPNCCount;
    }

    public void setTotalPNCCount(int totalPNCCount) {
        this.totalPNCCount = totalPNCCount;
    }

    public int getRegistrationCount() {
        return registrationCount;
    }

    public void setRegistrationCount(int registrationCount) {
        this.registrationCount = registrationCount;
    }

    private int registrationCount=0;
    public void setWomenCount(int womenCount) {
        this.womenCount = womenCount;
    }

    public void setChildrenCount(int childrenCount) {
        this.childrenCount = childrenCount;
    }

    public void setTotalVisitCount(int totalVisitCount) {
        this.totalVisitCount = totalVisitCount;
    }

    public void setIncompleteVisitCount(int incompleteVisitCount) {
        this.incompleteVisitCount = incompleteVisitCount;
    }

    public void setTotalReferralCount(int totalReferralCount) {
        this.totalReferralCount = totalReferralCount;
    }

    public void setNotReferredCount(int notReferredCount) {
        this.notReferredCount = notReferredCount;
    }

    public ArogyasakhiInfoModel(){

    }

    public ArogyasakhiInfoModel(int womenCount, int childrenCount, int totalVisitCount, int incompleteVisitCount, int totalReferralCount, int notReferredCount) {
        this.womenCount = womenCount;
        this.childrenCount = childrenCount;
        this.totalVisitCount = totalVisitCount;
        this.incompleteVisitCount = incompleteVisitCount;
        this.totalReferralCount = totalReferralCount;
        this.notReferredCount = notReferredCount;
    }

    public void setDueVisitCount(int dueVisitCount) {
        this.dueVisitCount = dueVisitCount;
    }

    public void setOverdueVisitCount(int overdueVisitCount) {
        this.overdueVisitCount = overdueVisitCount;
    }

    public int getWomenCount() {
        return womenCount;
    }

    public int getChildrenCount() {
        return childrenCount;
    }

    public int getTotalVisitCount() {
        return totalVisitCount;
    }

    public int getIncompleteVisitCount() {
        return incompleteVisitCount;
    }

    public int getDueVisitCount() {
        return dueVisitCount;
    }

    public int getOverdueVisitCount() {
        return overdueVisitCount;
    }

    public int getTotalReferralCount() {
        return totalReferralCount;
    }

    public int getNotReferredCount() {
        return notReferredCount;
    }
}
