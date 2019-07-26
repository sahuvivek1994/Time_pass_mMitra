package tech.inscripts.ins_armman.mMitra.menu

import android.content.Context
import android.database.Cursor
import android.graphics.BitmapFactory
import org.json.JSONException
import org.json.JSONObject
import tech.inscripts.ins_armman.mMitra.R
import tech.inscripts.ins_armman.mMitra.data.database.DatabaseContract
import tech.inscripts.ins_armman.mMitra.data.model.SyncRegistrationDetails
import tech.inscripts.ins_armman.mMitra.data.model.syncing.BeneficiaryDetails
import tech.inscripts.ins_armman.mMitra.data.model.syncing.FormDetails
import tech.inscripts.ins_armman.mMitra.data.model.syncing.QuestionAnswer
import tech.inscripts.ins_armman.mMitra.utility.Constants.*
import tech.inscripts.ins_armman.mMitra.utility.Utility
import java.util.ArrayList

class HomeActivityPresentor : IHomeActivityPresentor<IHomeActivityView>,IHomeActivityInteractor.OnDataSync,IHomeActivityInteractor.OnFormSync {

    private val FETCH_USER_DATA = 101
    private val FETCH_REGISTRATION_DATA = 102
    private val FETCH_FORMS_DATA = 103
    private val FETCH_UNSENT_FORM_COUNT = 104


    private var mIHomeActivityView: IHomeActivityView? = null
     var mInteractor: HomeActivityInteractor? = null
    private var mContext: Context? = null
    private var mUsername: String = ""
    private var mPassword:String = ""
    private var mImei: ArrayList<String>?=null

    var utility= Utility()
    private val mOnQueryFinished = object : IHomeActivityPresentor.OnQueryFinished {
        override fun onSuccess(cursor: Cursor, id: Int) {
            when (id) {
                FETCH_USER_DATA -> if (cursor.moveToFirst()) {
                    mUsername = cursor.getString(cursor.getColumnIndex(DatabaseContract.LoginTable.COLUMN_USERNAME))
                    mPassword = cursor.getString(cursor.getColumnIndex(DatabaseContract.LoginTable.COLUMN_PASSWORD))
                    mImei = utility.getDeviceImeiNumber(mContext!!)
                    val arogyasakhiName = cursor.getString(cursor.getColumnIndex(DatabaseContract.LoginTable.COLUMN_NAME))
                  //  mIHomeActivityView?.setArogyasakhiName(arogyasakhiName)
                }

                FETCH_UNSENT_FORM_COUNT -> if (cursor.moveToFirst())
                    mIHomeActivityView?.setUnsentFormsCount(cursor.getInt(0))

                FETCH_REGISTRATION_DATA -> if (cursor.count > 0) {
                    onFetchedRegistrationData(cursor)
                } else
                    syncUnsentForms()
            }
        }

        override fun onSuccess() {

        }

        override fun onFailure() {

        }
    }


    override fun fetchRegistrationData() {
    mIHomeActivityView?.showProgressBar(mContext?.getString(R.string.uploading_forms)!!)

        ResetFailureTask(object : IHomeActivityPresentor.OnResetTaskCompleted {
            override fun onResetCompleted() {
                mInteractor?.fetchRegistrationDetails(FETCH_REGISTRATION_DATA)
            }
        }, mInteractor)
            .execute()
    }

    override fun onFetchedRegistrationData(cursor: Cursor) {
        var regDetails = SyncRegistrationDetails()
        /*regDetails.setusername(mUsername)
        regDetails.setpassword(mPassword)
        */regDetails.setusername("test_user1")
        regDetails.setpassword("test_user1")
       regDetails.setImei("869432026925037")

        val regData = ArrayList<BeneficiaryDetails>()
        while (cursor.moveToNext()) {
            val details = BeneficiaryDetails()

            /*val blob = cursor.getBlob(cursor.getColumnIndex(DatabaseContract.RegistrationTable.COLUMN_IMAGE))
            if (blob != null) {
                val bitmap = BitmapFactory.decodeByteArray(blob, 0, blob.size)
                details.setImage(utility.getStringFromBitmap(bitmap))
            }*/

            val uniqueId = cursor.getString(cursor.getColumnIndex(DatabaseContract.RegistrationTable.COLUMN_UNIQUE_ID))
            details.setUniqueId(uniqueId)
            details.setName(cursor.getString(cursor.getColumnIndex(DatabaseContract.RegistrationTable.COLUMN_NAME)))
            details.setAddress(cursor.getString(cursor.getColumnIndex(DatabaseContract.RegistrationTable.COLUMN_ADDRESS)))
            details.setMobNo(cursor.getString(cursor.getColumnIndex(DatabaseContract.RegistrationTable.COLUMN_MOBILE_NO)))
            details.setLmp(cursor.getString(cursor.getColumnIndex(DatabaseContract.RegistrationTable.COLUMN_LMP_DATE)))
            details.setDob(cursor.getString(cursor.getColumnIndex(DatabaseContract.RegistrationTable.COLUMN_AGE)))
            details.setEducation(cursor.getString(cursor.getColumnIndex(DatabaseContract.RegistrationTable.COLUMN_EDUCATION)))
            details.setCreatedOn(cursor.getString(cursor.getColumnIndex(DatabaseContract.RegistrationTable.COLUMN_CREATED_ON)))
            regData.add(details)
        }
        if (regData.size == 0)
            return

        regDetails.setRegData(regData)

        mInteractor?.sendRegistrationBasicDetails(regDetails, this)
    }

