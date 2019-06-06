package tech.inscripts.ins_armman.mMitra.data.service

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import tech.inscripts.ins_armman.mMitra.data.Url.Companion.DOWNLOAD_HELP_MANUAL
import tech.inscripts.ins_armman.mMitra.data.model.syncing.RequestHelpModel

interface HelpManualDownloadServiceApi{

@Headers("Content-Type: application/json")
@POST(DOWNLOAD_HELP_MANUAL)
abstract fun downloadHelpManualJson(@Body helpModel: RequestHelpModel): Call<ResponseBody>
}