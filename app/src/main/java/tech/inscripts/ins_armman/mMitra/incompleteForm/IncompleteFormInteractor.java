package tech.inscripts.ins_armman.mMitra.incompleteForm;

import android.content.Context;
import android.database.Cursor;

import com.inscripts.ins_armman.npdsf.database.DBHelper;

/**
 * @author Aniket & Vivek  Created on 4/9/2018
 */

public class IncompleteFormInteractor implements IncompleteInteractor {

    private Context mContext;
    private DBHelper dbHelper;

    IncompleteFormInteractor(Context context) {
        this.mContext = context;
        dbHelper = new DBHelper(context);
    }

    @Override
    public Cursor fetchListIncompleteForm() {
        return dbHelper.getIncompleteFormListList();
    }

    @Override
    public Cursor fetchUniqueIdChildId(String unique) {
        return dbHelper.getChildIdFromMotherId(unique);
    }

    @Override
    public Cursor checkChildFilledForm(String unique) {
        return dbHelper.getuniqueIdFormId(unique);
    }
}
