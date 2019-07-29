package tech.inscripts.ins_armman.mMitra.data.model

import com.google.gson.annotations.SerializedName

class UpdateModel {
    @SerializedName("status")
    private var status: Boolean = false
    @SerializedName("dataSource")
    private var data: Data? = null

    fun getStatus(): Boolean {
        return status
    }

    fun setStatus(status: Boolean) {
        this.status = status
    }

    fun getData(): Data? {
        return data
    }

    fun setData(data: Data) {
        this.data = data
    }

    class Data {
        @SerializedName("id")
        var id: String=""
        @SerializedName("firstName")
        var name: String=""
        @SerializedName("versionName")
        var versionName: String=""
        @SerializedName("versionCode")
        var versionCode: String =""
        @SerializedName("description")
        var description: String=""
        @SerializedName("link")
        var link: String = ""
    }
}