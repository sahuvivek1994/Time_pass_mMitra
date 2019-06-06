package tech.inscripts.ins_armman.mMitra.data.service

import android.content.Context
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import tech.inscripts.ins_armman.mMitra.R
import tech.inscripts.ins_armman.mMitra.data.model.SyncRegistrationDetails
import tech.inscripts.ins_armman.mMitra.menu.IHomeActivityInteractor
import java.io.IOException

class SyncRegistrationService {
    var serviceApi: SyncRegistrationServiceApi?=null

    constructor(serviceApi: SyncRegistrationServiceApi?) {
        this.serviceApi = serviceApi
    }

    fun syncRegistrationDetails(registrationDetails : SyncRegistrationDetails, onDataSync : IHomeActivityInteractor.onDataSync,context: Context){
        val responsebodyCall : Call<ResponseBody> = serviceApi!!.SyncRegistrationDetails(registrationDetails)
        responsebodyCall.enqueue(object : retrofit2.Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                onDataSync.onFailure(context.getString(R.string.oops_some_thing_happened_wrong))
            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                try{
                    var loginJsonResponse : String?=null
                    if(response!!.body()!=null){
                        loginJsonResponse=response.body().string()
                    }
                    else if(response!!.errorBody()!=null){
                        loginJsonResponse=response.errorBody().string()
                    }
                    val jsonObject =JSONObject(loginJsonResponse)
                    onDataSync.onSuccessFullySyncRegData(jsonObject)
                }
                catch(e : IOException){
                    e.printStackTrace()
                    onDataSync.onFailure(context.getString(R.string.input_output_error_occured))
                }
                catch(e: JSONException){
                    e.printStackTrace()
                    onDataSync.onFailure(context.getString(R.string.invalid_data_frm_server))
                }
            }
        })
    }
}