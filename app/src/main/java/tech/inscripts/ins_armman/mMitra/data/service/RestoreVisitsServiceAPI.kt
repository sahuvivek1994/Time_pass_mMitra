package tech.inscripts.ins_armman.mMitra.data.service

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import tech.inscripts.ins_armman.mMitra.data.Url.Companion.GET_VISITS
import tech.inscripts.ins_armman.mMitra.data.model.restoreData.RestoreDataRequest
import tech.inscripts.ins_armman.mMitra.data.model.restoreData.RestoreVisits

interface RestoreVisitsServiceAPI {
    @Headers("Content-Type: application/json")
    @POST(GET_VISITS)
     fun restoreRegistrationData(@Body request: RestoreDataRequest): Call<RestoreVisits>
}