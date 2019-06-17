package tech.inscripts.ins_armman.mMitra.data.model.referral

import com.google.gson.annotations.SerializedName
import tech.inscripts.ins_armman.mMitra.data.database.DatabaseContract
import tech.inscripts.ins_armman.mMitra.data.model.UserDetails
import java.util.ArrayList

class ReferralModel : UserDetails() {
    @SerializedName(DatabaseContract.ReferralTable.COLUMN_UNIQUE_ID)
    private var uniqueId: String? = null

    @SerializedName("visits")
    private var referralKeys: ArrayList<ReferralKeys>? = null

    fun setUniqueId(uniqueId: String) {
        this.uniqueId = uniqueId
    }

    fun setReferralKeys(referralKeys: ArrayList<ReferralKeys>) {
        this.referralKeys = referralKeys
    }
}