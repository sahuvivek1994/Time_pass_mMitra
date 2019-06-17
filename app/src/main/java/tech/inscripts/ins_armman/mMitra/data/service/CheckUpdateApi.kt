package tech.inscripts.ins_armman.mMitra.data.service

import retrofit2.Call
import retrofit2.http.GET
import tech.inscripts.ins_armman.mMitra.data.Url.Companion.RELEASE
import tech.inscripts.ins_armman.mMitra.data.model.UpdateModel

interface CheckUpdateApi {
    @GET(RELEASE)
   fun getUpdateData(): Call<UpdateModel>
}