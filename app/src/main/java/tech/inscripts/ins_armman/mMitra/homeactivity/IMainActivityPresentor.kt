package tech.inscripts.ins_armman.mMitra.homeactivity

import android.database.Cursor
import tech.inscripts.ins_armman.mMitra.data.model.ArogyasakhiInfoModel
import tech.inscripts.ins_armman.mMitra.utility.IBasePresenter
import java.util.ArrayList

interface IMainActivityPresentor<V> : IBasePresenter<V> {
    fun getLoginDetail(userDetails : ArrayList<String>)
    fun fetchRegistrationData()
    fun onFetchedRegistrationData(cursor: Cursor)
    fun fetchUnsentFormsCount()
    fun syncUnsentForms()
    interface OnQueryFinished {

        fun onSuccess(cursor: Cursor, id: Int)

        fun onSuccess()

        fun onFailure()
    }

    interface OnResetTaskCompleted {

        fun onResetCompleted()
    }

    interface OnDueOverdueCalculationTaskCompleted {

        fun onDueOverdueTaskCompleted(infoModel: ArogyasakhiInfoModel)


        fun onTaskFailed()
    }

    fun checkUpdate()
    fun restoreData()
    fun logout()
    fun downloadForms()
     /*fun restoreRegistrations(pageNumber: Int)
     fun resetDataMemberValues()*/
}