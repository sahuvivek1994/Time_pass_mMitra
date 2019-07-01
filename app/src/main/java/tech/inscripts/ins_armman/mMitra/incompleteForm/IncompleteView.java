package tech.inscripts.ins_armman.mMitra.incompleteForm;

import android.content.Context;

import com.inscripts.ins_armman.npdsf.data.model.IncompleteFiledForm;

import java.util.List;

/**
 * @author Aniket & Vivek  Created on 4/9/2018
 */
public interface IncompleteView {

    void setAdapter(List<IncompleteFiledForm> mWomenList);

    Context getContext();

    void openActivity(String uniqueId, int form_id, String noOfChild, String childCounter);
}
