package tech.inscripts.ins_armman.mMitra.menu

import android.database.Cursor
import tech.inscripts.ins_armman.mMitra.data.model.ArogyasakhiInfoModel
import tech.inscripts.ins_armman.mMitra.utility.IBasePresenter

interface IHomeActivityPresentor<V> : IBasePresenter<V> {
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
}