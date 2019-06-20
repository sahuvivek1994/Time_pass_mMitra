package tech.inscripts.ins_armman.mMitra.login

import tech.inscripts.ins_armman.mMitra.utility.IBasePresenter

interface ILoginPresenter<v> : IBasePresenter<v>{

    fun initializeDBHelper()
    fun checkPermissions(): Boolean
    fun getPermissions(listPermissionsNeeded: List<String>)
    fun validateCredentials(username: String, password: String)
    fun loginUser(username: String,password: String)
    fun createRequestBody(username: String,password: String)
    fun checkIfUserAlreadyLoggedIn()

}