package tech.inscripts.ins_armman.mMitra.completeformList

import android.database.Cursor

interface ICompleteFormListInteractor {
     fun getCompleteFormList(unique_mother_id : String): Cursor
     fun checkFormPresent() : Int
     fun checkRegFormFilled(uniqueId : String) : String
}