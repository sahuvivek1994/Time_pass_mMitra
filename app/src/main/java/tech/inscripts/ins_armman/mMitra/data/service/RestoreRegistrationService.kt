package tech.inscripts.ins_armman.mMitra.data.service

import android.content.Context
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tech.inscripts.ins_armman.mMitra.R
import tech.inscripts.ins_armman.mMitra.data.model.restoreData.RestoreDataRequest
import tech.inscripts.ins_armman.mMitra.data.model.restoreData.RestoreRegistration
import tech.inscripts.ins_armman.mMitra.settingActivity.ISettingsInteractor
import java.io.IOException

class RestoreRegistrationService {
    private var mServiceAPI: RestoreRegistrationServiceApi?=null

    fun RestoreRegistrationService(mServiceAPI: RestoreRegistrationServiceApi) {
        this.mServiceAPI = mServiceAPI
    }

    fun downloadRegistrationData(context: Context, request: RestoreDataRequest, downloadFinished: ISettingsInteractor.OnRegistrationsDownloadFinished) {
        val call = mServiceAPI!!.restoreRegistrationData(request)
        call.enqueue(object : Callback<RestoreRegistration> {
            override fun onResponse(call: Call<RestoreRegistration>, response: Response<RestoreRegistration>) {

                if (response.code() == 200) {
                    downloadFinished.onSuccessRegistrationsDownloading(response.body())
                } else {
                    try {
                        downloadFinished.onFailure(response.errorBody().string())
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<RestoreRegistration>, t: Throwable) {
                downloadFinished.onFailure(context.getString(R.string.oops_some_thing_happened_wrong))
            }
        })
    }
}