package tech.inscripts.ins_armman.mMitra.data.service

import android.content.Context
import android.util.Log
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import tech.inscripts.ins_armman.mMitra.R
import tech.inscripts.ins_armman.mMitra.data.model.syncing.UpdateImageModel
import tech.inscripts.ins_armman.mMitra.menu.IHomeActivityInteractor
import java.io.IOException
import javax.security.auth.callback.Callback

class SyncUpdatePhotoservice {
    var mPhotoserviceApi : SyncUpdatePhotoServiceApi?=null

    constructor(mPhotoserviceApi: SyncUpdatePhotoServiceApi?) {
        this.mPhotoserviceApi = mPhotoserviceApi
    }

    fun syncUpdatePhotoDetails(updateImageModel: UpdateImageModel,updatedPhotoSync: IHomeActivityInteractor.onUpdatedPhotoSync){
        Log.d("SyncUpdatePhotoService","SyncUpdatePhotoService start request")
        var responseBosyCall = mPhotoserviceApi!!.SyncUpdatePhotoDetails(updateImageModel)
        responseBosyCall.enqueue(object :retrofit2.Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                updatedPhotoSync.onFailureUpdatedPhotoSync(R.string.oops_some_thing_happened_wrong)

            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                var jsonResponse: String? = null
                try {
                    if (response.body() != null) {
                        jsonResponse = response.body().string()
                    } else if (response.errorBody() != null)
                        jsonResponse = response.errorBody().string()
                    val jsonObject = JSONObject(jsonResponse)
                    Log.d("SyncUpdatePhotoService", "SyncUpdatePhotoService end request")
                    updatedPhotoSync.onSuccessfullyUpdatedPhotoSync(jsonObject)
                } catch (e: IOException) {
                    e.printStackTrace()
                    updatedPhotoSync.onFailureUpdatedPhotoSync(R.string.input_output_error_occured)
                } catch (e: JSONException) {
                    e.printStackTrace()
                    updatedPhotoSync.onFailureUpdatedPhotoSync(R.string.invalid_data_frm_server)
                }

            }

        })
    }
}