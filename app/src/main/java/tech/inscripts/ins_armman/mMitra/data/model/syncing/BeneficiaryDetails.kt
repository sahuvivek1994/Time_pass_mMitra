package tech.inscripts.ins_armman.mMitra.data.model.syncing

import com.google.gson.annotations.SerializedName
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
    var name: String? = null
    @SerializedName(RegistrationTable.COLUMN_ADDRESS)
    private var address: String? = null
    @SerializedName(RegistrationTable.COLUMN_LMP_DATE)
    private var lmp: String? = null
    @SerializedName(RegistrationTable.COLUMN_EDD_DATE)
    private var edd: String? = null
    @SerializedName(RegistrationTable.COLUMN_MOBILE_NO)
    private var mobNo: String? = null
    @SerializedName(RegistrationTable.COLUMN_ALTERNATE_NO)
    private var alternateNo: String? = null
    @SerializedName(RegistrationTable.COLUMN_EDUCATION)
    private var education: String? = null
    @SerializedName(RegistrationTable.COLUMN_RELIGION)
    private var religion: String? = null
    @SerializedName(RegistrationTable.COLUMN_CATEGORY)
    private var category: String? = null
    @SerializedName(RegistrationTable.COLUMN_VILLAGE_ID)
    private var villageId: String? = null
    @SerializedName(RegistrationTable.COLUMN_DOB)
    private var dob: String? = null
    @SerializedName(RegistrationTable.COLUMN_IMAGE)
    private var image: String? = null
    @SerializedName(RegistrationTable.COLUMN_CREATED_ON)
    private var createdOn: String? = null
    @SerializedName(RegistrationTable.COLUMN_MOTHER_ID)
    private var motherId: String? = null
    @SerializedName(RegistrationTable.COLUMN_GENDER)
    private var gender: String? = null
    @SerializedName(RegistrationTable.COLUMN_DELIVERY_DATE)
    private var deliveryDate: String? = null
    @SerializedName("closure_status")
    private var closeStatus: Int = 0
    @SerializedName(RegistrationTable.COLUMN_CLOSE_DATE)
    private var closeDate: String? = null
    @SerializedName(RegistrationTable.COLUMN_CLOSE_REASON)
    private var closeReason: String? = null
    @SerializedName(RegistrationTable.COLUMN_EXPIRED_DATE)
    private var expiredDate: String? = null
    @SerializedName(RegistrationTable.COLUMN_EXPIRED_REASON)
    private var expiredReason: String? = null

    @SerializedName("child_status")
    internal var child_status: String=""

            /*@SerializedName(DATA)
    private ArrayList<QuestionAnswer> dataSource;
    @SerializedName(REFERRAL)
    private ArrayList<Referral> referral; fun getDeliveryDate(): String? {
        return deliveryDate
    }*/

    fun getDeliveryDate() : String?{
        return deliveryDate
    }

    fun setDeliveryDate(deliveryDate: String) {
        this.deliveryDate = deliveryDate
    }

    fun getGender(): String? {
        return gender
    }

    fun setGender(gender: String) {
        this.gender = gender
    }

    fun setMotherId(motherId: String) {
        this.motherId = motherId
    }

    fun setUniqueId(uniqueId: String) {
        this.uniqueId = uniqueId
    }

    fun setFirstName(firstName: String) {
        this.name = firstName
    }

    fun setAddress(address: String) {
        this.address = address
    }

    fun setLmp(lmp: String) {
        this.lmp = lmp
    }

    fun setEdd(edd: String) {
        this.edd = edd
    }

    fun setMobNo(mobNo: String) {
        this.mobNo = mobNo
    }

    fun setAlternateNo(alternateNo: String) {
        this.alternateNo = alternateNo
    }

    fun setEducation(education: String) {
        this.education = education
    }

    fun setReligion(religion: String) {
        this.religion = religion
    }

    fun setCategory(category: String) {
        this.category = category
    }

    fun setVillageId(villageId: String) {
        this.villageId = villageId
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

    fun getFirstName(): String? {
        return name
    }

    fun getAddress(): String? {
        return address
    }

    fun getLmp(): String? {
        return lmp
    }

    fun getEdd(): String? {
        return edd
    }

    fun getMobNo(): String? {
        return mobNo
    }

    fun getAlternateNo(): String? {
        return alternateNo
    }

    fun getEducation(): String? {
        return education
    }

    fun getReligion(): String? {
        return religion
    }

    fun getCategory(): String? {
        return category
    }

    fun getVillageId(): String? {
        return villageId
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

    fun getMotherId(): String? {
        return motherId
    }

    fun getCloseStatus(): Int {
        return closeStatus
    }

    fun setCloseStatus(closeStatus: Int) {
        this.closeStatus = closeStatus
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

    fun getChild_status(): String {
        return child_status
    }

    fun setChild_status(child_status: String) {
        this.child_status = child_status
    }

    override fun toString(): String {
        return "BeneficiaryDetails{" +
                "uniqueId='" + uniqueId + '\''.toString() +
                ", name='" + name + '\''.toString() +
                ", address='" + address + '\''.toString() +
                ", lmp='" + lmp + '\''.toString() +
                ", edd='" + edd + '\''.toString() +
                ", mobNo='" + mobNo + '\''.toString() +
                ", alternateNo='" + alternateNo + '\''.toString() +
                ", education='" + education + '\''.toString() +
                ", religion='" + religion + '\''.toString() +
                ", category='" + category + '\''.toString() +
                ", villageId='" + villageId + '\''.toString() +
                ", dob='" + dob + '\''.toString() +
                ", image='" + image + '\''.toString() +
                ", createdOn='" + createdOn + '\''.toString() +
                '}'.toString()
    }

}