package tech.inscripts.ins_armman.mMitra.userProfile

import android.database.Cursor
import tech.inscripts.ins_armman.mMitra.data.model.completeFilledForm
import java.util.ArrayList

class UserProfilePresenter{
    var interactor = UserProfileInteractor()
    fun getUserDetails(){
        val womenList = ArrayList<completeFilledForm>()

        var cur : Cursor=  interactor.loginDetails()
        if (cur != null && cur.moveToFirst())
            do {
                var fullName = cur.getString(cur.getColumnIndex("name"))
                womenList.add(
                    completeFilledForm(fullName,cur.getString(cur.getColumnIndex("unique_id")))
                )
            } while (cur.moveToNext())

    }
}