package tech.inscripts.ins_armman.mMitra.data.model

import com.google.gson.annotations.SerializedName

import java.util.ArrayList


open class UserDetails {
    @SerializedName("username")
    var userName: String? = null
    @SerializedName("password")
    var password: String? = null
    @SerializedName("imei")
    private var imei: ArrayList<String>? = null
    @SerializedName("showdata")
    private var showdata: String? = null



    fun setShowdata(showdata: String) {
        this.showdata = showdata
    }

    fun setImei(imei: ArrayList<String>) {
        this.imei = imei
    }

    override fun toString(): String {
        return "UserDetails{" +
                "userName='" + userName + '\''.toString() +
                ", password='" + password + '\''.toString() +
                ", imei=" + imei +
                ", showdata='" + showdata + '\''.toString() +
                '}'.toString()
    }
}
