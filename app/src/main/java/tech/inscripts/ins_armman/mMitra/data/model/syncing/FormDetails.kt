package tech.inscripts.ins_armman.mMitra.data.model.syncing

import com.google.gson.annotations.SerializedName
import tech.inscripts.ins_armman.mMitra.data.model.UserDetails
import tech.inscripts.ins_armman.mMitra.utility.Constants.*

class FormDetails : UserDetails() {
    @SerializedName(UNIQUE_ID)
    private var uniqueId:String?=null
    @SerializedName(FORM_ID)
    private var formId:String?=null
    @SerializedName(DATA)
    private var data: ArrayList<QuestionAnswer>?=null

    fun setUniqueId(uniqueId: String) {
        this.uniqueId = uniqueId
    }
    fun getUniqueId(): String? {
        return uniqueId
    }

    fun setFormId(formId: String) {
        this.formId = formId
    }
    fun getFormId(): String? {
        return formId
    }

    fun setData(data: ArrayList<QuestionAnswer>) {
        this.data = data
    }
    fun getData(): ArrayList<QuestionAnswer>? {
        return data
    }

}