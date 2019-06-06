package tech.inscripts.ins_armman.mMitra.data.service

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import tech.inscripts.ins_armman.mMitra.data.Url.Companion.SYNC_REFERRAL_DATA
import tech.inscripts.ins_armman.mMitra.data.model.referral.ReferralModel

interface SyncReferralServiceApi {
    @Headers("Content-Type: application/json")
    @POST(SYNC_REFERRAL_DATA)
    fun syncReferralDetails(@Body referralModel: ReferralModel): Call<ResponseBody>
}