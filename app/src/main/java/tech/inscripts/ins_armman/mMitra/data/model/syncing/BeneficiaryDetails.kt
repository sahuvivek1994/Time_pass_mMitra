package tech.inscripts.ins_armman.mMitra.data.model.syncing

import com.google.gson.annotations.SerializedName
import tech.inscripts.ins_armman.mMitra.data.database.DatabaseContract
import tech.inscripts.ins_armman.mMitra.data.database.DatabaseContract.RegistrationTable

class BeneficiaryDetails {
   /* @SerializedName(RegistrationTable.COLUMN_UNIQUE_ID)
    private var uniqueId: String = ""
    @SerializedName(RegistrationTable.COLUMN_FIRST_NAME)
    private var name: String = ""
    @SerializedName(RegistrationTable.COLUMN_MIDDLE_NAME)
    private var middleName:String = ""
    @SerializedName(RegistrationTable.COLUMN_LAST_NAME)
    private var lastName: String = ""
    @SerializedName(RegistrationTable.COLUMN_ADDRESS)
    private var address:String = ""
    @SerializedName(RegistrationTable.COLUMN_LMP_DATE)
    private var lmp: String = ""
    @SerializedName(RegistrationTable.COLUMN_EDD_DATE)
    private var edd: String = ""
    @SerializedName(RegistrationTable.COLUMN_MOBILE_NO)
    private var mobNo: String = ""
    @SerializedName(RegistrationTable.COLUMN_ALTERNATE_NO)
    private var alternateNo: String = ""
    @SerializedName(RegistrationTable.COLUMN_EDUCATION)
    private var education: String = ""
    @SerializedName(RegistrationTable.COLUMN_RELIGION)
    private var religion: String = ""
    @SerializedName(RegistrationTable.COLUMN_CATEGORY)
    private var category:String = ""
    @SerializedName(RegistrationTable.COLUMN_VILLAGE_ID)
    private var villageId: String = ""
    @SerializedName(RegistrationTable.COLUMN_DOB)
    private var dob: String = ""
    @SerializedName(RegistrationTable.COLUMN_IMAGE)
    private var image: String = ""
    @SerializedName(RegistrationTable.COLUMN_CREATED_ON)
    private var createdOn : String = ""
    @SerializedName(RegistrationTable.COLUMN_MOTHER_ID)
    private var motherId: String = ""
    @SerializedName(RegistrationTable.COLUMN_GENDER)
    private var gender: String = ""
    @SerializedName(RegistrationTable.COLUMN_DELIVERY_DATE)
    private var deliveryDate: String = ""
    @SerializedName("closure_status")
    private var closeStatus: Int = 0
    @SerializedName(RegistrationTable.COLUMN_CLOSE_DATE)
    private var closeDate:String = ""
    @SerializedName(RegistrationTable.COLUMN_CLOSE_REASON)
    private var closeReason: String = ""
    @SerializedName(RegistrationTable.COLUMN_EXPIRED_DATE)
    private var expiredDate:String = ""
    @SerializedName(RegistrationTable.COLUMN_EXPIRED_REASON)
    private var expiredReason: String = ""
    @SerializedName("child_status")
    private var childStatus: String = ""
   */

