package tech.inscripts.ins_armman.mMitra.data.model.restoreData

import com.google.gson.annotations.SerializedName
import tech.inscripts.ins_armman.mMitra.data.model.syncing.Referral

class RestoreReferral {
    @SerializedName("total")
    var total:Int?=null
    @SerializedName("dataSource")
    var referralList: ArrayList<Referral>?=null

    override fun toString(): String {
        return "RestoreReferral(total=$total, referralList=$referralList)"
    }

}