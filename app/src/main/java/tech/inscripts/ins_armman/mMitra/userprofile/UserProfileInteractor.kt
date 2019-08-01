package tech.inscripts.ins_armman.mMitra.userprofile

import android.database.Cursor
import tech.inscripts.ins_armman.mMitra.utility.Utility

class UserProfileInteractor{

    var utility = Utility()

    fun loginDetails() : Cursor {
        return utility.getDatabase().rawQuery("select * from login", null)
    }

    fun regCount() : Cursor {
        return utility.getDatabase().rawQuery("select unique_id from registration ", null)

    }
}