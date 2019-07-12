package tech.inscripts.ins_armman.mMitra.incompleteForms

import android.database.Cursor
import tech.inscripts.ins_armman.mMitra.data.database.DatabaseContract
import tech.inscripts.ins_armman.mMitra.data.model.IncompleteFilledForm

class IncomleteFormPresenter : IIncompleteFormPresenter<IncompleteFormActivity> {


    var incompleteView :IncompleteFormActivity?=null
    var interactor : IncompleteFormInteractor?=null

    /**
     * this method is used to get the list of women whose forms are incomplete
     */
    override fun getIncompleteFormList() {
var space= " "
        var womenList : ArrayList<IncompleteFilledForm> =ArrayList()
        var cursor : Cursor = interactor?.getIncompleteFormList()!!
        if(cursor!=null && cursor.moveToFirst()){
            do{
               var fName=cursor.getString(cursor.getColumnIndex("first_name"))
               var mName=cursor.getString(cursor.getColumnIndex("middle_name"))
               var lName=cursor.getString(cursor.getColumnIndex("last_name"))
               var name= fName+space+mName+space+lName
                womenList.add(
                    IncompleteFilledForm(
                        cursor.getString(cursor.getColumnIndex("unique_id")),
                        name,
                        cursor.getString(cursor.getColumnIndex("form_id")),
                        cursor.getInt(cursor.getColumnIndex(DatabaseContract.FilledFormStatusTable.COLUMN_FORM_COMPLETION_STATUS))
                    )
                )
            }while(cursor.moveToNext())
        }
        incompleteView?.setAdapter(womenList)

    }

    override fun getUniqueIdFormId(uniqueId: String) {

       var cursor : Cursor= interactor?.getLastCompleteFilledForm(uniqueId)!!
        var formId= (cursor.getString(cursor.getColumnIndex("form_id")) ).toInt()
        incompleteView?.openActivity(uniqueId,formId)


    }

    override fun attachView(view: IncompleteFormActivity) {
    this.incompleteView=view
        interactor= IncompleteFormInteractor(incompleteView?.getContext())
    }

    override fun detachView() {
        incompleteView=null
    }
}