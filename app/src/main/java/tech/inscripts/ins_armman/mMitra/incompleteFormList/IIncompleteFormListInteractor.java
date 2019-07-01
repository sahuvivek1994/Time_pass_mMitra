package tech.inscripts.ins_armman.mMitra.incompleteFormList;

import android.database.Cursor;

public interface IIncompleteFormListInteractor {
    Cursor getCompleteFormList(String unique_id);
    Cursor getChildNo(String unique_mother_id);
}