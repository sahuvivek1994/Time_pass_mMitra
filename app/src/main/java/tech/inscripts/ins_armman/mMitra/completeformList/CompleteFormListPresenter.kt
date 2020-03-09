package tech.inscripts.ins_armman.mMitra.completeformList

import android.database.Cursor
import tech.inscripts.ins_armman.mMitra.data.model.CompleteFormQnA
import java.util.ArrayList

class CompleteFormListPresenter : ICompleteFormListPresenter<CompleteFormListActivity> {

    internal var view: ICompleteFormListView? = null
    internal lateinit var interactor: CompleteFormListInteractor
    internal var formDetails = ArrayList<CompleteFormQnA>()
    internal var formId = 0
    internal var count = 0
    internal var c = 0
    internal var res: Cursor? = null
    internal lateinit var child_name: String
    internal lateinit var formName:String

    override fun getCompleteFormList(unique_mother_id: String) {
        res = interactor.getCompleteFormList(unique_mother_id)
        if ((res != null) and res!!.moveToFirst()) {
            do {
                val obj = CompleteFormQnA()
                obj.formName = res!!.getString(res!!.getColumnIndex("visit_name"))
                obj.form_id = res!!.getInt(res!!.getColumnIndex("form_id"))
                formDetails.add(obj)
            } while (res!!.moveToNext())
        }
        if (!formDetails.isEmpty()) {
            view?.getData(formDetails)
        }
    }

    override fun attachView(view: CompleteFormListActivity) {
    this.view= view
        this.interactor= CompleteFormListInteractor(view.getContext())
    }

    override fun detachView() {
        view=null
    }

    override fun checkFormPresent(): Int? {
        var value = interactor?.checkFormPresent()
        return value
    }

    override fun checkRegFormFilled(uniqueId : String): String {
        return interactor?.checkRegFormFilled(uniqueId)
    }
}