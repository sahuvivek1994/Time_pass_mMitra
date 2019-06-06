package tech.inscripts.ins_armman.mMitra.data.model

import com.google.gson.annotations.SerializedName
import tech.inscripts.ins_armman.mMitra.data.model.syncing.BeneficiaryDetails
import tech.inscripts.ins_armman.mMitra.utility.Constants.BENEFICIARIES

class SyncRegistrationDetails : UserDetails() {
    @SerializedName(BENEFICIARIES)
    private var regData : ArrayList<BeneficiaryDetails>?=null
    fun setRegData(regData : ArrayList<BeneficiaryDetails>){
        this.regData=regData
    }

    override fun toString(): String {
        return "SyncRegistrationDetails(regData=$regData)"
    }

}