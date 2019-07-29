package tech.inscripts.ins_armman.mMitra.settingActivity

import android.app.DownloadManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.widget.ProgressBar
import android.widget.TextView
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import tech.inscripts.ins_armman.mMitra.R
import tech.inscripts.ins_armman.mMitra.data.database.DatabaseContract
import tech.inscripts.ins_armman.mMitra.data.database.DatabaseContract.*
import tech.inscripts.ins_armman.mMitra.data.database.GenericCursorLoader
import tech.inscripts.ins_armman.mMitra.data.database.LocalDataSource
import tech.inscripts.ins_armman.mMitra.data.model.Form
import tech.inscripts.ins_armman.mMitra.data.model.RequestFormModel
import tech.inscripts.ins_armman.mMitra.data.model.restoreData.BeneficiariesList
import tech.inscripts.ins_armman.mMitra.data.model.restoreData.RestoreDataRequest
import tech.inscripts.ins_armman.mMitra.data.model.syncing.BeneficiaryDetails
import tech.inscripts.ins_armman.mMitra.data.model.syncing.QuestionAnswer
import tech.inscripts.ins_armman.mMitra.data.model.syncing.RequestHelpModel
import tech.inscripts.ins_armman.mMitra.data.retrofit.RemoteDataSource
import tech.inscripts.ins_armman.mMitra.data.service.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*
import android.content.BroadcastReceiver as BroadcastReceiver1
import android.os.AsyncTask
import tech.inscripts.ins_armman.mMitra.utility.Constants
import tech.inscripts.ins_armman.mMitra.utility.Constants.*
import tech.inscripts.ins_armman.mMitra.utility.Utility

class SettingsInteractor : ISettingsInteractor, LoaderManager.LoaderCallbacks<Cursor> {
    val utility= Utility()
    var mContext: Context
    var mOnQueryFinished: ISettingsPresentor.OnQueryFinished? = null
    var mSettingsPresentor: SettingsPresentor
    var dataSource = RemoteDataSource()

    constructor(mContext: Context, mOnQueryFinished: ISettingsPresentor.OnQueryFinished, mSettingsPresentor: SettingsPresentor) {
        this.mContext = mContext
        this.mOnQueryFinished = mOnQueryFinished
        this.mSettingsPresentor = mSettingsPresentor
    }

    override fun changeLocale(context: Context, language: String) {

        utility.setApplicationLocale(context, language)
    }

    override fun downloadForms(requestFormModel: RequestFormModel, onFormDownloadFinished: ISettingsInteractor.OnFormDownloadFinished) {
        var remoteDataSource: RemoteDataSource = dataSource.getInstance()
        var formDownloadService: FormDownloadService = remoteDataSource.downloadFormService()
        formDownloadService.downloadForms(requestFormModel, onFormDownloadFinished, mContext)
    }

    override fun fetchLoginDetails(id: Int) {
        var bundle = Bundle()
        bundle.putString(Constants.QUERY_ARGS_TABLE_NAME, DatabaseContract.LoginTable.TABLE_NAME)
        bundle.putString(Constants.QUERY_ARGS_PROJECTION, null)
        bundle.putString(Constants.QUERY_ARGS_SELECTION, null)
        bundle.putString(Constants.QUERY_ARGS_SELECTION_ARGS, null)
        (mContext as AppCompatActivity).supportLoaderManager.restartLoader(id, bundle, this)
    }

    override fun fetchFormJsonHash(id: Int) {
        var bundle = Bundle()
        bundle.putString(Constants.QUERY_ARGS_TABLE_NAME, DatabaseContract.HashTable.TABLE_NAME)
        bundle.putString(Constants.QUERY_ARGS_PROJECTION, null)
        bundle.putString(Constants.QUERY_ARGS_SELECTION, null)
        bundle.putString(Constants.QUERY_ARGS_SELECTION_ARGS, null)
        (mContext as AppCompatActivity).supportLoaderManager.restartLoader(id, bundle, this)
    }


    override fun saveFormData(formJsonObject: JSONObject) {
        SaveFormASyncTask().execute(formJsonObject)

    }

    override fun downloadHelpManual(helpModel: RequestHelpModel, downloadFinished: ISettingsInteractor.onHelpManualDownloadFinished) {
        var remoteDataSource: RemoteDataSource = dataSource.getInstance()
        var manualDownloadService: HelpManualDownloadService = dataSource.helpManualDownloadService()
        manualDownloadService.downloadHelpManual(helpModel, downloadFinished, mContext)
    }

    fun deleteOldHelpData() {
        utility.getDatabase().execSQL("DROP TABLE IF EXISTS " + DatabaseContract.FaqTable.TABLE_NAME)
        utility.getDatabase().execSQL("DROP TABLE IF EXISTS " + DatabaseContract.VideoAnimationTable.TABLE_NAME)
        utility.getDatabase().execSQL("DROP TABLE IF EXISTS " + DatabaseContract.mMitraCallsTable.TABLE_NAME)

        utility.getDatabase().execSQL(DatabaseContract.FaqTable.CREATE_TABLE)
        utility.getDatabase().execSQL(DatabaseContract.VideoAnimationTable.CREATE_TABLE)
        utility.getDatabase().execSQL(DatabaseContract.mMitraCallsTable.CREATE_TABLE)
    }

