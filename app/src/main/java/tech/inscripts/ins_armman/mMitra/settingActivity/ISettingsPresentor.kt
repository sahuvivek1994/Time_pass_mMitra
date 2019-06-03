package tech.inscripts.ins_armman.mMitra.settingActivity

import android.content.Context
import android.database.Cursor
import tech.inscripts.ins_armman.mMitra.utility.IBasePresenter

interface ISettingsPresentor<V>:IBasePresenter<V> {
    abstract fun changeLanguage(context: Context, language: String)
    abstract fun downloadForms()
    abstract fun downloadHelpManual()
    abstract fun checkUpdate()
    abstract fun downloadApk(apkLink: String)
    abstract fun setApkDownloadProgress(progress: Int)
    abstract fun onApkDownloaded()
    abstract fun logout()
    abstract fun restoreData()
    abstract fun restoreRegistrations(pageNumber: Int)
    abstract fun restoreVisits(pageNumber: Int)
    abstract fun restoreReferrals(pageNumber: Int)
    abstract fun restoreGrowthMonitorings(pageNumber: Int)
    abstract fun resetDataMemberValues()

    interface OnQueryFinished {

        fun onSuccess(cursor: Cursor, id: Int)

        fun onSuccess()

        fun onFailure()
    }
}