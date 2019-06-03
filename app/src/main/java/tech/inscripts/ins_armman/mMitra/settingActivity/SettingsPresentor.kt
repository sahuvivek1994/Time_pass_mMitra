package tech.inscripts.ins_armman.mMitra.settingActivity

import android.content.Context
import tech.inscripts.ins_armman.mMitra.data.model.restoreData.RestoreDataRequest

class SettingsPresentor : ISettingsPresentor<ISettingsView>, ISettingsInteractor.OnFormDownloadFinished, ISettingsInteractor.onCheckUpdateFinished
    , ISettingsInteractor.onHelpManualDownloadFinished, ISettingsInteractor.OnRegistrationsDownloadFinished
    , ISettingsInteractor.OnVisitsDownloadFinished, ISettingsInteractor.OnReferralDownloadFinished
    , ISettingsInteractor.OnGrowthMonitoringFinished {

    private val FETCH_USER_DATA = 101
    private val FETCH_FORM_HASH = 102

    private var mSettingsView: ISettingsView? = null
    private var mSettingsInteractor: SettingsInteractor? = null
    private var mUsername: String? = null
    private var mPassword:String? = null
    private var mFormHash:String? = null

    private var totalPagesCalculated: Boolean = false

    private var totalPages: Int = 0
    private var pageCounter: Int = 0

    private var mRequest: RestoreDataRequest? = null

    override fun changeLanguage(context: Context, language: String) {

    }

    override fun downloadForms() {
    }

    override fun downloadHelpManual() {
    }

    override fun checkUpdate() {
    }

    override fun downloadApk(apkLink: String) {
    }

    override fun setApkDownloadProgress(progress: Int) {
    }

    override fun onApkDownloaded() {
    }

    override fun logout() {
    }

    override fun restoreData() {
    }

    override fun restoreRegistrations(pageNumber: Int) {
    }

    override fun restoreVisits(pageNumber: Int) {
    }

    override fun restoreReferrals(pageNumber: Int) {
    }

    override fun restoreGrowthMonitorings(pageNumber: Int) {
    }

    override fun resetDataMemberValues() {
    }

    override fun attachView(view: ISettingsView?) {

    }

    override fun detachView() {
    }
}