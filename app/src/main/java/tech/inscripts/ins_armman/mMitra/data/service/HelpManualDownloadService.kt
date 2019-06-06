package tech.inscripts.ins_armman.mMitra.data.service

import android.content.Context
import okhttp3.ResponseBody
import org.json.JSONException
import retrofit2.Call
import retrofit2.Response
import tech.inscripts.ins_armman.mMitra.R
import tech.inscripts.ins_armman.mMitra.data.model.syncing.RequestHelpModel
import tech.inscripts.ins_armman.mMitra.settingActivity.ISettingsInteractor
import java.io.IOException

class HelpManualDownloadService {
    private var mDownloadServiceApi: HelpManualDownloadServiceApi?=null

    constructor(mDownloadServiceApi: HelpManualDownloadServiceApi?) {
        this.mDownloadServiceApi = mDownloadServiceApi
    }

    fun downloadHelpManual(helpModel : RequestHelpModel, onHelpManualDownloadFinished: ISettingsInteractor.onHelpManualDownloadFinished,context: Context ){
        if(helpModel!=null){
           val responseBodyCall : Call<ResponseBody> = mDownloadServiceApi!!.downloadHelpManualJson(helpModel)
            responseBodyCall.enqueue(object : retrofit2.Callback<ResponseBody>{
                override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                    onHelpManualDownloadFinished.onFailure(context.getString(R.string.oops_some_thing_happened_wrong))
                }
                override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
try{
    var loginJsonResponse : String?=null
    if(response!!.body()!=null){
        loginJsonResponse=response.body().string()
    }
    else if(response!!.body()!=null){
        loginJsonResponse=response.errorBody().string()
    }
}
catch(e:IOException){
    onHelpManualDownloadFinished.onFailure(context.getString(R.string.input_output_error_occured))

}
catch(e:JSONException){
    onHelpManualDownloadFinished.onFailure(context.getString(R.string.invalid_data_frm_server))
}
                }
            })
        }
    }
}