package tech.inscripts.ins_armman.mMitra.completeFormsDetails

import android.database.Cursor

interface ICompleteFormsDetailsInteractor {
    abstract fun displayFormDetails(id: String, form_id: Int): Cursor
    abstract fun displayForm6Details(id: String, form_id: Int): Cursor
}