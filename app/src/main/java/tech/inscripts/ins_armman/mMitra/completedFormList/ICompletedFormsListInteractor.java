package tech.inscripts.ins_armman.mMitra.completedFormList;

import android.database.Cursor;

public interface ICompletedFormsListInteractor {
    Cursor getCompleteFormList();
    Cursor getChildNo(String unique_mother_id);
}
