package tech.inscripts.ins_armman.mMitra.data.service

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import tech.inscripts.ins_armman.mMitra.data.Url.Companion.AUTHENTICATE
import tech.inscripts.ins_armman.mMitra.data.model.UserDetails

interface LoginServiceAPI {
    @Headers("Content-Type: application/json")
    @POST(AUTHENTICATE)
    abstract fun getAuthentication(@Body userDetails: UserDetails): Call<ResponseBody>
}