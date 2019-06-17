package tech.inscripts.ins_armman.mMitra.data.model.restoreData

import com.google.gson.annotations.SerializedName
import tech.inscripts.ins_armman.mMitra.data.model.UserDetails


class RestoreDataRequest : UserDetails() {
    @SerializedName("limit")
    private var limit: Int = 0

    @SerializedName("pg")
    private var pageNumber: Int = 0

    fun setLimit(limit: Int) {
        this.limit = limit
    }

    fun setPageNumber(pageNumber: Int) {
        this.pageNumber = pageNumber
    }
}
