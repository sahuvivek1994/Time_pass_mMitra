package tech.inscripts.ins_armman.mMitra.login

import android.content.Context
import org.json.JSONObject
import tech.inscripts.ins_armman.mMitra.data.model.UserDetails

interface ILoginInteractor {

    fun saveUserDetails()

    fun login(userDetails:UserDetails,onLoginFinished:OnLoginFinished,context:Context)

    fun deleteUserDetails()

    fun userAlreadyLoggedIn(): Boolean

    interface OnLoginFinished
    {
            fun onSucess(jsonObject: JSONObject)

            fun onFailure(message:String)
    }

}