package tech.inscripts.ins_armman.mMitra.incompleteforms

import android.database.Cursor
import tech.inscripts.ins_armman.mMitra.data.database.DatabaseContract
import tech.inscripts.ins_armman.mMitra.data.model.IncompleteFilledForm

class IncomleteFormPresenter : IIncompleteFormPresenter<IncompleteFormActivity> {


    var incompleteView :IncompleteFormActivity?=null
    var interactor : IncompleteFormInteractor?=null
    var womenList : ArrayList<IncompleteFilledForm> =ArrayList()
    /**
     * this method is used to get the list of women whose forms are incomplete
     */
    override fun getIncompleteFormList() {
var space= " "

        var cursor : Cursor = interactor?.getIncompleteFormList()!!
       var a= cursor.count
        if(a!=null || a>0) {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    var name = cursor.getString(cursor.getColumnIndex("name"))
                    womenList.add(
                        IncompleteFilledForm(
                            cursor.getString(cursor.getColumnIndex("unique_id")),
                            name,
                            cursor.getString(cursor.getColumnIndex("form_id")),
                            cursor.getInt(cursor.getColumnIndex(DatabaseContract.FilledFormStatusTable.COLUMN_FORM_COMPLETION_STATUS))
                        )
                    )
                } while (cursor.moveToNext())
            }
            incompleteView?.setAdapter(womenList)
        }
        else
            incompleteView?.setAdapter(womenList)
    }

    override fun getUniqueIdFormId(uniqueId: String) : Int? {
        var formId= interactor?.getLastCompleteFilledForm(uniqueId)
        if (formId != null) {
            formId = formId +1
        }
        incompleteView?.openActivity(uniqueId,formId)
        return formId
    }

    override fun attachView(view: IncompleteFormActivity) {
    this.incompleteView=view
        interactor= IncompleteFormInteractor(incompleteView?.getContext())
    }

    override fun detachView() {
        incompleteView=null
    }
}