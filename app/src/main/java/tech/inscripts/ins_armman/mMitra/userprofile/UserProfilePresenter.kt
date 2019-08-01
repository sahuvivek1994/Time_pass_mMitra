package tech.inscripts.ins_armman.mMitra.userprofile

import android.database.Cursor
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

    fun getRegCount(): Int{
        var cur : Cursor=  interactor.regCount()
       var count= cur.count
        if(count>=0)
        return count
        else
            return 0

    }
}