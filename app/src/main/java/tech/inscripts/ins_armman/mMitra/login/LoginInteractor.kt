package tech.inscripts.ins_armman.mMitra.login

import android.content.Context
import android.database.Cursor
import tech.inscripts.ins_armman.mMitra.data.database.DBHelper
import tech.inscripts.ins_armman.mMitra.data.database.DatabaseContract
import tech.inscripts.ins_armman.mMitra.data.model.UserDetails
import tech.inscripts.ins_armman.mMitra.utility.utility

class LoginInteractor : ILoginInteractor
{
    private var mcontext: Context
    private var dbHelper: DBHelper? = null
    var cursor:Cursor? = null
    var util = utility()

    constructor(context: Context)
    {
        mcontext = context
    }

    override fun saveUserDetails() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun userAlreadyLoggedIn(): Boolean {

//        cursor = util.getDatabase().rawQuery("SELECT * FROM " + DatabaseContract.LoginTable.TABLE_NAME, null)
//        return cursor.moveToFirst()


        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteUserDetails() {
        util.getDatabase().delete(DatabaseContract.LoginTable.TABLE_NAME,null,null)
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun login(userDetails: UserDetails, onLoginFinished: ILoginInteractor.OnLoginFinished, context: Context) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }
}