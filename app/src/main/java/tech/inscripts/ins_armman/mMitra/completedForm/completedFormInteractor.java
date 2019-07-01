package tech.inscripts.ins_armman.mMitra.completedForm;

import android.content.Context;
import android.database.Cursor;

import com.inscripts.ins_armman.npdsf.database.DBHelper;

public class completedFormInteractor implements IcompletedInteractor {

    private Context mContext;
    private DBHelper dbHelper;

    completedFormInteractor(Context context) {
        this.mContext = context;
        dbHelper = new DBHelper(context);
    }


    @Override
    public Cursor fetchListcompleteForm() {
        return dbHelper.getcompleteFormListList();
    }
}
