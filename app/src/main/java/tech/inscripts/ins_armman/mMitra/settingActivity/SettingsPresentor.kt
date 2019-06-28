package tech.inscripts.ins_armman.mMitra.settingActivity

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.database.Cursor
import org.json.JSONException
import org.json.JSONObject
import tech.inscripts.ins_armman.mMitra.R
import tech.inscripts.ins_armman.mMitra.data.database.DatabaseContract
import tech.inscripts.ins_armman.mMitra.data.model.RequestFormModel
import tech.inscripts.ins_armman.mMitra.data.model.UpdateModel
import tech.inscripts.ins_armman.mMitra.data.model.restoreData.BeneficiariesList
import tech.inscripts.ins_armman.mMitra.data.model.restoreData.RestoreDataRequest
import tech.inscripts.ins_armman.mMitra.data.model.restoreData.RestoreRegistration
import tech.inscripts.ins_armman.mMitra.data.model.restoreData.RestoreVisits
import tech.inscripts.ins_armman.mMitra.data.model.syncing.BeneficiaryDetails
import tech.inscripts.ins_armman.mMitra.data.model.syncing.Referral
import tech.inscripts.ins_armman.mMitra.data.model.syncing.RequestHelpModel
import tech.inscripts.ins_armman.mMitra.login.Login
import tech.inscripts.ins_armman.mMitra.settingActivity.ISettingsPresentor.OnQueryFinished
import tech.inscripts.ins_armman.mMitra.utility.Constants.*
import tech.inscripts.ins_armman.mMitra.utility.Utility
import java.util.ArrayList

