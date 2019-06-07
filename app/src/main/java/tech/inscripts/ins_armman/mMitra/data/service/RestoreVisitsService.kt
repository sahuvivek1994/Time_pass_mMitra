package tech.inscripts.ins_armman.mMitra.data.service

import android.content.Context
import retrofit2.Call
import retrofit2.Response
import tech.inscripts.ins_armman.mMitra.R
import tech.inscripts.ins_armman.mMitra.data.model.restoreData.RestoreDataRequest
import tech.inscripts.ins_armman.mMitra.data.model.restoreData.RestoreVisits
import tech.inscripts.ins_armman.mMitra.settingActivity.ISettingsInteractor
import java.io.IOException
import javax.security.auth.callback.Callback

class RestoreVisitsService {
    var mServiceAPI : RestoreVisitsServiceAPI?=null

    constructor(mServiceAPI: RestoreVisitsServiceAPI?) {
        this.mServiceAPI = mServiceAPI
    }

    fun downloadVisitsData(context: Context, request: RestoreDataRequest, downloadFinished: ISettingsInteractor.OnVisitsDownloadFinished) {
      val call = mServiceAPI?.restoreRegistrationData(request)
        call!!.enqueue(object : retrofit2.Callback<RestoreVisits>{
            override fun onFailure(call: Call<RestoreVisits>?, t: Throwable?) {
              downloadFinished.onFailure(context.getString(R.string.oops_some_thing_happened_wrong))
            }

            override fun onResponse(call: Call<RestoreVisits>?, response: Response<RestoreVisits>?) {
                if (response!!.code() == 200) {
                    downloadFinished.onSuccessVisitsDownloading(response.body())
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