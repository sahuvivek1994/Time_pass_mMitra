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
import tech.inscripts.ins_armman.mMitra.forms.EnrollmentQuestions

class Login : AppCompatActivity(),ILoginView {

    override fun getContext(): Context {
       return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        val button_login=findViewById<Button>(R.id.button_login);
       initializeDBHelper()
        button_login.setOnClickListener(View.OnClickListener {
            val myIntent = Intent(this@Login, HomeActivity::class.java)
            startActivity(myIntent)
        } )
    }
    fun initializeDBHelper() {
        val dbHelper = DBHelper(getContext()?.getApplicationContext())
        DatabaseManager.initializeInstance(dbHelper)
        DatabaseManager.getInstance().openDatabase()    }
}