    override fun saveHelpManualData(helpJsonObject: JSONObject) {
        deleteOldHelpData()
        var values = ContentValues()
        utility.getDatabase().beginTransaction()
        try {
            if (helpJsonObject.has("FAQ")) {
                var faqJsonArray: JSONArray = helpJsonObject.getJSONArray("FAQ")
                var faqSize = faqJsonArray.length()
                for (index in 0 until faqSize) {
                    values.clear()
                    val jsonObject = faqJsonArray.getJSONObject(index)
                    values.put(DatabaseContract.FaqTable.COLUMN_QUESTION, jsonObject.optJSONObject("question").optString("languages"))
                    values.put(DatabaseContract.FaqTable.COLUMN_ANSWER, jsonObject.optJSONObject("answer").optString("languages"))
                    utility.getDatabase().insert(DatabaseContract.FaqTable.TABLE_NAME, null, values)
                }
            }

            if (helpJsonObject.has("Animation")) {
                var animJsonArray: JSONArray = helpJsonObject.getJSONArray("Animation")
                var animSize = animJsonArray.length()
                for (index in 0 until animSize) {
                    values.clear()
                    var jsonObject = animJsonArray.getJSONObject(index)
                    values.put(DatabaseContract.VideoAnimationTable.COLUMN_TYPE, TYPE_ANIMATION)
                    val titleObject = jsonObject.getJSONObject("title").getJSONObject("languages")
                    values.put(DatabaseContract.VideoAnimationTable.COLUMN_TITLE, titleObject.toString())
                    utility.getDatabase().insert(DatabaseContract.VideoAnimationTable.TABLE_NAME, null, values)
                    values.put(DatabaseContract.VideoAnimationTable.COLUMN_KEYWORD, jsonObject.optString("keyword"))
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
                    utility.getDatabase().insert(DatabaseContract.VideoAnimationTable.TABLE_NAME, null, values)
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
                    utility.getDatabase().insert(DatabaseContract.mMitraCallsTable.TABLE_NAME, null, values)
                }
            }

            addOrUpdateFormHash(HASH_ITEM_HELP_MANUAL, helpJsonObject.optString("hash"))
        } catch (e: JSONException) {
            e.printStackTrace()
        } finally {
            utility.getDatabase().setTransactionSuccessful()
            utility.getDatabase().endTransaction()
        }
    }

    override fun checkReleaseUpdate(onCheckUpdateFinished: ISettingsInteractor.onCheckUpdateFinished) {
        val remoteDataSource = dataSource.getInstance()
        val checkUpdateService = remoteDataSource.getCheckUpdateService()
        checkUpdateService.getUpdateData(onCheckUpdateFinished, mContext)
    }

