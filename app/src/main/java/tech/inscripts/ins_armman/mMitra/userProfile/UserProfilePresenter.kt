package tech.inscripts.ins_armman.mMitra.userProfile

import android.database.Cursor
import tech.inscripts.ins_armman.mMitra.data.model.completeFilledForm
import java.util.ArrayList

class UserProfilePresenter{
    var interactor = UserProfileInteractor()
    fun getUserDetails(userDetails : ArrayList<String>){
        var cur : Cursor=  interactor.loginDetails()
        if (cur != null && cur.moveToFirst())
            do {
                var fullName = cur.getString(cur.getColumnIndex("name"))
                var username = cur.getString(cur.getColumnIndex("username"))
                userDetails.add(fullName)
                userDetails.add(username)
            } while (cur.moveToNext())
    }

    fun getRegCount(regCount : Int){
        var cur : Cursor=  interactor.regCount()
       var count= cur.count
        if(count>=0)
        count=regCount

    }
}