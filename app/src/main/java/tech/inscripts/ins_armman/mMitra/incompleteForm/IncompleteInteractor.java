package tech.inscripts.ins_armman.mMitra.incompleteForm;

import android.database.Cursor;

/**
 * @author Aniket & Vivek  Created on 4/9/2018
 */

public interface IncompleteInteractor {

    Cursor fetchListIncompleteForm();

    Cursor fetchUniqueIdChildId(String unique);

    Cursor checkChildFilledForm(String unique);
}
