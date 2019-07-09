package tech.inscripts.ins_armman.mMitra.completeFormList

import android.database.Cursor

interface ICompleteFormListInteractor {
     fun getCompleteFormList(unique_mother_id : String): Cursor
}