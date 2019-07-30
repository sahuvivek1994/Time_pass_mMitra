package tech.inscripts.ins_armman.mMitra.menu

import android.os.AsyncTask

class ResetFailureTask : AsyncTask<Void, Void, Void> {
    var mOnResetTaskCompleted : IHomeActivityPresentor.OnResetTaskCompleted?=null
    var mHomeActivityInteractor : IHomeActivityInteractor?=null

    constructor(mOnResetTaskCompleted: IHomeActivityPresentor.OnResetTaskCompleted?, mHomeActivityInteractor: IHomeActivityInteractor?) : super() {
        this.mOnResetTaskCompleted = mOnResetTaskCompleted
        this.mHomeActivityInteractor = mHomeActivityInteractor
    }


    override fun doInBackground(vararg params: Void?): Void? {
    mHomeActivityInteractor?.resetFailureStatus()
        return null
    }

    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)
        mOnResetTaskCompleted?.onResetCompleted()
    }
}