package tech.inscripts.ins_armman.mMitra.data.model.referral

import com.google.gson.annotations.SerializedName
import tech.inscripts.ins_armman.mMitra.data.database.DatabaseContract

class ReferralKeys {
    @SerializedName(DatabaseContract.ReferralTable.COLUMN_NAME_REFERRED_TO)
    private var refferedTo: String? = null
    @SerializedName(DatabaseContract.ReferralTable.COLUMN_NAME_REFERRAL_DATE)
    private var refferedDate: String? = null
    @SerializedName(DatabaseContract.ReferralTable.COLUMN_NAME_FORM_ID)
    private var formId: String? = null

    fun setRefferedTo(refferedTo: String) {
        this.refferedTo = refferedTo
    }

    fun setRefferedDate(refferedDate: String) {
        this.refferedDate = refferedDate
    }

    fun setFormId(formId: String) {
        this.formId = formId
    }
}