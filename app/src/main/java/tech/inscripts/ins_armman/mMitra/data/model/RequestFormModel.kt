package tech.inscripts.ins_armman.mMitra.data.model

import com.google.gson.annotations.SerializedName

class RequestFormModel : UserDetails() {
    @SerializedName("hash")
    private var hash : String?=null
    public fun setHash(hash : String){
        this.hash=hash
    }
}