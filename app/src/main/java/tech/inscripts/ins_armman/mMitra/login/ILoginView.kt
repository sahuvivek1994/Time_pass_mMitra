package tech.inscripts.ins_armman.mMitra.login

import android.content.Context

interface ILoginView {
     fun getContext(): Context

     fun setUsernameError()

     fun setPasswordError()

     fun resetErrorMsg()

     fun showDialog(title : String,message : String)

     fun showProgressBar()

     fun hideProgressBar()

     fun openHomeActivity()

     fun setAuthenticationFailedError()

}