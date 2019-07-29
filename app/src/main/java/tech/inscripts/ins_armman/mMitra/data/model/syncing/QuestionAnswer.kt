package tech.inscripts.ins_armman.mMitra.data.model.syncing

import com.google.gson.annotations.SerializedName
import tech.inscripts.ins_armman.mMitra.utility.Constants.*

class QuestionAnswer {
    @SerializedName(QUESTION_KEYWORD)
    private var keyword :String=""
    @SerializedName(ANSWER)
    private var answer :String=""
    @SerializedName(CREATED_ON)
    private var createdOn :String=""

    fun setKeyword(keyword: String) {
        this.keyword = keyword
    }

    fun setAnswer(answer: String) {
        this.answer = answer
    }

    fun setCreatedOn(createdOn: String) {
        this.createdOn = createdOn
    }

    fun getKeyword(): String {
        return keyword
    }

    fun getAnswer(): String {
        return answer
    }

    fun getCreatedOn(): String {
        return createdOn
    }
    override fun toString(): String {
        return "QuestionAnswer{" +
                "keyword='" + keyword + '\''.toString() +
                ", answer='" + answer + '\''.toString() +
                ", createdOn='" + createdOn + '\''.toString() +
                '}'.toString()
    }

}