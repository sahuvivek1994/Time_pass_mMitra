package tech.inscripts.ins_armman.mMitra.data.model.syncing

import com.google.gson.annotations.SerializedName
import tech.inscripts.ins_armman.mMitra.utility.Constants.*

class QuestionAnswer {
    @SerializedName(QUESTION_KEYWORD)
    var keyword :String?=null
    @SerializedName(ANSWER)
    var answer :String?=null
    @SerializedName(CREATED_ON)
    var createdOn :String?=null

    override fun toString(): String {
        return "QuestionAnswer{" +
                "keyword='" + keyword + '\''.toString() +
                ", answer='" + answer + '\''.toString() +
                ", createdOn='" + createdOn + '\''.toString() +
                '}'.toString()
    }

}