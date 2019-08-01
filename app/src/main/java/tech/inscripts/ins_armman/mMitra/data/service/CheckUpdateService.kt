package tech.inscripts.ins_armman.mMitra.data.service

import android.content.Context
import retrofit2.Call
import retrofit2.Response
import tech.inscripts.ins_armman.mMitra.R
import tech.inscripts.ins_armman.mMitra.data.model.UpdateModel
import tech.inscripts.ins_armman.mMitra.settingactivity.ISettingsInteractor

class CheckUpdateService {
   var mCheckUpdateApi : CheckUpdateApi?=null

    constructor(mCheckUpdateApi: CheckUpdateApi?) {
        this.mCheckUpdateApi = mCheckUpdateApi
    }

    fun getUpdateData(onCheckUpdateFinished: ISettingsInteractor.onCheckUpdateFinished,context: Context){

        val call : Call<UpdateModel> = mCheckUpdateApi!!.getUpdateData()
        call.enqueue(object: retrofit2.Callback<UpdateModel>{
            override fun onFailure(call: Call<UpdateModel>?, t: Throwable?) {
                onCheckUpdateFinished.onFailure(context.getString(R.string.oops_some_thing_happened_wrong))
            }

            override fun onResponse(call: Call<UpdateModel>?, response: Response<UpdateModel>?) {
                if(response!!.code() == 200){
                    val mobileData : UpdateModel= response.body()
                    onCheckUpdateFinished.onUpdateCheckSuccess(mobileData)
                }
                else {
                    onCheckUpdateFinished.onFailure(context.getString(R.string.invalid_data_frm_server))
                }
            }
        })
    }
}