package tech.inscripts.ins_armman.mMitra.login

import android.content.Context
import org.json.JSONObject
import tech.inscripts.ins_armman.mMitra.data.model.UserDetails

interface ILoginInteractor {

    fun saveUserDetails(username: String, password: String, jsonObject: JSONObject)

    fun login(userDetails:UserDetails,onLoginFinished:OnLoginFinished,context:Context)

    fun deleteUserDetails()

    fun userAlreadyLoggedIn(): Boolean

    interface OnLoginFinished
    {
            fun onSuccess(jsonObject: JSONObject)

            fun onFailure(message:String)
    }

}