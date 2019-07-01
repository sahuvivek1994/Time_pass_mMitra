package tech.inscripts.ins_armman.mMitra.completedFormList;

import android.content.Context;
import android.database.Cursor;

import com.inscripts.ins_armman.npdsf.database.DBHelper;

public class CompletedFormsListInteractor implements ICompletedFormsListInteractor {
    Context mContext;
    DBHelper db;

    public CompletedFormsListInteractor(Context mContext) {
        this.mContext = mContext;
        this.db = new DBHelper(mContext);
    }

    @Override
    public Cursor getCompleteFormList() {
        return db.getFormsList();
    }

    @Override
    public Cursor getChildNo(String unique_mother_id) {
        return db.getChildIdFromMotherId(unique_mother_id);
    }
}
