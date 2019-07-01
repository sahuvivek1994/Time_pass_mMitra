package tech.inscripts.ins_armman.mMitra.completeForms

import android.content.Context
import tech.inscripts.ins_armman.mMitra.data.model.syncing.completeFiledForm

interface ICompleteFormView {
    fun setAdapter(mWomenList : List<completeFiledForm>)
    fun getContext() : Context
}