package tech.inscripts.ins_armman.mMitra.homeactivity

import android.os.AsyncTask

class ResetFailureTask : AsyncTask<Void, Void, Void> {
    var mOnResetTaskCompleted : IMainActivityPresentor.OnResetTaskCompleted?=null
    var mMainActivityInteractor : IMainActivityInteractor?=null

    constructor(mOnResetTaskCompleted: IMainActivityPresentor.OnResetTaskCompleted?, mMainActivityInteractor: IMainActivityInteractor?) : super() {
        this.mOnResetTaskCompleted = mOnResetTaskCompleted
        this.mMainActivityInteractor = mMainActivityInteractor
    }


    override fun doInBackground(vararg params: Void?): Void? {
    mMainActivityInteractor?.resetFailureStatus()
        return null
    }

    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)
        mOnResetTaskCompleted?.onResetCompleted()
    }
}