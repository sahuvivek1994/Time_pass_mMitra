package tech.inscripts.ins_armman.mMitra.settingActivity

import android.app.DownloadManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.database.Cursor
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.widget.ProgressBar
import android.widget.TextView
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import tech.inscripts.ins_armman.mMitra.R
import tech.inscripts.ins_armman.mMitra.data.database.DatabaseContract
import tech.inscripts.ins_armman.mMitra.data.model.RequestFormModel
import tech.inscripts.ins_armman.mMitra.data.model.restoreData.BeneficiariesList
import tech.inscripts.ins_armman.mMitra.data.model.restoreData.RestoreDataRequest
import tech.inscripts.ins_armman.mMitra.data.model.syncing.BeneficiaryDetails
import tech.inscripts.ins_armman.mMitra.data.model.syncing.Referral
import tech.inscripts.ins_armman.mMitra.data.model.syncing.RequestHelpModel
import tech.inscripts.ins_armman.mMitra.data.retrofit.RemoteDataSource
import tech.inscripts.ins_armman.mMitra.data.service.*
import tech.inscripts.ins_armman.mMitra.utility.Constants
import tech.inscripts.ins_armman.mMitra.utility.Constants.*
import tech.inscripts.ins_armman.mMitra.utility.Utility
import java.io.File
import java.net.URI
import java.util.ArrayList
import android.content.BroadcastReceiver as BroadcastReceiver1

class SettingsInteractor:ISettingsInteractor,LoaderManager.LoaderCallbacks<Cursor> {

     var mContext : Context
    var mOnQueryFinished : ISettingsPresentor.OnQueryFinished ?=null
        var mSettingsPresentor:SettingsPresentor
    var dataSource= RemoteDataSource()

    constructor(mContext: Context, mOnQueryFinished: ISettingsPresentor.OnQueryFinished, mSettingsPresentor: SettingsPresentor) {
        this.mContext = mContext
        this.mOnQueryFinished = mOnQueryFinished
        this.mSettingsPresentor = mSettingsPresentor
    }


    override fun changeLocale(context: Context, language: String) {
    Utility.setApplicationLocale(context,language)
    }

    override fun downloadForms(requestFormModel: RequestFormModel, onFormDownloadFinished: ISettingsInteractor.OnFormDownloadFinished) {
        var remoteDataSource : RemoteDataSource= dataSource.getInstance()
        var formDownloadService : FormDownloadService = remoteDataSource.downloadFormService()
        formDownloadService.downloadForms(requestFormModel,onFormDownloadFinished,mContext)
    }

    override fun fetchLoginDetails(id: Int) {
        var bundle = Bundle()
        bundle.putString(Constants.QUERY_ARGS_TABLE_NAME,DatabaseContract.LoginTable.TABLE_NAME)
        bundle.putString(Constants.QUERY_ARGS_PROJECTION,null)
        bundle.putString(Constants.QUERY_ARGS_SELECTION,null)
        bundle.putString(Constants.QUERY_ARGS_SELECTION_ARGS,null)
        (mContext as AppCompatActivity).supportLoaderManager.restartLoader(id, bundle, this)
    }

    override fun fetchFormJsonHash(id: Int) {
        var bundle = Bundle()
        bundle.putString(Constants.QUERY_ARGS_TABLE_NAME,DatabaseContract.LoginTable.TABLE_NAME)
        bundle.putString(Constants.QUERY_ARGS_PROJECTION,null)
        bundle.putString(Constants.QUERY_ARGS_SELECTION,null)
        bundle.putString(Constants.QUERY_ARGS_SELECTION_ARGS,null)
        (mContext as AppCompatActivity).supportLoaderManager.restartLoader(id,bundle,this)
    }


    override fun saveFormData(formJsonObject: JSONObject) {
        SaveFormASyncTask().execute(formJsonObject)

    }

