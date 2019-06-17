package tech.inscripts.ins_armman.mMitra.data.service

import android.content.Context
import retrofit2.Call
import retrofit2.Response
import tech.inscripts.ins_armman.mMitra.R
import tech.inscripts.ins_armman.mMitra.data.model.restoreData.RestoreDataRequest
import tech.inscripts.ins_armman.mMitra.data.model.restoreData.RestoreReferral
import tech.inscripts.ins_armman.mMitra.settingActivity.ISettingsInteractor
import java.io.IOException
import javax.security.auth.callback.Callback

class RestoreReferralService {
    var mServiceAPI : RestoreReferralServiceAPI?=null

    constructor(mServiceAPI: RestoreReferralServiceAPI?) {
        this.mServiceAPI = mServiceAPI
    }
    fun downloadReferralData(context: Context,request: RestoreDataRequest,downloadFinished: ISettingsInteractor.OnReferralDownloadFinished){
     var call = mServiceAPI!!.restoreReferralData(request)
        call.enqueue(object :retrofit2.Callback<RestoreReferral>{
            override fun onFailure(call: Call<RestoreReferral>?, t: Throwable?) {
                downloadFinished.onFailure(context.getString(R.string.oops_some_thing_happened_wrong))

            }

            override fun onResponse(call: Call<RestoreReferral>?, response: Response<RestoreReferral>?) {
                if (response!!.code() == 200) {
                    downloadFinished.onSuccessReferralDownloading(response.body())
                } else {
                    try {
                        downloadFinished.onFailure(response.errorBody().string())
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }

            }

        })
    }
}