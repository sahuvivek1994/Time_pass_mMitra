package tech.inscripts.ins_armman.mMitra.forms;

import android.widget.LinearLayout;

/**
 * Created by Amol on 25/10/16.
 */

public class Visit {

    String defaultValue;
    LinearLayout ll_sub;
    String parentQstnKeyword, pageScrollId;
    int parentQstnId;
    String setId;
    int dependantquesid;
    String formid;
    String quesid;
    String setid;
    String keyword;
    String answerType;
    String questionText;
    String marathilang;
    String validationfield;
    String validationCondition;
    String validationengmsg;
    String avoidRepetition;
    String validationmaramsg;
    String High_risk;
    String Counselling;
    String Referral;
    String lengthmin;
    String lengthmax;
    String lengthvalidationmsg;
    String highrisk_range;
    String highrisk_lang;
    String referral_range;
    String referral_lang;
    String counselling_lang;
    String rangemin;
    String rangemax;
    String rangevalidationmsg;
    String completedform;
    String formstatus;
    String formsubmit;
    int obstreticEmergency;
    String formVisitId;
    String formVisitName;
    String displayCondition, calculations;
    int orientation;
    String messages;
    String AncofWomen;

    public Visit(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Visit(String formid, String quesid, String keyword, String questionText) {
        this.formid = formid;
        this.quesid = quesid;
        this.keyword = keyword;
        this.questionText = questionText;

    }

    public Visit(String formid, String quesid, String setId, String keyword, String answerType, String questionText) {
        this.formid = formid;
        this.quesid = quesid;
        this.setId = setId;
        this.keyword = keyword;
        this.answerType = answerType;
        this.questionText = questionText;

    }

    public Visit(String formid, String quesid, String keyword, String questionText, String highrisk, String counselling, String referral) {

        this.formid = formid;
        this.quesid = quesid;
        this.keyword = keyword;
        this.questionText = questionText;
        this.High_risk = highrisk;
        this.Counselling = counselling;
        this.Referral = referral;
    }

    public Visit(int dependantquesid, String formid, String setId, String keyword, String answerType, String questionText, String validationCondition, String messages, String referral) {
        this.dependantquesid=dependantquesid;
        this.formid = formid;
        this.setId = setId;
        this.keyword = keyword;
        this.answerType = answerType;
        this.questionText = questionText;
        this.validationCondition = validationCondition;
        this.messages = messages;
        this.Referral = referral;
    }

    public Visit(LinearLayout ll_sub, String parentQstnKeyword, String pageScrollId, int parentQstnId, String formid, String setId, String keyword, String answerType, String questionText, String validationCondition, String messages, int orientation)
    {
        this.ll_sub=ll_sub;
        this.parentQstnKeyword = parentQstnKeyword;
        this.pageScrollId = pageScrollId;
        this.parentQstnId = parentQstnId;
        this.formid = formid;
        this.setId = setId;
        this.keyword = keyword;
        this.answerType = answerType;
        this.questionText = questionText;
        this.messages = messages;
        this.validationCondition = validationCondition;
        this.orientation = orientation;
    }

    public Visit(String keyword, String questionText) {
        this.keyword = keyword;
        this.questionText = questionText;
    }

    public Visit(String formVisitId, String formVisitName, String formstatus, int obstreticEmergency) {
        this.formVisitId = formVisitId;
        this.formVisitName = formVisitName;
        this.formstatus = formstatus;
        this.obstreticEmergency = obstreticEmergency;
    }


    public Visit(String formid, String quesid, String setid, String keyword, String answerType, String questionText, String validationfield, String validationCondition, String validationengmsg, String lengthmin, String lengthmax, String lengthvalidationmsg, String rangemin, String rangemax, String rangevalidationmsg, String messages, String displayCondition, String calculations, int orientation, String avoidRepetition) {
        this.formid = formid;
        this.quesid = quesid;
        this.setid = setid;
        this.keyword = keyword;
        this.answerType = answerType;
        this.questionText = questionText;
        this.validationfield = validationfield;
        this.validationCondition = validationCondition;
        this.validationengmsg = validationengmsg;
        this.lengthmin = lengthmin;
        this.lengthmax = lengthmax;
        this.lengthvalidationmsg = lengthvalidationmsg;
        this.rangemin = rangemin;
        this.rangemax = rangemax;
        this.rangevalidationmsg = rangevalidationmsg;
        this.messages = messages;
        this.displayCondition = displayCondition;
        this.calculations = calculations;
        this.orientation = orientation;
        this.avoidRepetition = avoidRepetition;
    }

    public Visit(String formid, String quesid, String keyword, String answerType, String questionText, String validationfield, String validationCondition, String validationengmsg) {
        this.formid = formid;
        this.quesid = quesid;
        this.keyword = keyword;
        this.answerType = answerType;
        this.questionText = questionText;
        this.validationfield = validationfield;
        this.validationCondition = validationCondition;
        this.validationengmsg = validationengmsg;
    }

    public Visit(String formid, String quesid, String keyword, String answerType, String questionText, String validationfield, String validationCondition, String validationengmsg, String lengthmin, String lengthmax, String lenghtmsg) {
        this.formid = formid;
        this.quesid = quesid;
        this.keyword = keyword;
        this.answerType = answerType;
        this.questionText = questionText;
        this.validationfield = validationfield;
        this.validationCondition = validationCondition;
        this.validationengmsg = validationengmsg;
        this.lengthmin = lengthmin;
        this.lengthmax = lengthmax;
        this.lengthvalidationmsg = lenghtmsg;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public LinearLayout getLl_sub() {
        return ll_sub;
    }

    public void setLl_sub(LinearLayout ll_sub) {
        this.ll_sub = ll_sub;
    }

    public String getParentQstnKeyword() {
        return parentQstnKeyword;
    }

    public void setParentQstnKeyword(String parentQstnKeyword) {
        this.parentQstnKeyword = parentQstnKeyword;
    }

    public String getPageScrollId() {
        return pageScrollId;
    }

    public void setPageScrollId(String pageScrollId) {
        this.pageScrollId = pageScrollId;
    }

    public int getParentQstnId() {
        return parentQstnId;
    }

    public void setParentQstnId(int parentQstnId) {
        this.parentQstnId = parentQstnId;
    }

    public String getSetId() {
        return setId;
    }

    public void setSetId(String setId) {
        this.setId = setId;
    }

    public int getDependantquesid() {
        return dependantquesid;
    }

    public void setDependantquesid(int dependantquesid) {
        this.dependantquesid = dependantquesid;
    }

    public String getSetid() {
        return setid;
    }

    public void setSetid(String setid) {
        this.setid = setid;
    }

    public String getHigh_risk() {
        return High_risk;
    }

    public void setHigh_risk(String high_risk) {
        High_risk = high_risk;
    }

    public String getCounselling() {
        return Counselling;
    }

    public void setCounselling(String counselling) {
        Counselling = counselling;
    }

    public String getReferral() {
        return Referral;
    }

    public void setReferral(String referral) {
        Referral = referral;
    }

    public String getLengthmin() {
        return lengthmin;
    }

    public void setLengthmin(String lengthmin) {
        this.lengthmin = lengthmin;
    }

    public String getLengthmax() {
        return lengthmax;
    }

    public void setLengthmax(String lengthmax) {
        this.lengthmax = lengthmax;
    }

    public String getLengthvalidationmsg() {
        return lengthvalidationmsg;
    }

    public void setLengthvalidationmsg(String lengthvalidationmsg) {
        this.lengthvalidationmsg = lengthvalidationmsg;
    }

    public String getHighrisk_range() {
        return highrisk_range;
    }

    public void setHighrisk_range(String highrisk_range) {
        this.highrisk_range = highrisk_range;
    }

    public String getReferral_range() {
        return referral_range;
    }

    public void setReferral_range(String referral_range) {
        this.referral_range = referral_range;
    }

    public String getReferral_lang() {
        return referral_lang;
    }

    public void setReferral_lang(String referral_lang) {
        this.referral_lang = referral_lang;
    }

    public String getCounselling_lang() {
        return counselling_lang;
    }

    public void setCounselling_lang(String counselling_lang) {
        this.counselling_lang = counselling_lang;
    }

    public String getRangemin() {
        return rangemin;
    }

    public void setRangemin(String rangemin) {
        this.rangemin = rangemin;
    }

    public String getHighrisk_lang() {
        return highrisk_lang;
    }

    public void setHighrisk_lang(String highrisk_lang) {
        this.highrisk_lang = highrisk_lang;
    }

    public String getRangemax() {
        return rangemax;
    }

    public void setRangemax(String rangemax) {
        this.rangemax = rangemax;
    }

    public String getRangevalidationmsg() {
        return rangevalidationmsg;
    }

    public void setRangevalidationmsg(String rangevalidationmsg) {
        this.rangevalidationmsg = rangevalidationmsg;
    }

    public String getFormstatus() {
        return formstatus;
    }

    public void setFormstatus(String formstatus) {
        this.formstatus = formstatus;
    }

    public String getCompletedform() {
        return completedform;
    }

    public void setCompletedform(String completedform) {
        this.completedform = completedform;
    }

    public String getFormsubmit() {
        return formsubmit;
    }

//    public Visit(String formstatus, String completedform, String formsubmit) {
//        this.formstatus = formstatus;
//        this.completedform = completedform;
//        this.formsubmit = formsubmit;
//    }

    public void setFormsubmit(String formsubmit) {
        this.formsubmit = formsubmit;
    }

    public int getObstreticEmergency() {
        return obstreticEmergency;
    }

    /*public Visit(String ancofWomen) {
        AncofWomen = ancofWomen;
    }*/

    public void setObstreticEmergency(int obstreticEmergency) {
        this.obstreticEmergency = obstreticEmergency;
    }

    public String getFormVisitId() {
        return formVisitId;
    }

    public void setFormVisitId(String formVisitId) {
        this.formVisitId = formVisitId;
    }

    public String getFormVisitName() {
        return formVisitName;
    }

    public void setFormVisitName(String formVisitName) {
        this.formVisitName = formVisitName;
    }

    public String getAncofWomen() {
        return AncofWomen;
    }

    public void setAncofWomen(String ancofWomen) {
        AncofWomen = ancofWomen;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public String getCalculations() {
        return calculations;
    }

    public void setCalculations(String calculations) {
        this.calculations = calculations;
    }

    public String getDisplayCondition() {
        return displayCondition;
    }

    public void setDisplayCondition(String displayCondition) {
        this.displayCondition = displayCondition;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public String getFormid() {
        return formid;
    }

    public void setFormid(String formid) {
        this.formid = formid;
    }

    public String getQuesid() {
        return quesid;
    }

    public void setQuesid(String quesid) {
        this.quesid = quesid;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getAnswerType() {
        return answerType;
    }

    public void setAnswerType(String answerType) {
        this.answerType = answerType;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getMarathilang() {
        return marathilang;
    }

    public void setMarathilang(String marathilang) {
        this.marathilang = marathilang;
    }

    public String getValidationfield() {
        return validationfield;
    }

    public void setValidationfield(String validationfield) {
        this.validationfield = validationfield;
    }

    public String getValidationCondition() {
        return validationCondition;
    }

    public void setValidationCondition(String validationCondition) {
        this.validationCondition = validationCondition;
    }

    public String getAvoidRepetition() {
        return avoidRepetition;
    }

    public String getValidationengmsg() {
        return validationengmsg;
    }

    public void setValidationengmsg(String validationengmsg) {
        this.validationengmsg = validationengmsg;
    }

    public String getValidationmaramsg() {
        return validationmaramsg;
    }

    public void setValidationmaramsg(String validationmaramsg) {
        this.validationmaramsg = validationmaramsg;
    }

    @Override
    public String toString() {
        return "Visit{" +
                "formid='" + formid + '\'' +
                ", formstatus='" + formstatus + '\'' +
                ", formVisitId='" + formVisitId + '\'' +
                ", formVisitName='" + formVisitName + '\'' +
                ", obstreticEmergency=" + obstreticEmergency +
                '}';
    }

}
