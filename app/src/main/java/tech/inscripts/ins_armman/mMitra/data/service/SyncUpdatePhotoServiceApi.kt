package tech.inscripts.ins_armman.mMitra.data.service

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import tech.inscripts.ins_armman.mMitra.data.Url.Companion.SYNC_UPDATE_PHOTO_DATA
import tech.inscripts.ins_armman.mMitra.data.model.syncing.UpdateImageModel

interface SyncUpdatePhotoServiceApi {
    @Headers("Content-Type: application/json")
    @POST(SYNC_UPDATE_PHOTO_DATA)
    fun SyncUpdatePhotoDetails(@Body updateImageModel: UpdateImageModel): Call<ResponseBody>
}