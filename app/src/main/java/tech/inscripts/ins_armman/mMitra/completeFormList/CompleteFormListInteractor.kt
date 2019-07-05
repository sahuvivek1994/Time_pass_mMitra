package tech.inscripts.ins_armman.mMitra.completeFormList

import android.content.Context
import android.database.Cursor
import tech.inscripts.ins_armman.mMitra.data.database.DBHelper

class CompleteFormListInteractor : ICompleteFormListInteractor {

    internal lateinit var mContext: Context
    internal lateinit var db: DBHelper

    constructor(mContext: Context) {
        this.mContext = mContext
        this.db = db
    }


    override fun getCompleteFormList(): Cursor {
    return db.getFormsList()
    }


}