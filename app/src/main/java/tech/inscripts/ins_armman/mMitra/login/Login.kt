package tech.inscripts.ins_armman.mMitra.login

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.login.*
import tech.inscripts.ins_armman.mMitra.HomeActivity
import tech.inscripts.ins_armman.mMitra.R
import tech.inscripts.ins_armman.mMitra.data.database.DBHelper
import tech.inscripts.ins_armman.mMitra.data.database.DatabaseManager
import tech.inscripts.ins_armman.mMitra.utility.Utility

class Login : AppCompatActivity(), ILoginView {

    override fun getContext(): Context {
        return this
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val uti = Utility()
        val applicationLanguage = uti.getLanguagePreferance(applicationContext)
        if(applicationLanguage.isEmpty())
        {
            uti.setApplicationLocale(applicationContext,"eng")
        }
        else
        {
            uti.setApplicationLocale(applicationContext,applicationLanguage)
        }

       initializeDBHelper()
        buttonLogin.setOnClickListener(View.OnClickListener {
            val username = edittext_username.text.toString()
            val password = edittext_pass.text.toString()
            if (username.equals("")) {
                setUsernameError()
            } else if (password.equals("")) {
                setPasswordError()
            }
            else
            {
                openHomeActivity()
            }

        })
    }
    fun initializeDBHelper() {
        val dbHelper = DBHelper(getContext().applicationContext)
        DatabaseManager.initializeInstance(dbHelper)
        DatabaseManager.getInstance().openDatabase()
    }


    override fun setUsernameError() {
        edittext_username.setText("Please Enter Username")
        //    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setPasswordError() {
        edittext_pass.setText("Please enter password")
        //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun resetErrorMsg() {
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

        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showProgressBar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgressBar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun openHomeActivity() {
        val myIntent = Intent(this@Login, HomeActivity::class.java)
        startActivity(myIntent)
    }

    override fun setAuthenticationFailedError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
