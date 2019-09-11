package tech.inscripts.ins_armman.mMitra.completeformsdetails

import android.content.Context
import android.database.Cursor
import tech.inscripts.ins_armman.mMitra.data.database.DBHelper

class CompleteFormsDetailsInteractor : ICompleteFormsDetailsInteractor {

    internal var mContext: Context
    internal var db: DBHelper

    constructor(mContext: Context) {
        this.mContext = mContext
        this.db = DBHelper(mContext)
    }

    override fun displayFormDetails(unique_id: String, form_id: Int): Cursor {
        return db.getCompleteFormDetails(unique_id, form_id)
    }
    override fun getAnswerLabel(ansArray : List<String>) : List<String> {
    return db.getAnswerLabel(ansArray) as List<String>
    }
    override fun getQuestionType(queKeyword: String): String {
    return db.getQuestionType(queKeyword)
    }
    override fun getDependantQuestion(que_keyword: String): String {
    return db.dependantQuestion(que_keyword)
    }
}