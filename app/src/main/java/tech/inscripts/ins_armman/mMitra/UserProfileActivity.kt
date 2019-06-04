package tech.inscripts.ins_armman.mMitra

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

class UserProfileActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_profile_activity)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitle("User Profile")
        setSupportActionBar(toolbar)
    }
}