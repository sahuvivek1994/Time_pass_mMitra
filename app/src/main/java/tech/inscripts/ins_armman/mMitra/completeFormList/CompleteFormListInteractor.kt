package tech.inscripts.ins_armman.mMitra.completeFormList

import android.content.Context
import android.database.Cursor
import tech.inscripts.ins_armman.mMitra.data.database.DBHelper

class CompleteFormListInteractor : ICompleteFormListInteractor {

      var mContext: Context?=null
      var dbHelper: DBHelper

    constructor(mContext: Context?) {
        this.mContext = mContext
        this.dbHelper = DBHelper(mContext)
    }


    override fun getCompleteFormList(): Cursor {
    return dbHelper.getFormsList()
    }


}