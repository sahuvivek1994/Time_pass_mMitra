package tech.inscripts.ins_armman.mMitra.login

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.login.*
import kotlinx.android.synthetic.main.progress_overlay.*
import tech.inscripts.ins_armman.mMitra.R
import tech.inscripts.ins_armman.mMitra.data.database.DBHelper
import tech.inscripts.ins_armman.mMitra.data.database.DatabaseManager
import tech.inscripts.ins_armman.mMitra.homeactivity.MainActivity
import tech.inscripts.ins_armman.mMitra.utility.Utility

/**
 * Created by Vivek and Juilee on 26/07/2019
 */

class Login : AppCompatActivity(), ILoginView {

    val mLoginPresenter = LoginPresenter()
    val uti = Utility()
    private var mTextInputLayoutUsername: TextInputLayout? = null
    private var mTextInputLayoutPassword: TextInputLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val applicationLanguage = uti.getLanguagePreferance(applicationContext)
        if(applicationLanguage.isEmpty())
        {
            uti.setApplicationLocale(applicationContext,"eng")
        }
        else
        {
            uti.setApplicationLocale(applicationContext,applicationLanguage)
        }

        setContentView(R.layout.login)

        mLoginPresenter.attachView(this)

       initializeDBHelper()

        buttonLogin.setOnClickListener(View.OnClickListener {
            val username = edittext_username.text.toString()
            val password = edittext_pass.text.toString()
            mLoginPresenter.validateCredentials(username,password)
        })
    }

    fun initializeDBHelper() {
        val dbHelper = DBHelper(getContext().applicationContext)
        DatabaseManager.initializeInstance(dbHelper)
        DatabaseManager.getInstance().openDatabase()
    }


    override fun setUsernameError() {
        edittext_username.setText("Please Enter Username")
    }

    override fun setPasswordError() {
        edittext_pass.setText("Please enter password")
    }

    override fun resetErrorMsg() {
        edittext_username.setError(null)
        edittext_pass.setError(null)
    }

    override fun showDialog(title: String, message: String) {
        val builder: AlertDialog.Builder
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert)
        } else {
            builder = AlertDialog.Builder(getContext())
        }
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton(R.string.yes) { dialog, which ->
                // continue with delete
            }
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    override fun showProgressBar() {
        uti.animateView(progress_overlay,View.VISIBLE,0.04f,200)
     }

    override fun hideProgressBar() {
        uti.animateView(progress_overlay,View.GONE,0.4f,200)
    }

    override fun openHomeActivity() {
        val myIntent = Intent(this@Login, MainActivity::class.java)
        startActivity(myIntent)
    }

    override fun setAuthenticationFailedError() {
        edittext_pass.setText("")
        mTextInputLayoutPassword?.setError(getString(R.string.authentication_error_msg))
    }

    override fun getContext(): Context {
        return this
    }

    override fun onPostResume() {
        super.onPostResume()
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getContext() != null) {
            if (mLoginPresenter.checkPermissions())
                mLoginPresenter.checkIfUserAlreadyLoggedIn()
        } else
            mLoginPresenter.checkIfUserAlreadyLoggedIn()
    }
}
