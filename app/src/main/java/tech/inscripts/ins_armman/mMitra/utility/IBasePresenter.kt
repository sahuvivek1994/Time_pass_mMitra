package tech.inscripts.ins_armman.mMitra.utility

interface IBasePresenter<V> {
    fun attachView(view :V)
    fun detachView()
}