    @SerializedName(RegistrationTable.COLUMN_UNIQUE_ID)
    private var uniqueId: String? = null
    @SerializedName(RegistrationTable.COLUMN_NAME)
    private var name: String? = null
    @SerializedName(RegistrationTable.COLUMN_ADDRESS)
    private var address: String? = null
    @SerializedName(RegistrationTable.COLUMN_LMP_DATE)
    private var lmp: String? = null
    @SerializedName(RegistrationTable.COLUMN_MOBILE_NO)
    private var mobNo: String? = null
    @SerializedName(RegistrationTable.COLUMN_EDUCATION)
    private var education: String? = null
    @SerializedName(RegistrationTable.COLUMN_AGE)
    private var dob: String? = null
    @SerializedName(RegistrationTable.COLUMN_IMAGE)
    private var image: String? = null
    @SerializedName(RegistrationTable.COLUMN_CREATED_ON)
    private var createdOn: String? = null
    @SerializedName(RegistrationTable.COLUMN_MARITAL_STATUS)
    private var marital_status: String? = null
    @SerializedName(RegistrationTable.COLUMN_CLOSE_DATE)
    private var closeDate: String? = null
    @SerializedName(RegistrationTable.COLUMN_CLOSE_REASON)
    private var closeReason: String? = null
    @SerializedName(RegistrationTable.COLUMN_EXPIRED_DATE)
    private var expiredDate: String? = null
    @SerializedName(RegistrationTable.COLUMN_EXPIRED_REASON)
    private var expiredReason: String? = null
    @SerializedName(DatabaseContract.LoginTable.COLUMN_USER_ID)
    private var userId: String? = null
    @SerializedName("mmitra_flag")
    private var flag:Int?=null


            /*@SerializedName(DATA)
    private ArrayList<QuestionAnswer> dataSource;
    @SerializedName(REFERRAL)
    private ArrayList<Referral> referral; fun getDeliveryDate(): String? {
        return marital_status
    }*/

    fun getDeliveryDate() : String?{
        return marital_status
    }

    fun setDeliveryDate(deliveryDate: String) {
        this.marital_status = deliveryDate
    }

    fun setUniqueId(uniqueId: String) {
        this.uniqueId = uniqueId
    }

    fun setName(name: String) {
        this.name = name
    }

    fun setUserId(userId: String) {
        this.userId = userId
    }

    fun setAddress(address: String) {
        this.address = address
    }

    fun setLmp(lmp: String) {
        this.lmp = lmp
    }


    fun setMobNo(mobNo: String) {
        this.mobNo = mobNo
    }

    fun setEducation(education: String) {
        this.education = education
    }


    fun setImage(image: String) {
        this.image = image
    }

    fun setDob(dob: String) {
        this.dob = dob
    }

    fun setCreatedOn(createdOn: String) {
        this.createdOn = createdOn
    }

    fun getUniqueId(): String? {
        return uniqueId
    }

    fun getName(): String? {
        return name
    }

    fun getUserId(): String? {
        return userId
    }

    fun getAddress(): String? {
        return address
    }

    fun getLmp(): String? {
        return lmp
    }

    fun getMobNo(): String? {
        return mobNo
    }

    fun getEducation(): String? {
        return education
    }



    fun getDob(): String? {
        return dob
    }

    fun getImage(): String? {
        return image
    }

    fun getCreatedOn(): String? {
        return createdOn
    }

    fun getCloseDate(): String? {
        return closeDate
    }

    fun setCloseDate(closeDate: String) {
        this.closeDate = closeDate
    }

    fun getCloseReason(): String? {
        return closeReason
    }

    fun setCloseReason(closeReason: String) {
        this.closeReason = closeReason
    }

    fun getExpiredDate(): String? {
        return expiredDate
    }

    fun setExpiredDate(expiredDate: String) {
        this.expiredDate = expiredDate
    }

    fun getExpiredReason(): String? {
        return expiredReason
    }

    fun setExpiredReason(expiredReason: String) {
        this.expiredReason = expiredReason
    }

    fun getFlag(): Int? {
        return flag
    }

    fun setFlag(flag: Int) {
        this.flag = flag
    }

    override fun toString(): String {
        return "BeneficiaryDetails{" +
                "uniqueId='" + uniqueId + '\''.toString() +
                ", name='" + name + '\''.toString() +
                //", middleName='" + middleName + '\''.toString() +
                //", lastName='" + lastName + '\''.toString() +
                ", address='" + address + '\''.toString() +
                ", lmp='" + lmp + '\''.toString() +
                ", marital_status='" + marital_status + '\''.toString() +
                ", mobNo='" + mobNo + '\''.toString() +
                ", education='" + education + '\''.toString() +
              //  ", image='" + image + '\''.toString() +
                ", createdOn='" + createdOn + '\''.toString() +
                '}'.toString()
    }

}