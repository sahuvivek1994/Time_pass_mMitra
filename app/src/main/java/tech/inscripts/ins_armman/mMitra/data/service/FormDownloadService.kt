package tech.inscripts.ins_armman.mMitra.data.service

import android.content.Context
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import tech.inscripts.ins_armman.mMitra.R
import tech.inscripts.ins_armman.mMitra.data.model.RequestFormModel
import tech.inscripts.ins_armman.mMitra.data.model.syncing.RequestHelpModel
import tech.inscripts.ins_armman.mMitra.settingActivity.ISettingsInteractor
import tech.inscripts.ins_armman.mMitra.utility.Utility
import java.io.IOException

class FormDownloadService {
    var formDownloadServiceAPI : FormDownloadServiceAPI ?=null

    constructor(formDownloadServiceAPI: FormDownloadServiceAPI?) {
        this.formDownloadServiceAPI = formDownloadServiceAPI
    }


    fun downloadForms(requestFormModel: RequestFormModel,onFormDownloadFinished : ISettingsInteractor.OnFormDownloadFinished,context: Context){
        if(requestFormModel == null){
            val responseBodyCall : Call<ResponseBody> = formDownloadServiceAPI!!.downloadFormJson(requestFormModel)
            responseBodyCall.enqueue(object : retrofit2.Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                    onFormDownloadFinished.onFailure(context.getString(R.string.oops_some_thing_happened_wrong))
                }

                override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                    try{
                    var loginJsonResponse : String?=null
                    if(response!!.body()!=null){
                        loginJsonResponse = response.body().string()
                    }
                    else if(response.errorBody()!=null){
                        loginJsonResponse = response.errorBody().string()
                    }
                    val loginJsonObject = JSONObject(loginJsonResponse)
                    onFormDownloadFinished.onSuccessFormDownloading(loginJsonObject,Utility.mdFive(loginJsonResponse))
                    }
                    catch (e : IOException){
                        e.printStackTrace()
                        onFormDownloadFinished.onFailure(context.getString(R.string.input_output_error_occured))
                    }
                    catch (e: JSONException){
                        e.printStackTrace()
                        onFormDownloadFinished.onFailure(context.getString(R.string.input_output_error_occured))
                    }
                }
            })
        }
    }
}