    override fun downloadAndSaveApk(apkLink: String) {
        var destination =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/Download/"
        val fileName = "Arogyasakhi.apk"
        destination += fileName
        val uri = Uri.parse("file://$destination")
        var file: File = File(destination)
        //delete update file if exists
        if (file.exists())
            file.delete()

        //set downloadManager
        var request = DownloadManager.Request(Uri.parse(apkLink))
        request.setDescription(mContext.getString(R.string.apk_download_request_text))
        request.setTitle(mContext.getString(R.string.app_name))

        //set destination
        request.setDestinationUri(uri)

        //get download service and enqueue file
        var manager: DownloadManager = mContext.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadId = manager.enqueue(request)


        Thread(Runnable {
            var downloading = true
            while (downloading) {
                var q: DownloadManager.Query = DownloadManager.Query()
                q.setFilterById(downloadId)

                var cur: Cursor = manager.query(q)
                if (cur != null) {
                    cur.moveToFirst()
                    var byte_downloaded = cur.getInt(cur.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                    var byte_total = cur.getInt(cur.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))

                    if (cur.getInt(cur.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                        downloading = false
                    }
                    if (byte_downloaded != 0) {
                        var dl_progress = byte_downloaded * 100 / byte_total
                        var progressInt = dl_progress
                        Log.i("download_apk", "progress" + progressInt)
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
        var cur: Cursor = utility.getDatabase().rawQuery(
            "SELECT * FROM "
                    + HashTable.TABLE_NAME
                    + " WHERE "
                    + HashTable.COLUMN_ITEM
                    + " = ? ", arrayOf(type)
        )
        return if (cur.moveToFirst()) cur.getString(cur.getColumnIndex(DatabaseContract.HashTable.COLUMN_HASH)) else DEFAULT_HASH

    }

    override fun downloadRegistrationData(request: RestoreDataRequest, downloadFinished: ISettingsInteractor.OnRegistrationsDownloadFinished) {
        var remoteDataSource: RemoteDataSource = dataSource.getInstance()
        var service: RestoreRegistrationService = remoteDataSource.restoreRegistrationService()
        service.downloadRegistrationData(mContext, request, downloadFinished)
    }

    override fun downloadVisitsData(request: RestoreDataRequest, downloadFinished: ISettingsInteractor.OnVisitsDownloadFinished) {
        var remoteDataSource: RemoteDataSource = dataSource.getInstance()
        var service: RestoreVisitsService = remoteDataSource.restoreVisitsService()
        service.downloadVisitsData(mContext, request, downloadFinished)
    }

    override fun saveDownloadedData(listRegistrations: ArrayList<BeneficiaryDetails>, listVisits: ArrayList<BeneficiariesList>) {

        SaveRestoredDataAsyncTask(listRegistrations, listVisits).execute()
    }

    override fun addOrUpdateFormHash(item: String, hash: String) {
        val values = ContentValues()
        values.put(HashTable.COLUMN_HASH, hash)
        values.put(HashTable.COLUMN_ITEM, item)
        val cursor = utility.getDatabase().rawQuery(
            "SELECT * FROM " + HashTable.TABLE_NAME + " WHERE " + HashTable.COLUMN_ITEM + " = ? ",
            arrayOf(item)
        )
        if (cursor.moveToFirst()) {
            utility.getDatabase().update(HashTable.TABLE_NAME, values, HashTable.COLUMN_ITEM + " = ? ", arrayOf(item))
        } else {
            utility.getDatabase().insert(HashTable.TABLE_NAME, null, values)
        }
    }

    override fun deleteLoginDetails() {
        utility.getDatabase().delete(LoginTable.TABLE_NAME, null, null)
    }

    override fun onCreateLoader(p0: Int, bundle: Bundle?): Loader<Cursor> {
        val sqLiteDatabase = utility.getDatabase()
        val queryType = bundle!!.getString(QUERY_TYPE)
        if (queryType != null && queryType.equals(LocalDataSource.QueryType.RAW.name, ignoreCase = true)) {
            return GenericCursorLoader(mContext, sqLiteDatabase, bundle, LocalDataSource.QueryType.RAW)
        } else {
            return GenericCursorLoader(mContext, sqLiteDatabase, bundle, LocalDataSource.QueryType.FUNCTION)
        }
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor) {
        mOnQueryFinished?.onSuccess(data, loader.id)

    }

    override fun onLoaderReset(p0: Loader<Cursor>) {
    }

    inner class SaveFormASyncTask : AsyncTask<JSONObject, Int, Void>() {

        var progressBar: ProgressBar? = null
        var mProgressDialog: AlertDialog? = null
        var mProgress: Int? = null

        var displayCondition: String = ""
        var visit_from_week: Int? = null
        var visit_to_week: Int? = null
        var orderno: String = ""
        var depend1: String = ""
        var question_keyword: String = ""
        var ques_ans_type: String = ""
        var messages: String = ""
        var calculations: String = ""
        var ques_lang: String = ""
        var option_id: String = ""
        var option_language: String = ""
        var option_messages: String = ""
        var dependant_ques_messages: String = ""
        var attr16: String = ""
        var attr17: String = ""
        var vali_req: String = ""
        var dependant: String = ""
        var custom_vali_cond: String = ""
        var cust_lang: String = ""
        var range_min: String = ""
        var range_max: String = ""
        var range_lang: String = ""
        var length_min: String = ""
        var length_max: String = ""
        var length_lang: String = ""
        var avoid_repetition: String = ""
        var option_keyword: String = ""
        var visit_name: String = ""
        var setid: String = ""
        var form_id: Int = 0
        var quesid: Int = 0

        override fun onPreExecute() {
            super.onPreExecute()
            var inflator = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var dialogView = inflator.inflate(R.layout.progress_dialog_layout, null)
            var textView: TextView = dialogView.findViewById(R.id.textView_label)
            progressBar = dialogView.findViewById(R.id.progressBar)
            textView.setText(R.string.saving_forms)
            progressBar!!.isIndeterminate = false
            progressBar!!.max = TOTAL_FORM_COUNT
            var mAlertDialogBuilder = AlertDialog.Builder(mContext)
            mAlertDialogBuilder.setView(dialogView)
            mAlertDialogBuilder.setCancelable(false)
            mProgressDialog = mAlertDialogBuilder.create()
            mProgressDialog!!.show()
        }

        override fun doInBackground(vararg jsonObjects: JSONObject?): Void? {
            utility.getDatabase().beginTransaction()
            deleteOldFormData()
            var jsonObject = jsonObjects[0]
            try {
                var formArray = jsonObject!!.optJSONArray("forms")
                var length = formArray.length()

                for (i in 0 until length) {
                    val formkeys = formArray.getJSONObject(i)
                    val visitNameString = formkeys.optString("details")
                    visit_name = visitNameString

                    if (visitNameString.contains("languages")) {
                        val visitNameObject = JSONObject(visitNameString)
                        visit_name = visitNameObject.optString("languages")
                    }

                    form_id = formkeys.optInt("form_id")
                    visit_from_week = formkeys.optInt("from_weeks")
                    visit_to_week = formkeys.optInt("to_weeks")
                    val orderId = formkeys.optString("orderby")

                    saveFormDetails(Form(form_id, visit_from_week!!, visit_to_week!!, visit_name, orderId))
                    val questionArray = formkeys.optJSONArray("question")
                    val qstnLength = questionArray.length()
                    for (j in 0 until qstnLength) {
                        val main_question_keys = questionArray.getJSONObject(j)
                        quesid = main_question_keys.optInt("id")
                        setid = main_question_keys.optString("set_id")
                        question_keyword = main_question_keys.optString("keyword")
                        orderno = main_question_keys.optString("order_no")
                        ques_ans_type = main_question_keys.optString("answer_type")
                        ques_lang = main_question_keys.optString("languages")

                        if (main_question_keys.optString("messages") != null && main_question_keys.optString("messages").length > 0) {
                            messages = main_question_keys.optString("messages")
                        } else {
                            messages = ""
                        }

                        if (main_question_keys.optString("calculations") != null && main_question_keys.optString("calculations").length > 0) {
                            calculations = main_question_keys.optString("calculations")
                        } else {
                            calculations = ""
                        }

                        if (main_question_keys.optString("validation") != null && main_question_keys.optString("validation").length > 0) {
                            val validationobject = main_question_keys.getJSONObject("validation")

                            if (validationobject.optString("required") != null && validationobject.optString("required").length > 0) {
                                vali_req = validationobject.optString("required")
                            } else {
                                vali_req = ""
                            }

                            if (validationobject.optString("avoid_repetition") != null && validationobject.optString("avoid_repetition").length > 0) {
                                avoid_repetition = validationobject.optString("avoid_repetition")
                            } else
                                avoid_repetition = ""

                            if (validationobject.optString("custom") != null && validationobject.optString("custom").length > 0) {
                                val customobject = validationobject.getJSONObject("custom")
                                custom_vali_cond = customobject.optString("validation_condition")
                                cust_lang = customobject.optString("languages")
                            } else {
                                custom_vali_cond = ""
                                cust_lang = ""
                            }

                            if (validationobject.optString("range") != null && validationobject.optString("range").length > 0) {
                                val rangeobject = validationobject.getJSONObject("range")
                                //
                                range_min = rangeobject.optString("min")
                                range_max = rangeobject.optString("max")
                                range_lang = rangeobject.optString("languages")
                            } else {
                                range_min = ""
                                range_max = ""
                                range_lang = ""
                            }

                            if (validationobject.optString("length") != null && validationobject.optString("length").length > 0) {

                                val lengthobject = validationobject.getJSONObject("length")
                                //
                                length_min = lengthobject.optString("min")
                                length_max = lengthobject.optString("max")
                                length_lang = lengthobject.optString("languages")
                            } else {
                                length_min = ""
                                length_max = ""
                                length_lang = ""
                            }

                            if (validationobject.optString("display_condition") != null && validationobject.optString("display_condition").length > 0) {
                                displayCondition = validationobject.optString("display_condition")
                            } else {
                                displayCondition = ""
                            }

                            //                            dbhelper.insertquesValidation(setid, quesid, vali_req, custom_vali_cond, cust_lang, range_min, range_max, range_lang, length_min, length_max, length_lang, displayCondition, avoid_repetition);
                            saveValidationData(form_id, quesid, vali_req, custom_vali_cond, cust_lang, range_min, range_max, range_lang,
                                length_min, length_max, length_lang, displayCondition, avoid_repetition)
                        }

                        if (main_question_keys.optJSONArray("options") != null) {
                            val main_question_optionArray = main_question_keys.optJSONArray("options")

                            for (k in 0 until main_question_optionArray.length()) {

                                val main_ques_options_key = main_question_optionArray.getJSONObject(k)

                                recursiveDependantCheck(main_ques_options_key, main_question_keys.getInt("orientation"))
                            }
                        }
                        //                        dbhelper.insertquestions(Form_id, quesid, question_keyword, orderno, ques_ans_type, setid, ques_lang, messages, calculations, main_question_keys.getInt("orientation"));
                        saveMainQuestions(form_id, quesid, question_keyword, ques_ans_type, ques_lang, messages, calculations, main_question_keys.getInt("orientation"))
                    }

                }
            } catch (e: JSONException) {

            }
            utility.getDatabase().setTransactionSuccessful()
            utility.getDatabase().endTransaction()
            return null
        }


        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            mProgressDialog?.dismiss()
        }

        fun deleteOldFormData() {
            utility.getDatabase().execSQL("DROP TABLE IF EXISTS " + DependentQuestionsTable.TABLE_NAME)
            utility.getDatabase().execSQL("DROP TABLE IF EXISTS " + FormDetailsTable.TABLE_NAME)
            utility.getDatabase().execSQL("DROP TABLE IF EXISTS " + MainQuestionsTable.TABLE_NAME)
            utility.getDatabase().execSQL("DROP TABLE IF EXISTS " + QuestionOptionsTable.TABLE_NAME)
            utility.getDatabase().execSQL("DROP TABLE IF EXISTS " + ValidationsTable.TABLE_NAME)

            utility.getDatabase().execSQL(FormDetailsTable.CREATE_TABLE)
            utility.getDatabase().execSQL(MainQuestionsTable.CREATE_TABLE)
            utility.getDatabase().execSQL(DependentQuestionsTable.CREATE_TABLE)
            utility.getDatabase().execSQL(QuestionOptionsTable.CREATE_TABLE)
            utility.getDatabase().execSQL(ValidationsTable.CREATE_TABLE)
        }


        fun saveFormDetails(form: Form) {
            var values = ContentValues()
            values.put(FormDetailsTable.COLUMN_FORM_ID, form.formId)
            values.put(FormDetailsTable.COLUMN_VISIT_NAME, form.visitName)
            values.put(FormDetailsTable.COLUMN_FROM_WEEKS, form.fromDays)
            values.put(FormDetailsTable.COLUMN_TO_WEEKS, form.toDays)
            values.put(FormDetailsTable.COLUMN_ORDER_ID, form.orderId)

            utility.getDatabase().insert(FormDetailsTable.TABLE_NAME, null, values)
        }

        fun saveValidationData(
            formId: Int, questionId: Int, compulsoryQstn: String, custValCondition: String, custValMessage: String,
            minRange: String, maxRange: String, rangeMessage: String, minLength: String, maxLength: String,
            LengthMessage: String, displayCondition: String, avoidRepetition: String
        ) {
            val values = ContentValues()
            values.put(ValidationsTable.COLUMN_FORM_ID, formId)
            values.put(ValidationsTable.COLUMN_QUESTION_ID, questionId)
            values.put(ValidationsTable.COLUMN_COMPULSORY_QUESTIONS, compulsoryQstn)
            values.put(ValidationsTable.COLUMN_CUSTOM_VALIDATION_CON, custValCondition)
            values.put(ValidationsTable.COLUMN_CUSTOM_VALIDATION_LANG, custValMessage)
            values.put(ValidationsTable.COLUMN_MIN_RANGE, minRange)
            values.put(ValidationsTable.COLUMN_MAX_RANGE, maxRange)
            values.put(ValidationsTable.COLUMN_RANGE_ERROR_MESSAGE, rangeMessage)
            values.put(ValidationsTable.COLUMN_MIN_LENGTH, minLength)
            values.put(ValidationsTable.COLUMN_MAX_LENGTH, maxLength)
            values.put(ValidationsTable.COLUMN_LENGTH_ERROR_MESSAGE, LengthMessage)
            values.put(ValidationsTable.COLUMN_DISPLAY_CONDITION, displayCondition)
            values.put(ValidationsTable.COLUMN_AVOID_REPETETIONS, avoidRepetition)

            utility.getDatabase().insertWithOnConflict(
                ValidationsTable.TABLE_NAME,
                null,
                values, SQLiteDatabase.CONFLICT_IGNORE
            )
        }

        fun saveMainQuestions(
            formId: Int, questionId: Int, keyword: String, questionType: String,
            questionLabel: String, messages: String, calculations: String, orientation: Int
        ) {
            val values = ContentValues()
            values.put(MainQuestionsTable.COLUMN_FORM_ID, formId)
            values.put(MainQuestionsTable.COLUMN_QUESTION_ID, questionId)
            values.put(MainQuestionsTable.COLUMN_KEYWORD, keyword)
            values.put(MainQuestionsTable.COLUMN_QUESTION_TYPE, questionType)
            values.put(MainQuestionsTable.COLUMN_QUESTION_LABEL, questionLabel)
            values.put(MainQuestionsTable.COLUMN_MESSAGES, messages)
            values.put(MainQuestionsTable.COLUMN_CALCULATION, calculations)
            values.put(MainQuestionsTable.COLUMN_ORIENTATION, orientation)

            utility.getDatabase().insert(MainQuestionsTable.TABLE_NAME, null, values)
        }

        fun saveDependentQuestions(
            optionKeyword: String,
            questionId: Int,
            formId: Int,
            keyword: String,
            questionType: String,
            questionLabel: String,
            messages: String,
            validation: String,
            orientation: Int
        ) {

            val values = ContentValues()
            values.put(DependentQuestionsTable.COLUMN_MAIN_QUESTION_OPTION_KEYWORD, optionKeyword)
            values.put(DependentQuestionsTable.COLUMN_QUESTION_ID, questionId)
            values.put(DependentQuestionsTable.COLUMN_FORM_ID, formId)
            values.put(DependentQuestionsTable.COLUMN_KEYWORD, keyword)
            values.put(DependentQuestionsTable.COLUMN_QUESTION_TYPE, questionType)
            values.put(DependentQuestionsTable.COLUMN_QUESTION_LABEL, questionLabel)
            values.put(DependentQuestionsTable.COLUMN_MESSAGES, messages)
            values.put(DependentQuestionsTable.COLUMN_VALIDATIONS, validation)
            values.put(DependentQuestionsTable.COLUMN_ORIENTATION, orientation)

            utility.getDatabase().insert(DatabaseContract.DependentQuestionsTable.TABLE_NAME, null, values)
        }

        fun saveQuestionOptions(formId: Int, questionId: Int, keyword: String, optionLabel: String, messages: String) {

            val values = ContentValues()

            values.put(QuestionOptionsTable.COLUMN_FORM_ID, formId)
            values.put(QuestionOptionsTable.COLUMN_QUESTION_ID, questionId)
            values.put(QuestionOptionsTable.COLUMN_KEYWORD, keyword)
            values.put(QuestionOptionsTable.COLUMN_OPTION_LABEL, optionLabel)
            values.put(QuestionOptionsTable.COLUMN_MESSAGES, messages)

            utility.getDatabase().insert(DatabaseContract.QuestionOptionsTable.TABLE_NAME, null, values)
        }

        @Throws(JSONException::class)
        fun recursiveDependantCheck(main_ques_options_key: JSONObject, orientation: Int) {
            option_id = main_ques_options_key.optString("id")
            option_keyword = main_ques_options_key.optString("keyword")
            depend1 = main_ques_options_key.optString("dependents")
            if (main_ques_options_key.optString("messages") != null && main_ques_options_key.optString("messages").length > 0) {
                option_messages = main_ques_options_key.optString("messages")
            } else {
                option_messages = ""
            }

            if (main_ques_options_key.optString("dependents") != null && main_ques_options_key.optString("dependents").length > 0) {
                dependant = "true"
            } else {
                dependant = "false"
            }

            option_language = main_ques_options_key.optString("languages")

            if (option_language != null && option_language.length > 0) {
                var object1: JSONObject? = null
                try {
                    object1 = main_ques_options_key.getJSONObject("languages")
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

                attr16 = object1?.optString("en")!!

                attr17 = object1?.optString("mr")

            }

            if (main_ques_options_key.optString("action") != null && main_ques_options_key.optString("action").length > 0) {

                val main_ques_action = main_ques_options_key.getJSONObject("action")

                if (main_ques_action.optJSONArray("question") != null) {
                    val jsonArray4 = main_ques_action.optJSONArray("question")

                    for (p in 0 until jsonArray4.length()) {
                        val dependant_ques_key = jsonArray4.getJSONObject(p)

                        val depandant_ques_lang = dependant_ques_key.getJSONObject("languages")

                        if (dependant_ques_key.optString("messages") != null && dependant_ques_key.optString("messages").length > 0) {
                            dependant_ques_messages = dependant_ques_key.optString("messages")
                        } else {
                            dependant_ques_messages = ""
                        }


                        if (dependant_ques_key.optJSONArray("options") != null && dependant_ques_key.optJSONArray("options").length() > 0) {
                            val jsonArray5 = dependant_ques_key.optJSONArray("options")

                            for (l in 0 until jsonArray5.length()) {
                                val depandant_ques_option_key = jsonArray5.getJSONObject(l)

                                if (depandant_ques_option_key.optString("messages") != null &&
                                    depandant_ques_option_key.optString("messages").length > 0) {
                                    dependant_ques_messages = depandant_ques_option_key.optString("messages").toString()
                                } else {
                                    dependant_ques_messages = ""
                                }

                                //                                dbhelper.insertDependantQuestion(Form_id, setid, dependant_ques_key.optString("id"), depandant_ques_option_key.optString("keyword"), "", depandant_ques_option_key.optString("languages"), "", dependant_ques_messages);
                                saveQuestionOptions(form_id, dependant_ques_key.optInt("id"), depandant_ques_option_key.optString("keyword"),
                                    depandant_ques_option_key.optString("languages"), dependant_ques_messages!!
                                )

                                if (depandant_ques_option_key.optString("action") != null &&
                                    depandant_ques_option_key.optString("action").length > 0) {
                                    val depandant_ques_action_dependant_key =
                                        depandant_ques_option_key.getJSONObject("action")

                                    val option_keyword = depandant_ques_option_key.optString("keyword")

                                    actionDependantQuestions(depandant_ques_action_dependant_key, option_keyword, orientation)
                                }

                            }
                        }

                        //                        dbhelper.insertDependantquestionlist(option_keyword, dependant_ques_key.optString("id"),Form_id,dependant_ques_key.optString("set_id"), dependant_ques_key.optString("keyword"), dependant_ques_key.optString("answer_type"), ""+depandant_ques_lang,dependant_ques_messages,dependant_ques_key.optString("validation"),dependant_ques_key.optString("order_no"),orientation);
                        saveDependentQuestions(option_keyword, dependant_ques_key.optInt("id"), form_id,
                            dependant_ques_key.optString("keyword"), dependant_ques_key.optString("answer_type"),
                            "" + depandant_ques_lang, dependant_ques_messages!!, dependant_ques_key.optString("validation"), orientation)
                    }
                }
            }

            //            dbhelper.insertDependantQuestion(Form_id,setid, quesid, option_keyword, "", option_language,"",option_messages);
            saveQuestionOptions(form_id, quesid, option_keyword, option_language, option_messages)

        }

        fun actionDependantQuestions( depedant_ques_action_dependant_key: JSONObject,option_keyword: String,orientation: Int){
            if(depedant_ques_action_dependant_key.optJSONArray("question")!=null){
                val jsonArray4 = depedant_ques_action_dependant_key.optJSONArray("question")
                for(p in 0 until jsonArray4.length()){
                    var dependant_ques_key = jsonArray4.getJSONObject(p)
                    var dependant_ques_lang : JSONObject = dependant_ques_key.getJSONObject("language")
                    if(dependant_ques_key.optString("messages")!=null && dependant_ques_key.optString("messages").length>0){
                        dependant_ques_messages= dependant_ques_key.optString("messages")
                    }
                    else{
                        dependant_ques_messages=""
                    }

                    if(dependant_ques_key.optJSONArray("options")!=null && dependant_ques_key.optJSONArray("options").length()>0){
                        var jsonArray5 : JSONArray= dependant_ques_key.optJSONArray("options")
                        for(l in 0 until jsonArray5.length()){

                            var dependant_ques_option_key = jsonArray5.getJSONObject(l)

                            if(dependant_ques_option_key.optString("messages")!=null && dependant_ques_option_key.optString("messages").length>0){
                                dependant_ques_messages = dependant_ques_option_key.optString("messages").toString()
                            }
                            else{
                                dependant_ques_messages=""
                            }

                            saveQuestionOptions(form_id, dependant_ques_key.optInt("id"), dependant_ques_option_key.optString("keyword"),
                                dependant_ques_option_key.optString("languages"), dependant_ques_messages!!)

                            if (dependant_ques_option_key.optString("action").toString() != null && dependant_ques_option_key.optString("action").toString().length > 0) {

                                val recursive_action_dependant_key = dependant_ques_option_key.getJSONObject("action")
                                val option_keyword = dependant_ques_option_key.optString("keyword")
                                actionDependantQuestions(recursive_action_dependant_key, option_keyword, orientation)
                            }
                        }
                    }

                    saveDependentQuestions(option_keyword,dependant_ques_key.optInt("id"),form_id,dependant_ques_key.optString("keyword"),
                        dependant_ques_key.optString("answer_type"),""+dependant_ques_lang,dependant_ques_messages,dependant_ques_key.optString("validation"),orientation)
                }
             }
        }

    }

    inner class SaveRestoredDataAsyncTask (listRegistrations: ArrayList<BeneficiaryDetails>,listVisits: ArrayList<BeneficiariesList>): AsyncTask<ArrayList<Object>, Int, Void>() {

        var progressBar: ProgressBar? = null
        var mProgressDialog: AlertDialog? = null
        var mProgress: Int = 0
        var listRegistrations: ArrayList<BeneficiaryDetails>? = null
        var listVisits: ArrayList<BeneficiariesList>? = null



        override fun onPreExecute() {
            super.onPreExecute()
            var inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var dialogView = inflater.inflate(R.layout.progress_dialog_layout, null)
            val textView = dialogView.findViewById<TextView>(R.id.textView_label)
            progressBar = dialogView.findViewById(R.id.progressBar)
            textView.setText(R.string.saving_forms)
            progressBar?.isIndeterminate = false
            progressBar?.max = listRegistrations!!.size + listVisits!!.size
            val mAlertDialogBuilder = AlertDialog.Builder(mContext)
            mAlertDialogBuilder.setView(dialogView)
            mAlertDialogBuilder.setCancelable(false)
            mProgressDialog = mAlertDialogBuilder.create()
            mProgressDialog?.show()


        }

        override fun doInBackground(vararg params: ArrayList<Object>?): Void? {
            deleteOldData()
            utility.getDatabase().beginTransaction()
            for (details in listRegistrations!!) {
                saveRegistrations(details)
                publishProgress(++mProgress)
            }

            for (data in listVisits!!) {
                saveVisits(data)
                publishProgress(++mProgress)
            }

            utility.getDatabase().setTransactionSuccessful()
            utility.getDatabase().endTransaction()
            return null
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            progressBar?.progress = values[0]!!
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            mProgressDialog?.dismiss()
        }


        fun saveRegistrations(data: BeneficiaryDetails) {
            val values = ContentValues()
            values.put(RegistrationTable.COLUMN_UNIQUE_ID, data.getUniqueId())
            values.put(RegistrationTable.COLUMN_FIRST_NAME, data.getFirstName())
            values.put(RegistrationTable.COLUMN_MIDDLE_NAME, data.getMiddleName())
            values.put(RegistrationTable.COLUMN_LAST_NAME, data.getLastName())
            values.put(RegistrationTable.COLUMN_MOBILE_NO, data.getMobNo())
            values.put(RegistrationTable.COLUMN_ALTERNATE_NO, data.getAlternateNo())
            values.put(RegistrationTable.COLUMN_VILLAGE_ID, data.getVillageId())
            values.put(RegistrationTable.COLUMN_LMP_DATE, data.getLmp())
            values.put(RegistrationTable.COLUMN_EDD_DATE, data.getEdd())
            values.put(RegistrationTable.COLUMN_ADDRESS, data.getAddress())
            values.put(RegistrationTable.COLUMN_DOB, data.getDob())
            values.put(RegistrationTable.COLUMN_EDUCATION, data.getEducation())
            values.put(RegistrationTable.COLUMN_RELIGION, data.getReligion())
            values.put(RegistrationTable.COLUMN_CATEGORY, data.getCategory())
            values.put(RegistrationTable.COLUMN_MOTHER_ID, data.getMotherId())
            values.put(RegistrationTable.COLUMN_GENDER, data.getGender())
            values.put(RegistrationTable.COLUMN_DELIVERY_DATE, data.getDeliveryDate())
            values.put(RegistrationTable.COLUMN_REGISTRATION_STATUS, 1)
            values.put(RegistrationTable.COLUMN_SYNC_STATUS, 1)
            values.put(RegistrationTable.COLUMN_CREATED_ON, data.getCreatedOn())
            values.put(RegistrationTable.COLUMN_CLOSE_STATUS, data.getCloseStatus())
            values.put(RegistrationTable.COLUMN_CLOSE_DATE, data.getCloseDate())
            values.put(RegistrationTable.COLUMN_CLOSE_REASON, data.getCloseReason())
            values.put(RegistrationTable.COLUMN_EXPIRED_DATE, data.getExpiredDate())
            values.put(RegistrationTable.COLUMN_EXPIRED_REASON, data.getExpiredReason())

            if (data.getImage() != null && !data.getImage().equals("")) {
                try {
                    val encodeByte = Base64.decode(data.getImage(), Base64.DEFAULT)
                    var bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
                    bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, false)
                    val out = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                    values.put(RegistrationTable.COLUMN_IMAGE, out.toByteArray())
                } catch (e: Exception) {
                    e.message
                }
            }
            utility.getDatabase().insert(RegistrationTable.TABLE_NAME, null, values)
        }

        fun saveVisits(data: BeneficiariesList) {
            for (list in data.visitsList!!) {
                val values = ContentValues()
                values.put(DatabaseContract.FilledFormStatusTable.COLUMN_UNIQUE_ID, data.uniqueId)
                values.put(DatabaseContract.FilledFormStatusTable.COLUMN_FORM_ID, list.formId)
                values.put(DatabaseContract.FilledFormStatusTable.COLUMN_FORM_COMPLETION_STATUS, 1)
                values.put(DatabaseContract.FilledFormStatusTable.COLUMN_FORM_SYNC_STATUS, 1)
                values.put(DatabaseContract.FilledFormStatusTable.COLUMN_CREATED_ON, list.createdOn)

                val referenceId =
                    utility.getDatabase().insert(DatabaseContract.FilledFormStatusTable.TABLE_NAME, null, values) as Int

                for (questionAnswer in list.questionAnswers!!) {
                    saveQuestionAnswers(referenceId, data.uniqueId, list.formId, questionAnswer)
                }
            }
        }

        fun saveQuestionAnswers(referenceId: Int, uniqueId: String, formId: Int, questionAnswer: QuestionAnswer) {
            val values = ContentValues()
            values.put(DatabaseContract.QuestionAnswerTable.COLUMN_REFERENCE_ID, referenceId)
            values.put(DatabaseContract.QuestionAnswerTable.COLUMN_UNIQUE_ID, uniqueId)
            values.put(DatabaseContract.QuestionAnswerTable.COLUMN_FORM_ID, formId)
            values.put(DatabaseContract.QuestionAnswerTable.COLUMN_QUESTION_KEYWORD, questionAnswer.keyword)
            values.put(DatabaseContract.QuestionAnswerTable.COLUMN_ANSWER_KEYWORD, questionAnswer.answer)
            values.put(DatabaseContract.QuestionAnswerTable.COLUMN_CREATED_ON, questionAnswer.createdOn)
            utility.getDatabase().insert(DatabaseContract.QuestionAnswerTable.TABLE_NAME, null, values)
        }

        fun deleteOldData() {
            utility.getDatabase().execSQL("DROP TABLE IF EXISTS " + RegistrationTable.TABLE_NAME)
            utility.getDatabase().execSQL("DROP TABLE IF EXISTS " + FilledFormStatusTable.TABLE_NAME)
            utility.getDatabase().execSQL("DROP TABLE IF EXISTS " + QuestionAnswerTable.TABLE_NAME)
            utility.getDatabase().execSQL("DROP TABLE IF EXISTS " + ReferralTable.TABLE_NAME)
            utility.getDatabase().execSQL("DROP TABLE IF EXISTS " + ChildGrowthTable.TABLE_NAME)

            utility.getDatabase().execSQL(RegistrationTable.CREATE_TABLE)
            utility.getDatabase().execSQL(FilledFormStatusTable.CREATE_TABLE)
            utility.getDatabase().execSQL(QuestionAnswerTable.CREATE_TABLE)
            utility.getDatabase().execSQL(ReferralTable.CREATE_TABLE)
            utility.getDatabase().execSQL(ChildGrowthTable.CREATE_TABLE)
        }
    }

}
