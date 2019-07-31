package tech.inscripts.ins_armman.mMitra.userProfile

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import tech.inscripts.ins_armman.mMitra.R
import tech.inscripts.ins_armman.mMitra.forms.QuestionInteractor

class UserProfileActivity : AppCompatActivity(){
var questionInteractor = QuestionInteractor(this)
   var username : String =""
   var name : String =""
    var regCount : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)


    }
}