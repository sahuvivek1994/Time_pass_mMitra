package tech.inscripts.ins_armman.mMitra.completedFormDetails;

import android.content.Context;
import android.database.Cursor;

import com.inscripts.ins_armman.npdsf.database.DBHelper;

public class CompleteFormDetailsInteractor implements ICompleteFormDetailsInteractor {
    Context mContext;
    DBHelper db;

    public CompleteFormDetailsInteractor(Context mContext) {
        this.mContext = mContext;
        this.db = new DBHelper(mContext);
    }

    @Override
    public Cursor displayFormDetails(String unique_id, int form_id) {
        return db.getCompleteFormDetails(unique_id, form_id);
    }

    @Override
    public Cursor displayForm6Details(String unique_id, int form_id) {
        return db.getForm6Details(unique_id, form_id);
    }

}
