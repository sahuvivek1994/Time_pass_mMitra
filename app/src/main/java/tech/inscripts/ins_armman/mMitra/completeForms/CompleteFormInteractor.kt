package tech.inscripts.ins_armman.mMitra.completeForms

import android.content.Context
import android.database.Cursor
import tech.inscripts.ins_armman.mMitra.data.database.DBHelper

class CompleteFormInteractor : ICompleteInteractor{

    private var mContext: Context ?= null
    private var dbHelper: DBHelper?=null

    constructor(mContext: Context?, dbHelper: DBHelper?) {
        this.mContext = mContext
        this.dbHelper = dbHelper
    }

    override fun fetchListCompleteForm(): Cursor {
    return null
    }

}