package tech.inscripts.ins_armman.mMitra
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import tech.inscripts.ins_armman.mMitra.forms.EnrollmentQuestions

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        val button_login=findViewById<Button>(R.id.button_login);
        button_login.setOnClickListener(View.OnClickListener {
            val myIntent = Intent(this@Login, EnrollmentQuestions::class.java)
            startActivity(myIntent)
        } )
    }
}
