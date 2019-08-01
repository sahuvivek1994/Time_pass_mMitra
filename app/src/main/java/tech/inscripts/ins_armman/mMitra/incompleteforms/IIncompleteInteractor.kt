package tech.inscripts.ins_armman.mMitra.incompleteforms

import android.database.Cursor

interface IIncompleteInteractor {
    fun getIncompleteFormList() : Cursor
     fun getLastCompleteFilledForm(unique: String): Cursor
}