package tech.inscripts.ins_armman.mMitra.menu

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.support.v7.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONException
import tech.inscripts.ins_armman.mMitra.data.database.DatabaseContract
import tech.inscripts.ins_armman.mMitra.data.database.GenericCursorLoader
import tech.inscripts.ins_armman.mMitra.data.database.LocalDataSource
import tech.inscripts.ins_armman.mMitra.data.model.SyncRegistrationDetails
import tech.inscripts.ins_armman.mMitra.data.model.syncing.FormDetails
import tech.inscripts.ins_armman.mMitra.data.model.syncing.UpdateImageModel
import tech.inscripts.ins_armman.mMitra.data.retrofit.RemoteDataSource
import tech.inscripts.ins_armman.mMitra.utility.Constants
import tech.inscripts.ins_armman.mMitra.utility.Constants.*
import tech.inscripts.ins_armman.mMitra.utility.Utility

class HomeActivityInteractor : IHomeActivityInteractor, LoaderManager.LoaderCallbacks<Cursor> {
    private var mContext: Context?=null
    private val mOnQueryFinished: IHomeActivityPresentor.OnQueryFinished
    var utility = Utility()
   var remoteDataSource=RemoteDataSource()
    constructor(mContext: Context?, mOnQueryFinished: IHomeActivityPresentor.OnQueryFinished) {
        this.mContext = mContext
        this.mOnQueryFinished = mOnQueryFinished
    }


    override fun fetchLoginDetails(id: Int) {
    var bundle=Bundle()
        bundle.putString(Constants.QUERY_ARGS_TABLE_NAME,DatabaseContract.LoginTable.TABLE_NAME)
        bundle.putStringArrayList(Constants.QUERY_ARGS_PROJECTION,null)
        bundle.putString(Constants.QUERY_ARGS_SELECTION,null)
        bundle.putStringArrayList(Constants.QUERY_ARGS_SELECTION_ARGS,null)
        (mContext as AppCompatActivity).supportLoaderManager.restartLoader(id, bundle, this)
    }

    override fun fetchRegistrationDetails(id: Int) {
        val query = ("SELECT * FROM "
                + DatabaseContract.RegistrationTable.TABLE_NAME
                + " WHERE "
                + DatabaseContract.RegistrationTable.COLUMN_SYNC_STATUS + " = 0 "
                + " AND "
                + DatabaseContract.RegistrationTable.COLUMN_REGISTRATION_STATUS + " = 1 "
                + " AND "
                + DatabaseContract.RegistrationTable.COLUMN_FAILURE_STATUS + " = 0 "
                + " LIMIT 2 ")

        val bundle = Bundle()
        bundle.putString(Constants.RAW_QUERY, query)
        bundle.putString(Constants.QUERY_TYPE, LocalDataSource.QueryType.RAW.name)
        (mContext as AppCompatActivity).supportLoaderManager.restartLoader(id, bundle, this)
    }

    override fun checkUnsentForms(): Cursor {
        val query = ("SELECT f.* FROM "
                + DatabaseContract.FilledFormStatusTable.TABLE_NAME + " f"
                + " JOIN "
                + DatabaseContract.RegistrationTable.TABLE_NAME + " r"
                + " ON "
                + " f." + DatabaseContract.FilledFormStatusTable.COLUMN_UNIQUE_ID + " = "
                + " r." + DatabaseContract.RegistrationTable.COLUMN_UNIQUE_ID
                + " WHERE "
                + " f." + DatabaseContract.FilledFormStatusTable.COLUMN_FORM_SYNC_STATUS + " = 0 "
                + " AND "
                + " f." + DatabaseContract.FilledFormStatusTable.COLUMN_FORM_COMPLETION_STATUS + " = 1 "
                + " AND "
                + " f." + DatabaseContract.FilledFormStatusTable.COLUMN_FAILURE_STATUS + " = 0 "
                + " AND "
                + " r." + DatabaseContract.RegistrationTable.COLUMN_SYNC_STATUS + " = 1 "
                + " LIMIT 1 ")
        return utility.getDatabase().rawQuery(query, null)
    }

