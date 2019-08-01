package tech.inscripts.ins_armman.mMitra.homeactivity

import android.database.Cursor
import org.json.JSONArray
import org.json.JSONObject
import tech.inscripts.ins_armman.mMitra.data.model.SyncRegistrationDetails
import tech.inscripts.ins_armman.mMitra.data.model.syncing.FormDetails
import tech.inscripts.ins_armman.mMitra.data.model.syncing.UpdateImageModel

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
}