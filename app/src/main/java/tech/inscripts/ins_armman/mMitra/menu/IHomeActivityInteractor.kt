package tech.inscripts.ins_armman.mMitra.menu

import com.google.gson.JsonObject
import org.json.JSONObject

interface IHomeActivityInteractor {
    interface onDataSync{
        fun onSuccessFullySyncRegData(jsonObjectResponse : JSONObject)
        fun onFailure(message : String)
    }

    interface onFormSync{
        fun onSuccessFullySyncRegData(jsonObjectResponse : JSONObject)
        fun onFailure(message : String)
    }

    interface onReferralSync {
        fun onSuccessfullySyncReferral(jsonObject: JSONObject)
        fun onFailure(message: String)
    }

    interface onGrowthMonitoringSync {
        fun onSuccessfullySyncGrowthMonitoring(jsonObject: JSONObject)
        fun onFailure(message: String)
    }

    interface onUpdatedPhotoSync {
        fun onSuccessfullyUpdatedPhotoSync(jsonObject: JSONObject)
        fun onFailureUpdatedPhotoSync(textResId: Int)
    }
}