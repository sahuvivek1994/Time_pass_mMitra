package tech.inscripts.ins_armman.mMitra.incompleteFormList;


import android.database.Cursor;

import com.inscripts.ins_armman.npdsf.data.model.CompleteFormQnA;

import java.util.ArrayList;

public class IncompleteFormListPresentor implements IIncompleteFormListPresentor<IncompleteFormList> {
    IncompleteFormListInteractor interactor;
    IIncompleteFormList view;
    ArrayList<CompleteFormQnA> formDetails = new ArrayList<>();
    ArrayList<CompleteFormQnA> childNumber = new ArrayList<>();
    ArrayList<CompleteFormQnA> childIdList = new ArrayList<>();
    int formId = 0;
    int count = 0, c = 0;
    Cursor res = null;
    String child_name, formName;


    @Override
    public void attachView(IncompleteFormList view) {
        this.view = view;
        this.interactor = new IncompleteFormListInteractor(view.getContext());
    }

    @Override
    public void detch() {
        view = null;
    }

    /**
     * this method is for getting the forms list of mother and her child.
     * if child count is greater than one then the child forms ie. form_id from 6 to 9 are repeated for every child.
     *
     * @param unique_mother_id= id to get the list of filled forms
     */
    @Override
    public void getCompleteFormList(String unique_mother_id) {
        res = interactor.getChildNo(unique_mother_id);
        c = res.getCount();
        //check child count
        if (res != null && res.moveToFirst()) {
            do {
                CompleteFormQnA childObj = new CompleteFormQnA();
                for (int i = 0; i < 4; i++) {
                    childObj.setUnique_id(res.getString(res.getColumnIndex("unique_id")));
                    childObj.setChildNAme(res.getString(res.getColumnIndex("name")));
                    childNumber.add(childObj);
                }
                childIdList.add(childObj);

            } while (res.moveToNext());
        }

        //get forms
        res = interactor.getCompleteFormList(unique_mother_id);
        //to display forms 1 to 5
        if (res != null & res.moveToFirst()) {
            do {
                CompleteFormQnA obj = new CompleteFormQnA();
                obj.setFormName(res.getString(res.getColumnIndex("visit_name")));
                obj.setForm_id(res.getInt(res.getColumnIndex("form_id")));
                int formId = res.getInt(res.getColumnIndex("form_id"));
                if ((formId >= 1 && formId <= 5)) {
                    formDetails.add(obj);
                }
            } while (res.moveToNext());
        }
        //to display forms 6 to 9
        //to display child forms depending on children number
        while (count < childIdList.size()) {
            res = interactor.getCompleteFormList(childIdList.get(count).getUnique_id());
            //to display forms 6 to 9
            if (res != null & res.moveToFirst()) {
                do {
                    CompleteFormQnA obj1 = new CompleteFormQnA();
                    obj1.setFormName(res.getString(res.getColumnIndex("visit_name")));
                    obj1.setForm_id(res.getInt(res.getColumnIndex("form_id")));
                    int formId1 = res.getInt(res.getColumnIndex("form_id"));
                    formName = res.getString(res.getColumnIndex("visit_name"));
                    if (formId1 >= 6 && formId1 <= 9) {
                        formDetails.add(obj1);
                    }

                } while (res.moveToNext());
            }
            count++;
        }

        //to display form 10
        res = interactor.getCompleteFormList(unique_mother_id);
        if (res != null & res.moveToFirst()) {
            do {
                CompleteFormQnA obj1 = new CompleteFormQnA();
                obj1.setFormName(res.getString(res.getColumnIndex("visit_name")));
                obj1.setForm_id(res.getInt(res.getColumnIndex("form_id")));
                int formId1 = res.getInt(res.getColumnIndex("form_id"));
                if (formId1 == 10) {
                    formDetails.add(obj1);
                }
            } while (res.moveToNext());
        }


        if (!formDetails.isEmpty() || !childNumber.isEmpty()) {
            view.getData(formDetails, childNumber);
        }

    }//end of getCompleteFormList()

}
