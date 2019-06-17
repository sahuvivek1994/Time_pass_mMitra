package tech.inscripts.ins_armman.mMitra.data.model.restoreData

import com.google.gson.annotations.SerializedName
import tech.inscripts.ins_armman.mMitra.data.model.syncing.QuestionAnswer

import java.util.ArrayList

class VisitsList {
    @SerializedName("id")
    var formId: Int = 0
    @SerializedName("created_on")
    var createdOn: String? = null
    @SerializedName("dataSource")
    var questionAnswers: ArrayList<QuestionAnswer>? = null

    override fun toString(): String {
        return "VisitsList{" +
                "formId=" + formId +
                ", createdOn='" + createdOn + '\''.toString() +
                ", questionAnswers=" + questionAnswers +
                '}'.toString()
    }
}
