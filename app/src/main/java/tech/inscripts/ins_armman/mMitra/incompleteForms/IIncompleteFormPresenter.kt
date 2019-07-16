package tech.inscripts.ins_armman.mMitra.incompleteForms

import tech.inscripts.ins_armman.mMitra.utility.IBasePresenter

interface IIncompleteFormPresenter<V> : IBasePresenter<V> {
    fun getIncompleteFormList()
     fun getUniqueIdFormId(uniqueId: String)
}