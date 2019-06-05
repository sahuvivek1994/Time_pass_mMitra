package tech.inscripts.ins_armman.mMitra.login

import org.json.JSONException
import org.json.JSONObject

class LoginInteractor{
interface OnLoadFinished{
    @Throws(JSONException::class)
    fun onSuccess(jsonObject: JSONObject)


   // fun onSuccess(jsonObject : JSONObject) throws
}
}