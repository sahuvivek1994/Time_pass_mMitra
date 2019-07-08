package tech.inscripts.ins_armman.mMitra.completeForms

import android.content.Context
import android.database.Cursor
import tech.inscripts.ins_armman.mMitra.data.database.DBHelper

class CompleteFormInteractor : ICompleteInteractor{

    private var mContext: Context ?= null
    private var dbHelper: DBHelper?=null

    constructor(mContext: Context?) {
        this.mContext = mContext
        this.dbHelper = DBHelper(mContext)
    }

    override fun fetchListCompleteForm(): Cursor? {
    return dbHelper?.getcompleteFormListList()
    }

}