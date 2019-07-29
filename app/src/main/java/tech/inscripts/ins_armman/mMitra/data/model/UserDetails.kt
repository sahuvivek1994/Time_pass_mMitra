package tech.inscripts.ins_armman.mMitra.data.model

import com.google.gson.annotations.SerializedName


import java.util.ArrayList



 open class UserDetails {

    @SerializedName("username")
    var userName: String = ""
    @SerializedName("password")
    var password: String = ""
    @SerializedName("imei")
    private var imei: ArrayList<String>? = null
    //private var imei: String = ""

    @SerializedName("showdata")
    private var showdata: String? = null

     @SerializedName("userId")
     private var userId: String? = null


    fun setShowdata(showdata: String) {
        this.showdata = showdata
    }


    fun setImei(imei: ArrayList<String>?) {
        this.imei = imei
    }

     /*fun setImei(imei: String) {
         this.imei = imei

     }*/
    fun setpassword(pass: String)
    {
        this.password = pass
    }

     fun setusername(user: String)
    {
        this.userName = user
    }

     fun setUserId(userId : String)
     {
         this.userId=userId
     }
     fun getUserId(): String? {
         return userId
     }



    override fun toString(): String {
        return "UserDetails{" +
                "userName='" + userName + '\''.toString() +
                ", password='" + password + '\''.toString() +

                ", imei=" + imei +
                ", showdata='" + showdata + '\''.toString() +
                '}'.toString()
    }

//                ", showdata='" + showdata + '\''.toString() +
//                '}'.toString()
    }



