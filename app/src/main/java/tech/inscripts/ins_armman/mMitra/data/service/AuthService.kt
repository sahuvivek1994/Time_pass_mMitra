package tech.inscripts.ins_armman.mMitra.data.service

import android.content.Context
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import tech.inscripts.ins_armman.mMitra.R
import tech.inscripts.ins_armman.mMitra.data.model.UserDetails
import tech.inscripts.ins_armman.mMitra.login.LoginInteractor
import java.io.IOException

class AuthService(cgit reateApiService: Any?) {
    private var loginServiceAPI: LoginServiceAPI? = null


    fun AuthService(loginServiceAPI: LoginServiceAPI) {
        this.loginServiceAPI = loginServiceAPI
    }

    fun getAuthentication(userDetails: UserDetails,onLoadFinished : LoginInteractor.OnLoadFinished,context: Context){
if(userDetails!=null){
    val responseBodyCall : Call<ResponseBody> = loginServiceAPI!!.getAuthentication(userDetails)
    responseBodyCall.enqueue(object : retrofit2.Callback<ResponseBody> {
        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            try {
                var loginJsonResponse: String? = null
                if (response.body() != null) {
                    loginJsonResponse = response.body().string()
                } else if (response.errorBody() != null) {
                    loginJsonResponse = response.errorBody().string()
                }
                val loginJsonObject = JSONObject(loginJsonResponse)
               // onLoginFinished.onSuccess(loginJsonObject)
            } catch (e: IOException) {
                e.printStackTrace()
               // onLoginFinished.onFailure(context.getString(R.string.input_output_error_occured))
            } catch (e: JSONException) {
                e.printStackTrace()
                //onLoginFinished.onFailure(context.getString(R.string.invalid_data_frm_server))
            }

        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            //onLoginFinished.onFailure(context.getString(R.string.oops_some_thing_happened_wrong))
        }
    })
}
    }
}