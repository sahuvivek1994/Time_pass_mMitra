package tech.inscripts.ins_armman.mMitra.data.model.syncing

import com.google.gson.annotations.SerializedName
import tech.inscripts.ins_armman.mMitra.data.model.UserDetails
import tech.inscripts.ins_armman.mMitra.utility.Constants.*

class FormDetails : UserDetails() {
    @SerializedName(UNIQUE_ID)
    var uniqueId:String?=null
    @SerializedName(FORM_ID)
    var formId:String?=null
    @SerializedName(CHILD_STATUS)
    var childStatus:Int?=null
    @SerializedName(DATA)
    var data: ArrayList<QuestionAnswer>?=null
    @SerializedName(REFERRAL)
    var referral: ArrayList<Referral>?=null

}