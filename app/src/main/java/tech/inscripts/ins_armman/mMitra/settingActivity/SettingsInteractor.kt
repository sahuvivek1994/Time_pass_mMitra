package tech.inscripts.ins_armman.mMitra.settingActivity

import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import org.json.JSONObject
import tech.inscripts.ins_armman.mMitra.data.model.RequestFormModel
import tech.inscripts.ins_armman.mMitra.data.model.restoreData.BeneficiariesList
import tech.inscripts.ins_armman.mMitra.data.model.restoreData.RestoreDataRequest
import tech.inscripts.ins_armman.mMitra.data.model.syncing.BeneficiaryDetails
import tech.inscripts.ins_armman.mMitra.data.model.syncing.Referral
import tech.inscripts.ins_armman.mMitra.data.model.syncing.RequestHelpModel
import tech.inscripts.ins_armman.mMitra.data.retrofit.RemoteDataSource
import tech.inscripts.ins_armman.mMitra.utility.Utility
import java.util.ArrayList

class SettingsInteractor:ISettingsInteractor,LoaderManager.LoaderCallbacks<Cursor> {

    lateinit var mContext : Context
    var mOnQueryFinished : ISettingsPresentor.OnQueryFinished ?=null
        lateinit var mSettingsPresentor:SettingsPresentor
    var instance= RemoteDataSource()

    constructor(mContext: Context, mOnQueryFinished: ISettingsPresentor.OnQueryFinished, mSettingsPresentor: SettingsPresentor) {
        this.mContext = mContext
        this.mOnQueryFinished = mOnQueryFinished
        this.mSettingsPresentor = mSettingsPresentor
    }


    override fun changeLocale(context: Context, language: String) {
    Utility.setApplicationLocale(context,language)
    }

    override fun downloadForms(requestFormModel: RequestFormModel, onFormDownloadFinished: ISettingsInteractor.OnFormDownloadFinished) {
var remoteDataSource : RemoteDataSource= RemoteDataSource.
    }

    override fun fetchLoginDetails(id: Int) {
    }

    override fun fetchFormJsonHash(id: Int) {
    }

    override fun saveFormData(formJsonObject: JSONObject) {
    }

    override fun downloadHelpManual(
        helpModel: RequestHelpModel,
        downloadFinished: ISettingsInteractor.onHelpManualDownloadFinished
    ) {
    }

    override fun saveHelpManualData(formJsonObject: JSONObject) {
    }

    override fun checkReleaseUpdate(onCheckUpdateFinished: ISettingsInteractor.onCheckUpdateFinished) {
    }

    override fun downloadAndSaveApk(apkLink: String) {
    }

    override fun getHash(type: String): String {
    }

    override fun downloadRegistrationData(
        request: RestoreDataRequest,
        downloadFinished: ISettingsInteractor.OnRegistrationsDownloadFinished
    ) {
    }

    override fun downloadVisitsData(
        request: RestoreDataRequest,
        downloadFinished: ISettingsInteractor.OnVisitsDownloadFinished
    ) {
    }

    override fun saveDownloadedData(
        listRegistrations: ArrayList<BeneficiaryDetails>,
        listVisits: ArrayList<BeneficiariesList>,
        listReferral: ArrayList<Referral>
    ) {
    }

    override fun addOrUpdateFormHash(item: String, hash: String) {
    }

    override fun deleteLoginDetails() {
    }

    override fun onCreateLoader(p0: Int, p1: Bundle?): Loader<Cursor> {
    }

    override fun onLoadFinished(p0: Loader<Cursor>, p1: Cursor?) {
    }

    override fun onLoaderReset(p0: Loader<Cursor>) {
    }
}