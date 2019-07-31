package tech.inscripts.ins_armman.mMitra.userProfile

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_user_profile.*
import tech.inscripts.ins_armman.mMitra.R
import tech.inscripts.ins_armman.mMitra.forms.QuestionInteractor
import java.util.ArrayList

class UserProfileActivity : AppCompatActivity(){
  var presenter = UserProfilePresenter()
   var username : String =""
   var name : String =""
    var userDetails = ArrayList<String>()
    var regCount : Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        presenter.getUserDetails(userDetails)
        presenter.getRegCount(regCount)
        textUserName.text=userDetails.get(0)
        textName.text=userDetails.get(1)

        if(regCount>0)
            textCount.text= regCount.toString()
    else
            textCount.text= "no registration"
    }
}