package tech.inscripts.ins_armman.mMitra.incompleteforms

import android.content.Context
import android.database.Cursor
import tech.inscripts.ins_armman.mMitra.data.database.DBHelper

class IncompleteFormInteractor : IIncompleteInteractor {
    override fun getLastCompleteFilledForm(uniqueId: String): Int? {
        return dbHelper?.getLastCompleteFilledFormId(uniqueId)
    }

    var context : Context?=null
    var dbHelper : DBHelper?=null

    constructor(context: Context?) {
        this.context = context
        dbHelper = DBHelper(context)
    }


    override fun getIncompleteFormList(): Cursor {
        return dbHelper?.incompleteFormListList!!
    }
}