package tech.inscripts.ins_armman.mMitra.settingActivity

import android.content.Context
import android.database.Cursor
import tech.inscripts.ins_armman.mMitra.utility.IBasePresenter

interface ISettingsPresentor<V>:IBasePresenter<V> {
     fun changeLanguage(context: Context, language: String)
     fun downloadForms()
     //fun downloadHelpManual()
     fun checkUpdate()
     fun downloadApk(apkLink: String)
     fun setApkDownloadProgress(progress: Int)
     fun onApkDownloaded()
     fun logout()
     fun restoreData()
     fun restoreRegistrations(pageNumber: Int)
     fun restoreVisits(pageNumber: Int)
    // fun restoreReferrals(pageNumber: Int)
    // fun restoreGrowthMonitorings(pageNumber: Int)
     fun resetDataMemberValues()

    interface OnQueryFinished {

        fun onSuccess(cursor: Cursor, id: Int)

        fun onSuccess()

        fun onFailure()
    }
}