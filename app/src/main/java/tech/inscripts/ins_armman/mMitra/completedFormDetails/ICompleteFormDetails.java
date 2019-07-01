package tech.inscripts.ins_armman.mMitra.completedFormDetails;

import android.content.Context;

import com.inscripts.ins_armman.npdsf.data.model.CompleteFormQnA;

import java.util.ArrayList;

public interface ICompleteFormDetails {
Context getContext();
void getFormdetails(ArrayList<CompleteFormQnA> formDetails);
}
