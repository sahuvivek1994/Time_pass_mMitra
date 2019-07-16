package tech.inscripts.ins_armman.mMitra.completeFormsDetails

import tech.inscripts.ins_armman.mMitra.utility.IBasePresenter

interface ICompleteFormsDetailsPresenter<V> : IBasePresenter<V> {
    abstract fun displayFIlledForm(unique_id: String, form_id: Int)

}