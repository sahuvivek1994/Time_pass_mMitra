package tech.inscripts.ins_armman.mMitra.completeFormList

import android.database.Cursor

interface ICompleteFormListInteractor {
     fun getCompleteFormList(): Cursor
}