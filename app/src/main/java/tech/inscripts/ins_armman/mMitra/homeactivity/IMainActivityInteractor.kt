package tech.inscripts.ins_armman.mMitra.homeactivity

import android.database.Cursor
import org.json.JSONArray
import org.json.JSONObject
import tech.inscripts.ins_armman.mMitra.data.model.RequestFormModel
import tech.inscripts.ins_armman.mMitra.data.model.SyncRegistrationDetails
import tech.inscripts.ins_armman.mMitra.data.model.restoreData.BeneficiariesList
import tech.inscripts.ins_armman.mMitra.data.model.restoreData.RestoreDataRequest
import tech.inscripts.ins_armman.mMitra.data.model.syncing.BeneficiaryDetails
import tech.inscripts.ins_armman.mMitra.data.model.syncing.FormDetails
import tech.inscripts.ins_armman.mMitra.data.model.syncing.UpdateImageModel
import tech.inscripts.ins_armman.mMitra.settingactivity.ISettingsInteractor
import java.util.ArrayList

interface IMainActivityInteractor  {
    fun getLoginDetails() :Cursor
     fun fetchLoginDetails(id: Int)
      fun fetchRegistrationDetails(id: Int)
      fun checkUnsentForms(): Cursor
      fun fetchFormData(referenceId: String): Cursor
      fun sendRegistrationBasicDetails(
          registrationDetails: SyncRegistrationDetails,
          onDataSync: IMainActivityInteractor.OnDataSync
    )

      fun updateRegistrationSyncStatus(jsonArray: JSONArray)
      fun sendForms(formDetails: FormDetails, onFormSync: IMainActivityInteractor.OnFormSync)
      fun updateFormSyncStatus(uniqueId: String, formId: String)
      fun fetchUnsentFormsCount(id: Int)

    fun fetchUpdatedPhotoData(id: Int)
      fun sendUpdatePhotoImage(
          updateImageModel: UpdateImageModel,
          photoSync: IMainActivityInteractor.OnUpdatedPhotoSync
    )

      fun updateUpdatePhotoImageStatus(uniqueId: String)
      fun updateUpdatePhotoImageFailureStatus(uniqueId: String, response: String)
      fun updateFormFailureStatus(uniqueId: String, formId: String, errorMsg: String)
      fun resetFailureStatus()
      fun updateRegistrationFailureStatus(uniqueId: String, errorMsg: String)

    interface OnDataSync{
        fun onSuccessFullySyncRegData(jsonObjectResponse : JSONObject)
        fun onFailure(message : String)
    }

    interface OnFormSync{
        fun onSuccessfullySyncForm(jsonObject : JSONObject)
        fun onFailure(message : String)
    }


    interface OnUpdatedPhotoSync {
        fun onSuccessfullyUpdatedPhotoSync(jsonObject: JSONObject)
        fun onFailureUpdatedPhotoSync(textResId: Int)
    }
    interface OnFormDownloadFinished {
        fun onSuccessFormDownloading(jsonObject: JSONObject, hash: String)
        fun onFailure(message: String)
    }
    fun getHash(type: String): String
    fun downloadForms(requestFormModel: RequestFormModel, onFormDownloadFinished: ISettingsInteractor.OnFormDownloadFinished)
    fun saveFormData(formJsonObject: JSONObject)
    fun deleteLoginDetails()
    fun downloadRegistrationData(request: RestoreDataRequest, downloadFinished: ISettingsInteractor.OnRegistrationsDownloadFinished)
    fun downloadVisitsData(request: RestoreDataRequest, downloadFinished: ISettingsInteractor.OnVisitsDownloadFinished)
    fun checkReleaseUpdate(onCheckUpdateFinished: ISettingsInteractor.onCheckUpdateFinished)
    fun saveDownloadedData(listRegistrations: ArrayList<BeneficiaryDetails>, listVisits: ArrayList<BeneficiariesList>)
    fun checkDirectWomanReg(uniqueId : String) : String?
}