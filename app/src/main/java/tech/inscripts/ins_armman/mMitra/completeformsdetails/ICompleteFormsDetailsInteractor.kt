package tech.inscripts.ins_armman.mMitra.completeformsdetails

import android.database.Cursor

interface ICompleteFormsDetailsInteractor {
     fun displayFormDetails(id: String, form_id: Int): Cursor
     fun getAnswerLabel(ansArray : List<String>) : List<String>
     fun getQuestionType(queType : String) : String
     fun getDependantQuestion(que_keyword : String) : String

}