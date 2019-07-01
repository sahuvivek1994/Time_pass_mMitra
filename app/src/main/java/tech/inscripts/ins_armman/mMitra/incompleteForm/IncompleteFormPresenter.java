package tech.inscripts.ins_armman.mMitra.incompleteForm;

import android.database.Cursor;

import com.inscripts.ins_armman.npdsf.data.model.IncompleteFiledForm;
import com.inscripts.ins_armman.npdsf.database.DatabaseContract.FilledFormStatusTable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aniket & Vivek  Created on 4/9/2018
 */
public class IncompleteFormPresenter implements IncompletePresenter<IncompleteView> {

    IncompleteView incompleteView;
    IncompleteFormInteractor incompleteFormInteractor;

    @Override
    public void attachView(IncompleteView incompl) {
        this.incompleteView = incompl;
        incompleteFormInteractor = new IncompleteFormInteractor(incompleteView.getContext());
    }

    @Override
    public void detch() {
        incompleteView = null;
    }

    @Override
    public void getListInCompleteForm() {
        List<IncompleteFiledForm> womenList = new ArrayList<>();
        Cursor cursor = incompleteFormInteractor.fetchListIncompleteForm();
        if (cursor != null && cursor.moveToFirst())
            do {
                womenList.add(new IncompleteFiledForm(cursor.getString(cursor.getColumnIndex("unique_id")),
                        cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getString(cursor.getColumnIndex("form_id")),
                        cursor.getInt(cursor.getColumnIndex(FilledFormStatusTable.COLUMN_FORM_COMPLETION_STATUS))));
            } while (cursor.moveToNext());

        incompleteView.setAdapter(womenList);
    }

    @Override
    public void getUniqueIdFormId(String uniqueId) {

        List<String> childList = new ArrayList<>();
        Cursor cursor = incompleteFormInteractor.fetchUniqueIdChildId(uniqueId);
        if (cursor != null && cursor.moveToFirst())
            do {
                childList.add(cursor.getString(cursor.getColumnIndex("unique_id")));
            } while (cursor.moveToNext());

        int i = 0;
        while (i < childList.size()) {
            Cursor cursor1 = incompleteFormInteractor.checkChildFilledForm(childList.get(i));
            if (cursor1 != null && cursor1.moveToFirst()) {
                try {
                    Integer form_id = Integer.valueOf(cursor1.getString(cursor1.getColumnIndex("form_id")));
                    if (form_id != 9) {
                        String childCounter = String.valueOf(i + 1);
                        int form_to_open = form_id + 1;
                        incompleteView.openActivity(uniqueId, form_to_open, String.valueOf(childList.size()), childCounter);
                        break;
                    }
                    else if (form_id == 9 && ((i+1)== childList.size()))
                    {
                        String childCounter = String.valueOf(i + 1);
                        int form_to_open = form_id + 1;
                        incompleteView.openActivity(uniqueId, form_to_open, String.valueOf(childList.size()), childCounter);
                        break;
                    }

                } catch (NumberFormatException a) {
                    // This condition is used only when the mother's completed max(form id)=5 but form 6 has never opened
                    String childCounter = String.valueOf(i + 1);
                    incompleteView.openActivity(uniqueId, 6, String.valueOf(childList.size()), childCounter);
                    break;
                }
            }
            i++;
        }

    }
}
