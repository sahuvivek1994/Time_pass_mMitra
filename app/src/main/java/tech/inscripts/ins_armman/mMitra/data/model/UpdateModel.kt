package tech.inscripts.ins_armman.mMitra.data.model

import com.google.gson.annotations.SerializedName

class UpdateModel {
    @SerializedName("status")
    private var status: Boolean = false
    @SerializedName("data")
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
        var id: String? = null
        @SerializedName("name")
        var name: String? = null
        @SerializedName("versionName")
        var versionName: String? = null
        @SerializedName("versionCode")
        var versionCode: String? = null
        @SerializedName("description")
        var description: String? = null
        @SerializedName("link")
        var link: String? = null
    }
}