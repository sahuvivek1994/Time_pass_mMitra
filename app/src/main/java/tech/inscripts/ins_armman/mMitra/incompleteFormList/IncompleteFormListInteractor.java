package tech.inscripts.ins_armman.mMitra.incompleteFormList;

import android.content.Context;
import android.database.Cursor;

import com.inscripts.ins_armman.npdsf.database.DBHelper;

public class IncompleteFormListInteractor implements IIncompleteFormListInteractor {

    Context mContext;
    DBHelper db;

    public IncompleteFormListInteractor(Context mContext) {
        this.mContext = mContext;
        this.db = new DBHelper(mContext);
    }

    @Override
    public Cursor getCompleteFormList(String unique_id) {
        return db.getIncompleteFormList(unique_id);
    }

    @Override
    public Cursor getChildNo(String unique_mother_id) {
        return db.getChildIdFromMotherId(unique_mother_id);
    }

}
