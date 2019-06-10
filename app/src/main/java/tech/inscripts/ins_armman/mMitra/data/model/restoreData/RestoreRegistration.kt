package tech.inscripts.ins_armman.mMitra.data.model.restoreData

import com.google.gson.annotations.SerializedName
import tech.inscripts.ins_armman.mMitra.data.model.syncing.BeneficiaryDetails
import java.util.ArrayList

class RestoreRegistration {
    @SerializedName("total")
    private val total: Int = 0
    @SerializedName("data")
    private val registrationData: ArrayList<BeneficiaryDetails>? = null

    fun getTotal(): Int {
        return total
    }

    fun getRegistrationData(): ArrayList<BeneficiaryDetails> {
        return this!!.registrationData!!
    }
}