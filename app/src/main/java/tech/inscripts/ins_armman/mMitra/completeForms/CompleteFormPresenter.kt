package tech.inscripts.ins_armman.mMitra.completeForms

import android.database.Cursor
import tech.inscripts.ins_armman.mMitra.data.model.completeFilledForm
import java.util.ArrayList

class CompleteFormPresenter : ICompleteFormPresenter<CompleteFormActivity> {

    internal var icompleteFormView: ICompleteFormView? = null
    internal var completeFormInteractor: CompleteFormInteractor?=null

    override fun getListCompleteForm() {
        val womenList = ArrayList<completeFilledForm>()
        var cur : Cursor = completeFormInteractor!!.fetchListCompleteForm()
    }

    override fun attachView(view: CompleteFormActivity) {
    }

    override fun detachView() {
    }
}