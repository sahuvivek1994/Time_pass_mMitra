package tech.inscripts.ins_armman.mMitra.incompleteforms

import android.content.Context
import tech.inscripts.ins_armman.mMitra.data.model.IncompleteFilledForm

interface IIncompleteFormView {
    fun getContext() : Context
    fun setAdapter(mWomenList : ArrayList<IncompleteFilledForm>)
    fun openActivity(uniqueId : String, formId : Int?)
}