class SettingsPresentor : ISettingsPresentor<ISettingsView>, ISettingsInteractor.OnFormDownloadFinished, ISettingsInteractor.onCheckUpdateFinished
    , ISettingsInteractor.onHelpManualDownloadFinished, ISettingsInteractor.OnRegistrationsDownloadFinished
    , ISettingsInteractor.OnVisitsDownloadFinished {

val utility= Utility()
    private val FETCH_USER_DATA = 101
    private val FETCH_FORM_HASH = 102

    private  var mSettingsView: ISettingsView? = null
    private var mSettingsInteractor: SettingsInteractor? = null
    private var mUsername: String? = null
    private var mPassword:String? = null
    private var mFormHash:String? = null

    private var totalPagesCalculated: Boolean = false

    private var totalPages: Int = 0
    private var pageCounter: Int = 0

    private lateinit var mRequest: RestoreDataRequest
    private val listRegistrations = ArrayList<BeneficiaryDetails>()
    private val listVisits = ArrayList<BeneficiariesList>()
    private val listReferral= ArrayList<Referral>()
   // private val listGrowthMonitoring= ArrayList<GrowthMonitoring>()


    var onQueryFinished : OnQueryFinished = object : OnQueryFinished{
        override fun onSuccess(cursor: Cursor, id: Int) {
           when(id){
               FETCH_USER_DATA -> if (cursor.moveToFirst()){
                   mUsername=cursor.getString(cursor.getColumnIndex(DatabaseContract.LoginTable.COLUMN_USERNAME))
                   mPassword=cursor.getString(cursor.getColumnIndex(DatabaseContract.LoginTable.COLUMN_PASSWORD))
               }
               FETCH_FORM_HASH ->if (cursor.moveToFirst()){
                   mFormHash=cursor.getString(cursor.getColumnIndex(DatabaseContract.HashTable.COLUMN_HASH))
               }
           }

        }

        override fun onSuccess() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onFailure() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }


    override fun changeLanguage(context: Context, language: String) {
    mSettingsInteractor?.changeLocale(context,language)
    }

    override fun downloadForms() {
        val a= mSettingsView?.getContext()
        val b= utility.hasInternetConnectivity(a)
        if (b) {
            mSettingsView?.showProgressBar(mSettingsView?.getContext()?.getString(R.string.downloading_data)!!)
            val details = RequestFormModel()
            details.userName=mUsername
            details.password=mPassword
            details.setImei(utility.getDeviceImeiNumber(mSettingsView!!.getContext()))
            details.setHash(mSettingsInteractor!!.getHash(HASH_ITEM_FORM))
            details.setShowdata("true")

            mSettingsInteractor?.downloadForms(details, this)
        } else
            mSettingsView?.showSnackBar(mSettingsView?.getContext()?.getString(R.string.no_internet_connection)!!)
    }

    override fun downloadHelpManual() {
        if (utility.hasInternetConnectivity(mSettingsView?.getContext())) {
            mSettingsView?.showProgressBar(mSettingsView!!.getContext().getString(R.string.downloading_data))
            val details = RequestHelpModel()
            details.userName = mUsername
            details.password = mPassword
            details.setImei(utility.getDeviceImeiNumber(mSettingsView?.getContext()!!))
            details.setHash(mSettingsInteractor?.getHash(HASH_ITEM_HELP_MANUAL)!!)
            mSettingsInteractor?.downloadHelpManual(details, this)
        } else
            mSettingsView?.showSnackBar(mSettingsView?.getContext()?.getString(R.string.no_internet_connection)!!)
    }

    override fun checkUpdate() {
        if(utility.hasInternetConnectivity(mSettingsView?.getContext())){
            mSettingsView?.showProgressBar(mSettingsView!!.getContext().getString(R.string.looking_for_update))
            mSettingsInteractor?.checkReleaseUpdate(this)
        }else mSettingsView?.showSnackBar(mSettingsView!!.getContext().getString(R.string.no_internet_connection))
    }

    override fun downloadApk(apkLink: String) {
        mSettingsView?.showApkDownloadProgress()
        mSettingsInteractor?.downloadAndSaveApk(apkLink)
    }

    override fun setApkDownloadProgress(progress: Int) {
        mSettingsView?.updateApkDownloadProgress(progress)
    }

    override fun onApkDownloaded() {
      mSettingsView?.dissmissApkDownloadProgress()
    }

    override fun logout() {
        mSettingsInteractor?.deleteLoginDetails()
        val intent = Intent(mSettingsView?.getContext(), Login::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        mSettingsView?.getContext()?.startActivity(intent)
    }

    override fun restoreData() {
        if(utility.hasInternetConnectivity(mSettingsView?.getContext())) {
            mSettingsView?.showProgressBar(mSettingsView!!.getContext().getString(R.string.downloading_data))
            resetDataMemberValues()
            restoreRegistrations(pageCounter)
        }
        else mSettingsView?.showSnackBar(mSettingsView!!.getContext().getString(R.string.no_internet_connection))
    }

    override fun restoreRegistrations(pageNumber: Int) {
        mRequest?.setPageNumber(pageNumber)
        mSettingsInteractor?.downloadRegistrationData(mRequest, this)
    }

    override fun restoreVisits(pageNumber: Int) {
        mRequest.setPageNumber(pageNumber)
        mSettingsInteractor?.downloadVisitsData(mRequest,this)
    }

    override fun restoreReferrals(pageNumber: Int) {
        mRequest.setPageNumber(pageNumber)

    }

    override fun restoreGrowthMonitorings(pageNumber: Int) {

    }

    override fun resetDataMemberValues() {
        mRequest = RestoreDataRequest()
        mRequest.userName= mUsername
        mRequest.password= mPassword
        mRequest.setImei(utility.getDeviceImeiNumber(mSettingsView!!.getContext()))
        mRequest.setLimit(FORM_DOWNLOAD_LIMIT)

        pageCounter=1
        totalPages=0
        totalPagesCalculated=false
        listRegistrations.clear()
        listVisits.clear()
        listReferral.clear()
    }

    override fun attachView(view: ISettingsView) {
        mSettingsView=view
        mSettingsInteractor= SettingsInteractor(mSettingsView!!.getContext(),onQueryFinished,this)
        mSettingsInteractor?.fetchLoginDetails(FETCH_USER_DATA)
        mSettingsInteractor?.fetchFormJsonHash(FETCH_FORM_HASH)
    }
    override fun detachView() {
    }

    override fun onSuccessFormDownloading(jsonObject: JSONObject, hash: String) {
        mSettingsView?.hideProgressBar()
        var value : Boolean =false

        value= jsonObject.get("status") as Boolean

        if(!value){
            if(jsonObject.has("response")){
                mSettingsView?.showSnackBar(mSettingsView!!.getContext().getString(R.string.forms_already_updated))
            } else{
                try{
                jsonObject.put("hash",hash)
                mSettingsInteractor?.saveFormData(jsonObject)
            }
            catch(e : JSONException){
                e.printStackTrace()
                mSettingsView?.showSnackBar(mSettingsView!!.getContext().getString(R.string.invalid_data_frm_server))
            }
            }
        }
        else{
            mSettingsView?.showSnackBar(mSettingsView!!.getContext().getString(R.string.forms_already_updated))
        }

    }

    override fun onUpdateCheckSuccess(updateModel: UpdateModel) {
        mSettingsView?.hideProgressBar()
        if(!updateModel.getStatus()){
            mSettingsView?.showSnackBar(mSettingsView!!.getContext().getString(R.string.dialog_app_updated_text))
            return
        }
        val context: Context= mSettingsView?.getContext()!!
        try{
            val pInfo : PackageInfo= context.packageManager.getPackageInfo(context.packageName,0)
            var versionCode = pInfo.versionCode
            val newVersionCode = Integer.parseInt(updateModel.getData()!!.versionCode)

            if(newVersionCode>versionCode){
                mSettingsView!!.updateAvailable(updateModel!!.getData()!!.link)
            }
        }catch(e : PackageManager.NameNotFoundException){
            e.printStackTrace()
        }
    }


    override fun onSuccessDownloadedHelpManual(jsonObject: JSONObject, hash: String) {
if(jsonObject.has("response")) {
    mSettingsView!!.showSnackBar(mSettingsView!!.getContext().getString(R.string.help_manual_already_updated))
}else{
     try{
         jsonObject.put("hash",hash)
         mSettingsInteractor!!.saveHelpManualData(jsonObject)
     }   catch (e: JSONException){
         e.printStackTrace()
         mSettingsView!!.showSnackBar(mSettingsView!!.getContext().getString(R.string.invalid_data_frm_server))
     }
    }
        mSettingsView!!.hideProgressBar()
    }


    override fun onSuccessRegistrationsDownloading(registration: RestoreRegistration) {
        if(registration.getTotal()>0){
            registration.getRegistrationData()?.let { listRegistrations.addAll(it) }
            if(!totalPagesCalculated){
                totalPagesCalculated= true
                //totalPages = Math.ceil(registration.getTotal() as Double / FORM_DOWNLOAD_LIMIT as Double).toInt()
                totalPages = Math.ceil((registration.getTotal()).toDouble() / (FORM_DOWNLOAD_LIMIT).toDouble()).toInt()
            }
        }
        if(pageCounter<totalPages){
            restoreRegistrations(++pageCounter)
        }else{
            pageCounter=1
            totalPages=0
            totalPagesCalculated=false
            restoreVisits(pageCounter)
        }
    }


    override fun onSuccessVisitsDownloading(visits: RestoreVisits) {
        if(visits.getTotal()>0){
            visits.getBeneficiariesLists()?.let { listVisits.addAll(it) }
            if(!totalPagesCalculated){
                totalPagesCalculated =true
                totalPages=Math.ceil((visits.getTotal()).toDouble() / (FORM_DOWNLOAD_LIMIT).toDouble() ).toInt()
            }
        }

        if(pageCounter<totalPages){
            restoreReferrals(++pageCounter)
        }
        else{
            pageCounter=1
            totalPages=0
            totalPagesCalculated=false
            restoreReferrals(pageCounter)
        }
    }

    override fun onFailure(message: String) {
        mSettingsView!!.hideProgressBar()
        var errorString=""
        try{
            var errorObject= JSONObject(message)
            errorString=message
        }
        catch(e:JSONException){
            e.printStackTrace()
            errorString=message
        }
        mSettingsView!!.showSnackBar(errorString)
    }

}