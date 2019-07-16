package tech.inscripts.ins_armman.mMitra.completeForms

import android.content.Context
import tech.inscripts.ins_armman.mMitra.data.model.completeFilledForm

interface ICompleteFormView {
    fun setAdapter(mWomenList : List<completeFilledForm>)
    fun getContext() : Context
}