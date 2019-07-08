package tech.inscripts.ins_armman.mMitra.completeForms

import android.database.Cursor
import tech.inscripts.ins_armman.mMitra.data.model.completeFilledForm
import java.util.ArrayList

class CompleteFormPresenter : ICompleteFormPresenter<CompleteFormActivity> {

     var icompleteFormView: ICompleteFormView? = null
     var completeFormInteractor: CompleteFormInteractor?=null

    override fun getListCompleteForm() {
        val womenList = ArrayList<completeFilledForm>()
      var cursor : Cursor? = completeFormInteractor?.fetchListCompleteForm()
        if (cursor != null && cursor.moveToFirst())
            do {
                var fName = cursor.getString(cursor.getColumnIndex("first_name"))
                var mName = cursor.getString(cursor.getColumnIndex("middle_name"))
                var lName = cursor.getString(cursor.getColumnIndex("last_name"))
                var space = " "
                var fullName= fName+space+mName+space+lName
                womenList.add(
                    completeFilledForm(fullName,cursor.getString(cursor.getColumnIndex("unique_id"))))
            } while (cursor.moveToNext())

        icompleteFormView?.setAdapter(womenList)
    }

    override fun attachView(completeFormView: CompleteFormActivity) {
        this.icompleteFormView = completeFormView
        completeFormInteractor = CompleteFormInteractor(completeFormView.getContext())
    }

    override fun detachView() {
        icompleteFormView = null
    }
}