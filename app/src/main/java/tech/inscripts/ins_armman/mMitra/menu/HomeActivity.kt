package tech.inscripts.ins_armman.mMitra.menu

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageButton
import tech.inscripts.ins_armman.mMitra.R

class HomeActivity : AppCompatActivity(),View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val button_registration =findViewById<ImageButton>(R.id.btnRegistration)
        val button_incomplete_form =findViewById<ImageButton>(R.id.btnIncompleteForm)
        val button_complete_form =findViewById<ImageButton>(R.id.btnCompleteForm)
        val button_user_profile =findViewById<ImageButton>(R.id.btnUserProfile)
        button_registration.setOnClickListener(this)
        button_incomplete_form.setOnClickListener(this)
        button_complete_form.setOnClickListener(this)
        button_user_profile.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
    }
}