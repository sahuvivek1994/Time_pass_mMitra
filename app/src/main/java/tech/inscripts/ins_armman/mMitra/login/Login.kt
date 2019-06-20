package tech.inscripts.ins_armman.mMitra.login
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import io.fabric.sdk.android.services.common.SafeToast
import kotlinx.android.synthetic.main.login.*
import kotlinx.android.synthetic.main.progress_overlay.*
import tech.inscripts.ins_armman.mMitra.R
import tech.inscripts.ins_armman.mMitra.mainMenu.MainActivity
import tech.inscripts.ins_armman.mMitra.utility.utility

    class Login : AppCompatActivity(),ILoginView {

    val applicationLanguage = utility()
    override fun getContext(): Context {
       return this
    }

    var loginPre = LoginPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        initializeDBHelper()
    }
    fun initializeDBHelper() {
        applicationLanguage.getLanguagePreferance(applicationContext)
        if(applicationLanguage != null)
        {
            applicationLanguage.setApplicationLocale(applicationContext,"en")
        }
        else
        {
            applicationLanguage.setApplicationLocale(applicationContext,applicationLanguage)
        }
        var logPresenter = LoginPresenter()
        logPresenter.attachView(this)
        init()
    }

    override fun setUsernameError() {
        println("Checkpoint 2")
        username_editText.setError("Please enter username")
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }

    override fun setPasswordError() {
        password_editText.setError("Please enter password")
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }

    override fun resetErrorMsg() {
        println("Checkpoint 1")
        username_editText.setError(null)
        password_editText.setError(null)
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        applicationLanguage.animateView(progress_overlay,View.VISIBLE,0.4f,200)
    }

    override fun hideProgressBar() {
        applicationLanguage.animateView(progress_overlay,View.GONE,0.4f,200)
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun openHomeActivity() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        startActivity(Intent(this@Login,MainActivity::class.java))
       }

    override fun setAuthenticationFailedError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        password_editText.setText("")
        password_editText.setError("Invalid Username and Password")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        moveTaskToBack(true)
        android.os.Process.killProcess(android.os.Process.myPid())
        System.exit(1)
    }

    fun init()
    {
        button_login.setOnClickListener {
            var name = username_editText.text.toString()
            var password = password_editText.text.toString()
            loginPre.validateCredentials(name,password)
        }
    }

}
