package tech.inscripts.ins_armman.mMitra.completeFormsDetails

import android.content.Context
import android.database.Cursor
import tech.inscripts.ins_armman.mMitra.data.database.DBHelper

class CompleteFormsDetailsInteractor : ICompleteFormsDetailsInteractor {
    internal var mContext: Context
    internal var db: DBHelper

    constructor(mContext: Context) {
        this.mContext = mContext
        this.db = DBHelper(mContext)
    }

    override fun displayFormDetails(unique_id: String, form_id: Int): Cursor {
        return db.getCompleteFormDetails(unique_id, form_id)
    }

}