    override fun downloadHelpManual(helpModel: RequestHelpModel, downloadFinished: ISettingsInteractor.onHelpManualDownloadFinished) {
        var remoteDataSource : RemoteDataSource = dataSource.getInstance()
        var manualDownloadService : HelpManualDownloadService = dataSource.helpManualDownloadService()
        manualDownloadService.downloadHelpManual(helpModel,downloadFinished,mContext)
    }

    fun deleteHelpData(){
        Utility.getDatabase().execSQL("DROP TABLE IF EXISTS " + DatabaseContract.FaqTable.TABLE_NAME)
        Utility.getDatabase().execSQL("DROP TABLE IF EXISTS " + DatabaseContract.VideoAnimationTable.TABLE_NAME)
        Utility.getDatabase().execSQL("DROP TABLE IF EXISTS " + DatabaseContract.mMitraCallsTable.TABLE_NAME)
    }

    override fun saveHelpManualData(helpJsonObject: JSONObject) {
        deleteHelpData()
        var values = ContentValues()
        Utility.getDatabase().beginTransaction()
        try{
            if(helpJsonObject.has("FAQ")){
               var faqJsonArray : JSONArray = helpJsonObject.getJSONArray("FAQ")
                var faqSize=faqJsonArray.length()
                for (index in 0 until faqSize) {
                    values.clear()
                    val jsonObject = faqJsonArray.getJSONObject(index)
                    values.put(DatabaseContract.FaqTable.COLUMN_QUESTION, jsonObject.optJSONObject("question").optString("languages"))
                    values.put(DatabaseContract.FaqTable.COLUMN_ANSWER,jsonObject.optJSONObject("answer").optString("languages"))
                    Utility.getDatabase().insert(DatabaseContract.FaqTable.TABLE_NAME, null, values)
                }
            }

            if(helpJsonObject.has("Animation")){
                var animJsonArray : JSONArray = helpJsonObject.getJSONArray("Animation")
                var animSize = animJsonArray.length()
                for(index in 0 until animSize){
                    values.clear()
                    var jsonObject = animJsonArray.getJSONObject(index)
                    values.put(DatabaseContract.VideoAnimationTable.COLUMN_TYPE, TYPE_ANIMATION)
                    values.put(DatabaseContract.VideoAnimationTable.COLUMN_KEYWORD, jsonObject.optString("keyword"))
                    val titleObject = jsonObject.getJSONObject("title").getJSONObject("languages")
                    values.put(DatabaseContract.VideoAnimationTable.COLUMN_TITLE, titleObject.toString())
                    Utility.getDatabase().insert(DatabaseContract.VideoAnimationTable.TABLE_NAME, null, values)
                }
            }

            if (helpJsonObject.has("Video")) {
                val videoJsonArray = helpJsonObject.getJSONArray("Video")
                val videoSize = videoJsonArray.length()
                for (index in 0 until videoSize) {
                    values.clear()
                    val jsonObject = videoJsonArray.getJSONObject(index)
                    values.put(DatabaseContract.VideoAnimationTable.COLUMN_TYPE, TYPE_VIDEO)
                    values.put(DatabaseContract.VideoAnimationTable.COLUMN_KEYWORD, jsonObject.optString("keyword"))
                    val titleObject = jsonObject.getJSONObject("title").getJSONObject("languages")
                    values.put(DatabaseContract.VideoAnimationTable.COLUMN_TITLE, titleObject.toString())
                    Utility.getDatabase().insert(DatabaseContract.VideoAnimationTable.TABLE_NAME, null, values)
                }
            }

            if (helpJsonObject.has("mMitra_calls")) {
                val callsJsonArray = helpJsonObject.getJSONArray("mMitra_calls")
                val callSize = callsJsonArray.length()
                for (index in 0 until callSize) {
                    values.clear()
                    val jsonObject = callsJsonArray.getJSONObject(index)
                    values.put(DatabaseContract.mMitraCallsTable.COLUMN_ORDER_NO, jsonObject.optInt("order_no"))
                    values.put(DatabaseContract.mMitraCallsTable.COLUMN_LANGUAGE, jsonObject.optString("language"))
                    values.put(DatabaseContract.mMitraCallsTable.COLUMN_KEYWORD, jsonObject.optString("keyword"))
                    values.put(DatabaseContract.mMitraCallsTable.COLUMN_TITLE, jsonObject.optString("title"))
                    Utility.getDatabase().insert(DatabaseContract.mMitraCallsTable.TABLE_NAME, null, values)
                }
            }

            addOrUpdateFormHash(HASH_ITEM_HELP_MANUAL,helpJsonObject.optString("hash"))
        }catch(e:JSONException){
            e.printStackTrace()
        }finally {
            Utility.getDatabase().setTransactionSuccessful()
            Utility.getDatabase().endTransaction()
        }
    }

