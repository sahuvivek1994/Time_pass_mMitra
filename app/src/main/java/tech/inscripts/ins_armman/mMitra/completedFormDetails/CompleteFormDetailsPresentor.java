package tech.inscripts.ins_armman.mMitra.completedFormDetails;

import android.database.Cursor;

import com.inscripts.ins_armman.npdsf.data.model.CompleteFormQnA;

import java.util.ArrayList;

public class CompleteFormDetailsPresentor implements ICompleteFormDetailsPresentor<CompletedFormDetails> {
    ICompleteFormDetails completeFormView;
    CompleteFormDetailsInteractor interactor;
    ArrayList<CompleteFormQnA> formDetails = new ArrayList<>();
    ArrayList<CompleteFormQnA> childFormId = new ArrayList<>();

    @Override
    public void attachView(CompletedFormDetails completeFormView) {
        this.completeFormView = completeFormView;
        this.interactor = new CompleteFormDetailsInteractor(completeFormView.getContext());
    }

    @Override
    public void detch() {
        completeFormView = null;
    }

    /**
     * This method is for getting the form details of mother or child depending on the form_id.
     * if the requested unique_id is of mother and form_id is between 1 to 5 or 10 the the mother form details are fetched from database.
     * if the requested unique_id is of child and form_id is from 6 to 9 then child form details are fetched from database.
     *
     * @param unique_id=unique id of mother or child depending on the form_id
     * @param form_id=form_id  of mother or child.
     */
    @Override
    public void displayFIlledForm(String unique_id, int form_id) {
      if (form_id == 6) {
            Cursor cur = interactor.displayForm6Details(unique_id, form_id);
            if (cur != null && cur.moveToFirst()) {
                do {
                    CompleteFormQnA completeFormQnA = new CompleteFormQnA();
                    completeFormQnA.setQuestion(cur.getString(cur.getColumnIndex("question_label")));
                    completeFormQnA.setAnswer(cur.getString(cur.getColumnIndex("answer_keyword")));
                    String que = cur.getString(cur.getColumnIndex("question_label"));
                    String ans = cur.getString(cur.getColumnIndex("answer_keyword"));
                    formDetails.add(completeFormQnA);
                    System.out.println("question :" + que + "\n" + "answer :" + ans);
                } while (cur.moveToNext());
            }
        } else {
            Cursor cur = interactor.displayFormDetails(unique_id, form_id);
            if (cur != null && cur.moveToFirst()) {
                do {
                    CompleteFormQnA completeFormQnA = new CompleteFormQnA();
                    String que=cur.getString(cur.getColumnIndex("question_label"));
                    if(que==null){
                        que=cur.getString(cur.getColumnIndex("question_keyword"));
                        completeFormQnA.setQuestion(que);
                    }
                    else
                    {
                        completeFormQnA.setQuestion(cur.getString(cur.getColumnIndex("question_label")));
                    }
                    String ans= cur.getString(cur.getColumnIndex("option_label"));
                    if(ans==null) {
                        ans= cur.getString(cur.getColumnIndex("answer_keyword"));
                        completeFormQnA.setAnswer(ans);
                    }
                    else{
                        completeFormQnA.setAnswer(cur.getString(cur.getColumnIndex("option_label")));
                    }
                    formDetails.add(completeFormQnA);
                    System.out.println("question :" + que + "\n" + "answer :" + ans);
                } while (cur.moveToNext());
            }
        }

        if (!formDetails.isEmpty()) {
            completeFormView.getFormdetails(formDetails);
        }

    }


}