    override fun fetchFormData(referenceId: String): Cursor {
        val query = ("SELECT * FROM "
                + DatabaseContract.QuestionAnswerTable.TABLE_NAME
                + " WHERE "
                + DatabaseContract.QuestionAnswerTable.COLUMN_REFERENCE_ID + " = ? ")
        return utility.getDatabase().rawQuery(query, arrayOf<String>(referenceId))
    }

    override fun sendRegistrationBasicDetails(registrationDetails: SyncRegistrationDetails, onDataSync: IHomeActivityInteractor.OnDataSync) {
        val remoteDataSource = remoteDataSource.getInstance()
        val registrationService = remoteDataSource.syncRegistrationService()
        registrationService.syncRegistrationDetails(registrationDetails, onDataSync, mContext!!)
    }

    override fun updateRegistrationSyncStatus(jsonArray: JSONArray) {
        val length = jsonArray.length()
        val values = ContentValues()
        for (i in 0 until length) {
            try {
                val `object` = jsonArray.getJSONObject(i)
                if (`object`.optBoolean(STATUS)) {
                    values.put(DatabaseContract.RegistrationTable.COLUMN_SYNC_STATUS, 1)
                    values.put(DatabaseContract.RegistrationTable.COLUMN_UPDATE_IMAGE_STATUS, 1)
                    values.put(DatabaseContract.RegistrationTable.COLUMN_FAILURE_STATUS, 0)
                    values.put(DatabaseContract.RegistrationTable.COLUMN_FAILURE_REASON, "")
                    utility.getDatabase().update(DatabaseContract.RegistrationTable.TABLE_NAME, values, DatabaseContract.RegistrationTable.COLUMN_UNIQUE_ID + " = ? ",
                        arrayOf<String>(`object`.optString(UNIQUE_ID)))
                    values.clear()
                } else if (`object`.has(UNIQUE_ID)) {
                    values.put(DatabaseContract.RegistrationTable.COLUMN_FAILURE_STATUS, 1)
                    values.put(DatabaseContract.RegistrationTable.COLUMN_FAILURE_REASON, `object`.optString(RESPONSE, INVALID_DATA))

                    utility.getDatabase().update(DatabaseContract.RegistrationTable.TABLE_NAME, values, DatabaseContract.RegistrationTable.COLUMN_UNIQUE_ID + " = ? ",
                        arrayOf<String>(`object`.optString(UNIQUE_ID)))
                    values.clear()
                }

            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }

    override fun sendForms(formDetails: FormDetails, onFormSync: IHomeActivityInteractor.OnFormSync) {
        val remoteDataSource = remoteDataSource.getInstance()
        val syncFormService = remoteDataSource.syncFormService()
        syncFormService.syncForms(formDetails, onFormSync, mContext!!)
    }

    override fun updateFormSyncStatus(uniqueId: String, formId: String) {
        val values = ContentValues()
        values.put(DatabaseContract.FilledFormStatusTable.COLUMN_FORM_SYNC_STATUS, 1)
        values.put(DatabaseContract.FilledFormStatusTable.COLUMN_FAILURE_STATUS, 0)
        values.put(DatabaseContract.FilledFormStatusTable.COLUMN_FAILURE_REASON, "")

        utility.getDatabase().update(
            DatabaseContract.FilledFormStatusTable.TABLE_NAME,
            values,
            DatabaseContract.FilledFormStatusTable.COLUMN_UNIQUE_ID + " = ? "
                    + " AND "
                    + DatabaseContract.FilledFormStatusTable.COLUMN_FORM_ID + " = ? ",
            arrayOf<String>(uniqueId, formId)
        )

        values.clear()
    }

    override fun fetchUnsentFormsCount(id: Int) {
        val query = ("SELECT (SELECT COUNT(*) FROM "
                + DatabaseContract.FilledFormStatusTable.TABLE_NAME
                + " WHERE "
                + DatabaseContract.FilledFormStatusTable.COLUMN_FORM_SYNC_STATUS
                + " = 0 "
                + " AND "
                + DatabaseContract.FilledFormStatusTable.COLUMN_FORM_COMPLETION_STATUS
                + " = 1 )"
                + " + "
                + " (SELECT COUNT(DISTINCT(" + DatabaseContract.RegistrationTable.COLUMN_UNIQUE_ID + ")) "
                + " FROM "
                + DatabaseContract.RegistrationTable.TABLE_NAME
                + " WHERE "
                + DatabaseContract.RegistrationTable.COLUMN_SYNC_STATUS + " = 1 "
                + " AND "
                + DatabaseContract.RegistrationTable.COLUMN_UPDATE_IMAGE_STATUS + " = 0 )")

        val bundle = Bundle()
        bundle.putString(RAW_QUERY, query)
        bundle.putString(QUERY_TYPE, LocalDataSource.QueryType.RAW.name)
        (mContext as AppCompatActivity).supportLoaderManager.restartLoader(id, bundle, this)
    }

    override fun fetchUpdatedPhotoData(id: Int) {

    }

    override fun sendUpdatePhotoImage(updateImageModel: UpdateImageModel, photoSync: IHomeActivityInteractor.OnUpdatedPhotoSync) {
    }

    override fun updateUpdatePhotoImageStatus(uniqueId: String) {
    }

    override fun updateUpdatePhotoImageFailureStatus(uniqueId: String, response: String) {
    }

    override fun updateFormFailureStatus(uniqueId: String, formId: String, errorMsg: String) {
     val values = ContentValues()
        values.put(DatabaseContract.FilledFormStatusTable.COLUMN_FAILURE_STATUS, 1)
        values.put(DatabaseContract.FilledFormStatusTable.COLUMN_FAILURE_REASON, errorMsg)

        val result = utility.getDatabase().update(
            DatabaseContract.FilledFormStatusTable.TABLE_NAME, values,
            DatabaseContract.FilledFormStatusTable.COLUMN_UNIQUE_ID + "=? AND "
            + DatabaseContract.FilledFormStatusTable.COLUMN_FORM_ID + " =? ", arrayOf<String>(uniqueId, formId))
        if (result < 1)
throw IllegalArgumentException("Invalid unique &/ formId")
    }

    override fun resetFailureStatus() {
        utility.getDatabase().beginTransaction()

        val values = ContentValues()
        //column name is same IN all table
        values.put(DatabaseContract.FilledFormStatusTable.COLUMN_FAILURE_STATUS, 0)

        utility.getDatabase().update(DatabaseContract.RegistrationTable.TABLE_NAME, values, null, null)
        utility.getDatabase().update(DatabaseContract.FilledFormStatusTable.TABLE_NAME, values, null, null)
        utility.getDatabase().setTransactionSuccessful()
        utility.getDatabase().endTransaction()
    }

    override fun updateRegistrationFailureStatus(uniqueId: String, errorMsg: String) {
        val values = ContentValues()
        values.put(DatabaseContract.RegistrationTable.COLUMN_FAILURE_STATUS, 1)
        values.put(DatabaseContract.RegistrationTable.COLUMN_FAILURE_REASON, errorMsg)

        val result = utility.getDatabase().update(
            DatabaseContract.RegistrationTable.TABLE_NAME,
            values,
            DatabaseContract.RegistrationTable.COLUMN_UNIQUE_ID + " =? ",
            arrayOf<String>(uniqueId)
        )

        if (result < 1)
            throw IllegalArgumentException("Invalid unique &/ formId")
    }



    override fun onCreateLoader(p0: Int, bundle: Bundle?): Loader<Cursor> {
    var sqLiteDatabase : SQLiteDatabase= utility.getDatabase()
        var queryType = bundle?.getString(Constants.QUERY_TYPE)
        if(queryType!=null && queryType.equals(LocalDataSource.QueryType.RAW.name, ignoreCase = true))
            return GenericCursorLoader(mContext,sqLiteDatabase,bundle,LocalDataSource.QueryType.RAW)
            return GenericCursorLoader(mContext,sqLiteDatabase,bundle,LocalDataSource.QueryType.FUNCTION)
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor) {
        mOnQueryFinished.onSuccess(data,loader.id)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
    }
}