    override fun checkReleaseUpdate(onCheckUpdateFinished: ISettingsInteractor.onCheckUpdateFinished) {
var remoteDataSource : RemoteDataSource= dataSource.getInstance()
        var checkUpdateService : CheckUpdateService= dataSource.getCheckUpdateService()
        checkUpdateService.getUpdateData(onCheckUpdateFinished,mContext)
    }

    override fun downloadAndSaveApk(apkLink: String) {
        var destination = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/Download/"
        val fileName = "Arogyasakhi.apk"
        destination += fileName
        val uri = Uri.parse("file://$destination")
        var file : File = File(destination)
        //delete update file if exists
        if(file.exists())
            file.delete()

        //set downloadManager
        var request = DownloadManager.Request(Uri.parse(apkLink))
        request.setDescription(mContext.getString(R.string.apk_download_request_text))
        request.setTitle(mContext.getString(R.string.app_name))

        //set destination
        request.setDestinationUri(uri)

        //get download service and enqueue file
        var manager : DownloadManager= mContext.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadId= manager.enqueue(request)


       Thread(Runnable {
           var downloading= true
           while(downloading){
               var q : DownloadManager.Query= DownloadManager.Query()
               q.setFilterById(downloadId)

               var cur : Cursor = manager.query(q)
               if(cur!=null){
                   cur.moveToFirst()
                   var byte_downloaded = cur.getInt(cur.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                   var byte_total = cur.getInt(cur.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))

                   if(cur.getInt(cur.getColumnIndex(DownloadManager.COLUMN_STATUS))== DownloadManager.STATUS_SUCCESSFUL){
                       downloading=false
                   }
                   if(byte_downloaded!=0){
                       var dl_progress = byte_downloaded*100/byte_total
                       var progressInt=dl_progress
                       Log.i("download_apk","progress"+progressInt)
                       mSettingsPresentor.setApkDownloadProgress(progressInt)
                   }
                   cur.close()
               }
           }
       }).start()

        val onComplete = object : android.content.BroadcastReceiver() {
            override fun onReceive(ctxt: Context, intent: Intent) {
                mSettingsPresentor.onApkDownloaded()
                val install = Intent(Intent.ACTION_VIEW)
                install.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                install.setDataAndType(
                    uri,
                    manager.getMimeTypeForDownloadedFile(downloadId)
                )
                if (install != null) {
                    mContext.startActivity(install)
                }
                mContext.unregisterReceiver(this)
                //finish();
            }
        }
        mContext.registerReceiver(onComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }

    override fun getHash(type: String): String {
        var cur : Cursor= Utility.getDatabase().rawQuery(
            "SELECT * FROM "
    + DatabaseContract.HashTable.TABLE_NAME
    + " WHERE "
    + DatabaseContract.HashTable.COLUMN_ITEM
    + " = ? ", arrayOf(type))
        return if (cur.moveToFirst()) cur.getString(cur.getColumnIndex(DatabaseContract.HashTable.COLUMN_HASH)) else DEFAULT_HASH

    }

    override fun downloadRegistrationData(request: RestoreDataRequest, downloadFinished: ISettingsInteractor.OnRegistrationsDownloadFinished) {
    var remoteDataSource : RemoteDataSource = dataSource.getInstance()
        var service : RestoreRegistrationService = remoteDataSource.restoreRegistrationService()
        service.downloadRegistrationData(mContext,request,downloadFinished)
    }

    override fun downloadVisitsData(request: RestoreDataRequest, downloadFinished: ISettingsInteractor.OnVisitsDownloadFinished) {
        var remoteDataSource : RemoteDataSource = dataSource.getInstance()
        var service : RestoreVisitsService = remoteDataSource.restoreVisitsService()
        service.downloadVisitsData(mContext,request,downloadFinished)
    }

    override fun saveDownloadedData(listRegistrations: ArrayList<BeneficiaryDetails>, listVisits: ArrayList<BeneficiariesList>) {

        SaveRestoredDataAsyncTask(listRegistrations, listVisits).execute()
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

   inner class SaveFormASyncTask : AsyncTask<JSONObject, Int, Void>() {

        var progressBar : ProgressBar?= null
        var mProgressDialog : AlertDialog ? =null
        var mProgress: Int? = null

        var displayCondition: String?=""
        var visit_from_week :Int?=null
        var visit_to_week : Int?=null
        var orderno: String?=""
        var depend1:String?=""
        var question_keyword:String?=""
        var ques_ans_type:String?=""
        var messages:String?=""
        var calculations:String?=""
        var ques_lang:String?=""
        var option_id:String?=""
        var option_language:String? = ""
        var option_messages:String?=""
        var dependant_ques_messages:String?=""
        var attr16:String? = null
        var attr17:String? = null
        var vali_req: String?=""
        var dependant:String?=""
        var custom_vali_cond:String?=""
        var cust_lang:String?=""
        var range_min:String?=""
        var range_max:String?=""
        var range_lang:String?=""
        var length_min:String?=""
        var length_max:String?=""
        var length_lang:String?=""
        var avoid_repetition:String?=""
        var option_keyword: String?=""
        var visit_name:String?=""
        var setid:String?=""

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
        }

        override fun onCancelled(result: Void?) {
            super.onCancelled(result)
        }

        override fun onCancelled() {
            super.onCancelled()
        }

        override fun onPreExecute() {
            super.onPreExecute()

            var inflator = mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }

        override fun doInBackground(vararg params: JSONObject?): Void {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }
    inner class SaveRestoredDataAsyncTask : AsyncTask<ArrayList<Object>,Int,Void>(){

        var progressBar : ProgressBar
        var mProgressDialog : AlertDialog
        var mProgress : Int
        var listRegistrations :ArrayList<BeneficiaryDetails>
        var listVisits :ArrayList<BeneficiariesList>

        constructor(
            listRegistrations: ArrayList<BeneficiaryDetails>,
            listVisits: ArrayList<BeneficiariesList>
        ) : super() {
            this.listRegistrations = listRegistrations
            this.listVisits = listVisits
        }

        override fun onPreExecute() {
            super.onPreExecute()
            var inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var dialogView = inflater.inflate(R.layout.progress_dialog_layout,null)
            val textView = dialogView.findViewById<TextView>(R.id.textView_label)
            progressBar = dialogView.findViewById(R.id.progressBar)
            textView.setText(R.string.saving_forms)
            progressBar.isIndeterminate = false
            progressBar.max = listRegistrations.size + listVisits.size
            val mAlertDialogBuilder = AlertDialog.Builder(mContext)
            mAlertDialogBuilder.setView(dialogView)
            mAlertDialogBuilder.setCancelable(false)
            mProgressDialog = mAlertDialogBuilder.create()
            mProgressDialog.show()


        }
        override fun doInBackground(vararg params: ArrayList<Object>?): Void {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
        }

        override fun onCancelled(result: Void?) {
            super.onCancelled(result)
        }

        override fun onCancelled() {
            super.onCancelled()
        }


    }
}