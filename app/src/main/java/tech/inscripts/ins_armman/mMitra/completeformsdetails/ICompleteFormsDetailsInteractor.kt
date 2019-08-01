package tech.inscripts.ins_armman.mMitra.completeformsdetails

import android.database.Cursor

interface ICompleteFormsDetailsInteractor {
     fun displayFormDetails(id: String, form_id: Int): Cursor

}