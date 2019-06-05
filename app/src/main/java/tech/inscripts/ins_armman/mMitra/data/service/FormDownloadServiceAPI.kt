package tech.inscripts.ins_armman.mMitra.data.service

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import tech.inscripts.ins_armman.mMitra.data.Url.Companion.DOWNLOAD_FORMS
import tech.inscripts.ins_armman.mMitra.data.model.RequestFormModel

interface FormDownloadServiceAPI {
    @Headers("Content-Type: application/json")

    @POST(DOWNLOAD_FORMS)
    fun downloadFormJson(@Body requestFormModel: RequestFormModel) : Call<ResponseBody>
}