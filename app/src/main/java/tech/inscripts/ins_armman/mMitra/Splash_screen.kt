package tech.inscripts.ins_armman.mMitra

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import tech.inscripts.ins_armman.mMitra.login.Login
import java.util.*
import kotlin.concurrent.schedule

class Splash_screen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        Timer().schedule(5000)
        {
            startActivity(Intent(this@Splash_screen, Login::class.java))
        }

    }
}
