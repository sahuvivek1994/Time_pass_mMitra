package tech.inscripts.ins_armman.mMitra.login
import android.content.Context

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import tech.inscripts.ins_armman.mMitra.HomeActivity
import tech.inscripts.ins_armman.mMitra.R
import tech.inscripts.ins_armman.mMitra.data.database.DBHelper
import tech.inscripts.ins_armman.mMitra.data.database.DatabaseManager

class Login : AppCompatActivity() {

    private var mEditTextUserName: EditText? = null
    private var mEditTextPassword: EditText? = null
    private var mButtonLogin: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.login)
        init()

    }

    fun init()
    {
        mEditTextUserName = findViewById<View>(R.id.username) as EditText
        mEditTextPassword = findViewById<View>(R.id.password) as EditText
        mButtonLogin = findViewById<View>(R.id.button_login) as Button
    }

    fun login(view: View)
    {
        val username = mEditTextUserName?.text.toString()
        val password = mEditTextPassword?.text.toString()
        Toast.makeText(this,"hello"+username,Toast.LENGTH_LONG).show()

        hell()
    }

    fun hell()
    {
        startActivity(Intent(this@Login, MainActivity::class.java))

    }