    override fun fetchUnsentFormsCount() {
    mInteractor?.fetchUnsentFormsCount(FETCH_UNSENT_FORM_COUNT)
    }

    override fun syncUnsentForms() {
        var cursor = mInteractor?.checkUnsentForms()
        if (cursor?.moveToFirst()!!) {

            var details = FormDetails()
            var answerList = ArrayList<QuestionAnswer>()

            details.setusername(mUsername!!)
            details.setpassword(mPassword!!)
            details.setImei("869432026925037")
            var uniqueId = cursor?.getString(cursor.getColumnIndex(DatabaseContract.FilledFormStatusTable.COLUMN_UNIQUE_ID))
            details.setUniqueId(uniqueId)
            var formId = cursor.getString(cursor.getColumnIndex(DatabaseContract.FilledFormStatusTable.COLUMN_FORM_ID))
            details.setFormId(formId)

            var cursorForm = mInteractor?.fetchFormData(cursor.getString(cursor.getColumnIndex(DatabaseContract.FilledFormStatusTable.COLUMN_ID)))
            while (cursorForm?.moveToNext()!!) {
                var answer = QuestionAnswer()
                answer.setKeyword(cursorForm.getString(cursorForm.getColumnIndex(DatabaseContract.QuestionAnswerTable.COLUMN_QUESTION_KEYWORD)))
                answer.setAnswer(cursorForm.getString(cursorForm.getColumnIndex(DatabaseContract.QuestionAnswerTable.COLUMN_ANSWER_KEYWORD)))
                answer.setCreatedOn(cursorForm.getString(cursorForm.getColumnIndex(DatabaseContract.QuestionAnswerTable.COLUMN_CREATED_ON)))
                answerList.add(answer)
            }

            details.setData(answerList)

            mInteractor?.sendForms(details, this)
        }
    }
    private fun markImproperVisitToSync(uniqueId: String, formId: String, errorMsg: String) {
        mInteractor?.updateFormFailureStatus(uniqueId, formId, errorMsg)
        syncUnsentForms()
    }

    private fun markImproperRegistrationToSync(uniqueId: String, errorMsg: String) {
        mInteractor?.updateRegistrationFailureStatus(uniqueId, errorMsg)
        mInteractor?.fetchRegistrationDetails(FETCH_REGISTRATION_DATA)
    }
    override fun attachView(view: IHomeActivityView) {
        mIHomeActivityView = view
        mContext = mIHomeActivityView?.getContext()
        mInteractor = HomeActivityInteractor(mContext, mOnQueryFinished)
        mInteractor?.fetchLoginDetails(FETCH_USER_DATA)
    }


    override fun detachView() {
    }

    override fun onSuccessFullySyncRegData(jsonObjectResponse: JSONObject) {
        if (!jsonObjectResponse.optBoolean(STATUS) && (jsonObjectResponse.optString(RESPONSE) == AUTHENTICATION_FAILED ||
                    jsonObjectResponse.optString(RESPONSE) == INVALID_IMEI)
        )
            onFailure(mContext?.getString(R.string.authentication_error_msg)!!)
        else {
            try {
                val jsonArrayResponse = jsonObjectResponse.getJSONArray(DATA)
                mInteractor?.updateRegistrationSyncStatus(jsonArrayResponse)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            mInteractor?.fetchRegistrationDetails(FETCH_REGISTRATION_DATA)
        }
    }

    override fun onFailure(message: String) {
        mIHomeActivityView?.hideProgressBar()
        mIHomeActivityView?.showSnackBar(message)
        fetchUnsentFormsCount()
    }


}